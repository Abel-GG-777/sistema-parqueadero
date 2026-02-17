/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.service;

import com.example.parqueadero.entity.Espacio;
import com.example.parqueadero.entity.Piso;
import com.example.parqueadero.entity.EstadoEspacio;
import com.example.parqueadero.repository.EspacioRepository;
import com.example.parqueadero.repository.PisoRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USUARIO
 */
@Service
public class EspacioService {

    private final EspacioRepository espacioRepository;
    private final PisoRepository pisoRepository;

    public EspacioService(EspacioRepository espacioRepository, PisoRepository pisoRepository) {
        this.espacioRepository = espacioRepository;
        this.pisoRepository = pisoRepository;
    }

    public List<Espacio> listarEspaciosPorPiso(Long pisoId) {
        return espacioRepository.findByPisoId(pisoId);
    }

    public List<Espacio> agregarEspacios(Long pisoId, int cantidad) {
        Piso piso = pisoRepository.findById(pisoId).orElseThrow();

        long existentes = espacioRepository.countByPisoId(pisoId);
        List<Espacio> nuevos = new ArrayList<>();

        for (int i = 1; i <= cantidad; i++) {
            Espacio e = new Espacio();
            e.setCodigo("A" + (existentes + i));
            e.setEstado(EstadoEspacio.LIBRE);
            e.setPiso(piso);
            nuevos.add(e);
        }

        return espacioRepository.saveAll(nuevos);
    }

    @Transactional
    public void eliminarEspacio(Long id) {

        Espacio espacio = espacioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado"));

        if (espacio.getEstado() == EstadoEspacio.OCUPADO) {
            throw new RuntimeException("No se puede eliminar un espacio ocupado");
        }

        espacioRepository.delete(espacio);
    }

    @Transactional
    public int eliminarEspaciosCantidad(Long pisoId, int cantidad) {

        List<Espacio> espacios = espacioRepository
                .findByPisoIdOrderByIdDesc(pisoId);

        int eliminados = 0;

        for (Espacio e : espacios) {

            if (eliminados >= cantidad) {
                break;
            }

            if (e.getEstado() == EstadoEspacio.LIBRE) {
                espacioRepository.delete(e);
                eliminados++;
            }
        }

        return eliminados;
    }

}
