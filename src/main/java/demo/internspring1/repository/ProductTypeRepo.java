package demo.internspring1.repository;

import demo.internspring1.model.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductTypeRepo extends JpaRepository<ProductType, Integer> {
    @Query(value = "select * from product_types pt where pt.name_product_type=:name", nativeQuery = true)
    Optional<ProductType> findByName(String name);
    @Query(value = "select * from product_types pt where pt.name_product_type like %:name%", nativeQuery = true)
    Page<ProductType> findByName(String name, Pageable pageable);
}
