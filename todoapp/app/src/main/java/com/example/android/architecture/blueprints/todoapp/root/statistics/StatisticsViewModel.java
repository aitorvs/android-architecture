package com.example.android.architecture.blueprints.todoapp.root.statistics;

final class StatisticsViewModel {
    final int completed;
    final int active;

    private StatisticsViewModel(int completed, int active) {
        this.completed = completed;
        this.active = active;
    }

    boolean isEmpty() {
        return completed == 0 && active == 0;
    }

    static StatisticsViewModel create(int completed, int active) {
        return new StatisticsViewModel(completed < 0 ? 0 : completed, active < 0 ? 0 : active);
    }
}
