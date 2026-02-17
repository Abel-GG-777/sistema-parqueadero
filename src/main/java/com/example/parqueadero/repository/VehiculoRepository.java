/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.repository;

import com.example.parqueadero.entity.Espacio;
import com.example.parqueadero.entity.Vehiculo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author USUARIO
 */
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    @Query("""
    SELECT COALESCE(SUM(v.valorPagado),0)
    FROM Vehiculo v
    WHERE v.estado = 'INACTIVO'
    AND v.fechaSalida BETWEEN :inicio AND :fin
    """)
    Double sumarPorRango(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin
    );

    List<Vehiculo> findByFechaIngresoBetween(
            LocalDateTime inicio,
            LocalDateTime fin
    );

    List<Vehiculo> findByFechaSalidaBetween(
            LocalDateTime inicio,
            LocalDateTime fin
    );

    @Query("""
    SELECT DATE(v.fechaSalida), SUM(v.valorPagado)
    FROM Vehiculo v
    WHERE v.estado = 'INACTIVO'
    AND v.fechaSalida >= :inicio
    AND v.fechaSalida < :fin
    GROUP BY DATE(v.fechaSalida)
    """)
    List<Object[]> obtenerCajaPorDia(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin
    );

}
