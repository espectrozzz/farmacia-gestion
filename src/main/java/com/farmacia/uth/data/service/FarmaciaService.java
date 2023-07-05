package com.farmacia.uth.data.service;

import com.farmacia.uth.data.entity.Farmacia;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class FarmaciaService {

    private final FarmaciaRepository repository;

    public FarmaciaService(FarmaciaRepository repository) {
        this.repository = repository;
    }

    public Optional<Farmacia> get(Long id) {
        return repository.findById(id);
    }

    public Farmacia update(Farmacia entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Farmacia> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Farmacia> list(Pageable pageable, Specification<Farmacia> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
