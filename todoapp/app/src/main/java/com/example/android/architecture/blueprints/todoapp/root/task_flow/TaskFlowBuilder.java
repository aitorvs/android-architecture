package com.example.android.architecture.blueprints.todoapp.root.task_flow;

import android.content.Context;
import com.example.android.architecture.blueprints.todoapp.data.TaskRepository;
import com.example.android.architecture.blueprints.todoapp.root.RootView;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskInteractor;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskScreen;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_details_flow.TaskDetailsFlowBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_details_flow.TaskDetailsFlowInteractor;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks.TasksBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks.TasksInteractor;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks.TasksScreen;
import com.example.android.architecture.blueprints.todoapp.screen_stack.ScreenStack;
import com.uber.rib.core.Builder;
import com.uber.rib.core.EmptyPresenter;
import com.uber.rib.core.InteractorBaseComponent;
import dagger.BindsInstance;
import dagger.Provides;
import java.lang.annotation.Retention;
import javax.inject.Qualifier;
import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.CLASS;

public class TaskFlowBuilder extends Builder<TaskFlowRouter, TaskFlowBuilder.ParentComponent> {

    public TaskFlowBuilder(ParentComponent dependency) {
        super(dependency);
    }

    /**
     * Builds a new {@link TaskFlowRouter}.
     *
     * @return a new {@link TaskFlowRouter}.
     */
    public TaskFlowRouter build() {
        TaskFlowInteractor interactor = new TaskFlowInteractor();
        Component component = DaggerTaskFlowBuilder_Component.builder()
                .parentComponent(getDependency())
                .interactor(interactor)
                .build();

        return component.taskflowRouter();
    }

    /**
     * Define dependencies required from your parent interactor here.
     */
    public interface ParentComponent {
        RootView rootView();
        TaskRepository taskRepository();
        ScreenStack screenStack();
        Context context();
    }

    @dagger.Module
    public abstract static class Module {

        @TaskFlowScope
        @Provides
        static EmptyPresenter presenter() {
            return new EmptyPresenter();
        }

        @TaskFlowScope
        @Provides
        static TaskFlowRouter router(Component component, TaskFlowInteractor interactor, RootView rootView,
            ScreenStack stack) {
            return new TaskFlowRouter(stack, interactor, component, rootView.viewContainer(),
                new TasksScreen(new TasksBuilder(component)), new AddTaskScreen(new AddTaskBuilder(component)),
                new TaskDetailsFlowBuilder(component));
        }

        @TaskFlowScope
        @Provides
        static TasksInteractor.Listener tasksListener(TaskFlowInteractor interactor) {
            return interactor. new TasksListener();
        }

        @TaskFlowScope
        @Provides
        static AddTaskInteractor.Listener addTaskListener(TaskFlowInteractor interactor) {
            return interactor. new AddOrEditTaskListener();
        }

        @TaskFlowScope
        @Provides
        static TaskDetailsFlowInteractor.Listener taskDetailFlowListener(TaskFlowInteractor interactor) {
            return interactor. new TaskDetailsFlowListener();
        }
    }

    @TaskFlowScope
    @dagger.Component(modules = Module.class, dependencies = ParentComponent.class)
    public interface Component extends
        InteractorBaseComponent<TaskFlowInteractor>,
        BuilderComponent,
        TaskDetailsFlowBuilder.ParentComponent,
        AddTaskBuilder.ParentComponent,
        TasksBuilder.ParentComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            Builder interactor(TaskFlowInteractor interactor);
            Builder parentComponent(ParentComponent component);
            Component build();
        }

    }

    interface BuilderComponent {
        TaskFlowRouter taskflowRouter();
    }

    @Scope
    @Retention(CLASS)
    @interface TaskFlowScope { }


    @Qualifier
    @Retention(CLASS)
    @interface TaskFlowInternal { }
}
