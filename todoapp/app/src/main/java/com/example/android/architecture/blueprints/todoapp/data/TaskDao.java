package com.example.android.architecture.blueprints.todoapp.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
interface TaskDao {
    /**
     * Select all tasks from the table
     * @return list of all tasks
     */
    @Query("SELECT * FROM tasks")
    Flowable<List<Task>> getTasks();

    /**
     * Delete tasks that are completed
     */
    @Query("DELETE FROM Tasks WHERE done = 1")
    void deleteCompletedTasks();

    /**
     * @return Returns a list of tasks that are completed
     */
    @Query("SELECT * FROM tasks WHERE done = 1")
    Flowable<List<Task>> getCompletedTasks();

    /**
     * @return Returns a list of {@link Task} that are active
     */
    @Query("SELECT * FROM tasks WHERE done = 0")
    Flowable<List<Task>> getActiveTasks();
    /**
     * Select all tasks by title.
     * @param id task identifier
     * @return {@link List} of {@link Task} that match the title.
     */
    @Query("SELECT * FROM tasks WHERE id = :id")
    Maybe<Task> findById(String id);

    /**
     * Insert one {@link Task}
     * @param task the task to be inserted. If the task already exist, it'll be replaced.
     */
    @Insert(onConflict = REPLACE)
    void insert(Task task);

    /**
     * Delete a {@link Task}
     * @param task {@link Task} to be deleted
     */
    @Delete
    void deleteTask(Task task);

    /**
     * Delete all the tasks in the database
     */
    @Query("DELETE FROM tasks")
    void deleteAllTasks();

    @Query("UPDATE tasks SET done = :completed WHERE id = :taskId")
    void completeTask(String taskId, boolean completed);
}
