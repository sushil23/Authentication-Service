package com.sushil.authentication.services;

import com.sushil.authentication.exceptions.IncorrectSessionDetailsException;
import com.sushil.authentication.exceptions.IncorrectUserIdOrPasswordException;
import com.sushil.authentication.exceptions.MaximumLoginLimitException;
import com.sushil.authentication.models.Session;
import com.sushil.authentication.models.User;
import com.sushil.authentication.repositories.SessionRepository;
import com.sushil.authentication.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthServiceStrategy implements AuthService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public AuthServiceStrategy(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    private char getRandomCharacter(int low, int high) {
        Random random = new Random();
        int randomNumber = random.nextInt(high - low + 1) + low;
        return (char)randomNumber;
    }

    private String getRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= length; i++) {
            char randomChar = getRandomCharacter(65, 122);
            sb.append(randomChar);
        }

        return sb.toString();
    }
    @Override
    public Optional<String> login(String email, String password) throws MaximumLoginLimitException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        User user = userOptional.get();
        if (!user.getPassword().equals(password)) {
            return Optional.empty();
        }

        List<Session> sessions = user.getSessions();
        if (sessions.size() == 2) {
            throw new MaximumLoginLimitException("Maximum login limit reached");
        }

        String sessionToken = user.getId() + "_" + getRandomString(20);
        Session sessionToSave = new Session();
        sessionToSave.setUser(user);
        sessionToSave.setToken(sessionToken);
        Session savedSession = sessionRepository.save(sessionToSave);
        return Optional.of(sessionToken);
    }

    @Override
    public boolean validate(String token) throws IncorrectSessionDetailsException {
        if (token.startsWith("Bearer ")) {
            String[] tokenParts = token.split(" ");
            if (tokenParts.length == 2 && tokenParts[1].contains("_")) {
                long userId = Long.parseLong(tokenParts[1].split("_")[0]);
                Optional<Session> sessionOptional = sessionRepository.findByToken(tokenParts[1]);
                if (sessionOptional.isEmpty()) {
                    throw new IncorrectSessionDetailsException("Incorrect session details provided");
                }

                Session session = sessionOptional.get();
                User user = session.getUser();
                if (user.getId() == userId) {
                    return true;
                } else {
                    throw new IncorrectSessionDetailsException("Incorrect session details provided");
                }
            } else {
                throw new IncorrectSessionDetailsException("Incorrect session details provided");
            }
        } else {
            throw new IncorrectSessionDetailsException("Incorrect session details provided");
        }
    }
}
