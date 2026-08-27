package com.dxc.notification_service.infrastructure.orgservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(
        name = "org-service",
        url = "${feign.clients.org-service.url}",
        configuration = OrgServiceFeignConfig.class
)
public interface OrgServiceFeignClient {

    // Vraie route org-service : GET /api/v1/campuses/{campusId}/users?role=...
    // Réponse paginée, pas un tableau brut. Rôle attendu en MAJUSCULES.
    @GetMapping("/api/v1/campuses/{campusId}/users")
    UserPageResponse getUsersByCampusAndRole(@PathVariable("campusId") UUID campusId,
                                             @RequestParam("role") String role);

    @JsonIgnoreProperties(ignoreUnknown = true)
    record UserPageResponse(List<OrgUser> content) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record OrgUser(UUID id) {}
}