package com.example.brokerterminal;

import com.example.brokerterminal.tcp.server.TCPServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BrokerTerminalApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(BrokerTerminalApplication.class, args);
    }

    @Bean
    public CommandLineRunner startTCPServer(TCPServer tcpServer) {
        return args -> tcpServer.start();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }
}
