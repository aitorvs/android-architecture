package com.example.android.architecture.blueprints.todoapp.data;

import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.Calls;
import retrofit2.mock.MockRetrofit;

class MockTaskService implements TaskService {
    private static final Response<ResponseBody> SUCCESS_EMPTY =
        Response.success(ResponseBody.create(MediaType.parse("application/json"), ""));

    private final BehaviorDelegate<TaskService> delegate;
    private static final Map<String, Task> DATA;

    static {
        DATA = new LinkedHashMap<>(1);
        addTask("remote #1", "remote desc #1");
        addTask("remote #2", "remote desc #2");
    }

    MockTaskService(MockRetrofit mockRetrofit) {
        delegate = mockRetrofit.create(TaskService.class);
    }

    @Override
    public Observable<Result<List<Task>>> getTasks() {
        return delegate.returning(Calls.response(Collections.unmodifiableList(new ArrayList<>(DATA.values()))))
            .getTasks();
    }

    @Override
    public Observable<Result<List<Task>>> getCompletedTasks() {
        List<Task> active = new ArrayList<>();
        for (Map.Entry<String, Task> entry : DATA.entrySet()) {
            if (entry.getValue().isDone()) {
                active.add(entry.getValue());
            }
        }

        return delegate.returning(Calls.response(Collections.unmodifiableList(active))).getActiveTasks();
    }

    @Override
    public Observable<Result<List<Task>>> getActiveTasks() {
        List<Task> active = new ArrayList<>();
        for (Map.Entry<String, Task> entry : DATA.entrySet()) {
            if (!entry.getValue().isDone()) {
                active.add(entry.getValue());
            }
        }

        return delegate.returning(Calls.response(Collections.unmodifiableList(active))).getActiveTasks();
    }

    @Override
    public Observable<Result<List<Task>>> getTaskById(String id) {
        return delegate.returning(Calls.response(Collections.singletonList(DATA.get(id)))).getTaskById(id);
    }

    @Override
    public Call<Void> deleteAll() {
        DATA.clear();
        return delegate.returning(Calls.response(SUCCESS_EMPTY)).deleteAll();

    }

    @Override
    public Call<Void> deleteCompletedTasks() {
        Iterator<Map.Entry<String, Task>> it = DATA.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Task> entry = it.next();
            if (entry.getValue().isDone()) {
                it.remove();
            }
        }

        return delegate.returning(Calls.response(SUCCESS_EMPTY)).deleteCompletedTasks();
    }

    @Override
    public Call<Void> deleteActiveTasks() {
        Iterator<Map.Entry<String, Task>> it = DATA.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Task> entry = it.next();
            if (!entry.getValue().isDone()) {
                it.remove();
            }
        }

        return delegate.returning(Calls.response(SUCCESS_EMPTY)).deleteCompletedTasks();
    }

    @Override
    public Call<Void> deleteTask(String id) {
        DATA.remove(id);
        return delegate.returning(Calls.response(SUCCESS_EMPTY)).deleteTask(id);
    }

    @Override
    public Call<Void> completeTask(String id) {
        Task task = DATA.get(id);
        if (task != null) {
            Task completedTask = new Task(task.getId(), task.getTitle(), task.getDescription(), true);
            DATA.put(completedTask.getId(), completedTask);
        }
        return delegate.returning(Calls.response(SUCCESS_EMPTY)).completeTask(id);
    }

    @Override
    public Call<Void> activateTask(String id) {
        Task task = DATA.get(id);
        if (task != null) {
            Task activeTask = new Task(task.getId(), task.getTitle(), task.getDescription(), false);
            DATA.put(activeTask.getId(), activeTask);
        }
        return delegate.returning(Calls.response(SUCCESS_EMPTY)).completeTask(id);
    }

    @Override
    public Call<Void> updateTask(Task task) {
        // updates/insert the task
        DATA.put(task.getId(), task);
        return delegate.returning(Calls.response(SUCCESS_EMPTY)).updateTask(task);
    }

    private static void addTask(String title, String desc) {
        Task task = Task.create(title, desc);
        DATA.put(task.getId(), task);
    }
}
