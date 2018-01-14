package com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task;

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

public class AddTaskInteractorTest extends RibTestBasePlaceholder {

    private static final Task TASK = Task.create("title", "desc");

    @Mock AddTaskInteractor.Listener listener;
    @Mock AddTaskInteractor.AddTaskPresenter presenter;
    @Mock AddTaskRouter router;
    @Mock TaskRepository taskRepository;

    private AddTaskInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        interactor = TestAddTaskInteractor.create(listener, presenter, taskRepository, TASK);
        // These are called when interactor is attached
        when(taskRepository.getTaskById(TASK.getId())).thenReturn(Maybe.just(TASK));
        when(presenter.editOrAddTask(TASK)).thenReturn(Observable.just(TASK));
    }

    @Test
    public void whenAddTaskClicked_shouldCallUpdateTask() {
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(taskRepository).updateTask(TASK);
    }

    @Test
    public void whenAddTaskClicked_shouldCallListenerOnComplete() {
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(listener).onActionCompleted();
    }

    @Test
    public void whenBecomeActive_shouldSubscribeForTaskChanges() {
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(taskRepository).getTaskById(TASK.getId());
    }

    @Test
    public void whenBecomeActive_shouldCallPresenterWithEditableTask() {
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(presenter).editOrAddTask(TASK);
    }
}
