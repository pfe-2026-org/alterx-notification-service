package com.dxc.notification_service.domain.notification.events;

// ActionCompletedPayload.java (nouveau)
import java.util.List;
public record ActionCompletedPayload(
        String eventId, String changeId, String changeKey, String changeTitle,
        String changeClientCampusId, String changeLeadId,
        String actionId, String actionTitle,
        List<String> teamLeadIds, List<String> recipientRoles,
        String occurredOn
) {}
