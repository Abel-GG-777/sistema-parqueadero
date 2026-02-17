/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.dto;

import com.example.parqueadero.entity.TipoVehiculo;
import java.time.LocalDateTime;

/**
 *
 * @author USUARIO
 */
public class VehiculoDTO {
    public Long id;
    public String placa;
    public TipoVehiculo tipoVehiculo;
    public String estado;
    public LocalDateTime fechaIngreso;
    public LocalDateTime fechaSalida;
    public Double valorPagado;

    public String piso;    
    public String espacio;  
}
