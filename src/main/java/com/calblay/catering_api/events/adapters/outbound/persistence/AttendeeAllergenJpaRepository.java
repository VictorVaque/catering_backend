package com.calblay.catering_api.events.adapters.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendeeAllergenJpaRepository
        extends JpaRepository<AttendeeAllergenEntity, AttendeeAllergenId> { }