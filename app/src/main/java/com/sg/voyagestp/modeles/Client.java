package com.sg.voyagestp.modeles;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Client implements Serializable {
    private String id;      //le serveur met automatiquement un id en string
    private String nom;
    private String prenom;

    private int age;
    private String email;
    private String telephone;

    private String adresse;
    @JsonProperty("mdp")  // Lier la propriété "mdp" dans JSON au champ "mdp" dans la classe Client
    private String mdp;

    public Client(String nom, String prenom, int age, String courriel, String telephone, String adresse, String mdp) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.email = courriel;
        this.telephone = telephone;
        this.adresse = adresse;

        this.mdp = mdp;
    }

    public Client(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String courriel) {
        this.email = courriel;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPassword() {
        return mdp;
    }

    public void setPassword(String password) {
        this.mdp = password;
    }
}
