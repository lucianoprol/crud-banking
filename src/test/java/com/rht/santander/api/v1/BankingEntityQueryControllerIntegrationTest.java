package com.rht.santander.api.v1;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.rht.santander.api.v1.mapper.BankingEntityApiMapper;
import com.rht.santander.service.BankingEntityService;
import com.rht.santander.service.model.BankingEntity;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class BankingEntityQueryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BankingEntityService service;

    @Autowired
    private BankingEntityApiMapper mapper;

    @Test
    void getAll_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/v1/entities")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getByCode_shouldReturnOk() throws Exception {
        BankingEntity entity = new BankingEntity();
        entity.setCode("ABC123");
        service.save(entity);

        mockMvc.perform(get("/v1/entities/{code}", "ABC123")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @Test
    void getByCode_shouldReturnNotFound_whenInvalidCode() throws Exception {
        mockMvc.perform(get("/v1/entities/{code}", "INVALID")
        		.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
