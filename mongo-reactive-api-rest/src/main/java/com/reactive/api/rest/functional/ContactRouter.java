package com.reactive.api.rest.functional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
@Configuration
public class ContactRouter {
    @Bean
    public RouterFunction<ServerResponse> routeContact(ContactHandler contactHandler){
        return RouterFunctions.route(GET("/functional/contacts/"), contactHandler::contactsList)
                .andRoute(GET("/functional/contacts/{id}"), contactHandler::getContactById)
                .andRoute(GET("/functional/contacts/email/{email}"), contactHandler::getContactByEmail)
                .andRoute(POST("/functional/contacts/"), contactHandler::insertContact)
                .andRoute(PUT("/functional/contacts/{id}"), contactHandler::updateContact)
                .andRoute(DELETE("/functional/contacts/{id}"), contactHandler::deleteContact);
    }
}
