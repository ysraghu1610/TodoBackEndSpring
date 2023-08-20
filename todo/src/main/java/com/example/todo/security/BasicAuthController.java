package com.example.todo.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin( origins = "http://localhost:4200/")
@RestController
public class BasicAuthController {

    @GetMapping("/basic-auth")
    public AuthenticationBean getAuthentication() {
        System.out.println("recieved request");
        return new AuthenticationBean("authenticated");
    }

}
