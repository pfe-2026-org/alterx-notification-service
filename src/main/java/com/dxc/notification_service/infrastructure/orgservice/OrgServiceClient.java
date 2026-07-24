package com.dxc.notification_service.infrastructure.orgservice;

import com.dxc.notification_service.domain.notification.ports.IOrgServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrgServiceClient implements IOrgServiceClient {

    private final OrgServiceFeignClient orgServiceFeignClient;

    @Override
    public List<String> getUserIdsByCampusAndRole(String campusId, String role) {
        try {
            var users = orgServiceFeignClient.getUsersByCampusAndRole(campusId, role);
            if (users == null) return List.of();
            return Arrays.stream(users).map(OrgServiceFeignClient.OrgUser::userId).toList();
        } catch (Exception e) {
            log.error("Erreur org-service campus {} role {}: {}", campusId, role, e.getMessage());
            return List.of();
        }
    }
}