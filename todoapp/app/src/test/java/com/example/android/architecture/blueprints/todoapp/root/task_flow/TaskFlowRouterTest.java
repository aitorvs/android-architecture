package com.example.android.architecture.blueprints.todoapp.root.task_flow;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.root.RootView;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskRouter;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskView;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail.TaskDetailBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail.TaskDetailRouter;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail.TaskDetailView;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks.TasksBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks.TasksRouter;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks.TasksView;
import com.uber.rib.core.RibTestBasePlaceholder;
import com.uber.rib.core.RouterHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskFlowRouterTest extends RibTestBasePlaceholder {

    private static Task TASK = Task.create("", "");

    @Mock TaskFlowBuilder.Component component;
    @Mock TaskFlowInteractor interactor;
    @Mock RootView rootView;
    @Mock AddTaskBuilder addTaskBuilder;
    @Mock TaskDetailBuilder taskDetailBuilder;
    @Mock TasksBuilder tasksBuilder;
    @Mock TasksRouter tasksRouter;
    @Mock AddTaskRouter addTaskRouter;
    @Mock TaskDetailRouter taskDetailRouter;
    @Mock AddTaskRouter editTaskRouter;
    @Mock TasksView tasksView;
    @Mock AddTaskView addTaskView;
    @Mock TaskDetailView taskDetailView;

    private TaskFlowRouter router;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        router = new TaskFlowRouter(interactor, component, rootView, tasksBuilder, addTaskBuilder, taskDetailBuilder);
    }

    ////// Task list

    @Test
    public void whenAttachTasks_shouldBuildTasksRouter() {
        when(tasksBuilder.build(rootView)).thenReturn(tasksRouter);
        RouterHelper.attach(router);
        router.attachTasks();
        verify(tasksBuilder).build(rootView);
        RouterHelper.detach(router);
    }

    @Test
    public void whenAttachTasks_shouldAttachView() {
        when(tasksBuilder.build(rootView)).thenReturn(tasksRouter);
        when(tasksRouter.getView()).thenReturn(tasksView);
        RouterHelper.attach(router);
        router.attachTasks();
        verify(rootView).addView(tasksView);
        RouterHelper.detach(router);
    }

    @Test
    public void whenDetachTasks_shouldRemoveView() {
        // set to mock so that it is not null
        router.tasksRouter = tasksRouter;
        when(tasksBuilder.build(rootView)).thenReturn(tasksRouter);
        when(tasksRouter.getView()).thenReturn(tasksView);
        RouterHelper.attach(router);
        router.detachTasks();
        verify(rootView).removeView(tasksView);
        RouterHelper.detach(router);
    }

    @Test
    public void whenDetachTasks_shouldNullRouter() {
        // set to mock so that it is not null
        router.tasksRouter = tasksRouter;
        when(tasksBuilder.build(rootView)).thenReturn(tasksRouter);
        when(tasksRouter.getView()).thenReturn(tasksView);
        RouterHelper.attach(router);
        router.detachTasks();
        assertEquals(router.tasksRouter, null);
        RouterHelper.detach(router);
    }

    @Test
    public void whenDetachTasksOnNullRouter_shouldNotExecute() {
        when(tasksBuilder.build(rootView)).thenReturn(tasksRouter);
        when(tasksRouter.getView()).thenReturn(tasksView);
        RouterHelper.attach(router);
        router.detachTasks();
        verify(rootView, never()).removeView(tasksView);
        RouterHelper.detach(router);
    }

    ////// New Task

    @Test
    public void whenAttachNewTask_shouldBuildTasksRouter() {
        when(addTaskBuilder.build(rootView)).thenReturn(addTaskRouter);
        RouterHelper.attach(router);
        router.attachNewTask();
        verify(addTaskBuilder).build(rootView);
        RouterHelper.detach(router);
    }

    @Test
    public void whenAttachNewTask_shouldAttachView() {
        when(addTaskBuilder.build(rootView)).thenReturn(addTaskRouter);
        when(addTaskRouter.getView()).thenReturn(addTaskView);
        RouterHelper.attach(router);
        router.attachNewTask();
        verify(rootView).addView(addTaskView);
        RouterHelper.detach(router);
    }

    @Test
    public void whenDetachNewTask_shouldRemoveView() {
        // set to mock so that it is not null
        router.newTaskRouter = addTaskRouter;
        when(addTaskBuilder.build(rootView)).thenReturn(addTaskRouter);
        when(addTaskRouter.getView()).thenReturn(addTaskView);
        RouterHelper.attach(router);
        router.detachNewTask();
        verify(rootView).removeView(addTaskView);
        RouterHelper.detach(router);
    }

    @Test
    public void whenDetachNewTask_shouldNullRouter() {
        // set to mock so that it is not null
        router.newTaskRouter = addTaskRouter;
        when(addTaskBuilder.build(rootView)).thenReturn(addTaskRouter);
        when(addTaskRouter.getView()).thenReturn(addTaskView);
        RouterHelper.attach(router);
        router.detachNewTask();
        assertEquals(router.newTaskRouter, null);
        RouterHelper.detach(router);
    }

    @Test
    public void whenDetachNewTaskOnNullRouter_shouldNotExecute() {
        when(addTaskBuilder.build(rootView)).thenReturn(addTaskRouter);
        when(addTaskRouter.getView()).thenReturn(addTaskView);
        RouterHelper.attach(router);
        router.detachNewTask();
        verify(rootView, never()).removeView(addTaskView);
        RouterHelper.detach(router);
    }

    @Test
    public void whenNewTaskAttached_getterShouldReturnTrue() {
        router.newTaskRouter = addTaskRouter;
        boolean value = router.isNewTaskAttached();
        assertThat(value, is(true));
    }

    @Test
    public void whenNewTaskDetached_getterShouldReturnFalse() {
        boolean value = router.isNewTaskAttached();
        assertThat(value, is(false));
    }

    ////// Edit Task
    
    @Test
    public void whenAttachEditTask_shouldBuildTasksRouter() {
        when(addTaskBuilder.build(rootView, TASK)).thenReturn(editTaskRouter);
        RouterHelper.attach(router);
        router.attachEditTask(TASK);
        verify(addTaskBuilder).build(rootView, TASK);
        RouterHelper.detach(router);
    }

    @Test
    public void whenAttachEditTask_shouldAttachView() {
        when(addTaskBuilder.build(rootView, TASK)).thenReturn(editTaskRouter);
        when(editTaskRouter.getView()).thenReturn(addTaskView);
        RouterHelper.attach(router);
        router.attachEditTask(TASK);
        verify(rootView).addView(addTaskView);
        RouterHelper.detach(router);
    }

    @Test
    public void whenDetachEditTask_shouldRemoveView() {
        // set to mock so that it is not null
        router.editTaskRouter = editTaskRouter;
        when(addTaskBuilder.build(rootView, TASK)).thenReturn(editTaskRouter);
        when(editTaskRouter.getView()).thenReturn(addTaskView);
        RouterHelper.attach(router);
        router.detachEditTask();
        verify(rootView).removeView(addTaskView);
        RouterHelper.detach(router);
    }

    @Test
    public void whenDetachEditTask_shouldNullRouter() {
        // set to mock so that it is not null
        router.editTaskRouter = editTaskRouter;
        when(addTaskBuilder.build(rootView, TASK)).thenReturn(editTaskRouter);
        when(editTaskRouter.getView()).thenReturn(addTaskView);
        RouterHelper.attach(router);
        router.detachEditTask();
        assertEquals(router.editTaskRouter, null);
        RouterHelper.detach(router);
    }

    @Test
    public void whenDetachEditTaskOnNullRouter_shouldNotExecute() {
        when(addTaskBuilder.build(rootView, TASK)).thenReturn(editTaskRouter);
        when(editTaskRouter.getView()).thenReturn(addTaskView);
        RouterHelper.attach(router);
        router.detachEditTask();
        verify(rootView, never()).removeView(addTaskView);
        RouterHelper.detach(router);
    }

    @Test
    public void whenEditTaskAttached_getterShouldReturnTrue() {
        router.editTaskRouter = editTaskRouter;
        boolean value = router.isEditTaskAttached();
        assertThat(value, is(true));
    }

    @Test
    public void whenEditTaskDetached_getterShouldReturnFalse() {
        boolean value = router.isEditTaskAttached();
        assertThat(value, is(false));
    }

    ////// Task Details

    @Test
    public void whenAttachTaskDetails_shouldBuildTasksRouter() {
        when(taskDetailBuilder.build(rootView, TASK)).thenReturn(taskDetailRouter);
        RouterHelper.attach(router);
        router.attachTaskDetails(TASK);
        verify(taskDetailBuilder).build(rootView, TASK);
        RouterHelper.detach(router);
    }

    @Test
    public void whenAttachTaskDetails_shouldAttachView() {
        when(taskDetailBuilder.build(rootView, TASK)).thenReturn(taskDetailRouter);
        when(taskDetailRouter.getView()).thenReturn(taskDetailView);
        RouterHelper.attach(router);
        router.attachTaskDetails(TASK);
        verify(rootView).addView(taskDetailView);
        RouterHelper.detach(router);
    }

    @Test
    public void whenDetachTaskDetails_shouldRemoveView() {
        // set to mock so that it is not null
        router.taskDetailRouter = taskDetailRouter;
        when(taskDetailBuilder.build(rootView, TASK)).thenReturn(taskDetailRouter);
        when(taskDetailRouter.getView()).thenReturn(taskDetailView);
        RouterHelper.attach(router);
        router.detachTaskDetails();
        verify(rootView).removeView(taskDetailView);
        RouterHelper.detach(router);
    }

    @Test
    public void whenDetachTaskDetails_shouldNullRouter() {
        // set to mock so that it is not null
        router.taskDetailRouter = taskDetailRouter;
        when(taskDetailBuilder.build(rootView, TASK)).thenReturn(taskDetailRouter);
        when(taskDetailRouter.getView()).thenReturn(taskDetailView);
        RouterHelper.attach(router);
        router.detachTaskDetails();
        assertEquals(router.taskDetailRouter, null);
        RouterHelper.detach(router);
    }

    @Test
    public void whenDetachTaskDetailsOnNullRouter_shouldNotExecute() {
        when(taskDetailBuilder.build(rootView, TASK)).thenReturn(taskDetailRouter);
        when(taskDetailRouter.getView()).thenReturn(taskDetailView);
        RouterHelper.attach(router);
        router.detachTaskDetails();
        verify(rootView, never()).removeView(taskDetailView);
        RouterHelper.detach(router);
    }

    @Test
    public void whenTaskDetailsAttached_getterShouldReturnTrue() {
        router.taskDetailRouter = taskDetailRouter;
        boolean value = router.isTaskDetailsAttached();
        assertThat(value, is(true));
    }

    @Test
    public void whenTaskDetailsDetached_getterShouldReturnFalse() {
        boolean value = router.isTaskDetailsAttached();
        assertThat(value, is(false));
    }
}
