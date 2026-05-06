package com.example.discoprep.supplier;

import jakarta.validation.constraints.NotBlank;

public record SupplierDto(
        Long id,

        @NotBlank(message = "Supplier name is required")
        String name,

        @NotBlank(message = "Country is required")
        String country,

        String city,

        Boolean active
) {
}