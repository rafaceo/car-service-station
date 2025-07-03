package kaspi.kz.car_service_station.controllers;

import kaspi.kz.car_service_station.dto.requests.RegistrationUserRequestDto;
import kaspi.kz.car_service_station.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<Object> userRegistrationResponse(@RequestBody RegistrationUserRequestDto request) {
        return authService.registrationUser(request);
    }
}
