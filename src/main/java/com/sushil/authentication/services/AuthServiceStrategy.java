package com.sushil.authentication.services;

import com.sushil.authentication.exceptions.IncorrectSessionDetailsException;
import com.sushil.authentication.exceptions.IncorrectUserIdOrPasswordException;
import com.sushil.authentication.exceptions.MaximumLoginLimitException;
import com.sushil.authentication.exceptions.UserAlreadyExistsException;
import com.sushil.authentication.models.Session;
import com.sushil.authentication.models.SessionStatus;
import com.sushil.authentication.models.User;
import com.sushil.authentication.repositories.SessionRepository;
import com.sushil.authentication.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.MultiValueMapAdapter;

import java.util.Random;

@Service
public class AuthServiceStrategy implements AuthService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public AuthServiceStrategy(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
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

    public User signUp(User user) throws UserAlreadyExistsException {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException("User with email - " + user.getEmail() + " already exists.");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    @Override
    public Session login(String email, String password) throws IncorrectUserIdOrPasswordException, MaximumLoginLimitException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new IncorrectUserIdOrPasswordException("Incorrect email or password");
        }

        User user = userOptional.get();
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new IncorrectUserIdOrPasswordException("Incorrect email or password");
        }

        List<Session> sessions = user.getSessions();
        if (sessions.size() == 2) {
            throw new MaximumLoginLimitException("Maximum login limit reached");
        }

        String sessionToken = user.getId() + "_" + getRandomString(20);
        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add("AUTH_TOKEN", sessionToken);

        Session sessionToSave = new Session();
        sessionToSave.setUser(user);
        sessionToSave.setToken(sessionToken);
        sessionToSave.setSessionStatus(SessionStatus.ACTIVE);
        Session savedSession = sessionRepository.save(sessionToSave);

        return savedSession;
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
