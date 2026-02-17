/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.dto;

/**
 *
 * @author USUARIO
 */
public class MetricasCajaDTO {

    private double hoy;
    private double semana;
    private double mes;
    private double anio;

    public MetricasCajaDTO(double hoy, double semana, double mes, double anio) {
        this.hoy = hoy;
        this.semana = semana;
        this.mes = mes;
        this.anio = anio;
    }

    public double getHoy() {
        return hoy;
    }

    public double getSemana() {
        return semana;
    }

    public double getMes() {
        return mes;
    }

    public double getAnio() {
        return anio;
    }
}
