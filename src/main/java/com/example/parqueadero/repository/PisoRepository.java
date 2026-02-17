/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parqueadero.repository;

import com.example.parqueadero.entity.Piso;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author USUARIO
 */
public interface PisoRepository  extends JpaRepository<Piso, Long> {
    
}
