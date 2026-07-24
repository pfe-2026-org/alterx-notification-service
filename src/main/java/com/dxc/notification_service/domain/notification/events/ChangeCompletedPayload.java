package com.dxc.notification_service.domain.notification.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

// ChangeCompletedPayload.java (mise à jour)
import java.util.List;
public record ChangeCompletedPayload(
        String eventId, String changeId, String changeKey, String title,
        String changeClientCampusId, String changeLeadId,
        List<String> teamLeadIds, List<String> recipientRoles,
        String occurredOn
) {}