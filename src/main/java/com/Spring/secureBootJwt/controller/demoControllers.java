package com.Spring.secureBootJwt.controller;

import com.Spring.secureBootJwt.dto.CreateUserDTO;
import com.Spring.secureBootJwt.model.UserEntity;
import com.Spring.secureBootJwt.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo")
public class demoControllers {

    @Autowired
    private IUserService userServ;

    @GetMapping("/notSecured")
    public ResponseEntity<String> notSecured(){
        return new ResponseEntity<>("not secured!", HttpStatus.OK);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> admin(){
        return new ResponseEntity<>("you are admin!", HttpStatus.OK);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> user(){
        return new ResponseEntity<>("you are user!", HttpStatus.OK);
    }

    @GetMapping("/invited")
    @PreAuthorize("hasRole('INVITED')")
    public ResponseEntity<String> invited(){
        return new ResponseEntity<>("you are invited!", HttpStatus.OK);
    }

    @PostMapping("/userCreate")
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody CreateUserDTO user) {
        try {
            UserEntity newUser = userServ.createUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/userDelete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userServ.deleteUser(id);
            return new ResponseEntity<>("User Deleted", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
