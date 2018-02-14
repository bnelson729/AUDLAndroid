package info.androidhive.audlandroid.utils;

import info.androidhive.audlandroid.R;
/**
 * Created by Ben on 3/13/2015.
 */
public class TeamIconLoader {
    public int get(String id) {
        if (id.equals("GENERAL")) {
            return R.drawable.ic_launcher;
        }
        if (id.equals("TEST")) {
            return R.drawable.ic_launcher;
        }
        if (id.equals("ATL")) {
            return R.drawable.team_ic_atl;
        }
        else if (id.equals("CHA")) {
            return R.drawable.team_ic_cha;
        }
        else if (id.equals("CHI")) {
            return R.drawable.team_ic_chi;
        }
        else if (id.equals("CIN")) {
            return R.drawable.team_ic_cin;
        }
        else if (id.equals("DC")) {
            return R.drawable.team_ic_dc;
        }
        else if (id.equals("DET")) {
            return R.drawable.team_ic_det;
        }
        else if (id.equals("IND")) {
            return R.drawable.team_ic_ind;
        }
        else if (id.equals("JAX")) {
            return R.drawable.team_ic_jax;
        }
        else if (id.equals("LA")) {
            return R.drawable.team_ic_la;
        }
        else if (id.equals("MAD")) {
            return R.drawable.team_ic_mad;
        }
        else if (id.equals("MIN")) {
            return R.drawable.team_ic_min;
        }
        else if (id.equals("MTL")) {
            return R.drawable.team_ic_mtl;
        }
        else if (id.equals("NSH")) {
            return R.drawable.team_ic_nsh;
        }
        else if (id.equals("NY")) {
            return R.drawable.team_ic_ny;
        }
        else if (id.equals("OTT")) {
            return R.drawable.team_ic_ott;
        }
        else if (id.equals("PHI")) {
            return R.drawable.team_ic_phi;
        }
        else if (id.equals("PIT")) {
            return R.drawable.team_ic_pit;
        }
        else if (id.equals("RAL")) {
            return R.drawable.team_ic_ral;
        }
        else if (id.equals("ROC")) {
            return R.drawable.team_ic_roc;
        }
        else if (id.equals("SD")) {
            return R.drawable.team_ic_sd;
        }
        else if (id.equals("SEA")) {
            return R.drawable.team_ic_sea;
        }
        else if (id.equals("SF")) {
            return R.drawable.team_ic_sf;
        }
        else if (id.equals("SJ")) {
            return R.drawable.team_ic_sj;
        }
        else if (id.equals("SLC")) {
            return R.drawable.team_ic_slc;
        }
        else if (id.equals("TOR")) {
            return R.drawable.team_ic_tor;
        }
        else if (id.equals("VAN")) {
            return R.drawable.team_ic_van;
        }
        else if (id.equals("AUS")) {
            return R.drawable.team_ic_aus;
        }
        else if (id.equals("DAL")) {
            return R.drawable.team_ic_dal;
        }
        else {
            return R.drawable.no_image;
        }
    }
}
