package com.example.android.architecture.blueprints.todoapp.root.tasks;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.data.TaskRepository;
import com.uber.rib.core.RibTestBasePlaceholder;
import com.uber.rib.core.InteractorHelper;
import io.reactivex.Observable;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TasksInteractorTest extends RibTestBasePlaceholder {

    private static final Task TASK = new Task(1, "title", "description", false);
    private static final List<Task> TASKS = Collections.singletonList(TASK);

    @Mock TasksInteractor.TasksPresenter presenter;
    @Mock TasksInteractor.Listener listener;
    @Mock TasksRouter router;
    @Mock TaskRepository taskRepository;

    private TasksInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        interactor = TestTasksInteractor.create(presenter, listener, taskRepository);
    }

    @Test
    public void whenTaskRepoEmitsTasks_shouldCallPresenterShowStatistics() {
        when(taskRepository.getTasks()).thenReturn(Observable.just(TASKS));
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(presenter).showTasks(TASKS);
    }

    @Test
    public void whenEmitsAddTask_shouldCallOnAddTaskListener() {
        when(presenter.addTask()).thenReturn(Observable.just(Notification.INSTANCE));
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(listener).onAddNewTask();
    }

    @Test
    public void whenEmitsSelectedTask_shouldCallOnTaskSelectedListener() {
        when(presenter.task()).thenReturn(Observable.just(TASK));
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(listener).onTaskSelected(TASK);
    }

    @Test
    public void whenEmitsTaskToComplete_shouldCallCompleteTaskRepo() {
        when(presenter.competedTask()).thenReturn(Observable.just(TASK));
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(taskRepository).completeTask(TASK);
    }

    @Test
    public void whenEmitsTaskToActivate_shouldCallActivateTaskRepo() {
        when(presenter.activateTask()).thenReturn(Observable.just(TASK));
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(taskRepository).activateTask(TASK);
    }

    /**
     * Simple instance
     */
    enum Notification {
        INSTANCE
    }
}
