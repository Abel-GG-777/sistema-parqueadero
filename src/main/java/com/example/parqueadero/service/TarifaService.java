/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.service;

import com.example.parqueadero.entity.*;
import com.example.parqueadero.repository.TarifaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author USUARIO
 */
@Service
public class TarifaService {

    private final TarifaRepository tarifaRepository;

    public TarifaService(TarifaRepository tarifaRepository) {
        this.tarifaRepository = tarifaRepository;
    }

    public Tarifa obtenerTarifa(TipoVehiculo tipoVehiculo,
            Horario horario,
            TipoDia tipoDia) {

        return tarifaRepository
                .findByTipoVehiculoAndHorarioAndTipoDia(
                        tipoVehiculo, horario, tipoDia)
                .orElseThrow(()
                        -> new RuntimeException("Tarifa no encontrada"));
    }

    public List<Tarifa> listarTarifas() {
        return tarifaRepository.findAll();
    }

    public Tarifa guardarTarifa(Tarifa tarifa) {
        return tarifaRepository.save(tarifa);
    }

    public Tarifa buscarPorId(Long id) {
        return tarifaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarifa no encontrada"));
    }

    public Tarifa actualizarTarifa(Long id, Tarifa nuevaTarifa) {

        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarifa no encontrada"));

        tarifa.setTipoVehiculo(nuevaTarifa.getTipoVehiculo());
        tarifa.setHorario(nuevaTarifa.getHorario());
        tarifa.setTipoDia(nuevaTarifa.getTipoDia());
        tarifa.setCostoPorHora(nuevaTarifa.getCostoPorHora());

        return tarifaRepository.save(tarifa);
    }

    public void eliminarTarifa(Long id) {

        if (!tarifaRepository.existsById(id)) {
            throw new RuntimeException("Tarifa no encontrada");
        }

        tarifaRepository.deleteById(id);
    }

}
