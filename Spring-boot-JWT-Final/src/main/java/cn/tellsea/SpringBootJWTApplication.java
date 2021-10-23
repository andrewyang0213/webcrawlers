package cn.tellsea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("cn.tellsea.mapper")
public class SpringBootJWTApplication extends SpringBootServletInitializer{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootJWTApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(SpringBootJWTApplication.class, args);
    }

}
