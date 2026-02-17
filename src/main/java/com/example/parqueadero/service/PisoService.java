/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.service;

import com.example.parqueadero.entity.EstadoEspacio;
import com.example.parqueadero.entity.Piso;
import com.example.parqueadero.repository.PisoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author USUARIO
 */
@Service
public class PisoService {

    private final PisoRepository pisoRepository;

    public PisoService(PisoRepository pisoRepository) {
        this.pisoRepository = pisoRepository;
    }

    public Piso crearPiso() {
        long total = pisoRepository.count();

        Piso piso = new Piso();
        piso.setNombre("Piso " + (total + 1));
        piso.setOrden((int) (total + 1));

        return pisoRepository.save(piso);
    }

    public List<Piso> listarPisos() {
        return pisoRepository.findAll();
    }

    public Piso obtenerPiso(Long id) {
        return pisoRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void eliminar(Long id) {

        Piso piso = pisoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Piso no encontrado"));

        boolean tieneOcupados = piso.getEspacios()
                .stream()
                .anyMatch(e -> e.getEstado() == EstadoEspacio.OCUPADO);

        if (tieneOcupados) {
            throw new RuntimeException(
                    "No se puede eliminar el piso porque tiene espacios OCUPADOS"
            );
        }

        pisoRepository.delete(piso);
    }

}
