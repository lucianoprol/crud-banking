package com.rht.santander.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rht.santander.repository.BankingEntityRepository;
import com.rht.santander.service.impl.BankingEntityServiceImpl;
import com.rht.santander.service.model.BankingEntity;

@SpringBootTest
class BankingEntityServiceIntegrationTest {

    @Autowired
    private BankingEntityServiceImpl service;

    @Autowired
    private BankingEntityRepository repository;

    private BankingEntity entity;
    
    @BeforeEach
    void setUp() {
    	repository.deleteAll();
        
        entity = new BankingEntity();
        entity.setCode("150");
        entity.setName("Banco Test");
    }

    @Test
    void saveAndRetrieveEntity() {
        service.save(entity);

        BankingEntity found = service.getByCode("150");

        assertNotNull(found);
        assertEquals("Banco Test", found.getName());
    }

    @Test
    void updateEntity_shouldPersistChanges() {
        service.save(entity);

        BankingEntity updated = new BankingEntity();
        updated.setCode("150");
        updated.setName("Banco Modificado");
        updated.setCity("Ciudad");
        updated.setPhoneNumber("4321-1234");

        boolean ok = service.update(updated);
        assertTrue(ok);

        BankingEntity reloaded = service.getByCode("150");
        assertEquals("Banco Modificado", reloaded.getName());
        assertEquals("Ciudad", reloaded.getCity());
        assertEquals("4321-1234", reloaded.getPhoneNumber());
    }

    @Test
    void deleteByCode_shouldRemoveEntity() {
        service.save(entity);

        boolean ok = service.delete("150");
        assertTrue(ok);
        assertThrows(NoSuchElementException.class, () -> service.getByCode("150"));
    }

    @Test
    void deleteByEntity_shouldRemoveEntity() {
        service.save(entity);

        boolean ok = service.delete(entity);
        assertTrue(ok);
        assertThrows(NoSuchElementException.class, () -> service.getByCode("150"));
    }

    @Test
    void getAll_shouldReturnAllEntities() {
        BankingEntity entity1 = new BankingEntity();
        entity1.setCode("100");
        entity1.setName("Entity 100");
        service.save(entity1);

        BankingEntity entity2 = new BankingEntity();
        entity2.setCode("200");
        entity2.setName("Entity 200");
        service.save(entity2);

        List<BankingEntity> all = service.getAll();

        assertTrue(all.size() >= 2);
        assertTrue(all.stream().anyMatch(e -> "100".equals(e.getCode())));
        assertTrue(all.stream().anyMatch(e -> "200".equals(e.getCode())));
    }

}
