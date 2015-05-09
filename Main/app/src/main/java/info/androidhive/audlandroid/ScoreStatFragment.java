package info.androidhive.audlandroid;

import info.androidhive.audlandroid.utils.TeamIconLoader;

import java.util.ArrayList;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ScoreStatFragment extends Fragment{

	public ScoreStatFragment(){}
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		 View rootView = inflater.inflate(R.layout.fragment_score_stat,container,false);
		 Bundle args = this.getArguments();
		 TextView homeGoal = (TextView) rootView.findViewById(R.id.homeGoal);
		 TextView awayGoal = (TextView) rootView.findViewById(R.id.awayGoal);
        TextView goalTitle = (TextView) rootView.findViewById(R.id.goalTitle);
		 TextView homeAssist = (TextView) rootView.findViewById(R.id.homeAssist);
		 TextView awayAssist = (TextView) rootView.findViewById(R.id.awayAssist);
        TextView assistTitle = (TextView) rootView.findViewById(R.id.assistTitle);
		 TextView homeDrop = (TextView) rootView.findViewById(R.id.homeDrop);
		 TextView awayDrop = (TextView) rootView.findViewById(R.id.awayDrop);
        TextView dropTitle = (TextView) rootView.findViewById(R.id.dropTitle);
		 TextView homeTA = (TextView) rootView.findViewById(R.id.homeTA);
		 TextView awayTA = (TextView) rootView.findViewById(R.id.awayTA);
        TextView taTitle = (TextView) rootView.findViewById(R.id.taTitle);
		 TextView homeD = (TextView) rootView.findViewById(R.id.homeD);
		 TextView awayD = (TextView) rootView.findViewById(R.id.awayD);
        TextView dTitle = (TextView) rootView.findViewById(R.id.dTitle);
		 ArrayList<String> homePlayers = args.getStringArrayList("HOMEPLAYERS");
		 ArrayList<String> awayPlayers = args.getStringArrayList("AWAYPLAYERS");
		 ArrayList<String> homeNumbers = args.getStringArrayList("HOMENUMBERS");
		 ArrayList<String> awayNumbers = args.getStringArrayList("AWAYNUMBERS");
         homeGoal.setText(homePlayers.get(0) + " (" + (homeNumbers.get(0)) + ")");
		 awayGoal.setText(awayPlayers.get(0) + " (" + (awayNumbers.get(0)) + ")");
		 homeAssist.setText(homePlayers.get(1) + " (" + (homeNumbers.get(1)) + ")");
		 awayAssist.setText(awayPlayers.get(1) + " (" + (awayNumbers.get(1)) + ")");
		 homeDrop.setText(homePlayers.get(2) + " (" + (homeNumbers.get(2)) + ")");
		 awayDrop.setText(awayPlayers.get(2) + " (" + (awayNumbers.get(2)) + ")");
		 homeTA.setText(homePlayers.get(3) + " (" + (homeNumbers.get(3)) + ")");
		 awayTA.setText(awayPlayers.get(3) + " (" + (awayNumbers.get(3)) + ")");
		 homeD.setText(homePlayers.get(4) + " (" + (homeNumbers.get(4)) + ")");
		 awayD.setText(awayPlayers.get(4) + " (" + (awayNumbers.get(4)) + ")");

        Typeface boldTypeface = Typeface.createFromAsset(getResources().getAssets(), "Roboto-Bold.ttf");
        Typeface mediumTypeface = Typeface.createFromAsset(getResources().getAssets(), "Roboto-Medium.ttf");

        homeGoal.setTypeface(boldTypeface);
        homeGoal.setTextColor(getResources().getColor(R.color.dark_blue));

        awayGoal.setTypeface(boldTypeface);
        awayGoal.setTextColor(getResources().getColor(R.color.dark_blue));

        goalTitle.setTypeface(mediumTypeface);
        goalTitle.setTextColor(getResources().getColor(R.color.light_gray));

        homeAssist.setTypeface(boldTypeface);
        homeAssist.setTextColor(getResources().getColor(R.color.dark_blue));

        awayAssist.setTypeface(boldTypeface);
        awayAssist.setTextColor(getResources().getColor(R.color.dark_blue));

        assistTitle.setTypeface(mediumTypeface);
        assistTitle.setTextColor(getResources().getColor(R.color.light_gray));

        homeDrop.setTypeface(boldTypeface);
        homeDrop.setTextColor(getResources().getColor(R.color.dark_blue));

        awayDrop.setTypeface(boldTypeface);
        awayDrop.setTextColor(getResources().getColor(R.color.dark_blue));

        dropTitle.setTypeface(mediumTypeface);
        dropTitle.setTextColor(getResources().getColor(R.color.light_gray));

        homeTA.setTypeface(boldTypeface);
        homeTA.setTextColor(getResources().getColor(R.color.dark_blue));

        awayTA.setTypeface(boldTypeface);
        awayTA.setTextColor(getResources().getColor(R.color.dark_blue));

        taTitle.setTypeface(mediumTypeface);
        taTitle.setTextColor(getResources().getColor(R.color.light_gray));

        homeD.setTypeface(boldTypeface);
        homeD.setTextColor(getResources().getColor(R.color.dark_blue));

        awayD.setTypeface(boldTypeface);
        awayD.setTextColor(getResources().getColor(R.color.dark_blue));

        dTitle.setTypeface(mediumTypeface);
        dTitle.setTextColor(getResources().getColor(R.color.light_gray));
		 return rootView;
	 }
}
