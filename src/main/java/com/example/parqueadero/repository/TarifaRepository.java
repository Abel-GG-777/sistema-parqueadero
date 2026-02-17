/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.repository;

import com.example.parqueadero.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
/**
 *
 * @author USUARIO
 */
public interface  TarifaRepository extends JpaRepository<Tarifa, Long> {
    Optional<Tarifa> findByTipoVehiculoAndHorarioAndTipoDia(
        TipoVehiculo tipoVehiculo,
        Horario horario,
        TipoDia tipoDia
    );
}
