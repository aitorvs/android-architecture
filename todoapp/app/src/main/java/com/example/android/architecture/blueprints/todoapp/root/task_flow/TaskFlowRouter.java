package com.example.android.architecture.blueprints.todoapp.root.task_flow;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import com.example.android.architecture.blueprints.todoapp.RouterExtended;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskRouter;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskScreen;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_details_flow.TaskDetailsFlowBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_details_flow.TaskDetailsFlowRouter;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks.TasksInteractor;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks.TasksRouter;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks.TasksScreen;
import com.example.android.architecture.blueprints.todoapp.screen_stack.ScreenStack;
import com.example.android.architecture.blueprints.todoapp.util.Services;
import com.uber.rib.core.screenstack.lifecycle.ScreenStackEvent;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import timber.log.Timber;

/**
 * Adds and removes children of {@link TaskFlowBuilder.TaskFlowScope}.
 *
 * This task mainly attaches and detaches the views related to the task workflow.
 */
public class TaskFlowRouter extends RouterExtended<TaskFlowInteractor, TaskFlowBuilder.Component> {

    private final ViewGroup parentView;
    private final TaskDetailsFlowBuilder taskDetailsFlowBuilder;
    private final ScreenStack backStack;
    private final TasksScreen taskScreen;
    private final AddTaskScreen addTaskScreen;
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Nullable TaskDetailsFlowRouter taskDetailsFlowRouter;

    TaskFlowRouter(ScreenStack screenStack, TaskFlowInteractor interactor, TaskFlowBuilder.Component component,
        ViewGroup viewGroup, TasksScreen taskScreen, AddTaskScreen addTaskScreen,
        TaskDetailsFlowBuilder taskDetailsFlowBuilder) {

        super(interactor, component);
        this.parentView = viewGroup;
        this.taskDetailsFlowBuilder = taskDetailsFlowBuilder;
        this.backStack = screenStack;
        this.taskScreen = taskScreen;
        this.addTaskScreen = addTaskScreen;
    }

    @Override
    protected void willAttach() {
        disposables.add(taskScreen
            .lifecycle()
            .subscribe(event -> {
                TasksRouter router = taskScreen.getRouter();
                handleScreenEvents(router, event);

                // check whether the tasks screen is appeared or not.
                // When APPEARED, it means access to the menu, otherwise, home is configure as UP button
                Context context = parentView.getContext();
                if (event == ScreenStackEvent.APPEARED) {
                    Services.setToolbarHomeAsMenu(context);
                } else {
                    Services.setToolbarHomeAsUp(context);
                }
            }, OnErrorNotImplementedException::new));

        disposables.add(addTaskScreen
            .lifecycle()
            .subscribe(event -> {
                AddTaskRouter router = addTaskScreen.getRouter();
                handleScreenEvents(router, event);
            }, OnErrorNotImplementedException::new));
    }

    @Override
    protected void willDetach() {
        super.willDetach();
        disposables.clear();
    }

    void attachTasks(TasksInteractor.Filter defaultTasksFilter) {
        Timber.d("attachTasks() called");
        taskScreen.setTaskFilter(defaultTasksFilter);
        backStack.pushScreen(taskScreen);
    }

    void attachNewTask() {
        Timber.d("attachNewTask() called");
        backStack.pushScreen(addTaskScreen);
    }

    void detachLatest() {
        backStack.popScreen();
    }

    void attachTaskDetails(Task selectedTask) {
        Timber.d("attachTaskDetails() called with: selectedTask = [" + selectedTask + "]");
        taskDetailsFlowRouter = taskDetailsFlowBuilder.build(parentView, selectedTask);
        attachChild(taskDetailsFlowRouter);
    }

    void detachTaskDetails() {
        Timber.d("detachTaskDetails() called");
        if (taskDetailsFlowRouter != null) {
            detachChild(taskDetailsFlowRouter);
            taskDetailsFlowRouter = null;
        }
    }
}
