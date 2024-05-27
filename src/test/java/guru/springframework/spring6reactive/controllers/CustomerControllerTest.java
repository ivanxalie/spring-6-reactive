package guru.springframework.spring6reactive.controllers;

import guru.springframework.spring6reactive.domain.Customer;
import guru.springframework.spring6reactive.model.CustomerDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(3)
    void testListCustomers() {
        webTestClient
                .get()
                .uri(CustomerController.CUSTOMER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", APPLICATION_JSON_VALUE)
                .expectBody().jsonPath("$.size()").isEqualTo(3);
    }

    @Test
    @Order(1)
    void testGetById() {
        webTestClient
                .get()
                .uri(CustomerController.CUSTOMER_PATH + "/{customerId}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", APPLICATION_JSON_VALUE)
                .expectBody(CustomerDTO.class);
    }

    @Test
    @Order(300)
    void testCreateCustomer() {
        webTestClient
                .post()
                .uri(CustomerController.CUSTOMER_PATH)
                .body(Mono.just(createTestCustomer()), CustomerDTO.class)
                .header("Content-type", APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/customer/4")
                .expectBody(CustomerDTO.class);
    }

    Customer createTestCustomer() {
        return Customer.builder()
                .name("Alex")
                .build();
    }

    @Test
    @Order(400)
    void testUpdateCustomer() {
        webTestClient
                .put()
                .uri(CustomerController.CUSTOMER_PATH + "/{customerId}", 1)
                .body(Mono.just(createTestCustomer()), CustomerDTO.class)
                .header("Content-type", APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(999)
    void testDeleteCustomer() {
        webTestClient
                .delete()
                .uri(CustomerController.CUSTOMER_PATH + "/{customerId}", 1)
                .exchange()
                .expectStatus().isNoContent();
    }
}