/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.dto;

/**
 *
 * @author USUARIO
 */
public class PisoDTO {
    private Long id;
    private String nombre;
    private Integer orden;

    public PisoDTO(Long id, String nombre, Integer orden) {
        this.id = id;
        this.nombre = nombre;
        this.orden = orden;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getOrden() {
        return orden;
    }
}
