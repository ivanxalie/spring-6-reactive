package guru.springframework.spring6reactive.services;

import guru.springframework.spring6reactive.model.CustomerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Flux<CustomerDTO> customers();

    Mono<CustomerDTO> findById(Integer id);

    Mono<CustomerDTO> createNewCustomer(CustomerDTO customerDTO);

    Mono<CustomerDTO> updateCustomer(Integer id, CustomerDTO customerDTO);

    Mono<CustomerDTO> patchCustomer(Integer id, CustomerDTO customerDTO);

    Mono<Void> deleteById(Integer id);
}