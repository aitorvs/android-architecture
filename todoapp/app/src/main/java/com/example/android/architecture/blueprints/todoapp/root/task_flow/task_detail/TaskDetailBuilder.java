package com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.data.TaskRepository;
import com.uber.rib.core.InteractorBaseComponent;
import com.uber.rib.core.ViewBuilder;
import dagger.Binds;
import dagger.BindsInstance;
import dagger.Provides;
import java.lang.annotation.Retention;
import javax.inject.Named;
import javax.inject.Qualifier;
import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Builder for the {@link TaskDetailScope}.
 *
 * TODO describe this scope's responsibility as a whole.
 */
public class TaskDetailBuilder
        extends ViewBuilder<TaskDetailView, TaskDetailRouter, TaskDetailBuilder.ParentComponent> {

    public TaskDetailBuilder(ParentComponent dependency) {
        super(dependency);
    }

    /**
     * Builds a new {@link TaskDetailRouter}.
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @param task This is the {@link Task} which details will be displayed
     * @return a new {@link TaskDetailRouter}.
     */
    public TaskDetailRouter build(ViewGroup parentViewGroup, Task task) {
        TaskDetailView view = createView(parentViewGroup);
        TaskDetailInteractor interactor = new TaskDetailInteractor();
        Component component = DaggerTaskDetailBuilder_Component.builder()
            .parentComponent(getDependency())
            .view(view)
            .task(task)
            .interactor(interactor)
            .build();
        return component.taskdetailRouter();
    }

    @Override
    protected TaskDetailView inflateView(LayoutInflater inflater, ViewGroup parentViewGroup) {
        return (TaskDetailView) inflater.inflate(R.layout.task_detail_rib, parentViewGroup, false);
    }

    /**
     * Define dependencies required from your parent interactor here.
     */
    public interface ParentComponent {
        TaskRepository taskRepository();
    }

    @dagger.Module
    public abstract static class Module {

        @TaskDetailScope
        @Binds
        abstract TaskDetailInteractor.TaskDetailPresenter presenter(TaskDetailView view);

        @TaskDetailScope
        @Provides
        static TaskDetailRouter router(
            Component component,
            TaskDetailView view,
            TaskDetailInteractor interactor) {
            return new TaskDetailRouter(view, interactor, component);
        }

        @TaskDetailScope
        @TaskDetailInternal
        @Binds
        abstract Task selectedTask(@Named("selected_task") Task task);
    }

    @TaskDetailScope
    @dagger.Component(modules = Module.class,
           dependencies = ParentComponent.class)
    interface Component extends InteractorBaseComponent<TaskDetailInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance Builder interactor(TaskDetailInteractor interactor);
            @BindsInstance Builder view(TaskDetailView view);
            @BindsInstance Builder task(@Named("selected_task") Task task);
            Builder parentComponent(ParentComponent component);
            Component build();
        }
    }

    interface BuilderComponent  {
        TaskDetailRouter taskdetailRouter();
    }

    @Scope
    @Retention(CLASS)
    @interface TaskDetailScope { }

    @Qualifier
    @Retention(CLASS)
    @interface TaskDetailInternal { }
}
