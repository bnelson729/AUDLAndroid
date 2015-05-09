package info.androidhive.audlandroid;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import info.androidhive.audlandroid.adapter.StandingsListBaseAdapter;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class StandingsDivisionListFragment extends Fragment{
    private ArrayList<ArrayList<String>> list;
	public StandingsDivisionListFragment(){}
	
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		Bundle bundle = this.getArguments();
        list = new ArrayList<ArrayList<String>>();
        ArrayList<String> divisionTeamNames;
        ArrayList<String> divisionTeamWins;
        ArrayList<String> divisionTeamLosses;
        ArrayList<String> divisionTeamPointsDiff;
        ArrayList<String> divisionTeamAbbrev;
		divisionTeamNames = bundle.getStringArrayList("divisionTeamNames");
		divisionTeamWins = bundle.getStringArrayList("divisionTeamWins");
		divisionTeamLosses = bundle.getStringArrayList("divisionTeamLosses");
		divisionTeamPointsDiff = bundle.getStringArrayList("divisionTeamPointsDiff");
        divisionTeamAbbrev = bundle.getStringArrayList("divisionTeamAbbrev");
        list.add(divisionTeamNames);
        list.add(divisionTeamWins);
        list.add(divisionTeamLosses);
        list.add(divisionTeamPointsDiff);
        list.add(divisionTeamAbbrev);
        View rootView = inflater.inflate(R.layout.list_container_w_divider, container,false);
        ListView listView = (ListView) rootView.findViewById(R.id.listview);
        final StandingsListBaseAdapter adapter = new StandingsListBaseAdapter(getActivity(),list);
        listView.setAdapter(adapter);

		return rootView;
	}
}
