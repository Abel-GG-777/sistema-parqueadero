/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.dto;

import com.example.parqueadero.entity.Rol;

/**
 *
 * @author USUARIO
 */
public class LoginResponse {
    private Long id;
    private String usuario;
    private Rol rol;

    
    public LoginResponse(Long id, String usuario, Rol rol) {
        this.id = id;
        this.usuario = usuario;
        this.rol = rol;
    }

    
    public Long getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public Rol getRol() {
        return rol;
    }
}
