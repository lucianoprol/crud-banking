package com.rht.santander.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rht.santander.exception.NotValidException;
import com.rht.santander.exception.TechnicalException;
import com.rht.santander.repository.BankingEntityRepository;
import com.rht.santander.service.impl.BankingEntityServiceImpl;
import com.rht.santander.service.model.BankingEntity;

@ExtendWith(MockitoExtension.class)
public class BankingEntityServiceTest {

    @Mock
    private BankingEntityRepository repository;

    @InjectMocks
    private BankingEntityServiceImpl service;

    private BankingEntity entity;

    @BeforeEach
    void setUp() {
    	repository.deleteAll();
        
        entity = new BankingEntity();
        entity.setCode("150");
        entity.setName("Banco Test");
    }
    

    @Test
    void getAll_ReturnsList() {
        when(repository.findAll()).thenReturn(Arrays.asList(entity));

        List<BankingEntity> result = service.getAll();

        assertEquals(1, result.size());
        assertEquals("150", result.get(0).getCode());
    }
    
    @Test
    void getByCode_Found() {
        when(repository.findByCode("150")).thenReturn(Optional.of(entity));

        BankingEntity result = service.getByCode("150");

        assertNotNull(result);
        assertEquals("150", result.getCode());
    }
    
    @Test
    void getByCode_NotFound_ThrowsException() {
        when(repository.findByCode("123")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.getByCode("123"));
    }
    
    @Test
    void save_NewEntity_Success() {
        when(repository.findByCode("150")).thenReturn(Optional.empty());

        boolean result = service.save(entity);

        assertTrue(result);
        verify(repository).save(entity);
    }
    
    @Test
    void save_NullEntity_ThrowsTechnicalException() {
        assertThrows(TechnicalException.class, () -> service.save(null));
    }

    @Test
    void save_EntityAlreadyExists_ThrowsNotValidException() {
        when(repository.findByCode("150")).thenReturn(Optional.of(entity));

        assertThrows(NotValidException.class, () -> service.save(entity));
    }

    @Test
    void update_ExistingEntity_Success() {
        when(repository.findByCode("150")).thenReturn(Optional.of(entity));

        entity.setName("Banco Modificado");

        boolean result = service.update(entity);

        assertTrue(result);
        verify(repository).save(entity);
    }
    
    @Test
    void deleteByCode_ExistingEntity_Success() {
        when(repository.findByCode("150")).thenReturn(Optional.of(entity));

        boolean result = service.delete("150");

        assertTrue(result);
        verify(repository).delete(entity);
    }

    @Test
    void deleteByCode_NotFound_ThrowsNotValidException() {
        when(repository.findByCode("123")).thenReturn(Optional.empty());

        assertThrows(NotValidException.class, () -> service.delete("123"));
    }
}
