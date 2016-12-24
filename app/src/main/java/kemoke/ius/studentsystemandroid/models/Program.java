package kemoke.ius.studentsystemandroid.models;

import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Program extends BaseModel {
    public static final Creator<Program> CREATOR = new Creator<Program>() {
        @Override
        public Program createFromParcel(Parcel source) {
            return new Program(source);
        }

        @Override
        public Program[] newArray(int size) {
            return new Program[size];
        }
    };
    public String name;
    @JsonIgnoreProperties({"program", "programs"})
    public Department department;
    public List<Student> students;
    public List<Course> courses;
    public List<CurriculumCourse> curriculum;

    public Program(String name, kemoke.ius.studentsystemandroid.models.Department department) {
        this.name = name;
        this.department = department;
    }

    public Program() {
    }

    protected Program(Parcel in) {
        super(in);
        this.name = in.readString();
        this.department = in.readParcelable(kemoke.ius.studentsystemandroid.models.Department.class.getClassLoader());
        this.students = in.createTypedArrayList(Student.CREATOR);
        this.courses = in.createTypedArrayList(Course.CREATOR);
        this.curriculum = in.createTypedArrayList(CurriculumCourse.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.name);
        dest.writeParcelable(this.department, flags);
        dest.writeTypedList(this.students);
        dest.writeTypedList(this.courses);
        dest.writeTypedList(this.curriculum);
    }
}
