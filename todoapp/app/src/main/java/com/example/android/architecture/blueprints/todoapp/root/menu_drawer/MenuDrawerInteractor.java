package com.example.android.architecture.blueprints.todoapp.root.menu_drawer;

import android.support.annotation.Nullable;
import com.uber.rib.core.Bundle;
import com.uber.rib.core.Interactor;
import com.uber.rib.core.RibInteractor;
import io.reactivex.Observable;
import javax.inject.Inject;

/**
 * Coordinates Business Logic for {@link MenuDrawerScope}.
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
public class MenuDrawerInteractor
        extends Interactor<MenuDrawerInteractor.MenuDrawerPresenter, MenuDrawerRouter> {

    @Inject MenuDrawerPresenter presenter;
    @Inject Listener listener;

    @Override
    protected void didBecomeActive(@Nullable Bundle savedInstanceState) {
        super.didBecomeActive(savedInstanceState);

        presenter.menuEvents()
            .subscribe(event -> {
                if (event == MenuEvent.TODO_LIST) {
                    listener.todoListSelected();
                } else if (event == MenuEvent.STATISTICS) {
                    listener.statisticsSelected();
                }
            });
    }

    @Override
    protected void willResignActive() {
        super.willResignActive();

        // TODO: Perform any required clean up here, or delete this method entirely if not needed.
    }


    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface MenuDrawerPresenter {
        Observable<MenuEvent> menuEvents();
    }

    public interface Listener {
        void todoListSelected();
        void statisticsSelected();
    }

}
