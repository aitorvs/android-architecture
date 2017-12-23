package com.example.android.architecture.blueprints.todoapp.root.task_flow.task_details_flow;

import android.view.ViewGroup;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.data.TaskRepository;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskInteractor;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail.TaskDetailBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail.TaskDetailInteractor;
import com.uber.rib.core.Builder;
import com.uber.rib.core.EmptyPresenter;
import com.uber.rib.core.InteractorBaseComponent;
import dagger.Binds;
import dagger.BindsInstance;
import dagger.Provides;
import java.lang.annotation.Retention;
import javax.inject.Named;
import javax.inject.Qualifier;
import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.CLASS;

public class TaskDetailsFlowBuilder extends Builder<TaskDetailsFlowRouter, TaskDetailsFlowBuilder.ParentComponent> {

    public TaskDetailsFlowBuilder(ParentComponent dependency) {
        super(dependency);
    }

    /**
     * Builds a new {@link TaskDetailsFlowRouter}.
     *
     * @return a new {@link TaskDetailsFlowRouter}.
     */
    public TaskDetailsFlowRouter build(ViewGroup rootView, Task task) {
        TaskDetailsFlowInteractor interactor = new TaskDetailsFlowInteractor();
        Component component = DaggerTaskDetailsFlowBuilder_Component.builder()
                .parentComponent(getDependency())
                .interactor(interactor)
                .view(rootView)
                .task(task)
                .build();

        return component.taskdetailsflowRouter();
    }

    /**
     * Define dependencies required from your parent interactor here.
     */
    public interface ParentComponent {
        TaskRepository taskRepository();
        TaskDetailsFlowInteractor.Listener taskDetailsFlowListener();
    }

    /**
     * Create provider methods for dependencies created by this Rib. These methods should be static.
     */
    @dagger.Module
    public abstract static class Module {

        @TaskDetailsFlowScope
        @Provides
        static EmptyPresenter presenter() {
            return new EmptyPresenter();
        }

        @TaskDetailsFlowScope
        @Provides
        static TaskDetailsFlowRouter router(Component component, TaskDetailsFlowInteractor interactor,
            @Named("root_view") ViewGroup rootView) {
            return new TaskDetailsFlowRouter(interactor, component, rootView, new TaskDetailBuilder(component),
                new AddTaskBuilder(component));
        }

        @TaskDetailsFlowScope
        @Binds
        abstract Task selectedTask(@Named("selected_task") Task selectedTask);

        @TaskDetailsFlowScope
        @Provides
        static TaskDetailInteractor.Listener taskDetailListener(TaskDetailsFlowInteractor interactor) {
            return interactor. new TaskDetailListener();
        }

        @TaskDetailsFlowScope
        @Provides
        static AddTaskInteractor.Listener editTaskListener(TaskDetailsFlowInteractor interactor) {
            return interactor. new EditTaskListener();
        }

    }

    @TaskDetailsFlowScope
    @dagger.Component(modules = Module.class, dependencies = ParentComponent.class)
    public interface Component extends
        InteractorBaseComponent<TaskDetailsFlowInteractor>,
        BuilderComponent,
        TaskDetailBuilder.ParentComponent,
        AddTaskBuilder.ParentComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance Builder interactor(TaskDetailsFlowInteractor interactor);
            @BindsInstance Builder view(@Named("root_view") ViewGroup rootView);
            @BindsInstance Builder task(@Named("selected_task") Task task);
            Builder parentComponent(ParentComponent component);
            Component build();
        }

    }

    interface BuilderComponent {
        TaskDetailsFlowRouter taskdetailsflowRouter();
    }

    @Scope
    @Retention(CLASS)
    @interface TaskDetailsFlowScope { }

    @Qualifier
    @Retention(CLASS)
    @interface TaskDetailsFlowInternal { }
}
