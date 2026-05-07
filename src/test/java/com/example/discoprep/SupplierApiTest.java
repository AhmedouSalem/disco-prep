package com.example.discoprep;

import com.example.discoprep.supplier.SupplierDto;
import com.example.discoprep.supplier.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
public class SupplierApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SupplierRepository supplierRepository;

    @BeforeEach
    void cleanDatabase() {
        supplierRepository.deleteAll();
    }

    @Test // Le test suit la méthode AAA (Arrange, Act, Assert)
    void testCreateSupplier() {
        SupplierDto request = new SupplierDto(
                null,
                "Green Supplier Toulouse",
                "France",
                "Toulouse",
                true,
                85
        );

        ResponseEntity<SupplierDto> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/suppliers",
                request,
                SupplierDto.class
        );

        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().id()).isNotNull();
        assertThat(response.getBody().name()).isEqualTo("Green Supplier Toulouse");
        assertThat(response.getBody().country()).isEqualTo("France");
        assertThat(response.getBody().city()).isEqualTo("Toulouse");
        assertThat(response.getBody().active()).isTrue();
        assertThat(response.getBody().greenScore()).isEqualTo(85);
    }

    @Test
    void souldListAllSuppliers() {
        SupplierDto request = new SupplierDto(
                null,
                "Supplier Montpellier",
                "France",
                "Montpellier",
                true,
                70
        );

        restTemplate.postForEntity(
                "http://localhost:" + port + "/api/suppliers",
                request,
                SupplierDto.class
        );

        ResponseEntity<SupplierDto[]> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/suppliers",
                SupplierDto[].class
        );

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody()[0].name()).isEqualTo("Supplier Montpellier");
    }
}
