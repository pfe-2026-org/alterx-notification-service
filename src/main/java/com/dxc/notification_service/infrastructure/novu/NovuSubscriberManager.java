package com.dxc.notification_service.infrastructure.novu;

import com.dxc.notification_service.domain.notification.ports.ISubscriberManager;
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
public class NovuSubscriberManager implements ISubscriberManager {

    private final RestTemplate restTemplate;

    @Value("${novu.api.url}")
    private String novuApiUrl;

    @Value("${novu.api.key}")
    private String novuApiKey;

    @Override
    public void upsertSubscriber(String subscriberId, String email,
                                 String firstName, String lastName) {
        try {

            String url = novuApiUrl + "/v1/subscribers";

            Map<String, Object> body = Map.of(
                    "subscriberId", subscriberId,
                    "email", email,
                    "firstName", firstName,
                    "lastName", lastName
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "ApiKey " + novuApiKey);

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(body, headers);


            restTemplate.postForEntity(url, request, String.class);

            log.info("Subscriber Novu OK: {} — {}", subscriberId, email);

        } catch (Exception e) {
            log.error("Subscriber Novu ERREUR {}: {}", subscriberId, e.getMessage());
        }
    }
}