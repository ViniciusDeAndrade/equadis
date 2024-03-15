package pt.com.equadis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.com.equadis.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
