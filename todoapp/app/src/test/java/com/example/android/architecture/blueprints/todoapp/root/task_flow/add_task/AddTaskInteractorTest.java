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

    private static final TaskViewModel TASK_VIEW_MODEL = new TaskViewModel("title", "desc");
    private static final Task TASK = Task.create(TASK_VIEW_MODEL.title, TASK_VIEW_MODEL.description);

    @Mock AddTaskInteractor.Listener listener;
    @Mock AddTaskInteractor.AddTaskPresenter presenter;
    @Mock AddTaskRouter router;
    @Mock TaskRepository taskRepository;

    private AddTaskInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        interactor = TestAddTaskInteractor.create(listener, presenter, taskRepository, TASK);
        when(taskRepository.getTaskById(TASK.getId())).thenReturn(Maybe.just(TASK));
        when(presenter.task()).thenReturn(Observable.just(TASK_VIEW_MODEL));
    }

    @Test
    public void whenAddTaskClicked_shouldCallPresenterClear() {
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(presenter).clear();
    }

    @Test
    public void whenAddTaskClicked_andTaskEmpty_shouldCallRepoNewTask() {
        when(taskRepository.getTaskById(TASK.getId())).thenReturn(Maybe.just(Task.EMPTY));
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(taskRepository).newTask(TASK_VIEW_MODEL.title, TASK_VIEW_MODEL.description);
    }

    @Test
    public void whenAddTaskClicked_andTaskNotEmpty_shouldCallRepoUpdate() {
        when(taskRepository.getTaskById(TASK.getId())).thenReturn(Maybe.just(TASK));
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(taskRepository).updateTask(TASK);
    }

    @Test
    public void whenBecomeActive_shouldSubscribeForTaskChanges() {
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(taskRepository).getTaskById(TASK.getId());
    }

    @Test
    public void whenBecomeActive_shouldCallPresenterWithEditableTask() {
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(presenter).editTask(TASK);
    }
}
