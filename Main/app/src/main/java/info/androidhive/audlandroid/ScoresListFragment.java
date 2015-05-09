package info.androidhive.audlandroid;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import info.androidhive.audlandroid.R;
import info.androidhive.audlandroid.adapter.ScoreDivisionsPagerAdapter;
import info.androidhive.audlandroid.interfaces.FragmentCallback;
import info.androidhive.audlandroid.model.ScoreListItem;
import info.androidhive.audlandroid.utils.Utils;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ScoresListFragment extends Fragment {
	private String TAG = "info.androidhive.audlandroid.model.ScoresListFragment";
	public ScoresListFragment(){}
	public ViewPager viewPager;
	public ScoreDivisionsPagerAdapter mAdapter;
	private ArrayList<String> divisionNames;
	private ArrayList<ArrayList<ScoreListItem>> leagueScores;
	private JSONArray jsonResult;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pager_container_scores, container, false);

        TextView txtView = (TextView) rootView.findViewById(R.id.list_header);
        txtView.setText("SCOREBOARD");
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
	
	public ArrayList<ArrayList<ScoreListItem>> parseJSON(JSONArray array){
		divisionNames = new ArrayList<String>();
		leagueScores = new ArrayList<ArrayList<ScoreListItem>>();
        Boolean setDivision = false;
        if (array.length()== 3) {
            divisionNames.add("East");
            divisionNames.add("Midwest");
            divisionNames.add("West");
            leagueScores.add(null);
            leagueScores.add(null);
            leagueScores.add(null);
        }
        else  if (array.length()== 4) {
            divisionNames.add("East");
            divisionNames.add("Midwest");
            divisionNames.add("West");
            divisionNames.add("South");
            leagueScores.add(null);
            leagueScores.add(null);
            leagueScores.add(null);
            leagueScores.add(null);
        }
        else {
            setDivision = true;
        }
		for(int i=0;i<array.length();i++){
			ArrayList<ScoreListItem> divisionScores = new ArrayList<ScoreListItem>();
			try {
				JSONArray array2 = array.getJSONArray(i);
                if (setDivision) divisionNames.add(array2.getString(0));
				JSONArray array3 = array2.getJSONArray(1);
				for(int j=0;j<array3.length();j++){
					ScoreListItem scoreItem = new ScoreListItem(array3.getJSONArray(j).getString(0),array3.getJSONArray(j).getString(1),array3.getJSONArray(j).getString(2),
							array3.getJSONArray(j).getString(3),array3.getJSONArray(j).getString(4),array3.getJSONArray(j).getString(5),array3.getJSONArray(j).getString(6),
							array3.getJSONArray(j).getString(7),array3.getJSONArray(j).getString(8),array3.getJSONArray(j).getString(9),array2.getString(0));
					divisionScores.add(scoreItem);
				}
                if (setDivision) leagueScores.add(divisionScores);
                else leagueScores.set(divisionNames.indexOf(array2.getString(0)), divisionScores);
			} catch (JSONException e) {
				Log.i(TAG,"JSON Exception");
			}
		}
		return leagueScores;
	}
		
	private void startAsyncTask(final FragmentActivity activity,final View rootView){
		final AUDLHttpRequest httpRequester = new AUDLHttpRequest(new FragmentCallback(){
			@Override
			public void onTaskDone(String response) {
					try{
						jsonResult = new JSONArray(response);
					}catch(JSONException e){
						e.printStackTrace();
					}

					parseJSON(jsonResult);
					viewPager = (ViewPager) rootView.findViewById(R.id.scores_pager);
					mAdapter = new ScoreDivisionsPagerAdapter(activity.getSupportFragmentManager(),divisionNames,leagueScores);
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
			            public void onPageScrolled(int position, float arg1, int arg2) {
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
		httpRequester.execute(serverURL + "/Scores");
	}
	
}
