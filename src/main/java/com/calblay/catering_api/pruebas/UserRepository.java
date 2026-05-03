package com.calblay.catering_api.pruebas;

import org.springframework.data.jpa.repository.JpaRepository; // Faltaba esta
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
