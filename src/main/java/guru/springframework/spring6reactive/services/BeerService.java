package guru.springframework.spring6reactive.services;

import guru.springframework.spring6reactive.model.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerService {
    Flux<BeerDTO> beers();

    Mono<BeerDTO> findById(Integer id);

    Mono<BeerDTO> createNewBeer(BeerDTO beerDTO);

    Mono<BeerDTO> updateBeer(Integer id, BeerDTO beerDTO);

    Mono<BeerDTO> patchBeer(Integer id, BeerDTO beerDTO);

    Mono<Void> deleteById(Integer id);
}
