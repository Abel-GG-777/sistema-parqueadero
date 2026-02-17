/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;


/**
 *
 * @author USUARIO
 */
@Entity
@Table(name = "vehiculos")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placa;

    

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVehiculo tipoVehiculo;

    @Column(name = "valor_pagado", nullable = false)
    private Double valorPagado;

    @ManyToOne
    @JoinColumn(name = "espacio_id")
    private Espacio espacio;

    private LocalDateTime fechaIngreso;

    private LocalDateTime fechaSalida;

    // ACTIVO = dentro del parqueadero
    // INACTIVO = retirado
    private String estado;

    public Vehiculo() {
    }

    public Vehiculo(String placa,  TipoVehiculo tipoVehiculo) {
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
        this.fechaIngreso = LocalDateTime.now();
        this.estado = "ACTIVO";
        this.valorPagado = 0.0;
    }

    public Long getId() {
        return id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

 

   

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(Double valorPagado) {
        this.valorPagado = valorPagado;
    }
    
    public Espacio getEspacio() {
        return espacio;
    }

    public void setEspacio(Espacio espacio) {
        this.espacio = espacio;
    }

}
