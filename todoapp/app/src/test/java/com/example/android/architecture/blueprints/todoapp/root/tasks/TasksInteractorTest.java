package com.example.android.architecture.blueprints.todoapp.root.tasks;

import com.uber.rib.core.RibTestBasePlaceholder;
import com.uber.rib.core.InteractorHelper;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TasksInteractorTest extends RibTestBasePlaceholder {

    @Mock TasksInteractor.TasksPresenter presenter;
    @Mock TasksInteractor.Listener listener;
    @Mock TasksRouter router;

    private TasksInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        interactor = TestTasksInteractor.create(presenter, listener);
    }

    @Test
    public void whenEmitsAddTask_shouldCallOnAddTaskListener() {
        when(presenter.addTask()).thenReturn(Observable.just(new Object()));
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(listener).onAddNewTask();
    }

}
