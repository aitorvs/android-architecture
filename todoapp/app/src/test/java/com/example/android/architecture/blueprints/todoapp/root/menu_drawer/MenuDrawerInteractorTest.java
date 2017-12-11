package com.example.android.architecture.blueprints.todoapp.root.menu_drawer;

import com.uber.rib.core.RibTestBasePlaceholder;
import com.uber.rib.core.InteractorHelper;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MenuDrawerInteractorTest extends RibTestBasePlaceholder {

    @Mock MenuDrawerInteractor.MenuDrawerPresenter presenter;
    @Mock MenuDrawerInteractor.Listener listener;
    @Mock MenuDrawerRouter router;

    private MenuDrawerInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        interactor = TestMenuDrawerInteractor.create(presenter, listener);
    }

    @Test
    public void whenEmitsTodoListEvent_shouldCallTodoListListener() {
        when(presenter.menuEvents()).thenReturn(Observable.just(MenuEvent.TODO_LIST));
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(listener).todoListSelected();
    }

    @Test
    public void whenEmitsTodoListEvent_shouldNotCallStatisticsListener() {
        when(presenter.menuEvents()).thenReturn(Observable.just(MenuEvent.TODO_LIST));
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(listener, never()).statisticsSelected();
    }

    @Test
    public void whenEmitsStatisticsEvent_shouldNotCallTodoListListener() {
        when(presenter.menuEvents()).thenReturn(Observable.just(MenuEvent.STATISTICS));
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(listener, never()).todoListSelected();
    }

    @Test
    public void whenEmitsTodoListEvent_shouldCallStatisticsListener() {
        when(presenter.menuEvents()).thenReturn(Observable.just(MenuEvent.STATISTICS));
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(listener).statisticsSelected();
    }
}
