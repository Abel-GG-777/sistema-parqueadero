/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.controller;

import com.example.parqueadero.dto.PisoDTO;
import com.example.parqueadero.entity.Piso;
import com.example.parqueadero.service.PisoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("/api/pisos")
@CrossOrigin(origins = "*")
public class PisoController {

    private final PisoService pisoService;

    public PisoController(PisoService pisoService) {
        this.pisoService = pisoService;
    }

    @PostMapping
    public PisoDTO crearPiso() {
        Piso p = pisoService.crearPiso();
        return new PisoDTO(p.getId(), p.getNombre(), p.getOrden());
    }

    @GetMapping
    public List<PisoDTO> listarPisos() {
        return pisoService.listarPisos()
                .stream()
                .map(p -> new PisoDTO(
                p.getId(),
                p.getNombre(),
                p.getOrden()
        ))
                .toList();
    }

    @GetMapping("/{id}")
    public PisoDTO obtenerPiso(@PathVariable Long id) {
        Piso p = pisoService.obtenerPiso(id);
        return new PisoDTO(p.getId(), p.getNombre(), p.getOrden());
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        pisoService.eliminar(id);
    }

}
