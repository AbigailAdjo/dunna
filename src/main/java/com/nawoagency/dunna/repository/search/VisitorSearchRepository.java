package com.nawoagency.dunna.repository.search;

import com.nawoagency.dunna.domain.Visitor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Visitor} entity.
 */
public interface VisitorSearchRepository extends ElasticsearchRepository<Visitor, Long> {
}
