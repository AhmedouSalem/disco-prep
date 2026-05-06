package com.example.discoprep.supplier;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository repository;

    public SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    public SupplierDto create(SupplierDto dto) {
        Supplier supplier = new Supplier(
                dto.name(),
                dto.country(),
                dto.city(),
                dto.greenScore() == null ? 0 : dto.greenScore()
        );

        if (dto.active() != null) {
            supplier.setActive(dto.active());
        }

        Supplier saved = repository.save(supplier);
        return toDto(saved);
    }

    public List<SupplierDto> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    private SupplierDto toDto(Supplier supplier) {
        return new SupplierDto(
                supplier.getId(),
                supplier.getName(),
                supplier.getCountry(),
                supplier.getCity(),
                supplier.isActive(),
                supplier.getGreenScore()
        );
    }
}