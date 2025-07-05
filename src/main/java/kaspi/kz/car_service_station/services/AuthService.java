package kaspi.kz.car_service_station.services;

import jakarta.ws.rs.core.Response;
import kaspi.kz.car_service_station.dto.requests.RegistrationUserRequestDto;
import kaspi.kz.car_service_station.logger.LoggerSlf4j;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final Keycloak keycloak;
    @Value("${keycloak.realm}")
    private String realm;

    public ResponseEntity<Object> registrationUser(RegistrationUserRequestDto request) {
        LoggerSlf4j logger = new LoggerSlf4j("registrationUser", request.getUsername());

        if(isUsernameExists(request.getUsername(), logger)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
        if(isEmailExists(request.getEmail(), logger)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }
        UserRepresentation newUser = new UserRepresentation();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setEnabled(true);
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("ROLE", List.of("USER"));
        newUser.setAttributes(attributes);
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.getPassword());
        newUser.setCredentials(List.of(credential));
        credential.setTemporary(false);
        logger.info("Password user created");
        Response response = keycloak.realm(realm).users().create(newUser);
        if (response.getStatus() != 201) {
            String errorMessage = response.readEntity(String.class); // прочитать тело ошибки
            logger.error("Failed to create user in Keycloak. Status: " + response.getStatus() + ". Error: " + errorMessage);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create user in Keycloak. Status: " + response.getStatus() + ". Error: " + errorMessage);
        }
        response.close();

        logger.info("Saved in the keycloak");
        return ResponseEntity.ok("User creating successful: " + request.getUsername());
    }

    private boolean isUsernameExists(String username, LoggerSlf4j logger) {
        logger.info("Checking if user exists with username: " + username);
        try {
            List<UserRepresentation> users = keycloak
                    .realm(realm)
                    .users()
                    .searchByUsername(username, true);
            boolean exists = !users.isEmpty();
            if (exists) {
                logger.error("User with username " + username + " already exists.");
            }
            return exists;

        } catch (Exception e) {
            logger.error("Error while searching user: " + e.getMessage(), e);
            return false;
        }
    }
    private boolean isEmailExists(String email, LoggerSlf4j logger) {
        logger.info("Checking if user exists with email: " + email);
        try {
            List<UserRepresentation> emails = keycloak
                    .realm(realm)
                    .users()
                    .searchByEmail(email, true);

            boolean exists = !emails.isEmpty();
            if (exists) {
                logger.error("User with email " + email + " already exists.");
            }

            return exists;

        } catch (Exception e) {
            logger.error("Error while searching user: " + e.getMessage(), e);
            return false;
        }
    }

}
