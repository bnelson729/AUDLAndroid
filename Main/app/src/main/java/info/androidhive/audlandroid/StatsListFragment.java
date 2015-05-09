package info.androidhive.audlandroid;

import info.androidhive.audlandroid.adapter.ExpandableListAdapter;
import info.androidhive.audlandroid.interfaces.FragmentCallback;
import info.androidhive.audlandroid.model.StatsListItem;
import info.androidhive.audlandroid.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class StatsListFragment extends Fragment {
	
	public StatsListFragment(){}
	
	SharedPreferences sharedPrefStats;
	JSONObject jsonResult = null;
    String response = null;
    HashMap<String, List<String>> listDataPlayers;
    HashMap<String, List<String>> listDataVal;
    ArrayList<String> listDataHeader;
	
	public ArrayList<String> parseJSON(JSONObject jsonResult){
        listDataHeader = new ArrayList<String>();
        listDataPlayers = new HashMap<String, List<String>>();
        listDataVal = new HashMap<String, List<String>>();
		try {
			Iterator keys = jsonResult.keys();
			while(keys.hasNext()){
                ArrayList<String> tempPlayers = new ArrayList<String>();
                ArrayList<String> tempData = new ArrayList<String>();
				String statName = (String) keys.next();
                listDataHeader.add(statName);
				JSONArray statValues = jsonResult.getJSONArray(statName);
				for(int i=0; i<statValues.length(); i++){
                    tempPlayers.add(statValues.getJSONArray(i).getString(0));
                    tempData.add(statValues.getJSONArray(i).getString(1));
				}
                listDataPlayers.put(statName, tempPlayers);
                listDataVal.put(statName, tempData);
			}
					
		} catch (Exception e) {
			Log.e("StatsListFragment", "Error when trying to create news objects from json : " + e.toString());
		}
		return listDataHeader;
	}
	
	public void startCacheHandler(final View rootView, final StatsListFragment frag) {
		final EmptyRequest emptyRequest = new EmptyRequest(
				new FragmentCallback() {
					@Override
					public void onTaskFailure(){
						Utils.ServerError(getActivity());
					}
					@Override
					public void onTaskDone(String response) {
						sharedPrefStats = frag.getActivity().getSharedPreferences(frag.getActivity().getResources().getString(R.string.StatsListCache), Context.MODE_PRIVATE);
						String oldResponse = sharedPrefStats.getString(frag.getActivity().getResources().getString(R.string.StatsListCache), "");
						
						if(!oldResponse.equals("")){
							try{
					            jsonResult = new JSONObject(oldResponse);
					        } catch (Exception e) {
					        	Log.e("StatsListFragment", "Response: " + oldResponse + ". Error creating json " + e.toString());
					        }
					        
					        parseJSON(jsonResult);

                            ExpandableListAdapter listAdapter = new ExpandableListAdapter(getActivity().getApplicationContext(), listDataHeader, listDataPlayers, listDataVal);

                            ExpandableListView expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);
                            // setting list adapter
                            expListView.setAdapter(listAdapter);
						}
					}
				});
			emptyRequest.execute("empty");
	}
	
	public void startAsyncTask(final View rootView, final StatsListFragment frag){
		final AUDLHttpRequest httpRequester = new AUDLHttpRequest(new FragmentCallback() {
			@Override
			public void onTaskFailure(){
				Utils.ServerError(getActivity());
			}
			@Override
			public void onTaskDone(String response) {
				sharedPrefStats = frag.getActivity().getSharedPreferences(frag.getActivity().getResources().getString(R.string.StatsListCache), Context.MODE_PRIVATE);
				String oldResponse = sharedPrefStats.getString(frag.getActivity().getResources().getString(R.string.StatsListCache), "");
				if(!oldResponse.equals(response)){
					
			        try{
			            jsonResult = new JSONObject(response);
			        } catch (Exception e) {
			        	Log.e("StatsListFragment", "Response: " + response + ". Error creating json " + e.toString());
			        }
			        
			        if(jsonResult != null && response.length() > 0){
						SharedPreferences.Editor editor = sharedPrefStats.edit();
		        	    editor.putString(frag.getActivity().getResources().getString(R.string.StatsListCache), response);
		        	    editor.commit();
			        }
			        
			        parseJSON(jsonResult);

                    ExpandableListAdapter listAdapter = new ExpandableListAdapter(getActivity().getApplicationContext(), listDataHeader, listDataPlayers, listDataVal);

                    ExpandableListView expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);
                    // setting list adapter
                    expListView.setAdapter(listAdapter);

				}
			}
		});
		String serverURL = getResources().getString(R.string.ServerURL);
		httpRequester.execute(serverURL + "/Stats");
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_container_stats, container, false);

        TextView txtView = (TextView) rootView.findViewById(R.id.list_header);
        txtView.setText("STATISTICS");
        txtView.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Roboto-Bold.ttf"));
        txtView.setTextColor(getResources().getColor(R.color.dark_blue));

        startCacheHandler(rootView, this);
        startAsyncTask(rootView, this);         
        return rootView;
    }
}
