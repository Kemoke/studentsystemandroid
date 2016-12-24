package kemoke.ius.studentsystemandroid.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenJson {
    public String jwt;
    public String type;
}