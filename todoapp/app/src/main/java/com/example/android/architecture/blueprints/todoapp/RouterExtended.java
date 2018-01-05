package com.example.android.architecture.blueprints.todoapp;

import com.uber.rib.core.InteractorBaseComponent;
import com.uber.rib.core.Router;
import com.uber.rib.core.screenstack.lifecycle.ScreenStackEvent;

public class RouterExtended<I extends com.uber.rib.core.Interactor, C extends InteractorBaseComponent>
    extends Router<I, C> {

    public RouterExtended(I interactor, C component) {
        super(interactor, component);
    }

    protected final void handleScreenEvents(Router<?, ?> router, ScreenStackEvent event) {
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
