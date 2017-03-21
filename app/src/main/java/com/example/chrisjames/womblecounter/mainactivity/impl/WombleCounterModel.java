package com.example.chrisjames.womblecounter.mainactivity.impl;

import com.example.chrisjames.womblecounter.mainactivity.WombleCounterContract;
import com.example.chrisjames.womblecounter.mainactivity.WombleCounterContract.ModelListener;

import java.lang.ref.WeakReference;

/**
 * Created by chrisjames on 15/3/17.
 */

public class WombleCounterModel
        implements WombleCounterContract.Model {

    private int mWombleCount;
    private WeakReference<ModelListener> mListenerWeakReference = new WeakReference<>(null);

    @Override
    public void setListener(ModelListener listener) {
        mListenerWeakReference = new WeakReference<>(listener);
    }

    @Override
    public int getCurrentWombleCount() {
        return mWombleCount;
    }

    @Override
    public void incrementWombleCount() {

        // Increment our womble count
        mWombleCount++;

        // Get our listener from the weak reference
        ModelListener listener = mListenerWeakReference.get();

        // If we have a listener,
        if (listener != null) {
            // tell the listener the womble count has changed
            listener.onWombleCountChanged();
        }
    }
}