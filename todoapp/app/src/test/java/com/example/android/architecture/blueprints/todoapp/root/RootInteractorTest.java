package com.example.android.architecture.blueprints.todoapp.root;

import com.uber.rib.core.InteractorHelper;
import com.uber.rib.core.RibTestBasePlaceholder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class RootInteractorTest extends RibTestBasePlaceholder {

    @Mock RootInteractor.RootPresenter presenter;
    @Mock RootRouter router;

    private RootInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        interactor = TestRootInteractor.create(presenter);
    }

    @Test
    public void whenGotoStatistics_shouldDetachTasks() {
        // Use InteractorHelper to drive your interactor's lifecycle.
        InteractorHelper.attach(interactor, presenter, router, null);
        interactor.gotoStatistics();
        verify(router).detachTasks();
        InteractorHelper.detach(interactor);
    }

    @Test
    public void whenGotoStatistics_shouldAttachStatistics() {
        // Use InteractorHelper to drive your interactor's lifecycle.
        InteractorHelper.attach(interactor, presenter, router, null);
        interactor.gotoStatistics();
        verify(router).attachStatistics();
        InteractorHelper.detach(interactor);
    }

    @Test
    public void whenGotoTodoList_shouldDetachStatistics() {
        // Use InteractorHelper to drive your interactor's lifecycle.
        InteractorHelper.attach(interactor, presenter, router, null);
        interactor.gotoTodoList();
        verify(router).detachStatistics();
        InteractorHelper.detach(interactor);
    }

    @Test
    public void whenGotoTodoList_shouldAttachTasks() {
        // Use InteractorHelper to drive your interactor's lifecycle.
        InteractorHelper.attach(interactor, presenter, router, null);
        interactor.gotoTodoList();
        verify(router).attachTasks();
        InteractorHelper.detach(interactor);
    }

    @Test
    public void whenBecomeActive_shouldAttachDrawer() {
        // Use InteractorHelper to drive your interactor's lifecycle.
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(router).attachMenuDrawer();
        InteractorHelper.detach(interactor);
    }

}
