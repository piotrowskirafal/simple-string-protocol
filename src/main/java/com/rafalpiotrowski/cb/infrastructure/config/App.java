package com.rafalpiotrowski.cb.infrastructure.config;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpReceivingChannelAdapter;
import org.springframework.integration.ip.tcp.TcpSendingMessageHandler;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.integration.ip.tcp.serializer.ByteArraySingleTerminatorSerializer;

import java.io.IOException;

@EnableIntegration
@IntegrationComponentScan
@Configuration
@ComponentScan(basePackages = "com.rafalpiotrowski.cb")
public class App {

    public static void main(String... args) throws IOException {
        SpringApplication app = new SpringApplication(App.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Bean
    public AbstractServerConnectionFactory connectionFactory() {
        TcpNetServerConnectionFactory connectionFactory = new TcpNetServerConnectionFactory(50000);
        connectionFactory.setSingleUse(false);
        connectionFactory.setSoTimeout(30_000);
        byte delimiter = 10;
        connectionFactory.setDeserializer(new ByteArraySingleTerminatorSerializer(delimiter));
        return connectionFactory;
    }

    @Bean
    public TcpReceivingChannelAdapter receiver(AbstractServerConnectionFactory connectionFactory) {
        TcpReceivingChannelAdapter adapter = new TcpReceivingChannelAdapter();
        adapter.setConnectionFactory(connectionFactory);
        adapter.setOutputChannelName(Channel.FROM_TCP);
        return adapter;
    }

    @Transformer(inputChannel = Channel.FROM_TCP, outputChannel = Channel.TO_APP)
    public String transformer(byte[] in) {
        return new String(in);
    }

    @Bean
    @ServiceActivator(inputChannel = Channel.TO_TCP)
    public TcpSendingMessageHandler sender(AbstractServerConnectionFactory connectionFactory) {
        TcpSendingMessageHandler handler = new TcpSendingMessageHandler();
        handler.setConnectionFactory(connectionFactory);
        return handler;
    }
}
