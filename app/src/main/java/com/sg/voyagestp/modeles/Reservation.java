package com.sg.voyagestp.modeles;

import java.io.Serializable;
import java.util.Date;
public class Reservation implements Serializable {
    private String id;
    private String idClient;
    private String idVoyage;
    private Date date;
    private int nombre_personne;
    private double montant;
    private int statut;

    public Reservation(String id, String idClient, String idVoyage, Date date, int nombre_personne, double montant, int statut) {
        this.id = id;
        this.idClient = idClient;
        this.idVoyage = idVoyage;
        this.date = date;
        this.nombre_personne = nombre_personne;
        this.montant = montant;
        this.statut = statut;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getIdClient() {
        return idClient;
    }

    public String getIdVoyage() {
        return idVoyage;
    }

    public Date getDate() {
        return date;
    }

    public int getNombre_personne() {
        return nombre_personne;
    }

    public double getMontant() {
        return montant;
    }

    public int getStatut() {
        return statut;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public void setIdVoyage(String idVoyage) {
        this.idVoyage = idVoyage;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setNombre_personne(int nombre_personne) {
        this.nombre_personne = nombre_personne;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

}
