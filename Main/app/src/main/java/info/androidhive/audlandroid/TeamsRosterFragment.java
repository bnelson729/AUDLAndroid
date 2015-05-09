package info.androidhive.audlandroid;

import java.util.ArrayList;

import info.androidhive.audlandroid.adapter.TeamRosterBaseAdapter;


import android.graphics.Typeface;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class TeamsRosterFragment extends Fragment {
	
	public TeamsRosterFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		String team_name = getArguments().getString("TEAM_NAME");
		ArrayList<String> player_ids = getArguments().getStringArrayList("PLAYER_IDS");
		ArrayList<String> player_names = getArguments().getStringArrayList("PLAYER_NAMES");
		
		View rootView = inflater.inflate(R.layout.list_container_w_divider, container, false);

        ArrayList<String> listVal = new ArrayList<String>();
        for (int i=0; i<player_names.size(); i++){
        	listVal.add(player_ids.get(i) + " " + player_names.get(i));
        }
		
		final ListView listview = (ListView) rootView.findViewById(R.id.listview);
        
        //final ListAdapter adapter = new ListAdapter(this.getActivity(), android.R.layout.simple_list_item_1, listVal);
		final TeamRosterBaseAdapter adapter = new TeamRosterBaseAdapter(getActivity(), player_ids, player_names);
        listview.setAdapter(adapter);

        return rootView;
    }		
}
