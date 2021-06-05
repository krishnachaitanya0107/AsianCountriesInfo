package com.example.asiancountriesinfo;

public class Model {

    String countryName,countryCapital,countryRegion,countrySubRegion,
            flagLink,borders,languages;
    long population;

    public Model(String countryName,String countryCapital,String countryRegion,
                 String countrySubRegion,String flagLink,String borders,
                 String languages,long population)
    {
        this.borders=borders;
        this.countryCapital=countryCapital;
        this.countryName=countryName;
        this.countryRegion=countryRegion;
        this.countrySubRegion=countrySubRegion;
        this.flagLink=flagLink;
        this.borders=borders;
        this.languages=languages;
        this.population=population;
    }

    public long getPopulation() {
        return population;
    }

    public String getCountryRegion() {
        return countryRegion;
    }

    public String getCountrySubRegion() {
        return countrySubRegion;
    }

    public String getFlagLink() {
        return flagLink;
    }

    public String getBorders() {
        return borders;
    }

    public String getLanguages() {
        return languages;
    }

    public String getCountryCapital() {
        return countryCapital;
    }

    public String getCountryName() {
        return countryName;
    }
}
