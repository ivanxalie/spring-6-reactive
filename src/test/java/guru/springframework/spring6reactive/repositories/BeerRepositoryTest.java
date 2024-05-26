package guru.springframework.spring6reactive.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6reactive.domain.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static reactor.test.StepVerifier.create;

@DataR2dbcTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository repository;

    @Test
    void testCreateJackson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        System.out.println(mapper.writeValueAsString(createTestBeer()));
    }

    @Test
    void saveNewBeer() {
        Mono<Beer> saved = repository.save(createTestBeer());

        create(saved).expectNextCount(1).verifyComplete();
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
}