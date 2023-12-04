package demo.internspring1.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "order_statuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderStatusId;

    private String statusName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderStatus", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private Set<Order> orders;
}
