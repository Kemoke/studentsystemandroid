package kemoke.ius.studentsystemandroid.models;

import android.os.Parcel;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonIgnore;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class Course extends BaseModel {
    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel source) {
            return new Course(source);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };
    @JsonField
    public String name;
    @JsonField
    public String code;
    @JsonField
    public double ects;
    @JsonField
    public Program program;
    @JsonField
    @JsonIgnore(ignorePolicy = JsonIgnore.IgnorePolicy.SERIALIZE_ONLY)
    public List<Section> sections;

    public Course(String name, String code, double ects, Program program) {
        this.name = name;
        this.code = code;
        this.ects = ects;
        this.program = program;
    }

    public Course() {
    }

    protected Course(Parcel in) {
        super(in);
        this.name = in.readString();
        this.code = in.readString();
        this.ects = in.readDouble();
        this.program = in.readParcelable(Program.class.getClassLoader());
        this.sections = in.createTypedArrayList(Section.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.name);
        dest.writeString(this.code);
        dest.writeDouble(this.ects);
        dest.writeParcelable(this.program, flags);
        dest.writeTypedList(this.sections);
    }
}
