package com.example.android.architecture.blueprints.todoapp.root.menu_drawer;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;
import com.example.android.architecture.blueprints.todoapp.R;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.Relay;
import io.reactivex.Observable;

/**
 * Top level view for {@link MenuDrawerBuilder.MenuDrawerScope}.
 */
class MenuDrawerView extends NavigationView implements MenuDrawerInteractor.MenuDrawerPresenter {

    private final BehaviorRelay<MenuEvent> behaviorRelay = BehaviorRelay.create();
    private final Relay<MenuEvent> menuRelay = behaviorRelay.toSerialized();

    public MenuDrawerView(Context context) {
        this(context, null);
    }

    public MenuDrawerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuDrawerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFitsSystemWindows(true);
    }

    @Override
    public Observable<MenuEvent> menuEvents() {
        // only emits distinct events
        return menuRelay.distinctUntilChanged();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.list_navigation_menu_item:
                    menuRelay.accept(MenuEvent.TODO_LIST);
                    break;
                case R.id.statistics_navigation_menu_item:
                    menuRelay.accept(MenuEvent.STATISTICS);
                    break;
                default:
                    break;
            }
            item.setCheckable(true);
            return true;
        });
    }
}
