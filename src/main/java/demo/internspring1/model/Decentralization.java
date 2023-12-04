package demo.internspring1.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "decentralizations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Decentralization {
    @PrePersist
    protected void onCreate(){
        this.createdAt=LocalDate.now();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updateAt=LocalDate.now();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int decentralizationId;

    @Enumerated(EnumType.STRING)
    private RoleName authorityName;

    private LocalDate createdAt;

    private LocalDate updateAt;
}
