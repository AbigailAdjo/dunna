package com.nawoagency.dunna.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link PartnerSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PartnerSearchRepositoryMockConfiguration {

    @MockBean
    private PartnerSearchRepository mockPartnerSearchRepository;

}
