package com.example.android.architecture.blueprints.todoapp.root.task_flow.task_details_flow;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskScreen;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail.TaskDetailScreen;
import com.example.android.architecture.blueprints.todoapp.screen_stack.ScreenStack;
import com.uber.rib.core.RibTestBasePlaceholder;
import com.uber.rib.core.RouterHelper;
import com.uber.rib.core.screenstack.lifecycle.ScreenStackEvent;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskDetailsFlowRouterTest extends RibTestBasePlaceholder {
    private static final Task SELECTED_TASK = Task.create("selected", "task");

    @Mock TaskDetailsFlowBuilder.Component component;
    @Mock TaskDetailsFlowInteractor interactor;
    @Mock ScreenStack backStack;
    @Mock TaskDetailScreen taskDetailScreen;
    @Mock AddTaskScreen editTaskScreen;

    private TaskDetailsFlowRouter router;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        router = new TaskDetailsFlowRouter(backStack, interactor, component, taskDetailScreen, editTaskScreen);
        when(taskDetailScreen.lifecycle()).thenReturn(Observable.just(ScreenStackEvent.BUILT));
        when(editTaskScreen.lifecycle()).thenReturn(Observable.just(ScreenStackEvent.BUILT));
    }

    @Test
    public void whenAttachDetails_shouldCallScreenSetTask() {
        // Use RouterHelper to drive your router's lifecycle.
        RouterHelper.attach(router);
        router.attachTaskDetails(SELECTED_TASK);
        verify(taskDetailScreen).setTask(SELECTED_TASK);
        RouterHelper.detach(router);
    }

    @Test
    public void whenAttachDetails_shouldPushTaskDetailScreen() {
        // Use RouterHelper to drive your router's lifecycle.
        RouterHelper.attach(router);
        router.attachTaskDetails(SELECTED_TASK);
        verify(backStack).pushScreen(taskDetailScreen);
        RouterHelper.detach(router);
    }

    @Test
    public void whenAttachEditTask_shouldCallScreenSetTask() {
        // Use RouterHelper to drive your router's lifecycle.
        RouterHelper.attach(router);
        router.attachEditTask(SELECTED_TASK);
        verify(editTaskScreen).setEditableTask(SELECTED_TASK);
        RouterHelper.detach(router);
    }

    @Test
    public void whenAttachEditTask_shouldPushEditTaskScreen() {
        // Use RouterHelper to drive your router's lifecycle.
        RouterHelper.attach(router);
        router.attachEditTask(SELECTED_TASK);
        verify(backStack).pushScreen(editTaskScreen);
        RouterHelper.detach(router);
    }

    @Test
    public void whenDetach_shouldPopBackStack() {
        // Use RouterHelper to drive your router's lifecycle.
        RouterHelper.attach(router);
        RouterHelper.detach(router);
        verify(backStack).popBackTo(-1, false);
    }
}
