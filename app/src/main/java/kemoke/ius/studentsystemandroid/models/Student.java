package kemoke.ius.studentsystemandroid.models;

import android.os.Parcel;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonIgnore;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class Student extends User {
    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
    @JsonField
    public String studentId;
    @JsonField
    public int semester;
    @JsonField
    public int year;
    @JsonField
    public double cgpa;
    @JsonField
    public Program program;
    @JsonField
    @JsonIgnore(ignorePolicy = JsonIgnore.IgnorePolicy.SERIALIZE_ONLY)
    public List<StudentGrade> grades;
    @JsonField
    @JsonIgnore(ignorePolicy = JsonIgnore.IgnorePolicy.SERIALIZE_ONLY)
    public List<Section> sections;

    public Student(String email, String password, String firstName, String lastName, String studentId, int semester, int year, Double cgpa, kemoke.ius.studentsystemandroid.models.Program program) {
        super(email, password, firstName, lastName);
        this.studentId = studentId;
        this.semester = semester;
        this.year = year;
        this.cgpa = cgpa;
        this.program = program;
    }

    public Student() {
    }

    protected Student(Parcel in) {
        super(in);
        this.studentId = in.readString();
        this.semester = in.readInt();
        this.year = in.readInt();
        this.cgpa = (Double) in.readValue(Double.class.getClassLoader());
        this.program = in.readParcelable(kemoke.ius.studentsystemandroid.models.Program.class.getClassLoader());
        this.grades = in.createTypedArrayList(StudentGrade.CREATOR);
        this.sections = in.createTypedArrayList(Section.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.studentId);
        dest.writeInt(this.semester);
        dest.writeInt(this.year);
        dest.writeValue(this.cgpa);
        dest.writeParcelable(this.program, flags);
        dest.writeTypedList(this.grades);
        dest.writeTypedList(this.sections);
    }
}
