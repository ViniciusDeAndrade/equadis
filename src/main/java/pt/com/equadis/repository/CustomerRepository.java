package pt.com.equadis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.com.equadis.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
