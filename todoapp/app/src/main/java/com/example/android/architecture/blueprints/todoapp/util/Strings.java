package com.example.android.architecture.blueprints.todoapp.util;

import android.support.annotation.Nullable;

public final class Strings {

    private Strings() {
        throw new AssertionError("no instances");
    }

    public static boolean isBlank(@Nullable String s) {
        return s == null || s.isEmpty();
    }
}
