package com.papra.magic.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EshterakMapperTest {

    private EshterakMapper eshterakMapper;

    @BeforeEach
    public void setUp() {
        eshterakMapper = new EshterakMapperImpl();
    }
}
