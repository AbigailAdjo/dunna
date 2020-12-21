package com.nawoagency.dunna.repository.search;

import com.nawoagency.dunna.domain.Partner;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Partner} entity.
 */
public interface PartnerSearchRepository extends ElasticsearchRepository<Partner, Long> {
}
