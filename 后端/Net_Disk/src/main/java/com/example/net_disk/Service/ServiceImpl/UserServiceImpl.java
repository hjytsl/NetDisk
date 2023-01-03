package com.example.net_disk.Service.ServiceImpl;

import com.example.net_disk.Entity.User;
import com.example.net_disk.Repository.UserRepository;
import com.example.net_disk.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByUsernameAndPassword(Long username, String password) {
        return userRepository.findByIdAndPassword(username, password);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void insertUser(User user) {
        userRepository.save(user);
    }
}
