package com.rht.santander.api.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.rht.santander.api.v1.mapper.BankingEntityApiMapper;
import com.rht.santander.model.BankingEntityList;
import com.rht.santander.model.BankingEntityRequest;
import com.rht.santander.service.BankingEntityService;
import com.rht.santander.service.model.BankingEntity;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class BankingEntityQueryControllerTest {

    @Mock
    private BankingEntityService service;

    @Mock
    private BankingEntityApiMapper mapper;

    @InjectMocks
    private BankingEntityQueryController controller;

    private BankingEntity entity;

    @BeforeEach
    void setUp() {
        entity = new BankingEntity();
        entity.setCode("ABC123");
    }

    @Test
    void getAll_shouldReturnEntityList() {
        BankingEntityList entityList = new BankingEntityList();
        when(service.getAll()).thenReturn(List.of(entity));
        when(mapper.toBankingEntityList(anyList())).thenReturn(entityList);

        ResponseEntity<BankingEntityList> response = controller.getAll();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(entityList, response.getBody());
        verify(service).getAll();
        verify(mapper).toBankingEntityList(anyList());
    }

    @Test
    void getByCode_shouldReturnEntityRequest() {
        BankingEntityRequest request = new BankingEntityRequest();
        when(service.getByCode("ABC123")).thenReturn(entity);
        when(mapper.toBankingEntityRequest(entity)).thenReturn(request);

        ResponseEntity<BankingEntityRequest> response = controller.getByCode("ABC123");

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(request, response.getBody());
        verify(service).getByCode("ABC123");
        verify(mapper).toBankingEntityRequest(entity);
    }

}
