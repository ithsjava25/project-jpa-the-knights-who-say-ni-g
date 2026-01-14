package org.example.tables;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "rental")
public class Rental {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "rental_id")
    private Long rentalId;

    @Column(name= "rental_date")
    private LocalDateTime rentalDate;



    public Rental() {
        this.rentalDate = LocalDateTime.now();
    }


    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    //ersätta many to many - egen entitet med id sammansatt nyckel film id och rental id
    //Cascade.all tar bort föräldern samt barn
    //orphanRemoval tvingar movieRental-tabellen att ta bort objektet om en rental försvinner, en rentalMovie ska inte kunna existera utan en rental
    @OneToMany(mappedBy = "rental",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RentalMovie> movierental = new HashSet<>();
    //Blir det många listor om man har List<Movierental> här?
    //Eller kan man slå ihop dem med att ha List<Movie> istället?


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

    public Set<RentalMovie> getMovierental() {
        return movierental;
    }

    public void setMovierental(Set<RentalMovie> movierental) {
        this.movierental = movierental;
    }



    public void addItem(RentalMovie item) {
        if (item != null) {
            movierental.add(item);
            item.setRental(this);
        }
    }

    public void removeItem(RentalMovie item) {
        if(item != null && movierental.remove(item)) {
            movierental.remove(item);
            item.setRental(null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Rental rental)) return false;
        return rentalId != null && Objects.equals(rentalId, rental.rentalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rentalId);
    }
}




