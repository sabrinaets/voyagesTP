package com.sg.voyagestp.modeles;

import java.io.Serializable;

public class Voyage implements Serializable {
    private String id;
    private String nom_voyage;

    private String description;
    private double prix;
    private String destination;
    private String image_url;
    private int duree_jours;

    private Trip[] trips;
    private String type_de_voyage;
    private String activites_incluses;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Voyage(String activites_incluses, String type_de_voyage, Trip[] trips, int duree_jours, String destination, double prix, String nom_voyage, String description, String image_url) {
        this.activites_incluses = activites_incluses;
        this.type_de_voyage = type_de_voyage;
        this.trips = trips;
        this.duree_jours = duree_jours;
        this.destination = destination;
        this.prix = prix;
        this.nom_voyage = nom_voyage;
        this.description = description;
        this.image_url = image_url;
    }
    public Voyage(){}

    public String getActivites_incluses() {
        return activites_incluses;
    }

    public void setActivites_incluses(String activites_incluses) {
        this.activites_incluses = activites_incluses;
    }

    public String getType_de_voyage() {
        return type_de_voyage;
    }

    public void setType_de_voyage(String type_de_voyage) {
        this.type_de_voyage = type_de_voyage;
    }

    public int getDuree_jours() {
        return duree_jours;
    }

    public void setDuree_jours(int duree_jours) {
        this.duree_jours = duree_jours;
    }

    public Trip[] getTrips() {
        return trips;
    }

    public void setTrips(Trip[] trips) {
        this.trips = trips;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getNom_voyage() {
        return nom_voyage;
    }

    public void setNom_voyage(String nom_voyage) {
        this.nom_voyage = nom_voyage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
