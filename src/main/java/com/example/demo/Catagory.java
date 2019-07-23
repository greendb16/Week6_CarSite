package com.example.demo;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Catagory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

//    @Column(unique = true)
    @NotNull
    private String type;

    @OneToMany(mappedBy = "catagory", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Car> cars;

    public Catagory() {
    }

    public Catagory(@NotNull String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }
}
