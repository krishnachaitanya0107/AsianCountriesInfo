package com.example.asiancountriesinfo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Country implements Serializable {

    @NonNull
    @PrimaryKey (autoGenerate = true)
    private int id;

    @ColumnInfo (name = "name")
    private String name;

    @ColumnInfo (name = "capital")
    private String capital;

    @ColumnInfo (name = "region")
    private String region;

    @ColumnInfo (name = "subRegion")
    private String subRegion;

    @ColumnInfo (name = "flagLink")
    private String flagLink;

    @ColumnInfo (name = "borders")
    private String borders;

    @ColumnInfo (name = "languages")
    private String languages;

    @ColumnInfo (name = "population")
    private long population;

    public String getFlagLink() {
        return flagLink;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public long getPopulation() {
        return population;
    }

    public String getBorders() {
        return borders;
    }

    public String getCapital() {
        return capital;
    }

    public String getLanguages() {
        return languages;
    }

    public String getRegion() {
        return region;
    }

    public String getSubRegion() {
        return subRegion;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBorders(String borders) {
        this.borders = borders;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public void setFlagLink(String flagLink) {
        this.flagLink = flagLink;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setSubRegion(String subRegion) {
        this.subRegion = subRegion;
    }

}
