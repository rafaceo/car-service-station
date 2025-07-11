package kaspi.kz.car_service_station.services;

import kaspi.kz.car_service_station.dto.requests.RequestCreateDto;
import kaspi.kz.car_service_station.dto.requests.RequestGetByStatusDto;
import kaspi.kz.car_service_station.dto.requests.RequestGetByUserNameDto;
import kaspi.kz.car_service_station.dto.requests.UpdateRequestDto;
import kaspi.kz.car_service_station.entity.Request;
import kaspi.kz.car_service_station.entity.Status;
import kaspi.kz.car_service_station.envs.Envs;
import kaspi.kz.car_service_station.kafka.producer.RequestStatusProducer;
import kaspi.kz.car_service_station.logger.LoggerSlf4j;
import kaspi.kz.car_service_station.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final RequestStatusProducer requestStatusProducer;
    private final Keycloak keycloak;
    private final Envs envs;



    public ResponseEntity<Object> createRequest(RequestCreateDto requestCreateDto) {
        LoggerSlf4j logger = new LoggerSlf4j("createRequest", requestCreateDto.getUsername());
        UserRepresentation user;
        try{
            logger.info("Check user exist");
            user = keycloak
                    .realm(envs.getRealm())
                    .users()
                    .searchByUsername(requestCreateDto.getUsername(),true)
                    .getFirst();
        } catch (Exception e) {
            return ResponseEntity.status(401).body("User does not exist, you can register a new one");
        }
       if (!checkUserCredentials(requestCreateDto.getUsername(), requestCreateDto.getPassword(), logger)){
           return ResponseEntity.status(401).body("Username or password incorrect, please try again");
       }

        Request request = new Request(user.getUsername(), Status.CREATED, LocalDateTime.now(),LocalDateTime.now());
        logger.info("Request created");
        requestRepository.save(request);
        logger.info("Request saved");
        return ResponseEntity.ok().body("New request succesfully created");
    }

    public ResponseEntity<Object> getRequestsByName(RequestGetByUserNameDto requestGetByUserNameDto) {
        LoggerSlf4j logger = new LoggerSlf4j("searchRequestByName", requestGetByUserNameDto.getSearchName());
        if (!requestGetByUserNameDto.getUsername().equals(envs.getEmployeeName())
                || !requestGetByUserNameDto.getPassword().equals(envs.getEmployeePassword())) {
            logger.error("Username or password incorrect, please try again");
            return ResponseEntity.status(401).body("Username or password incorrect, please try again");
        }
        UserRepresentation user;
        try{
            logger.info("Check user exist");
            user = keycloak
                    .realm(envs.getRealm())
                    .users()
                    .searchByUsername(requestGetByUserNameDto.getSearchName(), true)
                    .getFirst();
        } catch (Exception e) {
            return ResponseEntity.status(401).body("User does not exist, you can register a new one");
        }
        logger.info("Finding user's requests");
        return ResponseEntity.ok().body(requestRepository.getRequestsByUsername(user.getUsername()));
    }

    public ResponseEntity<Object> getRequestsByStatus(RequestGetByStatusDto requestGetByStatusDto) {
        LoggerSlf4j logger = new LoggerSlf4j("searchRequestByStatus", requestGetByStatusDto.getStatus().toString());
        if (!requestGetByStatusDto.getUsername().equals(envs.getEmployeeName())
                || !requestGetByStatusDto.getPassword().equals(envs.getEmployeePassword())) {
            logger.error("Username or password incorrect, please try again");
            return ResponseEntity.status(401).body("Username or password incorrect, please try again");
        }
        logger.info("Finding requests by status");
        return ResponseEntity.ok().body(requestRepository.findByStatus(requestGetByStatusDto.getStatus()));
    }

    public ResponseEntity<Object> updateRequest(UpdateRequestDto updateRequestDto) {
        LoggerSlf4j logger = new LoggerSlf4j("updateRequest", updateRequestDto.getUsername());
        if (!updateRequestDto.getUsername().equals(envs.getEmployeeName())
                || !updateRequestDto.getPassword().equals(envs.getEmployeePassword())) {
            logger.error("Username or password incorrect, please try again");
            return ResponseEntity.status(401).body("Username or password incorrect, please try again");
        }
        if(!requestRepository.existsById(updateRequestDto.getUuid())){
            logger.error("Request does not exist");
            return ResponseEntity.status(404).body("Request does not exist");
        }
        if(updateRequestDto.getStatus().equals(Status.COMPLETED)){
            logger.info("Notification: Your request has been completed, congratulations!");
        }
        logger.info("Sending update to kafka");
        requestStatusProducer.sendStatusUpdate(updateRequestDto);
        logger.info("Request updated");
        return ResponseEntity.ok().body("Request updated successfully");
    }

    private boolean checkUserCredentials(String username, String password, LoggerSlf4j logger) {
        try {
            Keycloak keycloakAuth = KeycloakBuilder.builder()
                    .serverUrl(envs.getKeycloakUrl())
                    .realm(envs.getRealm())
                    .username(username)
                    .password(password)
                    .clientId(envs.getClientId())
                    .clientSecret(envs.getClientSecret())
                    .grantType(OAuth2Constants.PASSWORD)
                    .build();

            keycloakAuth.tokenManager().getAccessToken();
            logger.info("User credentials valid");
            return true;
        } catch (Exception e) {
            logger.error("Invalid user credentials: " + e.getMessage());
            return false;
        }
    }
}
