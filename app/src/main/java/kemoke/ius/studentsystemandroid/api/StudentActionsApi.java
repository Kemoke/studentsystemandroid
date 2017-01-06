package kemoke.ius.studentsystemandroid.api;

import java.util.List;

import kemoke.ius.studentsystemandroid.models.CurriculumCourse;
import kemoke.ius.studentsystemandroid.models.Section;
import kemoke.ius.studentsystemandroid.models.StudentGrade;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface StudentActionsApi {
    @POST("/student/register")
    Call<Section> register(@Body Section section);

    @POST("/student/unregister")
    Call<Section> unregister(@Body Section section);

    @GET("/student/courses")
    Call<List<CurriculumCourse>> courses();

    @GET("/student/sections")
    Call<List<Section>> sections();

    @GET("/student/grades")
    Call<List<StudentGrade>> grades();

    @GET("/student/sections/registered")
    Call<List<Section>> registeredSections();
}
