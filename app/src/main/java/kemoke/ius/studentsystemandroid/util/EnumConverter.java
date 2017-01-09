package kemoke.ius.studentsystemandroid.util;

import com.bluelinelabs.logansquare.typeconverters.IntBasedTypeConverter;

import kemoke.ius.studentsystemandroid.models.CurriculumCourse;

public class EnumConverter extends IntBasedTypeConverter<CurriculumCourse.ElectiveType> {

    @Override
    public CurriculumCourse.ElectiveType getFromInt(int i) {
        return CurriculumCourse.ElectiveType.values()[i];
    }

    @Override
    public int convertToInt(CurriculumCourse.ElectiveType object) {
        return object.ordinal();
    }
}