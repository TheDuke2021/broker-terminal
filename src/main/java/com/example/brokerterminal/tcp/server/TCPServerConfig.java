package com.example.brokerterminal.tcp.server;

import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TCPServerConfig {

    @Bean
    public TCPServer tcpServer(ApplicationEventPublisher applicationEventPublisher, ApplicationArguments args) {
        String identifier = "server"; // Default
        if (args.containsOption("id"))
            identifier = args.getOptionValues("id").get(0);
        int port = 10000; // Default
        if (args.containsOption("port"))
            port = Integer.parseInt(args.getOptionValues("port").get(0));

        return new TCPServer(identifier, port, applicationEventPublisher);
    }
}
