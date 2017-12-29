package com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TasksViewModelTest {
    @Test
    public void whenIsLoading_shouldNotBeSuccess() {
        TasksViewModel model = TasksViewModel.loading();
        assertThat(model.isSuccess(), is(false));
    }

    @Test
    public void whenIsLoading_shouldNotBeError() {
        TasksViewModel model = TasksViewModel.loading();
        assertThat(model.isError(), is(false));
    }

    @Test
    public void whenIsLoading_tasksShouldBeNull() {
        TasksViewModel model = TasksViewModel.loading();
        assertEquals(model.getData(), null);
    }

    @Test
    public void whenIsLoading_errorShouldBeNull() {
        TasksViewModel model = TasksViewModel.loading();
        assertEquals(model.getError(), null);
    }

    @Test
    public void whenIsSuccess_shouldNotBeError() {
        TasksViewModel model = TasksViewModel.success(TaskQueryResult.create(Collections.singletonList(Task.EMPTY)));
        assertThat(model.isError(), is(false));
    }

    @Test
    public void whenIsSuccess_shouldNotBeLoading() {
        TasksViewModel model = TasksViewModel.success(TaskQueryResult.create(Collections.singletonList(Task.EMPTY)));
        assertThat(model.isLoading(), is(false));
    }

    @Test
    public void whenIsSuccess_tasksShouldNotBeNull() {
        List<Task> tasks = Collections.singletonList(Task.EMPTY);
        TasksViewModel model = TasksViewModel.success(TaskQueryResult.create(tasks));
        assertThat(model.getData().getTasks(), is(tasks));
    }

    @Test
    public void whenIsError_shouldNotBeLoading() {
        TasksViewModel model = TasksViewModel.error(new Exception());
        assertThat(model.isLoading(), is(false));
    }

    @Test
    public void whenIsError_shouldNotBeSuccess() {
        TasksViewModel model = TasksViewModel.error(new Exception());
        assertThat(model.isSuccess(), is(false));
    }
}
