package com.example.android.architecture.blueprints.todoapp.root;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.architecture.blueprints.todoapp.OptionsMenuService;
import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.TodoActivity;
import com.uber.rib.core.RibActivity;

/**
 * Top level view for {@link RootBuilder.RootScope}.
 */
public class RootView extends DrawerLayout implements RootInteractor.RootPresenter {
    @BindView(R.id.root_container) ViewGroup container;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private OptionsMenuService menuService;
    private final OptionsMenuService.Listener menuListener = new OptionsMenuService.Listener() {
        @Override
        public void onPrepareOptionsMenu(Menu menu) {
            /* no-op */
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == android.R.id.home) {
                openMenu();
                return true;
            }
            return false;
        }
    };

    public RootView(Context context) {
        this(context, null);
    }

    public RootView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RootView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        setupToolbar();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        menuService = (OptionsMenuService) getContext().getSystemService(TodoActivity.OPTIONS_MENU_SERVICE);
        menuService.addOptionsMenuListener(menuListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        menuService.removeOptionsMenuListener(menuListener);
    }

    @NonNull public final ViewGroup viewContainer() {
        return container;
    }

    @NonNull public final Toolbar getToolbar() {
        return this.toolbar;
    }

    private void openMenu() {
        openDrawer(GravityCompat.START);
    }

    @Override
    public void closeMenu() {
        if (isMenuOpen()) {
            closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean isMenuOpen() {
        return isDrawerOpen(GravityCompat.START);
    }

    @SuppressLint("WrongConstant")
    private void setupToolbar() {
        ((RibActivity) getContext()).setSupportActionBar(toolbar);
        ActionBar ab = (ActionBar) getContext().getSystemService(TodoActivity.ACTION_BAR_SERVICE);
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }
}
