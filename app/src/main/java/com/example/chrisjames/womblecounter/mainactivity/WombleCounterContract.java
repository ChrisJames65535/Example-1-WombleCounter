package com.example.chrisjames.womblecounter.mainactivity;

/**
 * Womble Counter Feature Contract
 */
public interface WombleCounterContract {

    /**
     * MVP view interface
     */
    interface View {
        void displayWombleCount(int wombleCount);
    }

    /**
     * MVP Presenter interface
     */
    interface Presenter {
        void bindMvpView(View view);

        void refreshMvpView();

        void onUserCountedAWomble();
    }

    /**
     * MVP Model interface
     */
    interface Model {
        void setListener(ModelListener listener);

        int getCurrentWombleCount();

        void incrementWombleCount();
    }

    /**
     * A listener used by the model to notify interested parties (the presenter) that something in the model has
     * changed.
     */
    interface ModelListener {
        void onWombleCountChanged();
    }
}