package kemoke.ius.studentsystemandroid.models;

import android.os.Parcel;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonIgnore;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class Department extends BaseModel {
    public static final Creator<Department> CREATOR = new Creator<Department>() {
        @Override
        public Department createFromParcel(Parcel source) {
            return new Department(source);
        }

        @Override
        public Department[] newArray(int size) {
            return new Department[size];
        }
    };
    @JsonField
    public String name;
    @JsonField
    @JsonIgnore(ignorePolicy = JsonIgnore.IgnorePolicy.SERIALIZE_ONLY)
    public List<Program> programs;
    @JsonField
    @JsonIgnore(ignorePolicy = JsonIgnore.IgnorePolicy.SERIALIZE_ONLY)
    public List<Instructor> instructors;

    public Department(String name) {
        this.name = name;
    }

    public Department() {
    }

    protected Department(Parcel in) {
        super(in);
        this.name = in.readString();
        this.programs = in.createTypedArrayList(Program.CREATOR);
        this.instructors = in.createTypedArrayList(Instructor.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.name);
        dest.writeTypedList(this.programs);
        dest.writeTypedList(this.instructors);
    }
}
