package com.dxc.notification_service.domain.notification.ports;

import java.util.List;

public interface IOrgServiceClient {
    List<String> getUserIdsByCampusAndRole(String campusId, String role);
}