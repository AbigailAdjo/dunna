package com.nawoagency.dunna.repository.search;

import com.nawoagency.dunna.domain.UserExtended;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link UserExtended} entity.
 */
public interface UserExtendedSearchRepository extends ElasticsearchRepository<UserExtended, Long> {
}
