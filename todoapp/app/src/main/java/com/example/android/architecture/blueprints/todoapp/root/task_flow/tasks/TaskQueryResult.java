package com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import java.util.List;

final class TaskQueryResult {
    private final List<Task> tasks;
    private final boolean filteredByCompleted;
    private final boolean filteredByActive;

    private TaskQueryResult(List<Task> tasks, boolean completedFilterApplied, boolean activeFilterApplied) {
        this.tasks = tasks;
        this.filteredByCompleted = completedFilterApplied;
        this.filteredByActive = activeFilterApplied;
    }

    boolean isFilteredByCompleted() {
        return filteredByCompleted;
    }

    boolean isFilteredByActive() {
        return filteredByActive;
    }

    List<Task> getTasks() {
        return tasks;
    }

    static TaskQueryResult create(List<Task> tasks) {
        return new TaskQueryResult(tasks, false, false);
    }

    static TaskQueryResult createWithFilterCompleted(List<Task> tasks) {
        return new TaskQueryResult(tasks, true, false);
    }

    static TaskQueryResult createWithFilterActive(List<Task> tasks) {
        return new TaskQueryResult(tasks, false, true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskQueryResult that = (TaskQueryResult) o;
        if (filteredByCompleted != that.filteredByCompleted) return false;
        if (filteredByActive != that.filteredByActive) return false;
        return tasks != null ? tasks.equals(that.tasks) : that.tasks == null;
    }

    @Override
    public int hashCode() {
        int result = tasks != null ? tasks.hashCode() : 0;
        result = 31 * result + (filteredByCompleted ? 1 : 0);
        result = 31 * result + (filteredByActive ? 1 : 0);
        return result;
    }
}
