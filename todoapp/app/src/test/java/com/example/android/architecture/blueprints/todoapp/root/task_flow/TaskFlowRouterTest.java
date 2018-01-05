package com.example.android.architecture.blueprints.todoapp.root.task_flow;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.root.RootView;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskScreen;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail.TaskDetailView;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_details_flow.TaskDetailsFlowBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_details_flow.TaskDetailsFlowRouter;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks.TasksInteractor;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks.TasksScreen;
import com.example.android.architecture.blueprints.todoapp.screen_stack.ScreenStack;
import com.uber.rib.core.RibTestBasePlaceholder;
import com.uber.rib.core.RouterHelper;
import com.uber.rib.core.screenstack.lifecycle.ScreenStackEvent;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskFlowRouterTest extends RibTestBasePlaceholder {

    private static Task TASK = Task.create("", "");

    @Mock TaskFlowBuilder.Component component;
    @Mock TaskFlowInteractor interactor;
    @Mock RootView rootView;
    @Mock AddTaskScreen addTaskScreen;
    @Mock TaskDetailsFlowBuilder taskDetailsFlowBuilder;
    @Mock TasksScreen tasksScreen;
    @Mock TaskDetailsFlowRouter taskDetailsFlowRouter;
    @Mock TaskDetailView taskDetailView;
    @Mock ScreenStack backStack;

    private TaskFlowRouter router;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        router = new TaskFlowRouter(backStack, interactor, component, rootView, tasksScreen, addTaskScreen,
            taskDetailsFlowBuilder);

        when(tasksScreen.lifecycle()).thenReturn(Observable.just(ScreenStackEvent.BUILT));
        when(addTaskScreen.lifecycle()).thenReturn(Observable.just(ScreenStackEvent.BUILT));
    }

    ////// Task list

    @Test
    public void whenAttachTasks_shouldSetFilter() {
        RouterHelper.attach(router);
        router.attachTasks(TasksInteractor.Filter.ALL);
        verify(tasksScreen).setTaskFilter(TasksInteractor.Filter.ALL);
        RouterHelper.detach(router);
    }

    @Test
    public void whenAttachTasks_shouldPushTasksScreen() {
        RouterHelper.attach(router);
        router.attachTasks(TasksInteractor.Filter.ALL);
        verify(backStack).pushScreen(tasksScreen);
        RouterHelper.detach(router);
    }

    @Test
    public void whenDetachLatest_shouldPopBackStack() {
        // set to mock so that it is not null
        RouterHelper.attach(router);
        router.detachLatest();
        verify(backStack).popScreen();
        RouterHelper.detach(router);
    }

    ////// New Task

    @Test
    public void whenAttachNewTask_shouldPushScreen() {
        RouterHelper.attach(router);
        router.attachNewTask();
        verify(backStack).pushScreen(addTaskScreen);
        RouterHelper.detach(router);
    }

    ////// Task Details

    @Test
    public void whenAttachTaskDetails_shouldBuildTasksRouter() {
        when(taskDetailsFlowBuilder.build(rootView, TASK)).thenReturn(taskDetailsFlowRouter);
        RouterHelper.attach(router);
        router.attachTaskDetails(TASK);
        verify(taskDetailsFlowBuilder).build(rootView, TASK);
        RouterHelper.detach(router);
    }

    @Test
    public void whenDetachTaskDetails_shouldNullRouter() {
        // set to mock so that it is not null
        router.taskDetailsFlowRouter = taskDetailsFlowRouter;
        when(taskDetailsFlowBuilder.build(rootView, TASK)).thenReturn(taskDetailsFlowRouter);
        RouterHelper.attach(router);
        router.detachTaskDetails();
        assertEquals(router.taskDetailsFlowRouter, null);
        RouterHelper.detach(router);
    }

    @Test
    public void whenDetachTaskDetailsOnNullRouter_shouldNotExecute() {
        when(taskDetailsFlowBuilder.build(rootView, TASK)).thenReturn(taskDetailsFlowRouter);
        RouterHelper.attach(router);
        router.detachTaskDetails();
        verify(rootView, never()).removeView(taskDetailView);
        RouterHelper.detach(router);
    }
}
