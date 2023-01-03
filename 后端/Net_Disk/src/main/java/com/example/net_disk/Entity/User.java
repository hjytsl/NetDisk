package com.example.net_disk.Entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class User implements Serializable {
    @Id //主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //对于bigint类型

    @Column(nullable = false, unique = true, length = 20)  //字段约束
    private String username;

    @Column(nullable = false, length = 20)
    private String password;
}
