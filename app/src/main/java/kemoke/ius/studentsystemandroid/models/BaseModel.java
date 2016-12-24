package kemoke.ius.studentsystemandroid.models;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class BaseModel implements Parcelable {
    public int id;

    public BaseModel() {
    }

    protected BaseModel(Parcel in) {
        this.id = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseModel baseModel = (BaseModel) o;

        return id == baseModel.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
