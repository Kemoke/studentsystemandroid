package kemoke.ius.studentsystemandroid.models;

import android.os.Parcel;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class CurriculumCourse extends BaseModel {
    @JsonField
    public int year;
    @JsonField
    public int semester;
    @JsonField
    public ElectiveType elective;
    @JsonField
    public Program program;
    @JsonField
    public Course course;

    public CurriculumCourse() {
    }

    public CurriculumCourse(int year, int semester, ElectiveType elective, Program program, Course course) {
        this.year = year;
        this.semester = semester;
        this.elective = elective;
        this.program = program;
        this.course = course;
    }

    protected CurriculumCourse(Parcel in) {
        super(in);
        this.year = in.readInt();
        this.semester = in.readInt();
        int tmpElective = in.readInt();

        this.elective = tmpElective == -1 ? null : CurriculumCourse.ElectiveType.values()[tmpElective];
        this.program = in.readParcelable(kemoke.ius.studentsystemandroid.models.Program.class.getClassLoader());
        this.course = in.readParcelable(kemoke.ius.studentsystemandroid.models.Course.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.year);
        dest.writeInt(this.semester);
        dest.writeInt(this.elective == null ? -1 : this.elective.ordinal());
        dest.writeParcelable(this.program, flags);
        dest.writeParcelable(this.course, flags);
    }

    public static final Creator<CurriculumCourse> CREATOR = new Creator<CurriculumCourse>() {
        @Override
        public CurriculumCourse createFromParcel(Parcel source) {
            return new CurriculumCourse(source);
        }

        @Override
        public CurriculumCourse[] newArray(int size) {
            return new CurriculumCourse[size];
        }
    };

    public enum ElectiveType {
        No,
        University,
        Faculty,
        Program
    }
}
