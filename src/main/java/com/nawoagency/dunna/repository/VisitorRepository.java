package com.nawoagency.dunna.repository;

import com.nawoagency.dunna.domain.Visitor;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Visitor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {
}
