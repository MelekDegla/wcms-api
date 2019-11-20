//package com.wecode.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
//import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
//import org.springframework.security.messaging.web.csrf.CsrfChannelInterceptor;
//
//import static org.springframework.messaging.simp.SimpMessageType.MESSAGE;
//import static org.springframework.messaging.simp.SimpMessageType.SUBSCRIBE;
//
//@Configuration
//
//public class WebsocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
//    @Override
//    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
//        messages
//                .simpSubscribeDestMatchers("/socket").permitAll()
//        .simpTypeMatchers(SUBSCRIBE,MESSAGE).permitAll();
//    }
//
//    @Override
//    protected boolean sameOriginDisabled() {
//        return true;
//    }
//
//    @Override
//    public CsrfChannelInterceptor csrfChannelInterceptor() {
//        return super.csrfChannelInterceptor();
//    }
//}
