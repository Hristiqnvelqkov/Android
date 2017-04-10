package com.apress.gerber.dragracing;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hriso on 4/4/2017.
 */

public class UserModel implements Parcelable {
    private int id;
    private String name;
    private String pass;
    private int mData;

    public UserModel() {

    }
    public UserModel(String name,int id,String pass){
        this.name=name;
        this.id=id;
        this.pass=pass;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mData);
        out.writeString(name);
        out.writeString(pass);
        out.writeInt(id);
    }

    public static final Parcelable.Creator<UserModel> CREATOR
            = new Parcelable.Creator<UserModel>() {
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        public UserModel[] newArray(int size) {
            return new UserModel[0];
        }
    };

    public UserModel(Parcel in) {
        mData = in.readInt();
        name=in.readString();
        pass=in.readString();
        id=in.readInt();
    }
}
