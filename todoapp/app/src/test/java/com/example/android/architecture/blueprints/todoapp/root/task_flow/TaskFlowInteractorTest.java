package com.example.android.architecture.blueprints.todoapp.root.task_flow;

import com.uber.rib.core.EmptyPresenter;
import com.uber.rib.core.InteractorHelper;
import com.uber.rib.core.RibTestBasePlaceholder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class TaskFlowInteractorTest extends RibTestBasePlaceholder {

    @Mock EmptyPresenter presenter;
    @Mock TaskFlowRouter router;

    private TaskFlowInteractor interactor;
    private TaskFlowInteractor.TasksListener tasksListener;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        interactor = TestTaskFlowInteractor.create();
        tasksListener = interactor.new TasksListener();
    }

    @Test
    public void whenBecomeActive_shouldAttachTasks() {
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(router).attachTasks();
        InteractorHelper.detach(interactor);
    }

    @Test
    public void whenTaskListener_onAddNewTask_shouldDetachTasks() {
        InteractorHelper.attach(interactor, presenter, router, null);
        tasksListener.onAddNewTask();
        verify(router).detachTasks();
        InteractorHelper.detach(interactor);
    }

    @Test
    public void whenTaskListener_onAddNewTask_shouldAttachNewTask() {
        InteractorHelper.attach(interactor, presenter, router, null);
        tasksListener.onAddNewTask();
        verify(router).attachNewTask();
        InteractorHelper.detach(interactor);
    }
}
