/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.controller;

import com.example.parqueadero.dto.EspacioDTO;
import com.example.parqueadero.service.EspacioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("/api/pisos/{pisoId}/espacios")
@CrossOrigin(origins = "*")
public class EspacioController {

    private final EspacioService espacioService;

    public EspacioController(EspacioService espacioService) {
        this.espacioService = espacioService;
    }

    @GetMapping
    public List<EspacioDTO> listarEspacios(@PathVariable Long pisoId) {
        return espacioService.listarEspaciosPorPiso(pisoId)
                .stream()
                .map(e -> new EspacioDTO(
                e.getId(),
                e.getCodigo(),
                e.getEstado().name()
        ))
                .toList();
    }

    @PostMapping
    public List<EspacioDTO> agregarEspacios(
            @PathVariable Long pisoId,
            @RequestParam int cantidad
    ) {
        return espacioService.agregarEspacios(pisoId, cantidad)
                .stream()
                .map(e -> new EspacioDTO(
                e.getId(),
                e.getCodigo(),
                e.getEstado().name()
        ))
                .toList();
    }

    @DeleteMapping("/{id}")
    public void eliminarEspacio(@PathVariable Long id) {
        espacioService.eliminarEspacio(id);
    }

    @DeleteMapping("/cantidad")
    public int eliminarCantidad(
            @PathVariable Long pisoId,
            @RequestParam int cantidad
    ) {
        return espacioService.eliminarEspaciosCantidad(pisoId, cantidad);
    }

}
