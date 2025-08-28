package com.rht.santander.api.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.rht.santander.api.v1.mapper.BankingEntityApiMapper;
import com.rht.santander.exception.NotValidException;
import com.rht.santander.model.BankingEntityRequest;
import com.rht.santander.model.BankingEntityResponse;
import com.rht.santander.service.BankingEntityService;
import com.rht.santander.service.model.BankingEntity;

public class BankingEntityUpdateControllerTest {

    @InjectMocks
    private BankingEntityUpdateController controller;

    @Mock
    private BankingEntityService service;

    @Mock
    private BankingEntityApiMapper mapper;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processBankingEntity_shouldCallService() throws NotValidException {
        BankingEntityRequest request = new BankingEntityRequest();
        BankingEntity entity = new BankingEntity();

        when(mapper.toBankingEntity(request)).thenReturn(entity);

        ResponseEntity<BankingEntityResponse> response = controller.processBankingEntity(request);

        verify(service).save(entity);
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void updateBankingEntity_shouldCallService() throws NotValidException {
        BankingEntityRequest request = new BankingEntityRequest();
        BankingEntity entity = new BankingEntity();

        when(mapper.toBankingEntity(request)).thenReturn(entity);

        ResponseEntity<BankingEntityResponse> response = controller.updateBankingEntity(request);

        verify(service).update(entity);
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void deleteBankingEntity_shouldCallService() {
        String code = "123";

        ResponseEntity<BankingEntityResponse> response = controller.deleteBankingEntity(code);

        verify(service).delete(code);
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void deleteWithQuery_shouldCallService() {
        String code = "123";
        BankingEntityRequest request = new BankingEntityRequest();
        BankingEntity entity = new BankingEntity();

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(BankingEntityRequest.class))).thenReturn(ResponseEntity.ok(request));
        when(mapper.toBankingEntity(request)).thenReturn(entity);

        ResponseEntity<BankingEntityResponse> response = controller.deleteWithQuery(code);

        verify(service).delete(entity);
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
