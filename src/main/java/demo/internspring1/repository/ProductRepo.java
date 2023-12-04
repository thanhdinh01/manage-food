package demo.internspring1.repository;

import demo.internspring1.model.Product;
import demo.internspring1.model.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    @Query(value = "select * from products pr where pr.name_product=:name", nativeQuery = true)
    Optional<Product> findByName(String name);
    @Query(value = "select * from products pr where pr.name_product like %:name%", nativeQuery = true)
    Page<Product> findByName(String name, Pageable pageable);
    @Query(value = "select * from products pr where pr.product_type_id=:prodTypeId", nativeQuery = true)
    Page<Product> findByProdTypeId(Integer prodTypeId, Pageable pageable);
    @Query(value = "select * from internspring1.products p where p.product_type_id=:prodTypeId and p.name_product like %:name%", nativeQuery = true)
    Page<Product> findProdByTypeAndName(Integer prodTypeId,String name, Pageable pageable);
}
