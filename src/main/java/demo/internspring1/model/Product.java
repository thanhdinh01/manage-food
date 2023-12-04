package demo.internspring1.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
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
    private int productId;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "product_type_id")
    private ProductType productType;

    private String nameProduct;

    private double price;

    @Lob
    private String avatarImageProduct;

    @Lob
    private String shortSummary;

    @Lob
    private String contentProduct;

    private int discount;

    private int status;

    private int numberOfViews=0;

    private int quantity;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private Set<CartItem> cartItems;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private Set<OrderDetail> orderDetails;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private Set<ProductReview> productReviews;

    public Product(int productId, ProductType productType, String nameProduct, double price, String avatarImageProduct,
                   String shortSummary,String contentProduct, int discount, int status, int quantity, LocalDateTime createdAt) {
        this.productId = productId;
        this.productType = productType;
        this.nameProduct = nameProduct;
        this.price = price;
        this.avatarImageProduct = avatarImageProduct;
        this.shortSummary = shortSummary;
        this.contentProduct = contentProduct;
        this.discount = discount;
        this.status = status;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }
}
