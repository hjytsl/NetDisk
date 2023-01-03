package com.example.net_disk.Util.CheckUtil;

public class PasswordCheckUtil {
    private boolean digitCheck(String password){  //用户名位数检查
        int digit = password.length();
        if (digit < 3 || digit > 10){
            return false;
        }
        else return true;
    }

    private boolean isNull(String password){
        if (password.equals("")){
            return true;
        }
        else return false;
    }

    public boolean PasswordCheck(String password){
        if (!isNull(password)){
            if (digitCheck(password)){
                return true;
            }
            else return false;
        }
        else return false;
    }
}
