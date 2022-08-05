package com.sk.op.application.admin.service.impl;

import com.sk.op.application.admin.service.ApplicationService;
import com.sk.op.application.entity.entity.Application;
import com.sk.op.application.entity.repository.ApplicationRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Setter(onMethod_ = @Autowired)
public class ApplicationServiceImpl implements ApplicationService {

    private ApplicationRepository applicationRepository;

    @Override
    public Application get(Long id) {
        return applicationRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Application> query(Pageable pageable) {
        return applicationRepository.findAll(pageable);
    }

    @Override
    public Page<Application> query(Pageable pageable, Application params) {
        return null;
    }
}
