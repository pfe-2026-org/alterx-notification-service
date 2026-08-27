package com.dxc.notification_service.infrastructure.userservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(
        name = "user-service",
        url = "${feign.clients.user-service.url}",
        configuration = UserServiceFeignConfig.class
)
public interface UserServiceFeignClient {

    // Vraies routes user-service : /api/v1/users/{id} et /api/v1/users/{id}/hierarchy
    // Toutes deux exigent le rôle ADMIN côté user-service.
    @GetMapping("/api/v1/users/{id}")
    UserServiceResponse getUserById(@PathVariable("id") UUID id);

    @GetMapping("/api/v1/users/{id}/hierarchy")
    HierarchyResponse getHierarchy(@PathVariable("id") UUID id);

    @JsonIgnoreProperties(ignoreUnknown = true)
    record UserServiceResponse(UUID id, String email, String firstName, String lastName) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record HierarchyResponse(TeamHierarchy hierarchy) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record TeamHierarchy(@JsonProperty("campusId") String campusId) {}
}