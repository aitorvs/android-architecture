package com.example.android.architecture.blueprints.todoapp.root.new_task;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.android.architecture.blueprints.todoapp.R;
import com.uber.rib.core.InteractorBaseComponent;
import com.uber.rib.core.ViewBuilder;
import java.lang.annotation.Retention;

import javax.inject.Scope;
import javax.inject.Qualifier;

import dagger.Provides;
import dagger.Binds;
import dagger.BindsInstance;

import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Builder for the {@link NewTaskScope}.
 *
 * TODO describe this scope's responsibility as a whole.
 */
public class NewTaskBuilder
        extends ViewBuilder<NewTaskView, NewTaskRouter, NewTaskBuilder.ParentComponent> {

    public NewTaskBuilder(ParentComponent dependency) {
        super(dependency);
    }

    /**
     * Builds a new {@link NewTaskRouter}.
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new {@link NewTaskRouter}.
     */
    public NewTaskRouter build(ViewGroup parentViewGroup) {
        NewTaskView view = createView(parentViewGroup);
        NewTaskInteractor interactor = new NewTaskInteractor();
        Component component = DaggerNewTaskBuilder_Component.builder()
                .parentComponent(getDependency())
                .view(view)
                .interactor(interactor)
                .build();
        return component.newtaskRouter();
    }

    @Override
    protected NewTaskView inflateView(LayoutInflater inflater, ViewGroup parentViewGroup) {
        return (NewTaskView) inflater.inflate(R.layout.new_task_rib, parentViewGroup, false);
    }

    /**
     * Define dependencies required from your parent interactor here.
     */
    public interface ParentComponent {
        NewTaskInteractor.Listener listener();
    }

    @dagger.Module
    public abstract static class Module {

        @NewTaskScope
        @Binds
        abstract NewTaskInteractor.NewTaskPresenter presenter(NewTaskView view);

        @NewTaskScope
        @Provides
        static NewTaskRouter router(
            Component component,
            NewTaskView view,
            NewTaskInteractor interactor) {
            return new NewTaskRouter(view, interactor, component);
        }

        // TODO: Create provider methods for dependencies created by this Rib. These should be static.
    }

    @NewTaskScope
    @dagger.Component(modules = Module.class,
           dependencies = ParentComponent.class)
    interface Component extends InteractorBaseComponent<NewTaskInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            Builder interactor(NewTaskInteractor interactor);
            @BindsInstance
            Builder view(NewTaskView view);
            Builder parentComponent(ParentComponent component);
            Component build();
        }
    }

    interface BuilderComponent  {
        NewTaskRouter newtaskRouter();
    }

    @Scope
    @Retention(CLASS)
    @interface NewTaskScope { }

    @Qualifier
    @Retention(CLASS)
    @interface NewTaskInternal { }
}
