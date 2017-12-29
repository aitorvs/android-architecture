package com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.data.TaskRepository;
import com.uber.rib.core.InteractorHelper;
import com.uber.rib.core.RibTestBasePlaceholder;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskDetailInteractorTest extends RibTestBasePlaceholder {
    private static final Task TASK = Task.create("title", "description");

    @Mock TaskDetailInteractor.TaskDetailPresenter presenter;
    @Mock TaskDetailRouter router;
    @Mock TaskRepository taskRepository;
    @Mock TaskDetailInteractor.Listener listener;

    private TaskDetailInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        interactor = TestTaskDetailInteractor.create(presenter, TASK, taskRepository, listener);
        // default streams
        when(presenter.completeTask()).thenReturn(Observable.just(Notification.INSTANCE));
        when(presenter.activateTask()).thenReturn(Observable.just(Notification.INSTANCE));
        when(presenter.editTask()).thenReturn(Observable.just(Notification.INSTANCE));
        when(presenter.deleteTask()).thenReturn(Observable.just(Notification.INSTANCE));
        when(taskRepository.getTaskById(TASK.getId())).thenReturn(Maybe.just(TASK));
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
    public void whenEditTaskEmits_shouldCallRepoGetTaskById() {
        when(presenter.editTask()).thenReturn(Observable.just(Notification.INSTANCE));
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(taskRepository).getTaskById(TASK.getId());
    }

    @Test
    public void whenEditTaskEmits_shouldCallOnEditListener() {
        when(presenter.editTask()).thenReturn(Observable.just(Notification.INSTANCE));
        when(taskRepository.getTaskById(TASK.getId())).thenReturn(Maybe.just(TASK));
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(listener).editTask(TASK);
    }

    @Test
    public void whenAttached_shouldShowTask() {
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(presenter).showDetailTask(TASK);
    }

    @Test
    public void whenAttached_getTaskByIdTwice() {
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(taskRepository).getTaskById(TASK.getId());
    }

    @Test
    public void whenAttached_shouldSubscribeToEditEvents() {
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(presenter).editTask();
    }

    @Test
    public void whenAttached_shouldSubscribeToCompleteTaskEvents() {
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(presenter).completeTask();
    }

    @Test
    public void whenAttached_shouldSubscribeToActivateTaskEvents() {
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(presenter).activateTask();
    }

    enum Notification {
        INSTANCE
    }
}
