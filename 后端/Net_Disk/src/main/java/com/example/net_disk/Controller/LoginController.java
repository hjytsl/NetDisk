package com.example.net_disk.Controller;

import com.example.net_disk.Entity.User;
import com.example.net_disk.Service.UserService;
import com.example.net_disk.Util.CheckUtil.PasswordCheckUtil;
import com.example.net_disk.Util.CheckUtil.UsernameCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private UsernameCheckUtil usernameCheckUtil = new UsernameCheckUtil();
    private PasswordCheckUtil passwordCheckUtil = new PasswordCheckUtil();

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public boolean login(Long username, String password){
//        if (usernameCheckUtil.usernameCheck(username) && passwordCheckUtil.PasswordCheck(password)){
//
//        }
//        else return false;
        User user =userService.getUserByUsernameAndPassword(username, password);
        if (user != null){
            return true;
        }
        else return false;
    }
}
