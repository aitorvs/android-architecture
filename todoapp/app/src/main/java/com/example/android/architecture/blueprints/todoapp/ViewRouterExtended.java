package com.example.android.architecture.blueprints.todoapp;

import android.view.View;
import com.uber.rib.core.InteractorBaseComponent;
import com.uber.rib.core.Router;
import com.uber.rib.core.ViewRouter;
import com.uber.rib.core.screenstack.lifecycle.ScreenStackEvent;

public class ViewRouterExtended<V extends View, I extends com.uber.rib.core.Interactor, C extends InteractorBaseComponent>
    extends ViewRouter<V, I, C> {

    public ViewRouterExtended(V view, I interactor, C component) {
        super(view, interactor, component);
    }

    public final void handleScreenEvents(Router<?, ?> router, ScreenStackEvent event) {
        switch (event) {
            case APPEARED:
                if (router != null) {
                    attachChild(router);
                }
                break;
            case HIDDEN:
            case REMOVED:
                if (router != null) {
                    detachChild(router);
                }
                break;
        }
    }
}
