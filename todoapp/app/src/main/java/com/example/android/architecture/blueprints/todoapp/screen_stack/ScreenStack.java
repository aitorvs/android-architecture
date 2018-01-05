package com.example.android.architecture.blueprints.todoapp.screen_stack;

import android.support.annotation.UiThread;
import android.view.ViewGroup;
import com.uber.rib.core.screenstack.ScreenStackBase;
import com.uber.rib.core.screenstack.ViewProvider;
import java.util.ArrayDeque;
import java.util.Deque;
import javax.annotation.Nullable;

@UiThread
public class ScreenStack implements ScreenStackBase {
    private final Deque<ViewProvider> backStack = new ArrayDeque<>();
    private final ViewGroup parentViewGroup;

    public ScreenStack(ViewGroup parentViewGroup) {
        this.parentViewGroup = parentViewGroup;
    }

    @Override
    public void pushScreen(ViewProvider viewProvider) {
        pushScreen(viewProvider, false);
    }

    @Override
    public void pushScreen(ViewProvider viewProvider, boolean shouldAnimate) {
        removeCurrentView();
        onCurrentViewHidden();
        backStack.push(viewProvider);
        // order matters here
        addCurrentView();
        onCurrentViewAppeared();
    }

    @Override
    public void popScreen() {
        popScreen(false);
    }

    @Override
    public void popScreen(boolean shouldAnimate) {
        if (backStack.isEmpty()) {
            return;
        }

        removeCurrentView();
        onCurrentViewRemoved();
        backStack.pop();
        addCurrentView();
        onCurrentViewAppeared();
    }

    @Override
    public void popBackTo(int index, boolean shouldAnimate) {
        for (int size = backStack.size() - 1; size > index; size--) {
            popScreen();
        }
    }

    @Override
    public boolean handleBackPress() {
        return handleBackPress(false);
    }

    @Override
    public boolean handleBackPress(boolean shouldAnimate) {
        if (backStack.size() == 1) {
            return false;
        }
        popScreen();
        return true;
    }

    @Override
    public int size() {
        return backStack.size();
    }

    /**
     * Returns the index of the last item in the stack.
     * @return -1 is return when the backstack is empty.
     */
    public int indexOfLastItem() {
        return size() - 1;
    }

    private void onCurrentViewHidden() {
        ViewProvider vp = getCurrentViewProvider();
        if (vp != null) {
            vp.onViewHidden();
        }
    }

    private void onCurrentViewAppeared() {
        ViewProvider vp = getCurrentViewProvider();
        if (vp != null) {
            vp.onViewAppeared();
        }
    }

    private void onCurrentViewRemoved() {
        ViewProvider vp = getCurrentViewProvider();
        if (vp != null) {
            vp.onViewRemoved();
        }
    }

    @Nullable
    private ViewProvider getCurrentViewProvider() {
        if (backStack.isEmpty()) {
            return null;
        }
        return backStack.peek();
    }

    private void addCurrentView() {
        ViewProvider vp = getCurrentViewProvider();
        if (vp != null) {
            parentViewGroup.addView(vp.buildView(parentViewGroup));
        }
    }

    private void removeCurrentView() {
        if (parentViewGroup.getChildCount() > 0) {
            parentViewGroup.removeViewAt(parentViewGroup.getChildCount() - 1);
        }
    }
}
