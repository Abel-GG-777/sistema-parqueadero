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
@Table(name = "tarifas")
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVehiculo tipoVehiculo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Horario horario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDia tipoDia;

    @Column(nullable = false)
    private Double costoPorHora;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
    }

    
    public Tarifa() {
    }

    

    public Long getId() {
        return id;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public TipoDia getTipoDia() {
        return tipoDia;
    }

    public void setTipoDia(TipoDia tipoDia) {
        this.tipoDia = tipoDia;
    }

    public Double getCostoPorHora() {
        return costoPorHora;
    }

    public void setCostoPorHora(Double costoPorHora) {
        this.costoPorHora = costoPorHora;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

}
