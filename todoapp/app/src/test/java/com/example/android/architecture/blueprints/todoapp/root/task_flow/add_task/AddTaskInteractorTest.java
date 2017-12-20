package com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task;

import android.support.v4.util.Pair;
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

    private static final Pair<String, String> PAIR = new Pair<>("title", "desc");

    @Mock AddTaskInteractor.AddTaskPresenter presenter;
    @Mock AddTaskRouter router;
    @Mock TaskRepository taskRepository;

    private AddTaskInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        interactor = TestAddTaskInteractor.create(presenter, taskRepository);
    }

    @Test
    public void whenAddTaskClicked_shouldCallRepoNewTask() {
        when(presenter.task()).thenReturn(Observable.just(PAIR));
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(presenter).clear();
    }

    @Test
    public void whenAddTaskClicked_shouldCallPresenterClear() {
        when(presenter.task()).thenReturn(Observable.just(PAIR));
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(taskRepository).newTask(PAIR.first, PAIR.second);
    }
}
