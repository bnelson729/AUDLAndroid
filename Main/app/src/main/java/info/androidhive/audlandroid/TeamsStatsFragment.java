package info.androidhive.audlandroid;

import info.androidhive.audlandroid.model.StatsListItem;
import info.androidhive.audlandroid.adapter.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;


public class TeamsStatsFragment extends Fragment {
	
	public TeamsStatsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		final String team_name = getArguments().getString("TEAM_NAME");
		final String team_id = getArguments().getString("TEAM_ID");
		
		ArrayList<String> listDataHeader = getArguments().getStringArrayList("STAT_KEYS");
		HashMap<String, List<String>> listDataPlayers = new HashMap<String, List<String>>();
		HashMap<String, List<String>> listDataVal = new HashMap<String, List<String>>();
		
		for (String key : listDataHeader) {
			ArrayList<StatsListItem> statData = getArguments().getParcelableArrayList(key);
			ArrayList<String> playersList = new ArrayList<String>();
			ArrayList<String> statsList = new ArrayList<String>();
			for (int i=0; i<statData.size(); i++){
				playersList.add(statData.get(i).getPlayerName());
				statsList.add(statData.get(i).getStatValue());
			}
			listDataPlayers.put(key, playersList);
			listDataVal.put(key, statsList);
		}
		
		View rootView = inflater.inflate(R.layout.list_container_team_stats, container, false);
		
        ExpandableListAdapter listAdapter = new ExpandableListAdapter(getActivity().getApplicationContext(), listDataHeader, listDataPlayers, listDataVal);
        
        ExpandableListView expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);
        // setting list adapter
        expListView.setAdapter(listAdapter);
        
        Button mButton = (Button) rootView.findViewById(R.id.more);
        mButton.setText("MORE STATS");
        mButton.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Roboto-Bold.ttf"));
        mButton.setTextColor(getResources().getColor(R.color.dark_blue));

        mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String ultimateURL = getResources().getString(R.string.UltimateTeamStatsURL);
				Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ultimateURL+ team_id + "/main"));
	            startActivity(myIntent);
			}
		});

        return rootView;
    }		
}

