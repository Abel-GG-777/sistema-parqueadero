/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.dto;

/**
 *
 * @author USUARIO
 */
public class CalculoPagoResponse {
    private double total;
    private String tarifarioTexto;
    private String tiempoTexto;

    public CalculoPagoResponse(double total, String tarifarioTexto, String tiempoTexto) {
        this.total = total;
        this.tarifarioTexto = tarifarioTexto;
        this.tiempoTexto = tiempoTexto;
    }

    public double getTotal() {
        return total;
    }

    public String getTarifarioTexto() {
        return tarifarioTexto;
    }

    public String getTiempoTexto() {
        return tiempoTexto;
    }
}
