package guru.springframework.spring6reactive.controllers;

import guru.springframework.spring6reactive.domain.Beer;
import guru.springframework.spring6reactive.model.BeerDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BeerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(3)
    void testListBeers() {
        webTestClient
                .get()
                .uri(BeerController.BEER_PATH)
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
                .uri(BeerController.BEER_PATH + "/{beerId}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", APPLICATION_JSON_VALUE)
                .expectBody(BeerDTO.class);
    }

    @Test
    @Order(300)
    void testCreateBeer() {
        webTestClient
                .post()
                .uri(BeerController.BEER_PATH)
                .body(Mono.just(createTestBeer()), BeerDTO.class)
                .header("Content-type", APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/beer/4")
                .expectBody(BeerDTO.class);
    }

    Beer createTestBeer() {
        return Beer.builder()
                .name("Space Dust")
                .style("IPA")
                .price(BigDecimal.TEN)
                .quantityOnHand(12)
                .upc("1234")
                .build();
    }

    @Test
    @Order(400)
    void testUpdateBeer() {
        webTestClient
                .put()
                .uri(BeerController.BEER_PATH + "/{beerId}", 1)
                .body(Mono.just(createTestBeer()), BeerDTO.class)
                .header("Content-type", APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(999)
    void testDeleteBeer() {
        webTestClient
                .delete()
                .uri(BeerController.BEER_PATH + "/{beerId}", 1)
                .exchange()
                .expectStatus().isNoContent();
    }
}