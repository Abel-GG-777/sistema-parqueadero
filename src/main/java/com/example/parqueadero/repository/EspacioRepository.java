/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.repository;

import com.example.parqueadero.entity.Espacio;
import com.example.parqueadero.entity.Piso;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
/**
 *
 * @author USUARIO
 */
public interface EspacioRepository extends JpaRepository<Espacio, Long> {
    
    List<Espacio> findByPisoId(Long pisoId);

    long countByPisoId(Long pisoId);
    
    List<Espacio> findByPisoIdOrderByIdDesc(Long pisoId);
}
