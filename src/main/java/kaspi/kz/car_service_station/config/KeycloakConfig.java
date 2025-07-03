package kaspi.kz.car_service_station.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {
    @Value("${keycloak.url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.clientId}")
    private String clientId;

    @Value("${keycloak.clientSecret}")
    private String clientSecret;

    @Value("${keycloak.credential}")
    private String credentials;

    @Bean
    public Keycloak keycloakBuilder() {
        return KeycloakBuilder.builder()
                        .clientSecret(clientSecret)
                .clientId(clientId)
                .realm(realm)
                .grantType(credentials)
                .serverUrl(keycloakUrl)
                .build();
    }
}
