package com.dxc.notification_service.infrastructure.orgservice;

import com.dxc.notification_service.infrastructure.userservice.FeignConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "org-service",
        url = "${org-service.url}",
        configuration = FeignConfig.class
)
public interface OrgServiceFeignClient {

    @GetMapping("/campuses/{campusId}/users")
    OrgUser[] getUsersByCampusAndRole(@PathVariable("campusId") String campusId,
                                      @RequestParam("role") String role);

    record OrgUser(@JsonProperty("userId") String userId) {}
}