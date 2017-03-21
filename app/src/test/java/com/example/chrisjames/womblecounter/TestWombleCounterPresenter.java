package com.example.chrisjames.womblecounter;

import com.example.chrisjames.womblecounter.mainactivity.WombleCounterContract;
import com.example.chrisjames.womblecounter.mainactivity.WombleCounterContract.ModelListener;
import com.example.chrisjames.womblecounter.mainactivity.impl.WombleCounterPresenter;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test the presenter Created by chrisjames on 15/3/17.
 */
public class TestWombleCounterPresenter {


    @Test
    public void presenterCallsSetListener() throws Exception {

        // Test that the presenter sets the model listener correctly
        MockModel mockModel = new MockModel();

        WombleCounterPresenter presenter = new WombleCounterPresenter(mockModel);

        assertEquals(1, mockModel.mCountSetListenerCall);
        assertEquals(presenter, mockModel.mModelListener);
    }

    @Test
    public void presenterUpdatesCountOnUserEvent() throws Exception {

        // Test that the presenter calls incrementWombleCount on the model in response to
        // onUserCountedAWomble() and the view is *not* updated (that happens when the listener is triggered)

        MockModel mockModel = new MockModel();
        MockView mockView = new MockView();

        WombleCounterPresenter presenter = new WombleCounterPresenter(mockModel);

        presenter.onUserCountedAWomble();
        // Increment is called
        assertEquals(1, mockModel.mCountIncrementWombleCountCall);
        // Update view is not called
        assertEquals(0, mockView.mCountDisplayWombleCountCall);
    }

    @Test
    public void presenterUpdatesViewOnListenerEvent() throws Exception {
        MockModel mockModel = new MockModel();
        MockView mockView = new MockView();

        WombleCounterPresenter presenter = new WombleCounterPresenter(mockModel);
        presenter.bindMvpView(mockView);

        presenter.onWombleCountChanged();
        assertEquals(1, mockView.mCountDisplayWombleCountCall);
    }

    @Test
    public void presenterUpdatesViewWithCorrectValue() throws Exception {
        MockModel mockModel = new MockModel();
        MockView mockView = new MockView();

        WombleCounterPresenter presenter = new WombleCounterPresenter(mockModel);
        presenter.bindMvpView(mockView);

        mockModel.mWombleCount = 99;
        presenter.onWombleCountChanged();
        assertEquals(1, mockView.mCountDisplayWombleCountCall);
        assertEquals(99, mockView.mDisplayedWombleCount);

        mockModel.mWombleCount = 56;
        presenter.refreshMvpView();
        assertEquals(2, mockView.mCountDisplayWombleCountCall);
        assertEquals(56, mockView.mDisplayedWombleCount);

    }


    //region Mock implementations.. you would usually use something like Mockito for this

    private class MockModel
            implements WombleCounterContract.Model {

        int mCountSetListenerCall = 0;
        int mCountGetCurrentWombleCountCall = 0;
        int mCountIncrementWombleCountCall = 0;

        int mWombleCount = 0;
        ModelListener mModelListener = null;

        @Override
        public void setListener(WombleCounterContract.ModelListener listener) {
            mCountSetListenerCall++;
            mModelListener = listener;
        }

        @Override
        public int getCurrentWombleCount() {
            mCountGetCurrentWombleCountCall++;
            return mWombleCount;
        }

        @Override
        public void incrementWombleCount() {
            mCountIncrementWombleCountCall++;
        }
    }

    private class MockView
            implements WombleCounterContract.View {
        int mCountDisplayWombleCountCall;
        int mDisplayedWombleCount;

        @Override
        public void displayWombleCount(int wombleCount) {
            mCountDisplayWombleCountCall++;
            mDisplayedWombleCount = wombleCount;
        }
    }

    // endregion
}