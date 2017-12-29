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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatisticsViewModel that = (StatisticsViewModel) o;
        if (completed != that.completed) return false;
        return active == that.active;
    }

    @Override
    public int hashCode() {
        int result = completed;
        result = 31 * result + active;
        return result;
    }
}
