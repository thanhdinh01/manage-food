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
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
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
    private int orderId;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    private double originalPrice;

    private double actualPrice;

    private String fullName;

    private String email;

    private String phone;

    private String address;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private Set<OrderDetail> orderDetails;
}
