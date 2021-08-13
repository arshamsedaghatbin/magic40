package com.papra.magic.web.rest;

import com.papra.magic.repository.EshterakRepository;
import com.papra.magic.service.EshterakService;
import com.papra.magic.service.dto.EshterakDTO;
import com.papra.magic.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.papra.magic.domain.Eshterak}.
 */
@RestController
@RequestMapping("/api")
public class EshterakResource {

    private final Logger log = LoggerFactory.getLogger(EshterakResource.class);

    private static final String ENTITY_NAME = "eshterak";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EshterakService eshterakService;

    private final EshterakRepository eshterakRepository;

    public EshterakResource(EshterakService eshterakService, EshterakRepository eshterakRepository) {
        this.eshterakService = eshterakService;
        this.eshterakRepository = eshterakRepository;
    }

    /**
     * {@code POST  /eshteraks} : Create a new eshterak.
     *
     * @param eshterakDTO the eshterakDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eshterakDTO, or with status {@code 400 (Bad Request)} if the eshterak has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/eshteraks")
    public ResponseEntity<EshterakDTO> createEshterak(@RequestBody EshterakDTO eshterakDTO) throws URISyntaxException {
        log.debug("REST request to save Eshterak : {}", eshterakDTO);
        if (eshterakDTO.getId() != null) {
            throw new BadRequestAlertException("A new eshterak cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EshterakDTO result = eshterakService.save(eshterakDTO);
        return ResponseEntity
            .created(new URI("/api/eshteraks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /eshteraks/:id} : Updates an existing eshterak.
     *
     * @param id the id of the eshterakDTO to save.
     * @param eshterakDTO the eshterakDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eshterakDTO,
     * or with status {@code 400 (Bad Request)} if the eshterakDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eshterakDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/eshteraks/{id}")
    public ResponseEntity<EshterakDTO> updateEshterak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EshterakDTO eshterakDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Eshterak : {}, {}", id, eshterakDTO);
        if (eshterakDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eshterakDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eshterakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EshterakDTO result = eshterakService.save(eshterakDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eshterakDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /eshteraks/:id} : Partial updates given fields of an existing eshterak, field will ignore if it is null
     *
     * @param id the id of the eshterakDTO to save.
     * @param eshterakDTO the eshterakDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eshterakDTO,
     * or with status {@code 400 (Bad Request)} if the eshterakDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eshterakDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eshterakDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/eshteraks/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EshterakDTO> partialUpdateEshterak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EshterakDTO eshterakDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Eshterak partially : {}, {}", id, eshterakDTO);
        if (eshterakDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eshterakDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eshterakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EshterakDTO> result = eshterakService.partialUpdate(eshterakDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eshterakDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /eshteraks} : get all the eshteraks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eshteraks in body.
     */
    @GetMapping("/eshteraks")
    public ResponseEntity<List<EshterakDTO>> getAllEshteraks(Pageable pageable) {
        log.debug("REST request to get a page of Eshteraks");
        Page<EshterakDTO> page = eshterakService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /eshteraks/:id} : get the "id" eshterak.
     *
     * @param id the id of the eshterakDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eshterakDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/eshteraks/{id}")
    public ResponseEntity<EshterakDTO> getEshterak(@PathVariable Long id) {
        log.debug("REST request to get Eshterak : {}", id);
        Optional<EshterakDTO> eshterakDTO = eshterakService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eshterakDTO);
    }

    /**
     * {@code DELETE  /eshteraks/:id} : delete the "id" eshterak.
     *
     * @param id the id of the eshterakDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/eshteraks/{id}")
    public ResponseEntity<Void> deleteEshterak(@PathVariable Long id) {
        log.debug("REST request to delete Eshterak : {}", id);
        eshterakService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
