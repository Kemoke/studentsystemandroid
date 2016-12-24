package kemoke.ius.studentsystemandroid.models;

import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GradeType extends BaseModel {
    public static final Creator<GradeType> CREATOR = new Creator<GradeType>() {
        @Override
        public GradeType createFromParcel(Parcel source) {
            return new GradeType(source);
        }

        @Override
        public GradeType[] newArray(int size) {
            return new GradeType[size];
        }
    };
    public String name;
    public int value;
    public Section section;

    public GradeType(String name, int value, kemoke.ius.studentsystemandroid.models.Section section) {
        this.name = name;
        this.value = value;
        this.section = section;
    }

    public GradeType() {
    }

    protected GradeType(Parcel in) {
        super(in);
        this.name = in.readString();
        this.value = in.readInt();
        this.section = in.readParcelable(kemoke.ius.studentsystemandroid.models.Section.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.name);
        dest.writeInt(this.value);
        dest.writeParcelable(this.section, flags);
    }
}
