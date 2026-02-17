/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.service;

import com.example.parqueadero.dto.CalculoPagoResponse;
import com.example.parqueadero.dto.ReporteCajaDTO;
import com.example.parqueadero.dto.ReporteIngresoDTO;
import com.example.parqueadero.dto.ReporteSalidaDTO;
import com.example.parqueadero.dto.VehiculoDTO;
import com.example.parqueadero.entity.*;
import com.example.parqueadero.repository.EspacioRepository;
import com.example.parqueadero.repository.VehiculoRepository;
import jakarta.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USUARIO
 */
@Service
public class VehiculoService {

    private final VehiculoRepository repository;
    private final EspacioRepository espacioRepository;
    private final TarifaService tarifaService;

    public VehiculoService(VehiculoRepository repository, EspacioRepository espacioRepository, TarifaService tarifaService) {
        this.repository = repository;
        this.espacioRepository = espacioRepository;
        this.tarifaService = tarifaService;
    }

    public Vehiculo ingresar(Vehiculo vehiculo) {
        vehiculo.setFechaIngreso(LocalDateTime.now());
        vehiculo.setEstado("ACTIVO");
        vehiculo.setValorPagado(0.0);
        return repository.save(vehiculo);
    }

    public List<VehiculoDTO> listarDTO() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public VehiculoDTO obtenerDTOPorId(Long id) {
        Vehiculo vehiculo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        return toDTO(vehiculo);
    }

    @Transactional
    public void asignarEspacio(Long vehiculoId, Long espacioId) {
        Vehiculo vehiculo = repository.findById(vehiculoId)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        if (!"ACTIVO".equals(vehiculo.getEstado())) {
            throw new RuntimeException("No se puede asignar espacio a un vehículo inactivo");
        }

        Espacio espacio = espacioRepository.findById(espacioId)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado"));

        if (espacio.getEstado() == EstadoEspacio.OCUPADO) {
            throw new RuntimeException("Espacio ocupado");
        }

        espacio.setEstado(EstadoEspacio.OCUPADO);
        vehiculo.setEspacio(espacio);
    }

    public void retirarConMonto(Long id, Double montoFinal) {

        Vehiculo vehiculo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        vehiculo.setFechaSalida(LocalDateTime.now());
        vehiculo.setValorPagado(montoFinal);

        Espacio espacio = vehiculo.getEspacio();
        if (espacio != null) {
            espacio.setEstado(EstadoEspacio.LIBRE);
            vehiculo.setEspacio(null);
        }

        vehiculo.setEstado("INACTIVO");

        repository.save(vehiculo);
    }

    public CalculoPagoResponse calcularPago(Long id) {

        Vehiculo vehiculo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        LocalDateTime ingreso = vehiculo.getFechaIngreso();
        LocalDateTime salida;

        //Para detener el calculo cuando el vehiculo esta egresado
        if ("INACTIVO".equals(vehiculo.getEstado())
                && vehiculo.getFechaSalida() != null) {
            salida = vehiculo.getFechaSalida();
        } else {
            salida = LocalDateTime.now();
        }

        double total = calcularValorPagado(vehiculo, ingreso, salida);

        List<TramoDetalle> tramos = calcularTramos(vehiculo, ingreso, salida);

        StringBuilder tarifarioTexto = new StringBuilder();

        for (TramoDetalle tramo : tramos) {

            double subtotal = (tramo.segundos / 3600.0) * tramo.costoPorHora;
            long minutos = tramo.segundos / 60;

            tarifarioTexto.append(
                    tramo.horario + " (S/ "
                    + String.format("%.2f", tramo.costoPorHora)
                    + "/h) → "
                    + minutos + " min → S/ "
                    + String.format("%.2f", subtotal)
                    + "\n"
            );
        }

        Duration duracion = Duration.between(ingreso, salida);
        long horas = duracion.toHours();
        long minutos = duracion.toMinutes() % 60;
        long segundos = duracion.getSeconds() % 60;

        String tiempoTexto;
        if (horas > 0) {
            tiempoTexto = horas + " h " + minutos + " min " + segundos + " seg";
        } else {
            tiempoTexto = minutos + " min " + segundos + " seg";
        }

        return new CalculoPagoResponse(
                total,
                tarifarioTexto.toString(),
                tiempoTexto
        );
    }

    private double calcularValorPagado(
            Vehiculo vehiculo,
            LocalDateTime ingreso,
            LocalDateTime salida) {

        double total = 0.0;
        TipoDia tipoDia = TipoDia.NORMAL;

        LocalDateTime actual = ingreso;

        while (actual.isBefore(salida)) {

            Horario horarioActual = determinarHorario(actual);

            LocalDateTime siguienteCambio = obtenerSiguienteCambioHorario(actual);

            LocalDateTime finTramo
                    = siguienteCambio.isBefore(salida) ? siguienteCambio : salida;

            long segundos = Duration.between(actual, finTramo).getSeconds();

            Tarifa tarifa = tarifaService.obtenerTarifa(
                    vehiculo.getTipoVehiculo(),
                    horarioActual,
                    tipoDia
            );

            total += (segundos / 3600.0) * tarifa.getCostoPorHora();

            actual = finTramo;
        }

        return Math.round(total * 100.0) / 100.0;
    }

    private Horario determinarHorario(LocalDateTime fecha) {
        int hora = fecha.getHour();
        return (hora >= 8 && hora < 20) ? Horario.DIA : Horario.NOCHE;
    }

    private LocalDateTime obtenerSiguienteCambioHorario(LocalDateTime fecha) {

        if (fecha.getHour() >= 8 && fecha.getHour() < 20) {
            return fecha.withHour(20).withMinute(0).withSecond(0);
        } else {
            return fecha.plusDays(fecha.getHour() >= 20 ? 1 : 0)
                    .withHour(8).withMinute(0).withSecond(0);
        }
    }

    private VehiculoDTO toDTO(Vehiculo v) {
        VehiculoDTO dto = new VehiculoDTO();
        dto.id = v.getId();
        dto.placa = v.getPlaca();
        dto.tipoVehiculo = v.getTipoVehiculo();
        dto.estado = v.getEstado();
        dto.fechaIngreso = v.getFechaIngreso();
        dto.fechaSalida = v.getFechaSalida();
        dto.valorPagado = v.getValorPagado();

        if (v.getEspacio() != null) {
            dto.espacio = v.getEspacio().getCodigo();
            dto.piso = v.getEspacio().getPiso().getNombre();
        } else {
            dto.espacio = null;
            dto.piso = null;
        }

        return dto;
    }

    //CLASE AUXILIAR
    private static class TramoDetalle {

        Horario horario;
        long segundos;
        double costoPorHora;

        public TramoDetalle(Horario horario, long segundos, double costoPorHora) {
            this.horario = horario;
            this.segundos = segundos;
            this.costoPorHora = costoPorHora;
        }
    }

    private List<TramoDetalle> calcularTramos(
            Vehiculo vehiculo,
            LocalDateTime ingreso,
            LocalDateTime salida) {

        List<TramoDetalle> tramos = new ArrayList<>();
        TipoDia tipoDia = TipoDia.NORMAL;

        LocalDateTime actual = ingreso;

        while (actual.isBefore(salida)) {

            Horario horarioActual = determinarHorario(actual);
            LocalDateTime siguienteCambio = obtenerSiguienteCambioHorario(actual);
            LocalDateTime finTramo = siguienteCambio.isBefore(salida)
                    ? siguienteCambio
                    : salida;

            long segundos = Duration.between(actual, finTramo).getSeconds();

            Tarifa tarifa = tarifaService.obtenerTarifa(
                    vehiculo.getTipoVehiculo(),
                    horarioActual,
                    tipoDia
            );

            tramos.add(new TramoDetalle(
                    horarioActual,
                    segundos,
                    tarifa.getCostoPorHora()
            ));

            actual = finTramo;
        }

        return tramos;
    }

    //REPORTE
    public List<ReporteIngresoDTO> reporteIngresos(
            LocalDate inicio,
            LocalDate fin) {

        LocalDateTime inicioDT = inicio.atStartOfDay();
        LocalDateTime finDT = fin.atTime(23, 59, 59);

        // Buscamos vehículos dentro del rango
        List<Vehiculo> lista
                = repository.findByFechaIngresoBetween(inicioDT, finDT);

        return lista.stream().map(v -> {

            String estadoSalida = "NO";

            if (v.getFechaSalida() != null) {

                LocalDate fechaSalida
                        = v.getFechaSalida().toLocalDate();

                if (!fechaSalida.isBefore(inicio)
                        && !fechaSalida.isAfter(fin)) {
                    estadoSalida = "SI";
                }
            }
            
             // Convertimos a DTO
            return new ReporteIngresoDTO(
                    v.getId(),
                    v.getPlaca(),
                    v.getFechaIngreso(),
                    estadoSalida
            );
        }).toList();
    }

    public List<ReporteSalidaDTO> reporteSalidas(
            LocalDate inicio,
            LocalDate fin) {

        
        LocalDateTime inicioDT = inicio.atStartOfDay();
        LocalDateTime finDT = fin.atTime(23, 59, 59);

        // Buscamos vehículos dentro del rango
        List<Vehiculo> lista
                = repository.findByFechaSalidaBetween(inicioDT, finDT);

        // Convertimos a DTO
        return lista.stream()
                .map(v -> new ReporteSalidaDTO(
                v.getId(),
                v.getPlaca(),
                v.getFechaIngreso(),
                v.getFechaSalida(),
                v.getValorPagado()
        ))
                .toList();
    }

    public List<ReporteCajaDTO> reporteCaja(
            LocalDate inicio,
            LocalDate fin) {

        LocalDateTime inicioDT = inicio.atStartOfDay();
        LocalDateTime finDT = fin.plusDays(1).atStartOfDay();

        List<Object[]> resultados
                = repository.obtenerCajaPorDia(inicioDT, finDT);

        return resultados.stream()
                .map(r -> new ReporteCajaDTO(
                ((java.sql.Date) r[0]).toLocalDate(),
                ((Number) r[1]).doubleValue()
        ))
                .toList();
    }

}
