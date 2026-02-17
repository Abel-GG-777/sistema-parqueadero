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
@Table(
        name = "espacios",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"codigo", "piso_id"})
        }
)
public class Espacio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String codigo; // A1, A2, A3...

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoEspacio estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "piso_id", nullable = false)
    private Piso piso;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = EstadoEspacio.LIBRE;
        }
    }

    

    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public EstadoEspacio getEstado() {
        return estado;
    }

    public Piso getPiso() {
        return piso;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setEstado(EstadoEspacio estado) {
        this.estado = estado;
    }

    public void setPiso(Piso piso) {
        this.piso = piso;
    }

}
