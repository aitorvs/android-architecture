package com.example.android.architecture.blueprints.todoapp.data;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 * {@link Task} database Android tests.
 */
@RunWith(AndroidJUnit4.class)
public class LocalTaskDataSourceTest {
    private TodoDatabase db;
    private LocalTaskDataSource source;

    @Before
    public void initDb() throws Exception {
        // create in memory DB. No need to clean up after every test
        db =
            Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), TodoDatabase.class).build();
        source = new LocalTaskDataSource(db);

    }

    @After
    public void closeDb() throws Exception {
        // close DB
        db.close();
    }

    @Test
    public void whenInsertOneTask_shouldSuccess() throws Exception {
        source.newTask("title", "desc");
        List<Task> tasks = source.getTasks().blockingFirst();
        assertThat(tasks.size(), is(1));
    }

    @Test
    public void whenInsertMultipleTask_shouldSuccess() throws Exception {
        source.newTask("title", "desc");
        source.newTask("title 2", "desc");
        source.newTask("title 3", "desc");
        source.newTask("title 4", "desc");
        List<Task> tasks = source.getTasks().blockingFirst();
        assertThat(tasks.size(), is(4));
    }

    @Test
    public void whenSameTitledTasksAdded_shouldNotOverride() throws Exception {
        source.newTask("title", "desc");
        source.newTask("title", "desc duplicate");
        List<Task> tasks = source.getTasks().blockingFirst();
        assertThat(tasks.size(), is(2));
    }

    @Test
    public void whenDescriptionUpdated_shouldSuccess() throws Exception {
        source.newTask("title", "desc");
        Task task = source.getTasks().blockingFirst().get(0);
        Task updatedTask = new Task(task.getId(), task.getTitle(), "updated description", task.isDone());
        source.updateTask(updatedTask);
        List<Task> tasks = source.getTasks().blockingFirst();
        assertThat(tasks.size(), is(1));
        assertThat(tasks.get(0).getDescription(), is("updated description"));
    }

    @Test
    public void whenTitleUpdated_shouldSuccess() throws Exception {
        source.newTask("title", "desc");
        Task task = source.getTasks().blockingFirst().get(0);
        Task updatedTask = new Task(task.getId(), "updated title", task.getTitle(), task.isDone());
        source.updateTask(updatedTask);
        List<Task> tasks = source.getTasks().blockingFirst();
        assertThat(tasks.size(), is(1));
        assertThat(tasks.get(0).getTitle(), is("updated title"));
    }

    @Test
    public void whenDeleteAllTasks_dbShouldBeEmpty() throws Exception {
        source.newTask("title", "desc");
        source.newTask("title 2", "desc");
        source.newTask("title 3", "desc");
        source.newTask("title 4", "desc");
        source.deleteAll();
        List<Task> tasks = source.getTasks().blockingFirst();
        assertThat(tasks.size(), is(0));
    }

    @Test
    public void whenDeleteOneTask_dbShouldNotBeEmpty() throws Exception {
        source.newTask("title", "desc");
        source.newTask("title 2", "desc");
        source.deleteTask("title");
        List<Task> tasks = source.getTasks().blockingFirst();
        assertThat(tasks.size(), is(1));
    }
}
