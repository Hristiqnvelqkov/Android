package com.apress.gerber.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import static com.apress.gerber.login.LogActivity.CITY_EXTRA;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LocationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class LocationFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    AutoCompleteTextView iSearch;
    View V;
    TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            Intent intent = new Intent(getContext(), FetchAddressIntentService.class);
            intent.putExtra(CITY_EXTRA, s.toString());
            getContext().startService(intent);

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        V = inflater.inflate(R.layout.fragment_location, container, false);
        ResponseReceiver receiver = new ResponseReceiver();
        IntentFilter filter = new IntentFilter(FetchAddressIntentService.BROADCAST_ACTION);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, filter);
        iSearch = (AutoCompleteTextView) V.findViewById(R.id.location1);
        iSearch.addTextChangedListener(textWatcher);
        return V;

    }

    public class ResponseReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String addresses = intent.getStringExtra(FetchAddressIntentService.EXTENDED_DATA_STATUS);
            String[] addressesArr = addresses.split(",");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, addressesArr);
            AutoCompleteTextView view = (AutoCompleteTextView) V.findViewById(R.id.location1);
            view.setAdapter(adapter);
        }
    }
}
