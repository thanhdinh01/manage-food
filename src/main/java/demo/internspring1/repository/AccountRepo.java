package demo.internspring1.repository;

import demo.internspring1.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Integer> {
    @Query(value = "select * from accounts where accounts.user_name=:email", nativeQuery = true)
    Optional<Account> findAccByEmail(String email);
    @Query(value = "select * from accounts where accounts.reset_password_token=:token", nativeQuery = true)
    Optional<Account> findAccByPWResetToken(String token);
    @Query(value = "select * from accounts where accounts.user_name=:email", nativeQuery = true)
    Account findByEmail(String email);
}
