package com.example.recipes_app;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

public class Recipe implements Parcelable {
    private String nume;
    private String descriere;
    private Boolean vegetariana;
    private TipReteta tipReteta;

    private String imageUrl;

    public Recipe() {
    }

    public Recipe(String nume, String descriere, boolean vegetarian, TipReteta tipReteta,String imageUrl) {
        this.nume = nume;
        this.descriere = descriere;
        this.vegetariana = vegetarian;
        this.tipReteta = tipReteta;
        this.imageUrl=imageUrl;
    }




    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public boolean isVegetarian() {
        return vegetariana;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetariana = vegetarian;
    }

    public TipReteta getTipReteta() {
        return tipReteta;
    }

    public void setTipReteta(TipReteta tipReteta) {
        this.tipReteta = tipReteta;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "nume='" + nume + '\'' +
                ", descriere='" + descriere + '\'' +
                ", vegetariana=" + vegetariana +
                ", tipReteta=" + tipReteta +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(nume);
        dest.writeString(descriere);
        if(vegetariana==null){
            dest.writeByte((byte) 0);
        }
        else {
            dest.writeByte((byte) 1);
            dest.writeByte((byte) (vegetariana?1:0));
        }
        if (tipReteta==null){
            dest.writeByte((byte) 0);
        }
        else {
            dest.writeByte((byte) 1);
            dest.writeString(tipReteta.name());
        }
        if (imageUrl==null){
            dest.writeByte((byte) 0);
        }
        else {
            dest.writeByte((byte) 1);
            dest.writeString(imageUrl);
        }
    }


    protected Recipe(Parcel in) {
        nume = in.readString();
        descriere = in.readString();
        byte tmpVegetariana = in.readByte();
        vegetariana = tmpVegetariana == 0 ? null : (in.readByte() == 1);
        byte tmpTipReteta=in.readByte();
        tipReteta=tmpTipReteta==0?null: (TipReteta.valueOf(in.readString()));
        byte tmpUrl=in.readByte();
        if (tmpUrl==0){
            imageUrl=null;
        }
        else {
            imageUrl=in.readString();
        }
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

}
