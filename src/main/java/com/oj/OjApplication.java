package com.oj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//开启数据库事务支持
@EnableTransactionManagement
@SpringBootApplication
//启用定时任务
@EnableScheduling
public class OjApplication {
    public static void main(String[] args) {
        SpringApplication.run(OjApplication.class, args);
    }

}


