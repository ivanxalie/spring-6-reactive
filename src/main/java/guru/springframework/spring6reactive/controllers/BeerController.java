package guru.springframework.spring6reactive.controllers;

import guru.springframework.spring6reactive.model.BeerDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static guru.springframework.spring6reactive.controllers.BeerController.BEER_PATH;

@RestController
@RequestMapping(BEER_PATH)
public class BeerController {
    public static final String BEER_PATH = "/api/v2/beer";

    @GetMapping
    Flux<BeerDTO> beers() {
        return Flux.just(
                BeerDTO.builder().id(1L).build(),
                BeerDTO.builder().id(2L).build()
        );
    }
}
