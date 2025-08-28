package com.rht.santander.api.v1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rht.santander.api.v1.mapper.BankingEntityApiMapper;
import com.rht.santander.model.BankingEntityRequest;
import com.rht.santander.repository.BankingEntityRepository;
import com.rht.santander.service.BankingEntityService;
import com.rht.santander.service.model.BankingEntity;
import com.rht.santander.utils.DataUtils;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class BankingEntityUpdateControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BankingEntityService service;

    @Autowired
    private BankingEntityRepository repository;
    
    @Autowired
    private BankingEntityApiMapper mapper;

    @MockitoBean
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private BankingEntityRequest request;
    private BankingEntity entity;

    @BeforeEach
    void setUp() {
    	repository.deleteAll(); // limpiamos la base antes de cada test
    	
        request = new BankingEntityRequest();
        request.setCode("150");
        request.setName("HSBC");
        request.setType("Banco");
        request.setStreetName("calle");
        request.setBuildingNumber(123);
        request.setCity("CABA");
        request.setProvince("CABA");
        request.setZipCode(1234);
        request.setPhoneNumber("4321-1234");
        
        entity = new BankingEntity();
        entity.setCode("150");
        entity.setName("HSBC");
        entity.setType("Banco");
        entity.setStreetName("calle");
        entity.setBuildingNumber(123);
        entity.setCity("CABA");
        entity.setProvince("CABA");
        entity.setZipCode(1234);
        entity.setPhoneNumber("4321-1234");
    }

    @Test
    void processBankingEntity_shouldReturnOk_whenNewBankingEntity() throws Exception {
        mockMvc.perform(post("/v1/entities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("OK"));
        
        assertThat(service.getByCode("150")).isNotNull();
    }

    @Test
    void processBankingEntity_shouldThrowNotValidException_whenBankingEntityExists() throws Exception {
    	service.save(entity);
    	
        mockMvc.perform(post("/v1/entities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.causes").value("INVALID PARAMETERS"))
                .andExpect(jsonPath("$.errors[0]").value("Exception validating request : Entity with code 150 alredy exists"));
        
        assertThat(service.getByCode("150")).isNotNull();
    }

    @Test
    void processBankingEntity_shouldThrowMethodArgumentNotValidException_whenRequestInvalid() throws Exception {
    	request.setName(null);
    	
        mockMvc.perform(post("/v1/entities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.causes").value("INVALID PARAMETERS"))
                .andExpect(jsonPath("$.errors[0]").value("name: A field is null"));
        
        assertThrows(NoSuchElementException.class, () -> service.getByCode("150"));
    }
    
    @Test
    void updateBankingEntity_shouldReturnOk_whenBankingEntityExists() throws Exception {
        service.save(entity);

        request.setName("MODIFICADO");
        
        mockMvc.perform(put("/v1/entities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("OK"));
        
        assertThat(service.getByCode("150")).isNotNull()
        .hasFieldOrPropertyWithValue("name", "MODIFICADO");
    }

    @Test
    void updateBankingEntity_shouldThrowNotValidException_whenBankingEntityNotFound() throws Exception {
        mockMvc.perform(put("/v1/entities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.causes").value("INVALID PARAMETERS"))
                .andExpect(jsonPath("$.errors[0]").value("Exception validating request : Entity with code 150 not found"));
        
        assertThrows(NoSuchElementException.class, () -> service.getByCode("150"));
    }
    
    @Test
    void deleteBankingEntity_shouldReturnOk_whenBankingEntityExists() throws Exception {
    	service.save(entity);
    	
        mockMvc.perform(delete("/v1/entities/{code}", "150"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("OK"));
        
        assertThrows(NoSuchElementException.class, () -> service.getByCode("150"));
    }
    
    @Test
    void deleteBankingEntity_shouldThrowNotValidException_whenBankingEntityNotFound() throws Exception {
        mockMvc.perform(delete("/v1/entities/{code}", "150"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.causes").value("INVALID PARAMETERS"))
                .andExpect(jsonPath("$.errors[0]").value("Exception validating request : Entity with code 150 not found"));
        
        assertThrows(NoSuchElementException.class, () -> service.getByCode("150"));
    }

    @Test
    void deleteWithQuery_shouldReturnOk_whenRestTemplateReturnsRequest() throws Exception {
    	service.save(entity);
    	
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(BankingEntityRequest.class))).thenReturn(ResponseEntity.ok(request));

        mockMvc.perform(delete("/v1/entities")
                        .param("code", "150"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("OK"));

        assertThrows(NoSuchElementException.class, () -> service.getByCode("150"));
    }


    @Test
    void deleteWithQuery_shouldThrowTechnicalException_whenRestTemplateReturnsNull() throws Exception {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(BankingEntityRequest.class))).thenReturn(null);

        mockMvc.perform(delete("/v1/entities")
                        .param("code", "150"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.causes").value("TECHNICAL ERROR. CONTACT TO ADMINISTRATOR"))
                .andExpect(jsonPath("$.errors[0]").isNotEmpty());
        
    }


    @Test
    void testDeleteWithQuery_shouldThrowNotFound_whenRestTemplateReturnsNotFound() throws Exception {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(BankingEntityRequest.class))).thenThrow(
        		new NoSuchElementException(String.format(DataUtils.EXCEPTION_VALIDATION_MSG 
								+ DataUtils.EXCEPTION_VALIDATION_ENTITY, "150")));
        
        mockMvc.perform(delete("/v1/entities")
                        .param("code", "150"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.causes").value("DATA NOT FOUND"))
                .andExpect(jsonPath("$.errors[0]").value("Exception validating request : Entity with code 150 not found"));
        
        assertThrows(NoSuchElementException.class, () -> service.getByCode("150"));
    }
}
