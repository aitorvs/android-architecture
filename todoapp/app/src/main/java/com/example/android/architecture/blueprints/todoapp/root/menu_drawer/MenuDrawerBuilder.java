package com.example.android.architecture.blueprints.todoapp.root.menu_drawer;

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
 * Builder for the {@link MenuDrawerScope}.
 *
 * TODO describe this scope's responsibility as a whole.
 */
public class MenuDrawerBuilder
        extends ViewBuilder<MenuDrawerView, MenuDrawerRouter, MenuDrawerBuilder.ParentComponent> {

    public MenuDrawerBuilder(ParentComponent dependency) {
        super(dependency);
    }

    /**
     * Builds a new {@link MenuDrawerRouter}.
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new {@link MenuDrawerRouter}.
     */
    public MenuDrawerRouter build(ViewGroup parentViewGroup) {
        MenuDrawerView view = createView(parentViewGroup);
        MenuDrawerInteractor interactor = new MenuDrawerInteractor();
        Component component = DaggerMenuDrawerBuilder_Component.builder()
                .parentComponent(getDependency())
                .view(view)
                .interactor(interactor)
                .build();
        return component.menudrawerRouter();
    }

    @Override
    protected MenuDrawerView inflateView(LayoutInflater inflater, ViewGroup parentViewGroup) {
        return (MenuDrawerView) inflater.inflate(R.layout.menu_drawer_rib, parentViewGroup, false);
    }

    /**
     * Define dependencies required from your parent interactor here.
     */
    public interface ParentComponent {
        MenuDrawerInteractor.Listener menuListener();
    }

    @dagger.Module
    public abstract static class Module {

        @MenuDrawerScope
        @Binds
        abstract MenuDrawerInteractor.MenuDrawerPresenter presenter(MenuDrawerView view);

        @MenuDrawerScope
        @Provides
        static MenuDrawerRouter router(
            Component component,
            MenuDrawerView view,
            MenuDrawerInteractor interactor) {
            return new MenuDrawerRouter(view, interactor, component);
        }

        // TODO: Create provider methods for dependencies created by this Rib. These should be static.
    }

    @MenuDrawerScope
    @dagger.Component(modules = Module.class,
           dependencies = ParentComponent.class)
    interface Component extends InteractorBaseComponent<MenuDrawerInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            Builder interactor(MenuDrawerInteractor interactor);
            @BindsInstance
            Builder view(MenuDrawerView view);
            Builder parentComponent(ParentComponent component);
            Component build();
        }
    }

    interface BuilderComponent  {
        MenuDrawerRouter menudrawerRouter();
    }

    @Scope
    @Retention(CLASS)
    @interface MenuDrawerScope { }

    @Qualifier
    @Retention(CLASS)
    @interface MenuDrawerInternal { }
}
