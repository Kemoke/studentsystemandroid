package kemoke.ius.studentsystemandroid.models;

import android.os.Parcel;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonIgnore;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class Section extends BaseModel {
    @JsonField
    public int number;
    @JsonField
    public int capacity;
    @JsonField
    public Course course;
    @JsonField
    public Instructor instructor;
    @JsonField
    @JsonIgnore(ignorePolicy = JsonIgnore.IgnorePolicy.SERIALIZE_ONLY)
    public List<GradeType> gradeTypes;
    @JsonField
    @JsonIgnore(ignorePolicy = JsonIgnore.IgnorePolicy.SERIALIZE_ONLY)
    public List<Student> students;
    @JsonField
    @JsonIgnore(ignorePolicy = JsonIgnore.IgnorePolicy.SERIALIZE_ONLY)
    public List<TimeIndex> timeTable;

    public Section(int number, int capacity, kemoke.ius.studentsystemandroid.models.Course course, kemoke.ius.studentsystemandroid.models.Instructor instructor, List<TimeIndex> timeTable) {
        this.number = number;
        this.capacity = capacity;
        this.course = course;
        this.instructor = instructor;
        this.timeTable = timeTable;
    }

    public Section() {
    }

    protected Section(Parcel in) {
        super(in);
        this.number = in.readInt();
        this.capacity = in.readInt();
        this.course = in.readParcelable(kemoke.ius.studentsystemandroid.models.Course.class.getClassLoader());
        this.instructor = in.readParcelable(kemoke.ius.studentsystemandroid.models.Instructor.class.getClassLoader());
        this.gradeTypes = in.createTypedArrayList(GradeType.CREATOR);
        this.students = in.createTypedArrayList(Student.CREATOR);
        this.timeTable = in.createTypedArrayList(TimeIndex.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.number);
        dest.writeInt(this.capacity);
        dest.writeParcelable(this.course, flags);
        dest.writeParcelable(this.instructor, flags);
        dest.writeTypedList(this.gradeTypes);
        dest.writeTypedList(this.students);
        dest.writeTypedList(this.timeTable);
    }

    public static final Creator<Section> CREATOR = new Creator<Section>() {
        @Override
        public Section createFromParcel(Parcel source) {
            return new Section(source);
        }

        @Override
        public Section[] newArray(int size) {
            return new Section[size];
        }
    };
}
