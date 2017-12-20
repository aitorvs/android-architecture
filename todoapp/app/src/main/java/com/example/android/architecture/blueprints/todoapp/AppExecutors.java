package com.example.android.architecture.blueprints.todoapp;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class AppExecutors {
    private static final int THREAD_COUNT = 3;
    private final Executor diskIO;

    private AppExecutors(Executor diskIO) {
        this.diskIO = diskIO;
    }

    public AppExecutors() {
        this(Executors.newSingleThreadExecutor());
    }

    /**
     * Return single thread {@link Executor} for disk IO operations
     *
     * @return single thread {@link Executor}
     */
    public Executor diskIO() {
        return this.diskIO;
    }
}
