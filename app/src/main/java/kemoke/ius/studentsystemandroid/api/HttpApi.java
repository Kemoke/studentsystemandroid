package kemoke.ius.studentsystemandroid.api;

import android.content.Context;

import kemoke.ius.studentsystemandroid.api.crud.CourseApi;
import kemoke.ius.studentsystemandroid.api.crud.DepartmentApi;
import kemoke.ius.studentsystemandroid.api.crud.InstructorApi;
import kemoke.ius.studentsystemandroid.api.crud.ProgramApi;
import kemoke.ius.studentsystemandroid.api.crud.SectionApi;
import kemoke.ius.studentsystemandroid.api.crud.StudentApi;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Contains retrofit implementations for api services.
 * Uses singleton pattern.
 */
@SuppressWarnings("unused")
public class HttpApi {
    private static Retrofit retrofit;
    private static AuthApi authApi;
    private static DepartmentApi departmentApi;
    private static ProgramApi programApi;
    private static CourseApi courseApi;
    private static SectionApi sectionApi;
    private static InstructorApi instructorApi;
    private static StudentApi studentApi;
    private static NewsApi newsApi;
    private static boolean isInit = false;

    public static void init(Context context){
        if(isInit) return;
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(context))
                .build();
        retrofit = new Retrofit.Builder().baseUrl("https://api.sis.kemoke.net")
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        isInit = true;
    }

    public static AuthApi AuthApi(){
        return getOrCreate(authApi, AuthApi.class);
    }

    public static DepartmentApi DepartmentApi(){
        return getOrCreate(departmentApi, DepartmentApi.class);
    }

    public static ProgramApi ProgramApi() {
        return getOrCreate(programApi, ProgramApi.class);
    }

    public static CourseApi CourseApi() {
        return getOrCreate(courseApi, CourseApi.class);
    }

    public static SectionApi SectionApi() {
        return getOrCreate(sectionApi, SectionApi.class);
    }

    public static InstructorApi InstructorApi() {
        return getOrCreate(instructorApi, InstructorApi.class);
    }

    public static StudentApi StudentApi() {
        return getOrCreate(studentApi, StudentApi.class);
    }

    public static NewsApi NewsApi(){
        return getOrCreate(newsApi, NewsApi.class);
    }

    private static <T> T getOrCreate(T service, Class<T> clazz){
        if(service == null){
            service = retrofit.create(clazz);
        }
        return service;
    }
}
