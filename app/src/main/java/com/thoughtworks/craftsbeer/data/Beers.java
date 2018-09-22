package com.thoughtworks.craftsbeer.data;

import android.support.annotation.NonNull;


public class Beers implements Comparable<Beers> {



    private String ounces;

    private String id;

    private String style;

    private String abv;

    private String name;

    private String ibu;

    int quantity;

    public String getOunces() {
        return ounces;
    }

    public void setOunces(String ounces) {
        this.ounces = ounces;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIbu() {
        return ibu;
    }

    public void setIbu(String ibu) {
        this.ibu = ibu;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ClassPojo [ounces = " + ounces + ", id = " + id + ", style = " + style + ", abv = " + abv + ", name = " + name + ", ibu = " + ibu + "]";
    }

    @Override
    public int compareTo(@NonNull Beers o) {
        if (this.abv.toLowerCase().equals(o.abv.toLowerCase())) {
            return this.ounces.toLowerCase().compareTo(o.abv.toLowerCase());
        } else {
            return this.ounces.toLowerCase().compareTo(o.abv.toLowerCase());
        }


    }

}
