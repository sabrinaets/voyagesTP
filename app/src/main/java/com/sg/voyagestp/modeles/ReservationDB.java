package com.sg.voyagestp.modeles;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReservationDB extends SQLiteOpenHelper {
        private static final String DB_NAME = "reservations.db";
        private static final int DB_VERSION = 6;

        public ReservationDB(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE reservations (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "idClient TEXT," +
                    "destination TEXT," +
                    "idVoyage TEXT," +
                    "date INTEGER," +
                    "nombre_personne INTEGER," +
                    "montant REAL," +
                    "statut INTEGER)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS reservations");
            onCreate(db);
        }
    }

