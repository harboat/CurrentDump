package com.github.harboat.core.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${broker.application-destination-prefix}")
    private String applicationDestinationPrefix;

    @Value("${broker.destination-prefix}")
    private String destinationPrefix;

    @Value("${broker.endpoint}")
    private String endpoint;

    @Value("${broker.relay-host}")
    private String relayHost;

    @Value("${broker.relay-port}")
    private Integer relayPort;

    @Value("${broker.client-login}")
    private String clientLogin;

    @Value("${broker.client-passcode}")
    private String clientPasscode;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry
                .enableStompBrokerRelay("/topic", "/queue")
                .setRelayHost(relayHost)
                .setRelayPort(relayPort)
                .setClientLogin("guest")
                .setClientPasscode("guest")
                .setSystemLogin("guest")
                .setSystemPasscode("guest");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
            registry.addEndpoint("/websocket")
                    .withSockJS();
    }
}
