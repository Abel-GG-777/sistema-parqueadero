/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.service;

import com.example.parqueadero.entity.Usuario;
import com.example.parqueadero.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author USUARIO
 */
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario autenticar(String usuario, String password) {
        return usuarioRepository
                .findByUsuarioAndPassword(usuario, password)
                .orElseThrow(() ->
                        new RuntimeException("Credenciales incorrectas")
                );
    }
}
