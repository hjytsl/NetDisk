package com.example.net_disk.Util.CheckUtil;

public class UsernameCheckUtil {
    private boolean digitCheck(String username){  //用户名位数检查
        int digit = username.length();
        if (digit < 3 || digit > 10){
            return false;
        }
        else return true;
    }

    private boolean isNull(String username){
        if (username.equals("")){
            return true;
        }
        else return false;
    }

    public boolean usernameCheck(String username){
        if (!isNull(username)){
            if (digitCheck(username)){
                return true;
            }
            else return false;
        }
        else return false;
    }
}
