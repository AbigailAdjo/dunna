package com.nawoagency.dunna.web.rest;

import com.nawoagency.dunna.domain.UserExtended;
import com.nawoagency.dunna.repository.UserExtendedRepository;
import com.nawoagency.dunna.repository.search.UserExtendedSearchRepository;
import com.nawoagency.dunna.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.nawoagency.dunna.domain.UserExtended}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserExtendedResource {

    private final Logger log = LoggerFactory.getLogger(UserExtendedResource.class);

    private static final String ENTITY_NAME = "userExtended";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserExtendedRepository userExtendedRepository;

    private final UserExtendedSearchRepository userExtendedSearchRepository;

    public UserExtendedResource(UserExtendedRepository userExtendedRepository, UserExtendedSearchRepository userExtendedSearchRepository) {
        this.userExtendedRepository = userExtendedRepository;
        this.userExtendedSearchRepository = userExtendedSearchRepository;
    }

    /**
     * {@code POST  /user-extendeds} : Create a new userExtended.
     *
     * @param userExtended the userExtended to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userExtended, or with status {@code 400 (Bad Request)} if the userExtended has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-extendeds")
    public ResponseEntity<UserExtended> createUserExtended(@Valid @RequestBody UserExtended userExtended) throws URISyntaxException {
        log.debug("REST request to save UserExtended : {}", userExtended);
        if (userExtended.getId() != null) {
            throw new BadRequestAlertException("A new userExtended cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserExtended result = userExtendedRepository.save(userExtended);
        userExtendedSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/user-extendeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-extendeds} : Updates an existing userExtended.
     *
     * @param userExtended the userExtended to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userExtended,
     * or with status {@code 400 (Bad Request)} if the userExtended is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userExtended couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-extendeds")
    public ResponseEntity<UserExtended> updateUserExtended(@Valid @RequestBody UserExtended userExtended) throws URISyntaxException {
        log.debug("REST request to update UserExtended : {}", userExtended);
        if (userExtended.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserExtended result = userExtendedRepository.save(userExtended);
        userExtendedSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userExtended.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-extendeds} : get all the userExtendeds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userExtendeds in body.
     */
    @GetMapping("/user-extendeds")
    public List<UserExtended> getAllUserExtendeds() {
        log.debug("REST request to get all UserExtendeds");
        return userExtendedRepository.findAll();
    }

    /**
     * {@code GET  /user-extendeds/:id} : get the "id" userExtended.
     *
     * @param id the id of the userExtended to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userExtended, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-extendeds/{id}")
    public ResponseEntity<UserExtended> getUserExtended(@PathVariable Long id) {
        log.debug("REST request to get UserExtended : {}", id);
        Optional<UserExtended> userExtended = userExtendedRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userExtended);
    }

    /**
     * {@code DELETE  /user-extendeds/:id} : delete the "id" userExtended.
     *
     * @param id the id of the userExtended to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-extendeds/{id}")
    public ResponseEntity<Void> deleteUserExtended(@PathVariable Long id) {
        log.debug("REST request to delete UserExtended : {}", id);
        userExtendedRepository.deleteById(id);
        userExtendedSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/user-extendeds?query=:query} : search for the userExtended corresponding
     * to the query.
     *
     * @param query the query of the userExtended search.
     * @return the result of the search.
     */
    @GetMapping("/_search/user-extendeds")
    public List<UserExtended> searchUserExtendeds(@RequestParam String query) {
        log.debug("REST request to search UserExtendeds for query {}", query);
        return StreamSupport
            .stream(userExtendedSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
