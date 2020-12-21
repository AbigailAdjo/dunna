package com.nawoagency.dunna.repository.search;

import com.nawoagency.dunna.domain.Country;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Country} entity.
 */
public interface CountrySearchRepository extends ElasticsearchRepository<Country, Long> {
}
