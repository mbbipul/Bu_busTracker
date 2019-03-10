package com.example.bu_bustracker;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import static com.example.bu_bustracker.R.array.destinition;

public class DirectionsFragment extends Fragment {

    private TextView searchView;
    View fragView;
    private TextView show;
    private static final int REQUEST_PLACE_PICKER = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        fragView = inflater.inflate(R.layout.direction_layout, null);

        searchView = fragView.findViewById(R.id.search_to_go);
        show = fragView.findViewById(R.id.show);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
//                    PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
//                    Intent intent = intentBuilder.build(getActivity());
//                    startActivityForResult(intent, REQUEST_PLACE_PICKER);
//
//                } catch (GooglePlayServicesRepairableException e) {
//                    GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), e.getConnectionStatusCode(),
//                            REQUEST_PLACE_PICKER);
//                } catch (GooglePlayServicesNotAvailableException e) {
//                    Toast.makeText(getActivity(), "Please install Google Play Services!", Toast.LENGTH_LONG).show();
//                }

                Intent showDirIntent = new Intent(getContext(),ShowDirectionBus.class);

                startActivity(showDirIntent);

            }
        });
        return fragView;

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        if (requestCode == REQUEST_PLACE_PICKER) {
//            if (resultCode == Activity.RESULT_OK) {
//
//                Place place = PlacePicker.getPlace(this.getActivity(),intent);
//                Intent showDirIntent = new Intent(this.getContext(),ShowDirection.class);
//                showDirIntent.putExtra("place", place.getName());
//
//                startActivity(showDirIntent);
//
//            } else if (resultCode == PlacePicker.RESULT_ERROR) {
//                Toast.makeText(getActivity(), "Places API failure! Check that the API is enabled for your key",
//                        Toast.LENGTH_LONG).show();
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, intent);
//        }
//
//   }
}
