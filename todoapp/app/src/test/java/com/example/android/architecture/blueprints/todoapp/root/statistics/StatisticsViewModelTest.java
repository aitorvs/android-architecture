package com.example.android.architecture.blueprints.todoapp.root.statistics;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StatisticsViewModelTest {

    @Test
    public void whenViewModelWithNegativeValues_shouldReturnIsEmpty() {
        StatisticsViewModel vm = StatisticsViewModel.create(-1, -1);
        assertThat(vm.isEmpty(), is(true));
    }

    @Test
    public void whenViewModelWithNonEmptyCompleteNonZero_shouldReturnIsNotEmpty() {
        StatisticsViewModel vm = StatisticsViewModel.create(1, -1);
        assertThat(vm.isEmpty(), is(false));
    }

    @Test
    public void whenViewModelWithNonEmptyActiveNonZero_shouldReturnIsNotEmpty() {
        StatisticsViewModel vm = StatisticsViewModel.create(-1, 1);
        assertThat(vm.isEmpty(), is(false));
    }
}
