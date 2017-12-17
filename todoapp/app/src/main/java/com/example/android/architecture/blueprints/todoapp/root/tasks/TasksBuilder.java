package com.example.android.architecture.blueprints.todoapp.root.tasks;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.android.architecture.blueprints.todoapp.R;
import com.uber.rib.core.InteractorBaseComponent;
import com.uber.rib.core.ViewBuilder;
import dagger.Binds;
import dagger.BindsInstance;
import dagger.Provides;
import java.lang.annotation.Retention;
import javax.inject.Qualifier;
import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Builder for the {@link TasksScope}.
 *
 * TODO describe this scope's responsibility as a whole.
 */
public class TasksBuilder
        extends ViewBuilder<TasksView, TasksRouter, TasksBuilder.ParentComponent> {

    public TasksBuilder(ParentComponent dependency) {
        super(dependency);
    }

    /**
     * Builds a new {@link TasksRouter}.
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new {@link TasksRouter}.
     */
    public TasksRouter build(ViewGroup parentViewGroup) {
        TasksView view = createView(parentViewGroup);
        TasksInteractor interactor = new TasksInteractor();
        Component component = DaggerTasksBuilder_Component.builder()
                .parentComponent(getDependency())
                .view(view)
                .interactor(interactor)
                .build();
        return component.tasksRouter();
    }

    @Override
    protected TasksView inflateView(LayoutInflater inflater, ViewGroup parentViewGroup) {
        return (TasksView) inflater.inflate(R.layout.tasks_rib, parentViewGroup, false);
    }

    public interface ParentComponent {
        TasksInteractor.Listener listener();
    }

    @dagger.Module
    public abstract static class Module {

        @TasksScope
        @Binds
        abstract TasksInteractor.TasksPresenter presenter(TasksView view);

        @TasksScope
        @Provides
        static TasksRouter router(Component component, TasksView view, TasksInteractor interactor) {
            return new TasksRouter(view, interactor, component);
        }

        // TODO: Create provider methods for dependencies created by this Rib. These should be static.
    }

    @TasksScope
    @dagger.Component(modules = Module.class,
           dependencies = ParentComponent.class)
    interface Component extends
        InteractorBaseComponent<TasksInteractor>,
        BuilderComponent
    {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            Builder interactor(TasksInteractor interactor);
            @BindsInstance
            Builder view(TasksView view);
            Builder parentComponent(ParentComponent component);
            Component build();
        }
    }

    interface BuilderComponent  {
        TasksRouter tasksRouter();
    }

    @Scope
    @Retention(CLASS)
    @interface TasksScope { }

    @Qualifier
    @Retention(CLASS)
    @interface TasksInternal { }
}
