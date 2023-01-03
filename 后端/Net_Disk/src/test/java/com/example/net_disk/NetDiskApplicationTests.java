package com.example.net_disk;

import com.example.net_disk.Util.CheckUtil.UsernameCheckUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NetDiskApplicationTests {

    @Test
    void contextLoads() {
        UsernameCheckUtil util = new UsernameCheckUtil();
        System.out.println(util.usernameCheck(""));
    }

}
