package com.example.android.architecture.blueprints.todoapp.root.task_flow;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks.TasksInteractor;
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
    private TaskFlowInteractor.TaskDetailsFlowListener taskDetailsFlowListener;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        interactor = TestTaskFlowInteractor.create();
        tasksListener = interactor.new TasksListener();
        taskDetailsFlowListener = interactor.new TaskDetailsFlowListener();
    }

    @Test
    public void whenBecomeActive_shouldAttachTasks() {
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(router).attachTasks(TasksInteractor.Filter.ALL);
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
    public void whenTaskListener_onTaskSelected_shouldAttachTaskDetails() {
        InteractorHelper.attach(interactor, presenter, router, null);
        tasksListener.onTaskSelected(Task.EMPTY);
        verify(router).attachTaskDetails(Task.EMPTY);
        InteractorHelper.detach(interactor);
    }

    @Test
    public void whenTaskDetailsFlowListener_onFlowFinished_shouldDetachTaskDetails() {
        InteractorHelper.attach(interactor, presenter, router, null);
        taskDetailsFlowListener.onFlowFinished();
        verify(router).detachTaskDetails();
        InteractorHelper.detach(interactor);
    }

    @Test
    public void whenTaskDetailsFlowListener_onFlowFinished_shouldNotReattachTasks() {
        InteractorHelper.attach(interactor, presenter, router, null);
        taskDetailsFlowListener.onFlowFinished();
        // once, because it is called when attached
        verify(router).attachTasks(TasksInteractor.Filter.ALL);
        InteractorHelper.detach(interactor);
    }
}
