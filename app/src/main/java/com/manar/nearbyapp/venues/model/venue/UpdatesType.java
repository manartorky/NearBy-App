package com.manar.nearbyapp.venues.model.venue;

import android.os.Parcel;
import android.os.Parcelable;


public class UpdatesType implements Parcelable {
    private int drawable;
    private String name;
    private boolean checked;

    public UpdatesType(String name, int drawable) {
        this.drawable = drawable;
        this.name = name;
    }

    protected UpdatesType(Parcel in) {
        drawable = in.readInt();
        name = in.readString();
        checked = in.readByte() != 0;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    public int getDrawable() {
        return drawable;
    }

    public String getName() {
        return name;
    }

    public static final Creator<UpdatesType> CREATOR = new Creator<UpdatesType>() {
        @Override
        public UpdatesType createFromParcel(Parcel in) {
            return new UpdatesType(in);
        }

        @Override
        public UpdatesType[] newArray(int size) {
            return new UpdatesType[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UpdatesType updatesType = (UpdatesType) o;
        return name.equals(updatesType.name) &&
                drawable == updatesType.drawable;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(drawable);
        dest.writeString(name);
        dest.writeByte((byte) (checked ? 1 : 0));
    }
}
