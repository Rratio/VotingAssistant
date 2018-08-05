package minorproject.votingassistant.ECJava;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import minorproject.votingassistant.R;

public class DMVoteStatus extends Fragment {
    FragmentManager fm;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dmvote_status, container, false);
        return view;
    }
}
