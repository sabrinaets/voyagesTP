package com.sg.voyagestp.modeles;

import java.util.Date;

public class Trip {
    private Date date;
    private int nb_places_disponibles;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNb_places_disponibles() {
        return nb_places_disponibles;
    }

    public void setNb_places_disponibles(int nb_places_disponibles) {
        this.nb_places_disponibles = nb_places_disponibles;
    }
}
