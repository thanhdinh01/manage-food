package demo.internspring1.dto.request;

import com.fasterxml.jackson.annotation.JsonBackReference;
import demo.internspring1.model.ProductType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private int productId;

    private int productTypeId;

    private String nameProduct;

    private double price;

    private String avatarImageProduct;

    private String shortSummary;

    private String contentProduct;

    private int discount;

    private int status;

    private int numberOfViews;

    private int quantity;

    private LocalDateTime createdAt;

}
