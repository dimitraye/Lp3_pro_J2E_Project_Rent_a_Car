package com.example.demo.model;

import javax.persistence.*;

/**
 * Model of the car
 */
@Entity
public class Car {

    String marque;
    int prix;
    String plaque;
    boolean rent;
    Dates dates;
    int id;

    public Car(){
    }

    public Car(String marque, int prix, String plaque) {
        this.marque = marque;
        this.prix = prix;
        this.plaque = plaque;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL)
    public Dates getDates() {
        return dates;
    }

    public void setDates(Dates dates) {
        this.dates = dates;
    }

    public boolean isRent() {
        return rent;
    }

    public void setRent(boolean rent) {
        this.rent = rent;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getPlaque() {
        return plaque;
    }

    public void setPlaque(String plaque) {
        this.plaque = plaque;
    }

    @Override
    public String toString() {
        return "Car{" +
                "marque='" + marque + '\'' +
                ", prix=" + prix +
                ", plaque='" + plaque + '\'' +
                ", rent=" + rent +
                ", dates=" + dates +
                ", id=" + id +
                '}';
    }
}
