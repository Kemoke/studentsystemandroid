package kemoke.ius.studentsystemandroid.models;

import android.os.Parcel;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonIgnore;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class Instructor extends User {
    public static final Creator<Instructor> CREATOR = new Creator<Instructor>() {
        @Override
        public Instructor createFromParcel(Parcel source) {
            return new Instructor(source);
        }

        @Override
        public Instructor[] newArray(int size) {
            return new Instructor[size];
        }
    };
    @JsonField
    public String instructorId;
    @JsonField
    public Department department;
    @JsonField
    @JsonIgnore(ignorePolicy = JsonIgnore.IgnorePolicy.SERIALIZE_ONLY)
    public List<Section> sections;

    public Instructor(String email, String password, String firstName, String lastName, String instructorId, kemoke.ius.studentsystemandroid.models.Department department) {
        super(email, password, firstName, lastName);
        this.instructorId = instructorId;
        this.department = department;
    }

    public Instructor() {
    }

    protected Instructor(Parcel in) {
        super(in);
        this.instructorId = in.readString();
        this.department = in.readParcelable(kemoke.ius.studentsystemandroid.models.Department.class.getClassLoader());
        this.sections = in.createTypedArrayList(Section.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.instructorId);
        dest.writeParcelable(this.department, flags);
        dest.writeTypedList(this.sections);
    }
}
