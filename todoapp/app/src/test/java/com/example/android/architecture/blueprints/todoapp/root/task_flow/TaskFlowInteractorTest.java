package com.example.android.architecture.blueprints.todoapp.root.task_flow;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.uber.rib.core.RibTestBasePlaceholder;
import com.uber.rib.core.EmptyPresenter;
import com.uber.rib.core.InteractorHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class TaskFlowInteractorTest extends RibTestBasePlaceholder {
    private static final Task TASK = Task.create("", "");

    @Mock EmptyPresenter presenter;
    @Mock TaskFlowRouter router;

    private TaskFlowInteractor interactor;
    private TaskFlowInteractor.TasksListener tasksListener;
    private TaskFlowInteractor.TaskDetailListener taskDetailListener;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        interactor = TestTaskFlowInteractor.create();
        tasksListener = interactor.new TasksListener();
        taskDetailListener = interactor.new TaskDetailListener();
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

    @Test
    public void whenTaskDetailListener_onEditTask_shouldDetachTaskDetails() {
        InteractorHelper.attach(interactor, presenter, router, null);
        taskDetailListener.onEditTask(TASK);
        verify(router).detachTaskDetails();
        InteractorHelper.detach(interactor);
    }

    @Test
    public void whenTaskDetailListener_onEditTask_shouldAttachEditTask() {
        InteractorHelper.attach(interactor, presenter, router, null);
        taskDetailListener.onEditTask(TASK);
        verify(router).attachEditTask(TASK);
        InteractorHelper.detach(interactor);
    }
}
