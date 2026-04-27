package com.calblay.catering_api.menus;

import com.calblay.catering_api.menus.enums.EstadoRegistro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    
    // Método de Spring Data para buscar por nombre (LIKE) y filtrar por estado
    @Query("SELECT m FROM Menu m WHERE " +
           "LOWER(m.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) AND " +
           "(:estado IS NULL OR m.estado = :estado)")
    List<Menu> buscarYFiltrarMenus(@Param("nombre") String nombre, @Param("estado") EstadoRegistro estado);
}