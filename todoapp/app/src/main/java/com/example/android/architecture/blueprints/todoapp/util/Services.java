package com.example.android.architecture.blueprints.todoapp.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.example.android.architecture.blueprints.todoapp.OptionsMenuService;
import com.example.android.architecture.blueprints.todoapp.R;
import com.uber.rib.core.RibActivity;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.example.android.architecture.blueprints.todoapp.util.Preconditions.checkNotNull;

public final class Services {

    public static final String ACTION_BAR_SERVICE = "com.example.android.architecture.blueprints.todoapp.action_bar";
    public static final String OPTIONS_MENU_SERVICE = "com.example.android.architecture.blueprints.todoapp.options_menu";

    private static AtomicBoolean menuAsUp = new AtomicBoolean(false);

    private Services() {
        throw new AssertionError("no instances");
    }

    /**
     * Get an instance of the {@link ActionBar}
     * @param context current caller {@link Context}
     * @return instance of the app {@link ActionBar}
     */
    @SuppressLint("WrongConstant")
    public static ActionBar getSupportActionBar(@NonNull Context context) {
        return (ActionBar) checkNotNull(context).getSystemService(ACTION_BAR_SERVICE);
    }

    /**
     * Set support {@link ActionBar}
     * @param context current caller {@link Context}
     * @param toolbar {@link Toolbar} instance to be set as {@link ActionBar}
     */
    public static void setSupportActionBar(@NonNull Context context, Toolbar toolbar) {
        ((RibActivity) context).setSupportActionBar(toolbar);
    }

    public static synchronized void setToolbarHomeAsUp(@NonNull Context context) {
        ActionBar ab = getSupportActionBar(checkNotNull(context));
        if (ab != null) {
            ab.setHomeAsUpIndicator(null);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        menuAsUp.set(true);
    }

    public static synchronized void setToolbarHomeAsMenu(@NonNull Context context) {
        ActionBar ab = getSupportActionBar(checkNotNull(context));
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        menuAsUp.set(false);
    }

    /**
     * @return returns <code>true</code> if the menu is configure as UP button
     */
    public static boolean isMenuAsUp() {
        return menuAsUp.get();
    }

    public static OptionsMenuService getOptionsMenuService(@NonNull Context context) {
        return (OptionsMenuService) checkNotNull(context).getSystemService(OPTIONS_MENU_SERVICE);
    }

    /**
     * Hides the soft keyboard
     * @param context caller {@link Context}
     * @param view caller {@link View}
     */
    public static synchronized void hideSoftKeyboard(@NonNull Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Shows the soft keyboard
     * @param context caller {@link Context}
     * @param focusedView the currently focused {@link View}
     */
    public static synchronized void showSoftKeyboard(@NonNull Context context, View focusedView) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(focusedView, 0);
    }
}
