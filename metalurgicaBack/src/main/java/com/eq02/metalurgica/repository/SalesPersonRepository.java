package com.eq02.metalurgica.repository;

import com.eq02.metalurgica.domain.SalesPerson;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SalesPerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalesPersonRepository extends JpaRepository<SalesPerson, Long> {}
