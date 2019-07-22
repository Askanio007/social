package com.social.server.service.impl;

import com.social.server.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

@Slf4j
public abstract class CommonServiceImpl<EntityClass, ID, Repository extends JpaRepository<EntityClass, ID>> implements CommonService<EntityClass, ID> {

    protected final Repository repository;
    private final Class<EntityClass> persistentClass;

    @SuppressWarnings("unchecked")
    public CommonServiceImpl(Repository repository) {
        this.repository = repository;
        this.persistentClass = (Class<EntityClass>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public EntityClass getById(ID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity " + persistentClass.getName() + " not found. ID = " + id));
    }

    protected void validateEmptyEntityId(long... id) {
        if (Arrays.stream(id).anyMatch(el -> el == 0L)) {
            throw new EntityNotFoundException("Entity ID is 0");
        }
    }

    @Override
    public EntityClass save(EntityClass entity) {
        return repository.save(entity);
    }
}
