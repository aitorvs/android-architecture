package com.example.android.architecture.blueprints.todoapp;

import android.support.annotation.CallSuper;
import android.view.MenuItem;
import android.view.View;
import com.uber.rib.core.Interactor;
import com.uber.rib.core.InteractorBaseComponent;
import com.uber.rib.core.ViewRouter;

public class ViewRouterExtension<V extends View, I extends Interactor, C extends InteractorBaseComponent>
    extends ViewRouter<V, I, C> {

    public ViewRouterExtension(V view, I interactor, C component) {
        super(view, interactor, component);
    }

    @CallSuper
    public boolean handleOptionsItemSelected(MenuItem item) {
        // do not consume the event
        return false;
    }
}
