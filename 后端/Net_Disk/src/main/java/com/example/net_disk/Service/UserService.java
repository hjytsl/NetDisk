package com.example.net_disk.Service;

import com.example.net_disk.Entity.User;

public interface UserService {
    User getUserByUsernameAndPassword(Long username, String password);
    User getUserByUsername(String username);
    void insertUser(User user);
}
