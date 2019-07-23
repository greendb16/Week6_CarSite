package com.example.demo;


import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @NotNull
    private String Model;

    @NotNull
    @Min(1000)
    @Max(10000)
    private int year;

    @NotNull
    private double cost;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="catagory_id")
    private Catagory catagory;

    public Car() {
    }

    public Car(@NotNull String model, @NotNull @Min(1000) @Max(10000) int year, @NotNull double cost, Catagory catagory) {
        Model = model;
        this.year = year;
        this.cost = cost;
        this.catagory = catagory;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Catagory getCatagory() {
        return catagory;
    }

    public void setCatagory(Catagory catagory) {
        this.catagory = catagory;
    }
}
