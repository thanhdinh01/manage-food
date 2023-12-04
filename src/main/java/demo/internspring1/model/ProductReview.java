package demo.internspring1.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductReview {
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
    private int productReviewId;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    private String contentRated;

    private int pointEvaluation;

    private int status;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;
}
