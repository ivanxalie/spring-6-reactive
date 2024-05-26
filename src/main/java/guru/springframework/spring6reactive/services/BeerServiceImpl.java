package guru.springframework.spring6reactive.services;

import guru.springframework.spring6reactive.domain.Beer;
import guru.springframework.spring6reactive.mappers.BeerMapper;
import guru.springframework.spring6reactive.model.BeerDTO;
import guru.springframework.spring6reactive.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {
    private final BeerRepository repository;
    private final BeerMapper mapper;


    @Override
    public Flux<BeerDTO> beers() {
        return repository.findAll()
                .map(mapper::toBeerDto);
    }

    @Override
    public Mono<BeerDTO> findById(Integer id) {
        return repository.findById(id)
                .map(mapper::toBeerDto);
    }

    @Override
    public Mono<BeerDTO> createNewBeer(BeerDTO beerDTO) {
        return repository
                .save(mapper.toBeer(beerDTO))
                .map(mapper::toBeerDto);
    }

    @Override
    public Mono<BeerDTO> updateBeer(Integer id, BeerDTO beerDTO) {
        return repository
                .findById(id)
                .map(saved -> {
                    updateBeer(beerDTO, saved);
                    return saved;
                })
                .flatMap(repository::save)
                .map(mapper::toBeerDto);
    }

    private void updateBeer(BeerDTO beerDTO, Beer saved) {
        saved.setName(beerDTO.getName());
        saved.setStyle(beerDTO.getStyle());
        saved.setPrice(beerDTO.getPrice());
        saved.setUpc(beerDTO.getUpc());
        saved.setQuantityOnHand(beerDTO.getQuantityOnHand());
    }

    @Override
    public Mono<BeerDTO> patchBeer(Integer id, BeerDTO beerDTO) {
        return repository
                .findById(id)
                .map(saved -> {
                    patchBeer(beerDTO, saved);
                    return saved;
                })
                .flatMap(repository::save)
                .map(mapper::toBeerDto);
    }

    private void patchBeer(BeerDTO beerDTO, Beer saved) {
        if (StringUtils.hasText(beerDTO.getName()))
            saved.setName(beerDTO.getName());
        if (StringUtils.hasText(beerDTO.getStyle()))
            saved.setStyle(beerDTO.getStyle());
        if (beerDTO.getPrice() != null)
            saved.setPrice(beerDTO.getPrice());
        if (StringUtils.hasText(beerDTO.getUpc()))
            saved.setUpc(beerDTO.getUpc());
        if (beerDTO.getQuantityOnHand() != null)
            saved.setQuantityOnHand(beerDTO.getQuantityOnHand());
    }

    @Override
    public Mono<Void> deleteById(Integer id) {
        return repository.deleteById(id);
    }
}