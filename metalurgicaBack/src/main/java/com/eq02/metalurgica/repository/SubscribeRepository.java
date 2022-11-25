package com.eq02.metalurgica.repository;

import com.eq02.metalurgica.domain.Subscribe;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Subscribe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {}
