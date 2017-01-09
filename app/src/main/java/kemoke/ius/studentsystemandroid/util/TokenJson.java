package kemoke.ius.studentsystemandroid.util;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class TokenJson {
    @JsonField
    public String jwt;
    @JsonField
    public String type;
}