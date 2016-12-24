package kemoke.ius.studentsystemandroid.models;

import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeIndex extends BaseModel {
    public static final Creator<TimeIndex> CREATOR = new Creator<TimeIndex>() {
        @Override
        public TimeIndex createFromParcel(Parcel source) {
            return new TimeIndex(source);
        }

        @Override
        public TimeIndex[] newArray(int size) {
            return new TimeIndex[size];
        }
    };
    public int day;
    public int startTime;
    public int endTime;
    public Section section;

    public TimeIndex(int day, int startTime, int endTime, kemoke.ius.studentsystemandroid.models.Section section) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.section = section;
    }

    public TimeIndex() {
    }

    protected TimeIndex(Parcel in) {
        super(in);
        this.day = in.readInt();
        this.startTime = in.readInt();
        this.endTime = in.readInt();
        this.section = in.readParcelable(kemoke.ius.studentsystemandroid.models.Section.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.day);
        dest.writeInt(this.startTime);
        dest.writeInt(this.endTime);
        dest.writeParcelable(this.section, flags);
    }
}
