package com.example.backend.core.service_Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.backend.core.core_repository.IUserCoreRepository;
import com.example.backend.core.model.User;
import com.example.backend.core.req_model.ChangePasswordRequest;
import com.example.backend.core.service.IUserService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final IUserCoreRepository coreRepository;

    @Override
    public Page<User> getAll(Pageable pageable) {
        return coreRepository.getAll(pageable);

    }

    @Override
    public User getById(Long id) {
        return coreRepository.findById(id);
    }

    @Override
    public User update(Long id, User user) {
        return coreRepository.update(id, user);
    }

    @Override
    public void delete(Long id) {
        coreRepository.deleteById(id);
    }

    @Override
    public User getByUserName(String username) {
        return coreRepository.findByUserName(username);
    }

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        coreRepository.save(user);
    }

}
