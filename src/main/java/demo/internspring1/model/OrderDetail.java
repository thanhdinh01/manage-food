package demo.internspring1.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_details")
@Data
public class OrderDetail {
    @PrePersist
    protected void onCreate(){
        this.createdAt= LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updateAt=LocalDateTime.now();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderDetailId;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "product_id")
    private Product product;

    private double priceTotal;

    private int quantity;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;
}
