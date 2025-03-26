package com.sg.voyagestp;

public class Utilisateur {
    private String id;      //le serveur met automatiquement un id en string
    private String nom;
    private String prenom;

    private int age;
    private String courriel;
    private String telephone;

    private String adresse;
    private String password;

    public Utilisateur(String nom, String prenom, int age, String courriel, String telephone, String adresse, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.courriel = courriel;
        this.telephone = telephone;
        this.adresse = adresse;
        this.password = password;
    }

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

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
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
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
