package com.papra.magic.repository;

import com.papra.magic.domain.Eshterak;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Eshterak entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EshterakRepository extends JpaRepository<Eshterak, Long> {}
