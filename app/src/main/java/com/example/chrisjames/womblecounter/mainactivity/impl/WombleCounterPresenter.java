package com.example.chrisjames.womblecounter.mainactivity.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.chrisjames.womblecounter.mainactivity.WombleCounterContract;
import com.example.chrisjames.womblecounter.mainactivity.WombleCounterContract.View;


import java.lang.ref.WeakReference;

/**
 * Created by chrisjames on 15/3/17.
 */
public class WombleCounterPresenter
        implements WombleCounterContract.Presenter,
                   WombleCounterContract.ModelListener {

    private WombleCounterContract.Model mModel;
    private WeakReference<WombleCounterContract.View> mViewWeakReference;

    public WombleCounterPresenter(@NonNull WombleCounterContract.Model model) {
        this.mModel = model;
        model.setListener(this);
    }

    @Override
    public void bindMvpView(@Nullable View view) {
        mViewWeakReference = new WeakReference<>(view);
    }

    @Override
    public void refreshMvpView() {

        // We have been asked to refresh the view with our state held in the model
        // Get our reference to the view (which *can* be null)
        WombleCounterContract.View view = mViewWeakReference.get();

        if (view != null) {
            view.displayWombleCount(mModel.getCurrentWombleCount());
        }
    }

    @Override
    public void onUserCountedAWomble() {
        // Update the model but don't update the view. The model will tell us if we need to do that via the
        // listener
        mModel.incrementWombleCount();
    }

    @Override
    public void onWombleCountChanged() {
        // The model has told us the womble count has changed

        // Get the view out of our weak reference..
        WombleCounterContract.View view = mViewWeakReference.get();
        if (view != null) {
            // update (just the) changed counter if we have a view
            view.displayWombleCount(mModel.getCurrentWombleCount());
        }
    }


}