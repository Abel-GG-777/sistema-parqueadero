/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.service;

import com.example.parqueadero.dto.MetricasCajaDTO;
import com.example.parqueadero.repository.VehiculoRepository;
import org.springframework.stereotype.Service;

import java.time.*;
/**
 *
 * @author USUARIO
 */
@Service
public class CajaService {
    private final VehiculoRepository repository;

    public CajaService(VehiculoRepository repository) {
        this.repository = repository;
    }

    public MetricasCajaDTO obtenerMetricas() {

        LocalDateTime ahora = LocalDateTime.now();

        // HOY
        LocalDateTime inicioHoy = ahora.toLocalDate().atStartOfDay();
        LocalDateTime finHoy = inicioHoy.plusDays(1);

        double hoy = repository.sumarPorRango(inicioHoy, finHoy);

        // SEMANA
        LocalDate inicioSemanaDate =
                ahora.toLocalDate().with(DayOfWeek.MONDAY);
        LocalDateTime inicioSemana = inicioSemanaDate.atStartOfDay();
        LocalDateTime finSemana = inicioSemana.plusDays(7);

        double semana = repository.sumarPorRango(inicioSemana, finSemana);

        // MES
        LocalDate inicioMesDate =
                ahora.toLocalDate().withDayOfMonth(1);
        LocalDateTime inicioMes = inicioMesDate.atStartOfDay();
        LocalDateTime finMes = inicioMes.plusMonths(1);

        double mes = repository.sumarPorRango(inicioMes, finMes);

        // AÑO
        LocalDate inicioAnioDate =
                ahora.toLocalDate().withDayOfYear(1);
        LocalDateTime inicioAnio = inicioAnioDate.atStartOfDay();
        LocalDateTime finAnio = inicioAnio.plusYears(1);

        double anio = repository.sumarPorRango(inicioAnio, finAnio);

        return new MetricasCajaDTO(hoy, semana, mes, anio);
    }
}
