package guru.springframework.spring6reactive.controllers;

import guru.springframework.spring6reactive.model.CustomerDTO;
import guru.springframework.spring6reactive.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static guru.springframework.spring6reactive.controllers.CustomerController.CUSTOMER_PATH;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(CUSTOMER_PATH)
@RequiredArgsConstructor
public class CustomerController {
    public static final String CUSTOMER_PATH = "/api/v2/customer";
    private final CustomerService service;

    @GetMapping
    Flux<CustomerDTO> Customers() {
        return service.customers();
    }

    @GetMapping("/{customerId}")
    Mono<CustomerDTO> findById(@PathVariable("customerId") Integer id) {
        return service
                .findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND)));
    }

    @PostMapping
    Mono<ResponseEntity<Void>> createNewCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        return service
                .createNewCustomer(customerDTO)
                .map(saved -> ResponseEntity.created(
                        UriComponentsBuilder.fromHttpUrl("http://localhost:8080/" + CUSTOMER_PATH + "/{customerId}")
                                .build(saved.getId())
                ).build());
    }

    @PutMapping("/{customerId}")
    Mono<ResponseEntity<Void>> updateCustomer(@PathVariable("customerId") Integer id,
                                              @Valid @RequestBody CustomerDTO customerDTO) {
        return service
                .updateCustomer(id, customerDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND)))
                .map(saved -> ResponseEntity.noContent().build());
    }

    @PatchMapping("/{customerId}")
    Mono<ResponseEntity<Void>> patchCustomer(@PathVariable("customerId") Integer id,
                                             @Valid @RequestBody CustomerDTO customerDTO) {
        return service
                .patchCustomer(id, customerDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND)))
                .map(saved -> ResponseEntity.ok().build());
    }

    @DeleteMapping("/{customerId}")
    Mono<ResponseEntity<Void>> deleteById(@PathVariable("customerId") Integer id) {
        return service
                .findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND)))
                .map(customerDTO -> service.deleteById(id))
                .thenReturn(ResponseEntity.noContent().build());
    }
}
