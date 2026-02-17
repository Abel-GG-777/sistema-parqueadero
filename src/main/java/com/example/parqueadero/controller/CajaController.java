/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.controller;

import com.example.parqueadero.dto.MetricasCajaDTO;
import com.example.parqueadero.service.CajaService;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("/api/caja")
@CrossOrigin
public class CajaController {
    private final CajaService service;

    public CajaController(CajaService service) {
        this.service = service;
    }

    @GetMapping("/metricas")
    public MetricasCajaDTO obtenerMetricas() {
        return service.obtenerMetricas();
    }
}
