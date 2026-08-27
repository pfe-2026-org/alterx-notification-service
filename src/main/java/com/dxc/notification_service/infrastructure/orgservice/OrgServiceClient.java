package com.dxc.notification_service.infrastructure.orgservice;

import com.dxc.notification_service.domain.notification.ports.IOrgServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrgServiceClient implements IOrgServiceClient {

    private final OrgServiceFeignClient orgServiceFeignClient;

    @Override
    public List<String> getUserIdsByCampusAndRole(String campusId, String role) {
        try {
            UUID campusUuid = UUID.fromString(campusId);
            var response = orgServiceFeignClient.getUsersByCampusAndRole(campusUuid, role.toUpperCase());
            if (response == null || response.content() == null) return List.of();
            return response.content().stream()
                    .map(OrgServiceFeignClient.OrgUser::id)
                    .map(UUID::toString)
                    .toList();
        } catch (IllegalArgumentException e) {
            log.error("campusId invalide (pas un UUID): {}", campusId);
            return List.of();
        } catch (Exception e) {
            log.error("Erreur org-service campus {} role {}: {}", campusId, role, e.getMessage());
            return List.of();
        }
    }
}