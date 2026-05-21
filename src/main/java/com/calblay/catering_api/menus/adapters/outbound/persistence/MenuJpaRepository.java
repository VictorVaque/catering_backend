package com.calblay.catering_api.menus.adapters.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MenuJpaRepository extends JpaRepository<MenuEntity, Long> {
    List<MenuEntity> findByEstado(String estado);
    List<MenuEntity> findByNombreContainingIgnoreCaseAndEstado(String nombre, String estado);
    Optional<MenuEntity> findByIdMenu(Long idMenu);
}
