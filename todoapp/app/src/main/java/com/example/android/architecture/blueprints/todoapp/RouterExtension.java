package com.example.android.architecture.blueprints.todoapp;

import android.support.annotation.CallSuper;
import android.view.MenuItem;
import com.uber.rib.core.InteractorBaseComponent;
import com.uber.rib.core.Router;

public class RouterExtension<I extends com.uber.rib.core.Interactor, C extends InteractorBaseComponent>
    extends Router<I, C> {

    public RouterExtension(I interactor, C component) {
        super(interactor, component);
    }

    @CallSuper
    public boolean handleOptionsItemSelected(MenuItem item) {
        return false;
    }
}
