package com.example.android.architecture.blueprints.todoapp.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import com.example.android.architecture.blueprints.todoapp.TodoActivity;

import static com.example.android.architecture.blueprints.todoapp.util.Preconditions.checkNotNull;

public final class Services {

    private Services() {
        throw new AssertionError("no instances");
    }

    @SuppressLint("WrongConstant")
    public static ActionBar getSupportActionBar(@NonNull Context context) {
        return (ActionBar) checkNotNull(context).getSystemService(TodoActivity.ACTION_BAR_SERVICE);
    }
}
