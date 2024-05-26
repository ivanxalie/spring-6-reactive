package guru.springframework.spring6reactive.mappers;

import guru.springframework.spring6reactive.domain.Customer;
import guru.springframework.spring6reactive.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    CustomerDTO toCustomerDto(Customer customer);

    Customer toCustomer(CustomerDTO customerDTO);
}
