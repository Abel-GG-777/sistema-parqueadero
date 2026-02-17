/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.controller;

import com.example.parqueadero.dto.CalculoPagoResponse;
import com.example.parqueadero.dto.ReporteCajaDTO;
import com.example.parqueadero.dto.ReporteIngresoDTO;
import com.example.parqueadero.dto.ReporteSalidaDTO;
import com.example.parqueadero.dto.VehiculoDTO;
import com.example.parqueadero.entity.Vehiculo;
import com.example.parqueadero.service.VehiculoService;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin
public class VehiculoController {

    private final VehiculoService service;

    public VehiculoController(VehiculoService service) {
        this.service = service;
    }

    @PostMapping
    public Vehiculo ingresar(@RequestBody Vehiculo vehiculo) {
        return service.ingresar(vehiculo);
    }

    @GetMapping
    public List<VehiculoDTO> listar() {
        return service.listarDTO();
    }

    @GetMapping("/{id}")
    public VehiculoDTO obtenerPorId(@PathVariable Long id) {
        return service.obtenerDTOPorId(id);
    }

    @PutMapping("/retirar/{id}")
    public void retirar(
            @PathVariable Long id,
            @RequestParam Double monto
    ) {
        service.retirarConMonto(id, monto);
    }

    @GetMapping("/{id}/calculo")
    public CalculoPagoResponse calcular(@PathVariable Long id) {
        return service.calcularPago(id);
    }

    @PutMapping("/{vehiculoId}/espacio/{espacioId}")
    public void asignarEspacio(
            @PathVariable Long vehiculoId,
            @PathVariable Long espacioId
    ) {
        service.asignarEspacio(vehiculoId, espacioId);
    }

    
    
    @GetMapping("/reportes/ingresos")
    public List<ReporteIngresoDTO> ingresos(
            @RequestParam String inicio,
            @RequestParam String fin) {

        return service.reporteIngresos(
                LocalDate.parse(inicio),
                LocalDate.parse(fin)
        );
    }

    @GetMapping("/reportes/salidas")
    public List<ReporteSalidaDTO> salidas(
            @RequestParam String inicio,
            @RequestParam String fin) {

        return service.reporteSalidas(
                LocalDate.parse(inicio),
                LocalDate.parse(fin)
        );
    }

    @GetMapping("/reportes/caja")
    public List<ReporteCajaDTO> caja(
            @RequestParam String inicio,
            @RequestParam String fin) {

        return service.reporteCaja(
                LocalDate.parse(inicio),
                LocalDate.parse(fin)
        );
    }

}
