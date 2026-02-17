/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.controller;

import com.example.parqueadero.entity.Tarifa;
import com.example.parqueadero.service.TarifaService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("/api/tarifas")
@CrossOrigin(origins = "*")
public class TarifaController {

    private final TarifaService tarifaService;

    public TarifaController(TarifaService tarifaService) {
        this.tarifaService = tarifaService;
    }

    //Listar
    @GetMapping
    public List<Tarifa> listar() {
        return tarifaService.listarTarifas();
    }

    //Registrar
    @PostMapping
    public Tarifa guardar(@RequestBody Tarifa tarifa) {
        return tarifaService.guardarTarifa(tarifa);
    }

    @GetMapping("/{id}")
    public Tarifa obtenerPorId(@PathVariable Long id) {
        return tarifaService.buscarPorId(id);
    }

    //Modificar
    @PutMapping("/{id}")
    public Tarifa actualizar(@PathVariable Long id,
            @RequestBody Tarifa tarifa) {
        return tarifaService.actualizarTarifa(id, tarifa);
    }

    //Eliminar
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        tarifaService.eliminarTarifa(id);
    }
}
