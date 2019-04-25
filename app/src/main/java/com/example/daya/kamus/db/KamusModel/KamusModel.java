package com.example.daya.kamus.db.KamusModel;

import android.os.Parcel;
import android.os.Parcelable;

public class KamusModel implements Parcelable {
    private int id;
    private String WORD;
    private String TRANSLATE;

    public KamusModel() {
    }

    public KamusModel(String WORD, String TRANSLATE) {
        this.WORD = WORD;
        this.TRANSLATE = TRANSLATE;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWORD() {
        return WORD;
    }

    public void setWORD(String WORD) {
        this.WORD = WORD;
    }

    public String getTRANSLATE() {
        return TRANSLATE;
    }

    public void setTRANSLATE(String TRANSLATE) {
        this.TRANSLATE = TRANSLATE;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.WORD);
        dest.writeString(this.TRANSLATE);
    }

    private KamusModel(Parcel in) {
        this.id = in.readInt();
        this.WORD = in.readString();
        this.TRANSLATE = in.readString();
    }

    public static final Parcelable.Creator<KamusModel> CREATOR = new Parcelable.Creator<KamusModel>() {
        @Override
        public KamusModel createFromParcel(Parcel source) {
            return new KamusModel(source);
        }

        @Override
        public KamusModel[] newArray(int size) {
            return new KamusModel[size];
        }
    };
}
