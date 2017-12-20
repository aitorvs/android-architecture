package com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.uber.rib.core.RibTestBasePlaceholder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TaskDetailInteractorTest extends RibTestBasePlaceholder {
    private static final Task TASK = new Task(1, "title", "description", false);

    @Mock TaskDetailInteractor.TaskDetailPresenter presenter;
    @Mock TaskDetailRouter router;

    private TaskDetailInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        interactor = TestTaskDetailInteractor.create(presenter, TASK);
    }

    @Test
    public void whenEditTaskEmits_shouldCallListener() {
        throw new RuntimeException("Implement test");
    }

    @Test
    public void whenAttached_shouldShowTask() {
        throw new RuntimeException("Implement test");
    }

    enum Notification {
        INSTANCE
    }
}
