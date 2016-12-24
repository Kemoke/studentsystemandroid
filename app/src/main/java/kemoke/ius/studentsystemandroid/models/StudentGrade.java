package kemoke.ius.studentsystemandroid.models;

import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentGrade extends BaseModel {
    public static final Creator<StudentGrade> CREATOR = new Creator<StudentGrade>() {
        @Override
        public StudentGrade createFromParcel(Parcel source) {
            return new StudentGrade(source);
        }

        @Override
        public StudentGrade[] newArray(int size) {
            return new StudentGrade[size];
        }
    };
    public int score;
    public GradeType gradeType;
    public Student student;

    public StudentGrade(int score, kemoke.ius.studentsystemandroid.models.GradeType gradeType, kemoke.ius.studentsystemandroid.models.Student student) {
        this.score = score;
        this.gradeType = gradeType;
        this.student = student;
    }

    public StudentGrade() {
    }

    protected StudentGrade(Parcel in) {
        super(in);
        this.score = in.readInt();
        this.gradeType = in.readParcelable(kemoke.ius.studentsystemandroid.models.GradeType.class.getClassLoader());
        this.student = in.readParcelable(kemoke.ius.studentsystemandroid.models.Student.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.score);
        dest.writeParcelable(this.gradeType, flags);
        dest.writeParcelable(this.student, flags);
    }
}
