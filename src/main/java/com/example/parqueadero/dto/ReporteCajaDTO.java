/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.dto;

import java.time.LocalDate;

/**
 *
 * @author USUARIO
 */
public class ReporteCajaDTO {

    private LocalDate fecha;
    private double total;

    public ReporteCajaDTO(LocalDate fecha, double total) {
        this.fecha = fecha;
        this.total = total;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public double getTotal() {
        return total;
    }
    
    
}
