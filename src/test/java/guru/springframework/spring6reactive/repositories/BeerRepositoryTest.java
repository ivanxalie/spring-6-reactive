package guru.springframework.spring6reactive.repositories;

import guru.springframework.spring6reactive.domain.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@DataR2dbcTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository repository;

    @Test
    void saveNewBeer() {
        Mono<Beer> saved = repository.save(createTestBeer());
        saved.subscribe(System.out::println);

//        create(saved).expectNextCount(1).verifyComplete();

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