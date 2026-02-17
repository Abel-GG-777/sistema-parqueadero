/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;



/**
 *
 * @author USUARIO
 */
@Entity
@Table(name = "pisos")
public class Piso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre; 

    @Column(nullable = false)
    private Integer orden; // 1, 2, 3

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "piso", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Espacio> espacios;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
    }

    

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getOrden() {
        return orden;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public List<Espacio> getEspacios() {
        return espacios;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public void setEspacios(List<Espacio> espacios) {
        this.espacios = espacios;
    }
}
