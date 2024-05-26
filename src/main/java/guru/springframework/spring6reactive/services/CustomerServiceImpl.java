package guru.springframework.spring6reactive.services;

import guru.springframework.spring6reactive.domain.Customer;
import guru.springframework.spring6reactive.mappers.CustomerMapper;
import guru.springframework.spring6reactive.model.CustomerDTO;
import guru.springframework.spring6reactive.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Override
    public Flux<CustomerDTO> customers() {
        return repository.findAll().map(mapper::toCustomerDto);
    }

    @Override
    public Mono<CustomerDTO> findById(Integer id) {
        return repository.findById(id).map(mapper::toCustomerDto);
    }

    @Override
    public Mono<CustomerDTO> createNewCustomer(CustomerDTO customerDTO) {
        return repository.save(mapper.toCustomer(customerDTO)).map(mapper::toCustomerDto);
    }

    @Override
    public Mono<CustomerDTO> updateCustomer(Integer id, CustomerDTO customerDTO) {
        return repository
                .findById(id)
                .map(saved -> {
                    updateCustomer(customerDTO, saved);
                    return saved;
                })
                .flatMap(repository::save)
                .map(mapper::toCustomerDto);
    }

    private void updateCustomer(CustomerDTO customerDTO, Customer saved) {
        saved.setName(customerDTO.getName());
    }

    @Override
    public Mono<CustomerDTO> patchCustomer(Integer id, CustomerDTO customerDTO) {
        return repository
                .findById(id)
                .map(saved -> {
                    patchCustomer(customerDTO, saved);
                    return saved;
                })
                .flatMap(repository::save)
                .map(mapper::toCustomerDto);
    }

    private void patchCustomer(CustomerDTO customerDTO, Customer saved) {
        if (StringUtils.hasText(customerDTO.getName()))
            saved.setName(customerDTO.getName());
    }

    @Override
    public Mono<Void> deleteById(Integer id) {
        return repository.deleteById(id);
    }
}
