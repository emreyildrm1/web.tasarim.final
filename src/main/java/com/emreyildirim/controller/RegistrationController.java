package com.emreyildirim.controller;

import com.emreyildirim.model.dto.RegistrationDto;
import com.emreyildirim.service.LoginRequest;
import com.emreyildirim.model.entity.RegistrationEntity;
import com.emreyildirim.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/")
public class RegistrationController {

    private final RegistrationService service;

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/get-user")
    @ResponseBody
    public RegistrationDto getUserById(@RequestParam Integer id) {
        return this.service.getUserById(id);
    }
    @GetMapping("/get-all-users")
    public String getAllUsers(Model model) {
        List<RegistrationEntity> users = this.service.getAllUsers();
        model.addAttribute("users", users);
        return "get_all_users_data";
    }
    @PostMapping("/create-user")
    @ResponseBody
    public ResponseEntity<Map<String, Boolean>> createUser(@RequestBody RegistrationDto dto) {
        boolean createResponse = this.service.createUser(dto);
        Map<String, Boolean> response = new HashMap<>();
        response.put("status", createResponse);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/update-user")
    @ResponseBody
    public boolean updateUser(@RequestBody RegistrationDto dto) {
        return this.service.createUser(dto);
    }
    @GetMapping("/delete-user")
    @ResponseBody
    public void deleteUser(@RequestParam Integer id) {
        this.service.deleteUser(id);
    }
    @PostMapping("/validate-login")
    @ResponseBody
    public ResponseEntity<Map<String, Boolean>> validateLogin(@RequestBody LoginRequest loginRequest) {
        boolean authResponse = this.service.validateLogin(loginRequest.getEmail(), loginRequest.getPassword());
        Map<String, Boolean> response = new HashMap<>();
        response.put("status", authResponse);
        return ResponseEntity.ok(response);
    }

}
