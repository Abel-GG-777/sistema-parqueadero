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
public class ReporteSalidaDTO {

    private Long id;
    private String placa;
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaSalida;
    private double monto;

    public ReporteSalidaDTO(Long id, String placa,
            LocalDateTime fechaIngreso,
            LocalDateTime fechaSalida,
            double monto) {
        this.id = id;
        this.placa = placa;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.monto = monto;
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

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public double getMonto() {
        return monto;
    }
    
    
}
