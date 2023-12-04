package demo.internspring1.repository;

import demo.internspring1.model.Decentralization;
import demo.internspring1.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Decentralization, Integer> {
    @Query(value = "select * from decentralizations where decentralizations.authority_name=:rolename", nativeQuery = true)
    Optional<Decentralization> findByName(@Param("rolename") String rolename);
}
