package com.example.android.architecture.blueprints.todoapp.root.menu_drawer;

import com.uber.rib.core.ViewRouter;

/**
 * Adds and removes children of {@link MenuDrawerBuilder.MenuDrawerScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
public class MenuDrawerRouter extends ViewRouter<MenuDrawerView, MenuDrawerInteractor, MenuDrawerBuilder.Component> {

    MenuDrawerRouter(MenuDrawerView view, MenuDrawerInteractor interactor, MenuDrawerBuilder.Component component) {
        super(view, interactor, component);
    }
}
