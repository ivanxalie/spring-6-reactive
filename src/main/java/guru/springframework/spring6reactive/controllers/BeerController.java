package guru.springframework.spring6reactive.controllers;

import guru.springframework.spring6reactive.model.BeerDTO;
import guru.springframework.spring6reactive.services.BeerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static guru.springframework.spring6reactive.controllers.BeerController.BEER_PATH;

@RestController
@RequestMapping(BEER_PATH)
@RequiredArgsConstructor
public class BeerController {
    public static final String BEER_PATH = "/api/v2/beer";
    private final BeerService service;

    @GetMapping
    Flux<BeerDTO> beers() {
        return service.beers();
    }

    @GetMapping("/{beerId}")
    Mono<BeerDTO> findById(@PathVariable("beerId") Integer id) {
        return service.findById(id);
    }

    @PostMapping
    Mono<ResponseEntity<Void>> createNewBeer(@Valid @RequestBody BeerDTO beerDTO) {
        return service
                .createNewBeer(beerDTO)
                .map(saved -> ResponseEntity.created(
                        UriComponentsBuilder.fromHttpUrl("http://localhost:8080/" + BEER_PATH + "/{beerId}")
                                .build(saved.getId())
                ).build());
    }

    @PutMapping("/{beerId}")
    Mono<ResponseEntity<Void>> updateBeer(@PathVariable("beerId") Integer id, @Valid @RequestBody BeerDTO beerDTO) {
        return service
                .updateBeer(id, beerDTO)
                .map(saved -> ResponseEntity.ok().build());
    }

    @PatchMapping("/{beerId}")
    Mono<ResponseEntity<Void>> patchBeer(@PathVariable("beerId") Integer id, @Valid @RequestBody BeerDTO beerDTO) {
        return service
                .patchBeer(id, beerDTO)
                .map(saved -> ResponseEntity.ok().build());
    }

    @DeleteMapping("/{beerId}")
    Mono<ResponseEntity<Void>> deleteById(@PathVariable("beerId") Integer id) {
        return service
                .deleteById(id)
                .map(saved -> ResponseEntity.noContent().build());
    }
}
