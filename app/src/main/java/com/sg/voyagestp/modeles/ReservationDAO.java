package com.sg.voyagestp.modeles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationDAO {
    private SQLiteDatabase db;
    private ReservationDB reservationDB;

    public ReservationDAO(Context context) {
        reservationDB = new ReservationDB(context);
    }

    public void open() {
        db = reservationDB.getWritableDatabase();
    }

    public void close() {
        reservationDB.close();
    }

    public long ajouterReservation(Reservation r) {
        ContentValues values = new ContentValues();
        values.put("idClient", r.getIdClient());
        values.put("destination",r.getDestination());
        values.put("idVoyage", r.getIdVoyage());
        values.put("date", r.getDate().getTime());
        values.put("nombre_personne", r.getNombre_personne());
        values.put("montant", r.getMontant());
        values.put("statut", r.getStatut());

        return db.insert("reservations", null, values);
    }

    public List<Reservation> getReservationsParClient(String idClient) {
        List<Reservation> liste = new ArrayList<>();
        Cursor cursor = db.query("reservations",
                null,
                "idClient = ?",
                new String[]{idClient},
                null, null, "date DESC");

        if (cursor.moveToFirst()) {
            do {
                Reservation r = new Reservation();
                r.setId(cursor.getString(cursor.getColumnIndexOrThrow("id")));
                r.setIdClient(cursor.getString(cursor.getColumnIndexOrThrow("idClient")));
                r.setIdVoyage(cursor.getString(cursor.getColumnIndexOrThrow("idVoyage")));
                r.setDestination(cursor.getString(cursor.getColumnIndexOrThrow("destination")));
                r.setDate(new Date(cursor.getLong(cursor.getColumnIndexOrThrow("date"))));
                r.setNombre_personne(cursor.getInt(cursor.getColumnIndexOrThrow("nombre_personne")));
                r.setMontant(cursor.getDouble(cursor.getColumnIndexOrThrow("montant")));
                r.setStatut(cursor.getInt(cursor.getColumnIndexOrThrow("statut")));

                liste.add(r);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return liste;
    }

    public void updateStatut(String idReservation, int nouveauStatut) {
        ContentValues values = new ContentValues();
        values.put("statut", nouveauStatut);
        db.update("reservations", values, "id = ?", new String[]{idReservation});
    }


    public int supprimerReservation(String idReservation) {
        return db.delete("reservations", "id = ?", new String[]{idReservation});
    }
}
