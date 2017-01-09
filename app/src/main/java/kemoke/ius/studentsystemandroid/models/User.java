package kemoke.ius.studentsystemandroid.models;

import android.os.Parcel;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class User extends BaseModel {
    @JsonField
    public String email;
    @JsonField
    public String password;
    @JsonField
    public String firstName;
    @JsonField
    public String lastName;

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
    }

    protected User(Parcel in) {
        super(in);
        this.email = in.readString();
        this.password = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}