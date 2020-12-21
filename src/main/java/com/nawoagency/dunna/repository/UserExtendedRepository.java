package com.nawoagency.dunna.repository;

import com.nawoagency.dunna.domain.UserExtended;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserExtended entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserExtendedRepository extends JpaRepository<UserExtended, Long> {
}
