package kemoke.ius.studentsystemandroid.models;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class Article {
    @JsonField
    public String title;
    @JsonField
    public String text;
    @JsonField
    public String imgUri;
    @JsonField
    public String link;
}
