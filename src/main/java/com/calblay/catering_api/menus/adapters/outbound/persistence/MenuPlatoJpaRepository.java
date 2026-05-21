package com.calblay.catering_api.menus.adapters.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MenuPlatoJpaRepository extends JpaRepository<MenuPlatoEntity, Long> {
    List<MenuPlatoEntity> findByIdMenu(Long idMenu);
}
