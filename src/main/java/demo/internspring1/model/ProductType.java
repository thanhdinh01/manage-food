package demo.internspring1.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "product_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductType {
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateAt = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productTypeId;

    private String nameProductType;

    private String imageTypeProduct;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productType", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private Set<Product> products;

    public ProductType(String nameProductType, String imageTypeProduct) {
        this.nameProductType = nameProductType;
        this.imageTypeProduct = imageTypeProduct;
    }

    public ProductType(int productTypeId, String nameProductType, String imageTypeProduct, LocalDateTime createdAt) {
        this.productTypeId = productTypeId;
        this.nameProductType = nameProductType;
        this.imageTypeProduct = imageTypeProduct;
        this.createdAt = createdAt;
    }
}
