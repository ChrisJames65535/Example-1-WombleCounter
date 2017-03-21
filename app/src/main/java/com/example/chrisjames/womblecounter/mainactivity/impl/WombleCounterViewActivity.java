package com.example.chrisjames.womblecounter.mainactivity.impl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.chrisjames.womblecounter.R;
import com.example.chrisjames.womblecounter.mainactivity.WombleCounterContract;
import com.example.chrisjames.womblecounter.shared.ObjectRegistry;

/**
 * Activity implementing the MVP View of our WombleCounter Feature Created by chrisjames on 15/3/17.
 */
public class WombleCounterViewActivity
        extends AppCompatActivity
        implements WombleCounterContract.View {

    // Used to store the ObjectRegistry key for our presenter in the savedInstanceState bundle
    private static final String STATE_PRESENTER = "presenter";

    // The brains of the operation; our Presenter
    private WombleCounterContract.Presenter mPresenter;

    // A humble Android text view that will show our magnificent Womble count
    private TextView mWombleCountValueView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fairly standard, set the layout
        setContentView(R.layout.activity_main);

        // And bind our view field to the views in our layout
        mWombleCountValueView = (TextView) findViewById(R.id.activity_main_WombleCounterValue);

        // Set our click button listener
        findViewById(R.id.activity_main_CountWomblerButton).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // When the button is clicked,
                        // tell the presenter that the user counted a Womble..
                        mPresenter.onUserCountedAWomble();
                    }
                }
        );

        // This is important. If we create a presenter every time, we will keep on resetting our precious
        // Womble count. Wombles are hard to spot because they are always overground, underground, wombiling free.
        // You don't want to lose count of how many you see..
        if (savedInstanceState == null) {

            // New instance of this activity
            // create a new model (will set our Womble count to zero)
            WombleCounterContract.Model model = new WombleCounterModel();

            // and create a new presenter, passing it our new model
            mPresenter = new WombleCounterPresenter(model);

        } else {

            // This activity has been re-created (maybe due to rotation) so we will have
            // saved our presenter (and model) in the object registry and put the resulting
            // key into the saved instance state.

            // Get the object registry (it's really a singleton/static in disguise)
            ObjectRegistry objectRegistry = new ObjectRegistry();

            // Get the object registry key for the presenter out of the saved instance state
            String registryKey = savedInstanceState.getString(STATE_PRESENTER);

            // Retrieve our presenter from the object registry (this also removes it from the registry
            // so that we don't accumulate presenters every time we restore the activity
            mPresenter = objectRegistry.get(registryKey);

            // The presenter "owns" the model, so it is already attached to our presenter and will have our precious
            // Womble count in it.
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // onSaveInstanceState is called before this activity is destroyed but is going to be reconstructed

        // Get the object registry (it's really a singleton/static in disguise)
        ObjectRegistry objectRegistry = new ObjectRegistry();

        // Put our presenter into the registry to save it, we get a key which we will
        // put into the saved instance state
        String registryKey = objectRegistry.put(mPresenter);

        // Put our registry key into the saved instance state
        outState.putString(STATE_PRESENTER, registryKey);

        // Because the presenter owns the model, we have preserved our precious Womble count. When we are re-created
        // we will pick up the registry key from the savedInstanceState and retrieve the presenter and model from the
        // registry with it.
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Pass this MVP view to the presenter. The views may not be fully restored yet but that
        // doesn't matter because we will ask the presenter to refresh all of our views when we resume
        mPresenter.bindMvpView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Ask our presenter to refresh all of our views with the correct values from the Model
        mPresenter.refreshMvpView();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Tell the presenter we are not available. This prevents useless updates to our views (which can't be seen
        // or are actually unavailable
        mPresenter.bindMvpView(null);
    }

    @Override
    public void displayWombleCount(int wombleCount) {

        // Display the number of wombles we have seen
        mWombleCountValueView.setText(getString(R.string.wombleCountValueFormat, wombleCount));

        // For best results, try hanging around Wimbeldon Common.
    }
}
