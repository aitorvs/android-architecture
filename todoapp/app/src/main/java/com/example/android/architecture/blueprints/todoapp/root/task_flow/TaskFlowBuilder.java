package com.example.android.architecture.blueprints.todoapp.root.task_flow;

import com.example.android.architecture.blueprints.todoapp.data.TaskRepository;
import com.example.android.architecture.blueprints.todoapp.root.RootView;
import com.example.android.architecture.blueprints.todoapp.root.add_task.AddTaskBuilder;
import com.example.android.architecture.blueprints.todoapp.root.tasks.TasksBuilder;
import com.example.android.architecture.blueprints.todoapp.root.tasks.TasksInteractor;
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
        static TaskFlowRouter router(Component component, TaskFlowInteractor interactor, RootView rootView) {
            return new TaskFlowRouter(interactor, component, rootView.viewContainer(), new TasksBuilder(component),
                new AddTaskBuilder(component));
        }

        @TaskFlowScope
        @Provides
        static TasksInteractor.Listener tasksListener(TaskFlowInteractor interactor) {
            return interactor. new TasksListener();
        }
    }

    @TaskFlowScope
    @dagger.Component(modules = Module.class, dependencies = ParentComponent.class)
    public interface Component extends
        InteractorBaseComponent<TaskFlowInteractor>,
        BuilderComponent,
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
