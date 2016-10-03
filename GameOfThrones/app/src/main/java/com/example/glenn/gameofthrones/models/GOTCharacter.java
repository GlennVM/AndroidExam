package com.example.glenn.gameofthrones.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Glenn on 10/08/2016.
 */
public class GOTCharacter implements Parcelable {

    private String name;
    private String description;
    private int image;

    public GOTCharacter(String name, String description, int image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(image);
    }
}
