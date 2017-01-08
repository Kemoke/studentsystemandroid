package kemoke.ius.studentsystemandroid.api;

import java.util.List;

import kemoke.ius.studentsystemandroid.models.GradeType;
import kemoke.ius.studentsystemandroid.models.Section;
import kemoke.ius.studentsystemandroid.models.Student;
import kemoke.ius.studentsystemandroid.models.StudentGrade;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InstructorActionsApi {

    @GET("/instructor/sections")
    Call<List<Section>> sections();

    @GET("/instructor/section/{id}/students")
    Call<List<Student>> sectionStudents(@Path("id") int sectionId);

    @POST("/instructor/gradetypes/{id}")
    Call<Section> setGradeTypes(@Path("id") int sectionId, @Body List<GradeType> gradeTypes);

    @GET("/instructor/gradetypes/{id}")
    Call<List<GradeType>> getGradeTypes(@Path("id") int sectionId);

    @GET("/instructor/grades/{id}")
    Call<List<StudentGrade>> getStudentGrades(@Path("id") int studentId);

    @POST("/instructor/grade/{id}")
    Call<StudentGrade> setStudentGrade(@Path("id") int studentId, @Body StudentGrade grade);
}
