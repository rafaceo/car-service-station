package kaspi.kz.car_service_station.envs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Envs {
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

    @Value("${keycloak.employeeName}")
    private String employeeName;

    @Value("${keycloak.employeePassword}")
    private String employeePassword;
}
