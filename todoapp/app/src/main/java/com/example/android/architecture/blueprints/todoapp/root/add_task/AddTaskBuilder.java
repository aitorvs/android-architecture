package com.example.android.architecture.blueprints.todoapp.root.add_task;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.data.TaskRepository;
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
 * Builder for the {@link AddTaskScope}.
 *
 * TODO describe this scope's responsibility as a whole.
 */
public class AddTaskBuilder
        extends ViewBuilder<AddTaskView, AddTaskRouter, AddTaskBuilder.ParentComponent> {

    public AddTaskBuilder(ParentComponent dependency) {
        super(dependency);
    }

    /**
     * Builds a new {@link AddTaskRouter}.
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new {@link AddTaskRouter}.
     */
    public AddTaskRouter build(ViewGroup parentViewGroup) {
        AddTaskView view = createView(parentViewGroup);
        AddTaskInteractor interactor = new AddTaskInteractor();
        Component component = DaggerAddTaskBuilder_Component.builder()
                .parentComponent(getDependency())
                .view(view)
                .interactor(interactor)
                .build();
        return component.addtaskRouter();
    }

    @Override
    protected AddTaskView inflateView(LayoutInflater inflater, ViewGroup parentViewGroup) {
        return (AddTaskView) inflater.inflate(R.layout.add_task_rib, parentViewGroup, false);
    }

    /**
     * Define dependencies required from your parent interactor here.
     */
    public interface ParentComponent {
        TaskRepository taskRepository();
    }

    @dagger.Module
    public abstract static class Module {

        @AddTaskScope
        @Binds
        abstract AddTaskInteractor.AddTaskPresenter presenter(AddTaskView view);

        @AddTaskScope
        @Provides
        static AddTaskRouter router(
            Component component,
            AddTaskView view,
            AddTaskInteractor interactor) {
            return new AddTaskRouter(view, interactor, component);
        }

        // TODO: Create provider methods for dependencies created by this Rib. These should be static.
    }

    @AddTaskScope
    @dagger.Component(modules = Module.class,
           dependencies = ParentComponent.class)
    interface Component extends InteractorBaseComponent<AddTaskInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            Builder interactor(AddTaskInteractor interactor);
            @BindsInstance
            Builder view(AddTaskView view);
            Builder parentComponent(ParentComponent component);
            Component build();
        }
    }

    interface BuilderComponent  {
        AddTaskRouter addtaskRouter();
    }

    @Scope
    @Retention(CLASS)
    @interface AddTaskScope { }

    @Qualifier
    @Retention(CLASS)
    @interface AddTaskInternal { }
}
