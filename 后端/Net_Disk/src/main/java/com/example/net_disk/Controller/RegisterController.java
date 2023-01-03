package com.example.net_disk.Controller;

import com.example.net_disk.Entity.User;
import com.example.net_disk.Service.UserService;
import com.example.net_disk.Util.CheckUtil.PasswordCheckUtil;
import com.example.net_disk.Util.CheckUtil.UsernameCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    private UsernameCheckUtil usernameCheckUtil = new UsernameCheckUtil();
    private PasswordCheckUtil passwordCheckUtil = new PasswordCheckUtil();

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public int register(String username, String password, String second){   //状态1：两次输入密码不一致   状态2：密码或用户名不合法   状态3：用户名已存在   状态4：创建成功
        if (!(password.equals(second))){
            return 1;
        }
        if (usernameCheckUtil.usernameCheck(username) && passwordCheckUtil.PasswordCheck(password)){
            User user =userService.getUserByUsername(username);
            if (user != null){
                return 3;
            }
            else{
                User newUser = new User();
                newUser.setUsername(username);
                newUser.setPassword(password);
                userService.insertUser(newUser);
                return 4;
            }
        }
        else return 2;
    }
}
