package cn.edu.neu.shop.pin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("cn.edu.neu.shop.pin.mapper")
@EnableScheduling
public class PinApplication {

    public static void main(String[] args) {
        SpringApplication.run(PinApplication.class, args);
    }

}
