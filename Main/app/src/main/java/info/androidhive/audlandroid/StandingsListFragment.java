package info.androidhive.audlandroid;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import info.androidhive.audlandroid.R;
import info.androidhive.audlandroid.adapter.StandingsDivisionsPagerAdapter;
import info.androidhive.audlandroid.interfaces.FragmentCallback;
import info.androidhive.audlandroid.model.TeamRecordItem;
import info.androidhive.audlandroid.utils.Utils;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class StandingsListFragment extends Fragment {
	JSONArray jsonResult = null;
	public StandingsListFragment(){}
	public String TAG = "info.androidhive.audlandroid.ScheduleListFragment";
	public ViewPager viewPager;
	public StandingsDivisionsPagerAdapter mAdapter;
	private ArrayList<String> divisionNames;
	private ArrayList<ArrayList<TeamRecordItem>> leagueRecords;
	@Override 
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.pager_container_standings, container, false);

        TextView txtView = (TextView) rootView.findViewById(R.id.list_header);
        txtView.setText("STANDINGS");
        txtView.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Roboto-Bold.ttf"));
        txtView.setTextColor(getResources().getColor(R.color.dark_blue));

        startAsyncTask(getActivity(),rootView);
        return rootView;
    }

    private int getIndex(String division) {
        if (division.equals("South")) {
            return 3;
        }
        else if (division.equals("West")) {
            return 2;
        }
        else if (division.equals("Midwest")) {
            return 1;
        }
        else {
            return 0;
        }
    }
	
	public ArrayList<ArrayList<TeamRecordItem>> parseJSON(JSONArray array){
		divisionNames = new ArrayList<String>();
		leagueRecords = new ArrayList<ArrayList<TeamRecordItem>>();
        Boolean setDivision = false;
        if (array.length()== 3) {
            divisionNames.add("East");
            divisionNames.add("Midwest");
            divisionNames.add("West");
            leagueRecords.add(null);
            leagueRecords.add(null);
            leagueRecords.add(null);
        }
        else  if (array.length()== 4) {
            divisionNames.add("East");
            divisionNames.add("Midwest");
            divisionNames.add("West");
            divisionNames.add("South");
            leagueRecords.add(null);
            leagueRecords.add(null);
            leagueRecords.add(null);
            leagueRecords.add(null);
        }
        else {
            setDivision = true;
        }
		for(int i=0;i<array.length();i++){
			try {
				ArrayList<TeamRecordItem> divisionRecords = new ArrayList<TeamRecordItem>();
				JSONArray array2 = array.getJSONArray(i);
                if (setDivision) divisionNames.add(array2.getString(0));
				for(int j=1;j<array2.length();j++){
					JSONArray record = array2.getJSONArray(j);
					TeamRecordItem recordItem = new TeamRecordItem(record.getString(0),record.getString(1),record.getString(2),record.getString(3),record.getString(4),record.getString(5));
					divisionRecords.add(recordItem);
				}
                if (setDivision) leagueRecords.add(divisionRecords);
                else leagueRecords.set(divisionNames.indexOf(array2.getString(0)), divisionRecords);
			} catch (JSONException e) {
				Log.i(TAG,"JSON Exception" + e.toString());
			}
		}
		return leagueRecords;
	}
	private void startAsyncTask(FragmentActivity activity, final View rootView) {
		final AUDLHttpRequest httpRequester = new AUDLHttpRequest(new FragmentCallback() {
			
			@Override
			public void onTaskDone(String response) {
				try{
					jsonResult = new JSONArray(response);
				}catch(Exception e){
					Log.e(TAG,"Response" + response + ". Error creating json " + e.toString());
				}
				parseJSON(jsonResult);
				viewPager = (ViewPager) rootView.findViewById(R.id.standings_pager);
		        mAdapter = new StandingsDivisionsPagerAdapter(getActivity().getSupportFragmentManager(),divisionNames,leagueRecords);
		 
		        viewPager.setAdapter(mAdapter);

                ImageView westIcon = (ImageView) rootView.findViewById(R.id.west);
                ImageView midwestIcon = (ImageView) rootView.findViewById(R.id.midwest);
                ImageView eastIcon = (ImageView) rootView.findViewById(R.id.east);
                ImageView southIcon = (ImageView) rootView.findViewById(R.id.south);

                eastIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewPager.setCurrentItem(0);
                    }
                });
                midwestIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewPager.setCurrentItem(1);
                    }
                });
                westIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewPager.setCurrentItem(2);
                    }
                });
                southIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewPager.setCurrentItem(3);
                    }
                });

		        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
		        	 
		            @Override
		            public void onPageSelected(int position) {
                        ImageView westIcon = (ImageView) rootView.findViewById(R.id.west);
                        ImageView midwestIcon = (ImageView) rootView.findViewById(R.id.midwest);
                        ImageView eastIcon = (ImageView) rootView.findViewById(R.id.east);
                        ImageView southIcon = (ImageView) rootView.findViewById(R.id.south);

                        westIcon.setImageResource(R.drawable.west_off);
                        midwestIcon.setImageResource(R.drawable.midwest_off);
                        eastIcon.setImageResource(R.drawable.east_off);
                        southIcon.setImageResource(R.drawable.south_off);

                        if (position==0) {
                            eastIcon.setImageResource(R.drawable.east_on);
                        }
                        else if (position==1) {
                            midwestIcon.setImageResource(R.drawable.midwest_on);
                        }
                        else if (position==2) {
                            westIcon.setImageResource(R.drawable.west_on);
                        }
                        else {
                            southIcon.setImageResource(R.drawable.south_on);
                        }
		            }
		         
		            @Override
		            public void onPageScrolled(int arg0, float arg1, int arg2) {
		            	
		            }
		         
		            @Override
		            public void onPageScrollStateChanged(int arg0) {
		            
		            }
		        });

			}

			@Override
			public void onTaskFailure() {
				Utils.ServerError(getActivity());
				
			}
		});
		String serverURL = getResources().getString(R.string.ServerURL);
		httpRequester.execute(serverURL + "/Standings");
	}
}
