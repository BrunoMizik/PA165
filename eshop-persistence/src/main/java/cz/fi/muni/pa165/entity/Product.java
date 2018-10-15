package cz.fi.muni.pa165.entity;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false,unique=true)
    private String name;

    @Enumerated()
    private Color color;


    @Temporal(TemporalType.DATE)
    private LocalDate addedDate;

    public Product(Long productId) {
        this.id = productId;
    }
    public Product() {
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
