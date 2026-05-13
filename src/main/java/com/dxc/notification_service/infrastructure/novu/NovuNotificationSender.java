package com.dxc.notification_service.infrastructure.novu;

import com.dxc.notification_service.domain.notification.ports.INotificationSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class NovuNotificationSender implements INotificationSender {

    private final RestTemplate restTemplate;

    @Value("${novu.api.url}")
    private String novuApiUrl;

    @Value("${novu.api.key}")
    private String novuApiKey;

    @Override
    public void send(String recipientId, String templateId, Object payload) {
        try {
            Map<String, Object> body = Map.of(
                    "name", templateId,
                    "to", Map.of("subscriberId", recipientId),
                    "payload", payload
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "ApiKey " + novuApiKey);

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    novuApiUrl + "/v1/events/trigger",
                    request, String.class
            );

            log.info("Novu OK — template: {}, recipient: {}, status: {}",
                    templateId, recipientId, response.getStatusCode());

        } catch (Exception e) {
            log.error("Novu ERREUR — template: {}, recipient: {}: {}",
                    templateId, recipientId, e.getMessage());
        }
    }
}