package guru.springframework.spring6reactive.bootstrap;

import guru.springframework.spring6reactive.domain.Beer;
import guru.springframework.spring6reactive.repositories.BeerRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static java.time.LocalDateTime.now;

@Component
@RequiredArgsConstructor
@DependsOnDatabaseInitialization
public class BootstrapDB {
    private final BeerRepository repository;

    @PostConstruct
    public void init() {
        repository.count().subscribe(count -> {
            if (count == 0) initBeers();
        });
        repository.findAll().subscribe(System.out::println);
    }

    private void initBeers() {
        Beer beerDTO1 = Beer.builder()
                .name("Galaxy Cat")
                .style("PALE_ALE")
                .upc("123456")
                .price(BigDecimal.valueOf(12.99))
                .quantityOnHand(122)
                .createdDate(now())
                .lastModifiedDate(now())
                .build();
        Beer beerDTO2 = Beer.builder()
                .name("Crank")
                .style("PALE_ALE")
                .upc("123456890")
                .price(BigDecimal.valueOf(11.99))
                .quantityOnHand(392)
                .createdDate(now())
                .lastModifiedDate(now())
                .build();
        Beer beerDTO3 = Beer.builder()
                .name("Sunshine City")
                .style("IPA")
                .upc("1234")
                .price(BigDecimal.valueOf(13.99))
                .quantityOnHand(144)
                .createdDate(now())
                .lastModifiedDate(now())
                .build();
        repository.saveAll(
                List.of(
                        beerDTO1,
                        beerDTO2,
                        beerDTO3
                )
        ).subscribe();
    }
}
