package com.social.server.service.impl;

import com.social.server.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Slf4j
public abstract class CommonServiceImpl<Class, ID, Repository extends JpaRepository<Class, ID>> implements CommonService<Class, ID> {

    protected final Repository repository;

    public CommonServiceImpl(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Class findById(ID id) {
        Optional<Class> optionalClass = repository.findById(id);
        if (!optionalClass.isPresent()) {
            log.error("Entity not found. ID = {}", id );
            return null;
        }
        return optionalClass.get();
    }
}