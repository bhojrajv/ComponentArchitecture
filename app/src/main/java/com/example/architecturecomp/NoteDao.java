package com.example.architecturecomp;

import android.icu.lang.UCharacter;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void inset(Entity entity);
    @Delete
    void delete(Entity entity);
    @Update
    void update(Entity entity);
    @Query("DELETE FROM notes_table")
    void deletAll();
    @Query("SELECT * FROM notes_table ORDER BY priorty DESC")
    LiveData<List<Entity>>getAll();
}
