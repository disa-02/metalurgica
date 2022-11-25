package com.eq02.metalurgica.repository;

import com.eq02.metalurgica.domain.Roow;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Roow entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoowRepository extends JpaRepository<Roow, Long> {}
