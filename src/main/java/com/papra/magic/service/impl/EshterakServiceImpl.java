package com.papra.magic.service.impl;

import com.papra.magic.domain.Eshterak;
import com.papra.magic.repository.EshterakRepository;
import com.papra.magic.service.EshterakService;
import com.papra.magic.service.dto.EshterakDTO;
import com.papra.magic.service.mapper.EshterakMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Eshterak}.
 */
@Service
@Transactional
public class EshterakServiceImpl implements EshterakService {

    private final Logger log = LoggerFactory.getLogger(EshterakServiceImpl.class);

    private final EshterakRepository eshterakRepository;

    private final EshterakMapper eshterakMapper;

    public EshterakServiceImpl(EshterakRepository eshterakRepository, EshterakMapper eshterakMapper) {
        this.eshterakRepository = eshterakRepository;
        this.eshterakMapper = eshterakMapper;
    }

    @Override
    public EshterakDTO save(EshterakDTO eshterakDTO) {
        log.debug("Request to save Eshterak : {}", eshterakDTO);
        Eshterak eshterak = eshterakMapper.toEntity(eshterakDTO);
        eshterak = eshterakRepository.save(eshterak);
        return eshterakMapper.toDto(eshterak);
    }

    @Override
    public Optional<EshterakDTO> partialUpdate(EshterakDTO eshterakDTO) {
        log.debug("Request to partially update Eshterak : {}", eshterakDTO);

        return eshterakRepository
            .findById(eshterakDTO.getId())
            .map(
                existingEshterak -> {
                    eshterakMapper.partialUpdate(existingEshterak, eshterakDTO);

                    return existingEshterak;
                }
            )
            .map(eshterakRepository::save)
            .map(eshterakMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EshterakDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Eshteraks");
        return eshterakRepository.findAll(pageable).map(eshterakMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EshterakDTO> findOne(Long id) {
        log.debug("Request to get Eshterak : {}", id);
        return eshterakRepository.findById(id).map(eshterakMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Eshterak : {}", id);
        eshterakRepository.deleteById(id);
    }
}
