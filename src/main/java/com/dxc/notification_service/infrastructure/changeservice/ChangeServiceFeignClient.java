package com.dxc.notification_service.infrastructure.changeservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(
        name = "change-service",
        url = "${feign.clients.change-service.url}",
        configuration = ChangeServiceFeignConfig.class
)
public interface ChangeServiceFeignClient {

    @GetMapping("/api/v1/changes/{id}")
    ChangeDetailResponse getChangeById(@PathVariable("id") UUID id);

    record ChangeDetailResponse(
            UUID id, String key, String title, String description,
            String changeClientCampusId, String status, String probability,
            String impactLevel, int riskScore, String riskLevel,
            String changeLeadId, String createdAt,
            int actionCount, int attachmentCount
    ) {}
}