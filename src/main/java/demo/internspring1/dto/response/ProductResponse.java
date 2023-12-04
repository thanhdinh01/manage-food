package demo.internspring1.dto.response;

import demo.internspring1.dto.request.ProdTypeRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private int productId;

    private ProdTypeRequest productType;

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
