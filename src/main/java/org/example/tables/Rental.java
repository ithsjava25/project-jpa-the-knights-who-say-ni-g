package org.example.tables;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rental")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentalId;

    private LocalDateTime rentalDate;

    private LocalDateTime returnDate;

    private BigDecimal totalRentalPrice;

    public Rental() {
        this.rentalDate = LocalDateTime.now();
    }


    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "rental", cascade = CascadeType.PERSIST)
    private List<Movierental> movierental  = new ArrayList<>();

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getRentalId() {
        return rentalId;
    }

    public LocalDateTime getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDateTime rentalDate) {
        this.rentalDate = rentalDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public BigDecimal getTotalRentalPrice() {
        return totalRentalPrice;
    }

    public void setTotalRentalPrice(BigDecimal totalRentalPrice) {
        this.totalRentalPrice = totalRentalPrice;
    }

    public List<Movierental> getMovierental() {
        return movierental;
    }

    public void setMovierental(List<Movierental> movierental) {
        this.movierental = movierental;
    }
}

