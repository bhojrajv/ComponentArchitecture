package com.example.architecturecomp;

import androidx.room.PrimaryKey;

@androidx.room.Entity(tableName = "Notes_table")
public class Entity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String priorty;

    public Entity(String title, String description, String priorty) {
        this.title = title;
        this.description = description;
        this.priorty = priorty;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriorty() {
        return priorty;
    }
}
