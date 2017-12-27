package com.example.android.architecture.blueprints.todoapp.root.menu_drawer;

import com.example.android.architecture.blueprints.todoapp.ViewRouterExtension;

/**
 * Adds and removes children of {@link MenuDrawerBuilder.MenuDrawerScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
public class MenuDrawerRouter extends
    ViewRouterExtension<MenuDrawerView, MenuDrawerInteractor, MenuDrawerBuilder.Component> {

    MenuDrawerRouter(MenuDrawerView view, MenuDrawerInteractor interactor, MenuDrawerBuilder.Component component) {
        super(view, interactor, component);
    }
}
