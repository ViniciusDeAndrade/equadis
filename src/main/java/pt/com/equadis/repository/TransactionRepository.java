package pt.com.equadis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.com.equadis.entity.AccountTransaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<AccountTransaction, Long> {
    List<AccountTransaction> findAllByAccountId(Long id);
}
