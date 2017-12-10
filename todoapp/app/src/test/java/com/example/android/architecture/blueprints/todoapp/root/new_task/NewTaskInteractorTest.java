package com.example.android.architecture.blueprints.todoapp.root.new_task;

import com.uber.rib.core.RibTestBasePlaceholder;
import com.uber.rib.core.InteractorHelper;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NewTaskInteractorTest extends RibTestBasePlaceholder {

    @Mock NewTaskInteractor.NewTaskPresenter presenter;
    @Mock NewTaskInteractor.Listener listener;
    @Mock NewTaskRouter router;

    private NewTaskInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        interactor = TestNewTaskInteractor.create(presenter, listener);
    }

    /**
     * TODO: Delete this example and add real tests.
     */
    @Test
    public void whenViewEmitsClick_shouldCallListener() {
        when(presenter.clicks()).thenReturn(Observable.just(new Object()));

        InteractorHelper.attach(interactor, presenter, router, null);
        verify(listener).addTask();
    }

}
