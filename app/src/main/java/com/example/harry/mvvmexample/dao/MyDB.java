package com.example.harry.mvvmexample.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.harry.mvvmexample.model.City;

@Database(entities = {City.class}, version = 3, exportSchema = false)
public abstract class MyDB extends RoomDatabase {

    public abstract CityDao cityDao();

    private static MyDB INSTANCE;

    public static MyDB getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (MyDB.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDB.class, "my_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return INSTANCE;
    }

}
