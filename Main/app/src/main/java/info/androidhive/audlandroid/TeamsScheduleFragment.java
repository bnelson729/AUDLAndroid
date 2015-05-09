package info.androidhive.audlandroid;

import java.util.ArrayList;

import info.androidhive.audlandroid.adapter.ScheduleListBaseAdapter;
import info.androidhive.audlandroid.model.ScheduleListItem;


import android.graphics.Typeface;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class TeamsScheduleFragment extends Fragment {
	
	public TeamsScheduleFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

		String team_name = getArguments().getString("TEAM_NAME");
		String team_id = getArguments().getString("TEAM_ID");
        String team_abbrev = getArguments().getString("TEAM_ABBREV");
		ArrayList<String> sched_teams = getArguments().getStringArrayList("SCHED_TEAMS");
		ArrayList<String> sched_teamIds = getArguments().getStringArrayList("SCHED_TEAMIDS");
		ArrayList<String> sched_times = getArguments().getStringArrayList("SCHED_TIMES");
		ArrayList<String> sched_dates = getArguments().getStringArrayList("SCHED_DATES");
		
		View rootView = inflater.inflate(R.layout.list_container_w_divider, container, false);
		
		ArrayList<ScheduleListItem> schedData = new ArrayList<ScheduleListItem>();
		for (int i=0; i<sched_teams.size(); i++){
			schedData.add(new ScheduleListItem(null, team_name, team_abbrev, team_id, sched_teams.get(i), sched_teamIds.get(i), sched_dates.get(i), sched_times.get(i)));
		}
		
		final ListView listview = (ListView) rootView.findViewById(R.id.listview);
		
		ScheduleListBaseAdapter adapter = new ScheduleListBaseAdapter(getActivity(), schedData);
		listview.setAdapter(adapter);

        return rootView;
    }		
}
