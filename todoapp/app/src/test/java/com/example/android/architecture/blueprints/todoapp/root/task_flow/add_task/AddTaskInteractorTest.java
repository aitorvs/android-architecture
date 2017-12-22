package com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task;

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

public class AddTaskInteractorTest extends RibTestBasePlaceholder {

    private static final TaskViewModel TASK_VIEW_MODEL = new TaskViewModel("title", "desc");

    @Mock AddTaskInteractor.AddTaskPresenter presenter;
    @Mock AddTaskRouter router;
    @Mock TaskRepository taskRepository;

    private AddTaskInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        interactor = TestAddTaskInteractor.create(presenter, taskRepository, Task.EMPTY);
    }

    @Test
    public void whenAddTaskClicked_shouldCallPresenterClear() {
        when(presenter.task()).thenReturn(Observable.just(TASK_VIEW_MODEL));
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(presenter).clear();
    }

    @Test
    public void whenAddTaskClicked_shouldCallRepoNewTaskIfEditableTaskNotEmpty() {
        when(presenter.task()).thenReturn(Observable.just(TASK_VIEW_MODEL));
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(taskRepository).newTask(TASK_VIEW_MODEL.title, TASK_VIEW_MODEL.description);
    }

    @Test
    public void whenAddTaskClicked_shouldCallRepoUpdateIfEditableTaskNotEmpty() {
        Task editableTask = Task.create("title", "desc"); // shall match the TASK_VIEW_MODEL
        interactor.editableTask = editableTask;
        when(presenter.task()).thenReturn(Observable.just(TASK_VIEW_MODEL));
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(taskRepository).updateTask(editableTask);
    }
}
