/**
 * (C) 2017. National Australia Bank [All rights reserved]. This product and related documentation are protected by
 * copyright restricting its use, copying, distribution, and decompilation. No part of this product or related
 * documentation may be reproduced in any form by any means without prior written authorization of National Australia
 * Bank. Unless otherwise arranged, third parties may not have access to this product or related documents.
 */

package com.example.chrisjames.womblecounter;

import com.example.chrisjames.womblecounter.mainactivity.WombleCounterContract.Model;
import com.example.chrisjames.womblecounter.mainactivity.WombleCounterContract.ModelListener;
import com.example.chrisjames.womblecounter.mainactivity.WombleCounterContract.Presenter;
import com.example.chrisjames.womblecounter.mainactivity.WombleCounterContract.View;
import com.example.chrisjames.womblecounter.mainactivity.impl.WombleCounterModel;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Test the model Created by chrisjames on 15/3/17.
 */
public class TestWombleCounterModel {

    @Test
    public void initialCountCorrect() throws Exception {
        // Test the initial value of the womble count is correct
        Model model = new WombleCounterModel();
        assertEquals(0, model.getCurrentWombleCount());
    }

    @Test
    public void countIncrementedCorrect() throws Exception {
        // Test that incrementing the womble count totals correctly

        Model model = new WombleCounterModel();

        model.incrementWombleCount();
        model.incrementWombleCount();
        model.incrementWombleCount();

        assertEquals(3, model.getCurrentWombleCount());
    }

    @Test
    public void incrementTriggersListener() throws Exception {
        // Test that the listener is triggered once for each increment

        MockModelListener mockModelListener = new MockModelListener();

        Model model = new WombleCounterModel();
        model.setListener(mockModelListener);

        model.incrementWombleCount();
        model.incrementWombleCount();
        model.incrementWombleCount();

        assertEquals(3, mockModelListener.mCountOnWombleCountChangedCall);
    }

    // region Mock Model Listener - normally would do with something like Mockito

    private class MockModelListener
            implements ModelListener {

        int mCountOnWombleCountChangedCall = 0;

        @Override
        public void onWombleCountChanged() {
            mCountOnWombleCountChangedCall++;
        }
    }

    // endregion
}