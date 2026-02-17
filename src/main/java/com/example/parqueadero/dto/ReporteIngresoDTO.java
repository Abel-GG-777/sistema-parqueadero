/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.dto;

import java.time.LocalDateTime;

/**
 *
 * @author USUARIO
 */
public class ReporteIngresoDTO {
    private Long id;
    private String placa;
    private LocalDateTime fechaIngreso;
    private String estadoSalida;

    public ReporteIngresoDTO(Long id, String placa,
                             LocalDateTime fechaIngreso,
                             String estadoSalida) {
        this.id = id;
        this.placa = placa;
        this.fechaIngreso = fechaIngreso;
        this.estadoSalida = estadoSalida;
    }

    public Long getId() {
        return id;
    }

    public String getPlaca() {
        return placa;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public String getEstadoSalida() {
        return estadoSalida;
    }
    
    
}
