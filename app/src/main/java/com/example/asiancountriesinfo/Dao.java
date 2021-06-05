package com.example.asiancountriesinfo;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Query("SELECT * FROM Country")
    List<Country> getAll();

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    void insert(Country country);

    @Query("DELETE FROM Country")
    void deleteAll();

}
