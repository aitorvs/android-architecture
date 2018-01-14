package com.example.android.architecture.blueprints.todoapp.data;

import io.reactivex.Observable;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * This is a very simple {@link Retrofit} service. This service is not meant to represent a proper design for a
 * RESTFUL API.
 * I just followed the {@link TaskDataSource} API to avoid mappings.
 *
 * A real app would have a properly design REST API and perform more map operations down the stream
 */
interface TaskService {

    @GET("tasks")
    Observable<Result<List<Task>>> getTasks();

    @GET("tasks/completed")
    Observable<Result<List<Task>>> getCompletedTasks();

    @GET("tasks/active")
    Observable<Result<List<Task>>> getActiveTasks();

    @GET("task/{id}")
    Observable<Result<List<Task>>> getTaskById(@Path("id") String id);

    @DELETE("tasks")
    Call<Void> deleteAll();

    @DELETE("tasks/completed")
    Call<Void> deleteCompletedTasks();

    @DELETE("tasks/active")
    Call<Void> deleteActiveTasks();

    @DELETE("tasks/{id}")
    Call<Void> deleteTask(@Path("id") String id);

    @PUT("task/{id}/complete")
    Call<Void> completeTask(@Path("id") String id);

    @PUT("task/{id}/activate")
    Call<Void> activateTask(@Path("id") String id);

    @POST("task/update")
    Call<Void> updateTask(@Body Task task);
}
