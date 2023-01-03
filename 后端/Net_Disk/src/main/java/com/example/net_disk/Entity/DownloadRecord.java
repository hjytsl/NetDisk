package com.example.net_disk.Entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class DownloadRecord implements Serializable {
    @Id //主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //对于bigint类型

    @Column(nullable = false)  //字段约束
    private Long uid;

    @Column(nullable = false)  //字段约束
    private Long fid;

    @Column(nullable = false, length = 20)  //字段约束
    private String date;

    @Column(nullable = false, length = 20)  //字段约束
    private String fname;
}
