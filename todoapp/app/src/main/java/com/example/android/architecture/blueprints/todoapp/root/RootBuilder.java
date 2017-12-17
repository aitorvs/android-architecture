package com.example.android.architecture.blueprints.todoapp.root;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.root.menu_drawer.MenuDrawerBuilder;
import com.example.android.architecture.blueprints.todoapp.root.menu_drawer.MenuDrawerInteractor;
import com.example.android.architecture.blueprints.todoapp.root.statistics.StatisticsBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.TaskFlowBuilder;
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
 * Builder for the {@link RootScope}.
 *
 * TODO describe this scope's responsibility as a whole.
 */
public class RootBuilder
        extends ViewBuilder<RootView, RootRouter, RootBuilder.ParentComponent> {

    public RootBuilder(ParentComponent dependency) {
        super(dependency);
    }

    /**
     * Builds a new {@link RootRouter}.
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new {@link RootRouter}.
     */
    public RootRouter build(ViewGroup parentViewGroup) {
        RootView view = createView(parentViewGroup);
        RootInteractor interactor = new RootInteractor();
        Component component = DaggerRootBuilder_Component.builder()
                .parentComponent(getDependency())
                .view(view)
                .interactor(interactor)
                .build();
        return component.rootRouter();
    }

    @Override
    protected RootView inflateView(LayoutInflater inflater, ViewGroup parentViewGroup) {
        return (RootView) inflater.inflate(R.layout.root_rib, parentViewGroup, false);
    }

    public interface ParentComponent {
        // TODO: Define dependencies required from your parent interactor here.
    }

    /**
     * Create provider methods for dependencies created by this Rib. These should be static.
     */
    @dagger.Module
    public abstract static class Module {

        @RootScope
        @Provides
        static MenuDrawerInteractor.Listener menuDrawerListener(RootInteractor rootInteractor) {
            return rootInteractor.new MenuDrawerListener();
        }

        @RootScope
        @Binds
        abstract RootInteractor.RootPresenter presenter(RootView view);

        @RootScope
        @Provides
        static RootRouter router(
            Component component,
            RootView view,
            RootInteractor interactor) {
            return new RootRouter(view, interactor, component, new TaskFlowBuilder(component),
                new MenuDrawerBuilder(component), new StatisticsBuilder(component));
        }
    }

    @RootScope
    @dagger.Component(modules = Module.class,
           dependencies = ParentComponent.class)
    interface Component extends
        InteractorBaseComponent<RootInteractor>,
        BuilderComponent,
        TaskFlowBuilder.ParentComponent,
        MenuDrawerBuilder.ParentComponent,
        StatisticsBuilder.ParentComponent
    {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            Builder interactor(RootInteractor interactor);
            @BindsInstance
            Builder view(RootView view);
            Builder parentComponent(ParentComponent component);
            Component build();
        }
    }

    interface BuilderComponent  {
        RootRouter rootRouter();
    }

    @Scope
    @Retention(CLASS)
    @interface RootScope { }

    @Qualifier
    @Retention(CLASS)
    @interface RootInternal { }
}
