package com.emreyildirim.service;

import com.emreyildirim.bean.PasswordEncoderBean;
import com.emreyildirim.model.dto.RegistrationDto;
import com.emreyildirim.model.entity.RegistrationEntity;
import com.emreyildirim.model.repository.IRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RegistrationService {
    private final IRegistrationRepository repository;
    private final PasswordEncoderBean passwordEncoderBean;
    public RegistrationDto getUserById(Integer id) {
        Optional<RegistrationEntity> user = this.repository.findById(id);
        return user.map(RegistrationDto::entityToDto).orElse(null);
    }
    public List<RegistrationEntity> getAllUsers() {
        return this.repository.findAll();
    }
    public boolean createUser(RegistrationDto dto) {
        boolean doesUserExist = this.repository.existsByEmail(dto.getEmail());
        if (doesUserExist) {
            return false;
        } else {
            // Encoding Password
            dto.setPassword(passwordEncoderBean.passwordEncoderMethod().encode(dto.getPassword()));
            this.repository.save(RegistrationEntity.dtoToEntity(dto));
            return true;
        }
    }
    public void deleteUser(Integer id) {
        this.repository.deleteById(id);
    }
    public boolean validateLogin(String email, String password) {
        RegistrationEntity user = this.repository.findByEmail(email);

        if (user != null && comparePasswords(password, user.getPassword())) {
            System.out.println("VALIDATE LOGIN OPERATION");
            return true;
        } else {
            return false;
        }
    }
    private boolean comparePasswords(String plainPassword, String hashedPassword) {
        return passwordEncoderBean.passwordEncoderMethod().matches(plainPassword, hashedPassword);
    }
}
