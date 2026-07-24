package com.dxc.notification_service.infrastructure.orgservice;

import com.dxc.notification_service.domain.notification.ports.IOrgServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
//@Component
//@Primary
public class MockOrgServiceClient implements IOrgServiceClient {

    @Override
    public List<String> getUserIdsByCampusAndRole(String campusId, String role) {
        log.info("MOCK — getUserIdsByCampusAndRole: {} / {}", campusId, role);
        if ("change_manager".equals(role)) return List.of("changemanager-id");
        if ("outsourcing_manager".equals(role)) return List.of("supplier-id");
        return List.of();
    }
}