package com.dxc.notification_service.domain.notification.events;


import java.util.List;
public record PendingTeamLeadsReminderPayload(
        String eventId, String changeId, String changeKey, String changeTitle,
        String changeClientCampusId,
        List<PendingAssignmentItem> pendingAssignments,
        String occurredOn
) {
    public record PendingAssignmentItem(String actionId, String actionTitle, String teamLeadId) {}
}
