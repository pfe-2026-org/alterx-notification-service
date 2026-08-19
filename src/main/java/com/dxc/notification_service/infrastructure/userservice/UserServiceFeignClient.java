package com.dxc.notification_service.infrastructure.userservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "user-service",
        url = "${feign.clients.user-service.url}",
        configuration = UserServiceFeignConfig.class
)
public interface UserServiceFeignClient {

    @GetMapping("/users/{userId}")
    UserServiceResponse getUserById(@PathVariable("userId") String userId);

    @GetMapping("/api/users/{userId}/hierarchy")
    HierarchyResponse getHierarchy(@PathVariable("userId") String userId);

    record UserServiceResponse(String id, String email, String firstName, String lastName) {}
    record HierarchyResponse(@JsonProperty("campusId") String campusId) {}
}