/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.controller;

import com.example.parqueadero.dto.LoginRequest;
import com.example.parqueadero.dto.LoginResponse;
import com.example.parqueadero.entity.Usuario;
import com.example.parqueadero.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        Usuario usuario = usuarioService.autenticar(
                request.getUsuario(),
                request.getPassword()
        );

        LoginResponse response = new LoginResponse(
                usuario.getId(),
                usuario.getUsuario(),
                usuario.getRol()
        );

        return ResponseEntity.ok(response);
    }
}
