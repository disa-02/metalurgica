package com.eq02.metalurgica.repository;

import com.eq02.metalurgica.domain.MadeOf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MadeOf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MadeOfRepository extends JpaRepository<MadeOf, Long> {}
