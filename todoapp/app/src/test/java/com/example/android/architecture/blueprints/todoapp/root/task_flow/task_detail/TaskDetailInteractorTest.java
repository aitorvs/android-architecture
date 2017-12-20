package com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.data.TaskRepository;
import com.uber.rib.core.InteractorHelper;
import com.uber.rib.core.RibTestBasePlaceholder;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskDetailInteractorTest extends RibTestBasePlaceholder {
    private static final Task TASK = new Task(1, "title", "description", false);

    @Mock TaskDetailInteractor.TaskDetailPresenter presenter;
    @Mock TaskDetailRouter router;
    @Mock TaskRepository taskRepository;

    private TaskDetailInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        interactor = TestTaskDetailInteractor.create(presenter, TASK, taskRepository);
    }

    @Test
    public void whenEmitsCompleteTask_shouldCallRepoComplete() {
        when(presenter.completeTask()).thenReturn(Observable.just(Notification.INSTANCE));
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(taskRepository).completeTask(TASK);
    }

    @Test
    public void whenEmitsActivateTask_shouldCallRepoActivate() {
        when(presenter.completeTask()).thenReturn(Observable.just(Notification.INSTANCE));
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(taskRepository).activateTask(TASK);
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
