package com.example.retrofit.config;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.retrofit.dao.DepartamentDao;
import com.example.retrofit.dao.ProfessorDao;
import com.example.retrofit.model.Departament;
import com.example.retrofit.model.Professor;

@Database(entities = {Professor.class, Departament.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class RoomConfig extends RoomDatabase {

    private static RoomConfig instance = null;

    public abstract ProfessorDao professorDAO();
    public abstract DepartamentDao departamentDao();

    public static RoomConfig getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    RoomConfig.class,
                    "database")
                    .allowMainThreadQueries() //Permite que o room rode na main principal
                    .fallbackToDestructiveMigration()  // receia o database se necessário
                    .build();
        }

        return instance;
    }
}
