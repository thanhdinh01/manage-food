package demo.internspring1.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProdTypeRequest {
    private int productTypeId;
    private String nameProductType;
    private String imageProductType;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}
