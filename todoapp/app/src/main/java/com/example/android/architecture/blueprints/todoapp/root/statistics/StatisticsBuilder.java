package com.example.android.architecture.blueprints.todoapp.root.statistics;

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
 * Builder for the {@link StatisticsScope}.
 *
 * TODO describe this scope's responsibility as a whole.
 */
public class StatisticsBuilder
        extends ViewBuilder<StatisticsView, StatisticsRouter, StatisticsBuilder.ParentComponent> {

    public StatisticsBuilder(ParentComponent dependency) {
        super(dependency);
    }

    /**
     * Builds a new {@link StatisticsRouter}.
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new {@link StatisticsRouter}.
     */
    public StatisticsRouter build(ViewGroup parentViewGroup) {
        StatisticsView view = createView(parentViewGroup);
        StatisticsInteractor interactor = new StatisticsInteractor();
        Component component = DaggerStatisticsBuilder_Component.builder()
                .parentComponent(getDependency())
                .view(view)
                .interactor(interactor)
                .build();
        return component.statisticsRouter();
    }

    @Override
    protected StatisticsView inflateView(LayoutInflater inflater, ViewGroup parentViewGroup) {
        return (StatisticsView) inflater.inflate(R.layout.statistics_rib, parentViewGroup, false);
    }

    public interface ParentComponent {
        // TODO: Define dependencies required from your parent interactor here.
    }

    @dagger.Module
    public abstract static class Module {

        @StatisticsScope
        @Binds
        abstract StatisticsInteractor.StatisticsPresenter presenter(StatisticsView view);

        @StatisticsScope
        @Provides
        static StatisticsRouter router(
            Component component,
            StatisticsView view,
            StatisticsInteractor interactor) {
            return new StatisticsRouter(view, interactor, component);
        }

        // TODO: Create provider methods for dependencies created by this Rib. These should be static.
    }

    @StatisticsScope
    @dagger.Component(modules = Module.class,
           dependencies = ParentComponent.class)
    interface Component extends InteractorBaseComponent<StatisticsInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            Builder interactor(StatisticsInteractor interactor);
            @BindsInstance
            Builder view(StatisticsView view);
            Builder parentComponent(ParentComponent component);
            Component build();
        }
    }

    interface BuilderComponent  {
        StatisticsRouter statisticsRouter();
    }

    @Scope
    @Retention(CLASS)
    @interface StatisticsScope { }

    @Qualifier
    @Retention(CLASS)
    @interface StatisticsInternal { }
}
