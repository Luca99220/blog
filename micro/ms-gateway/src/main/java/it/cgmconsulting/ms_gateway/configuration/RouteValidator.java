package it.cgmconsulting.ms_gateway.configuration;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {

    // v0 -> no token
    // v1 -> ADMIN
    // v2 -> WRITER
    // v3 -> MEMBER
    // v4 -> MODERATOR
    // v99 -> chiamate interne

    public boolean isOpenEndpoint(ServerHttpRequest req){
        return (req.getURI().getPath().contains("v0") || req.getURI().getPath().contains("actuator"));
    }
}
