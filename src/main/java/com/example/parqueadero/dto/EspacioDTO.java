/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.dto;

/**
 *
 * @author USUARIO
 */
public class EspacioDTO {

    private Long id;
    private String codigo;
    private String estado;

    public EspacioDTO(Long id, String codigo, String estado) {
        this.id = id;
        this.codigo = codigo;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getEstado() {
        return estado;
    }
}
