package com.techtest.test.services;

import com.techtest.test.entities.User;
import com.techtest.test.repos.AddressRepository;
import com.techtest.test.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final AddressRepository addressRepository;
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    public User createUser(String name, String username, String password) {
        User existing = userRepository.findByUsername(username);
        if (existing != null) throw new RuntimeException("There already is a user with such username");
        User u = new User(name, username, password);

        return userRepository.save(u);

    }

    public User createUser(User user) {
        addressRepository.save(user.getAddress());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User existing = userRepository.findByUsername(user.getUsername());
        if (existing != null) throw new RuntimeException("There already is a user with such username");
        return userRepository.save(user);
    }

    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
