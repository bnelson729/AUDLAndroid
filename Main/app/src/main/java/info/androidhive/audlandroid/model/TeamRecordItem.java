package info.androidhive.audlandroid.model;

public class TeamRecordItem {
	private String teamName;
	private String teamID;
	private String wins;
	private String losses;
	private String pointsDiff;
    private String abbreviation;
	
	public TeamRecordItem(String teamName,String teamID,String wins,String losses,String pointsDiff,String abbrev) {
		this.teamName = teamName;
		this.teamID = teamID;
		this.wins = wins;
		this.losses = losses;
		this.pointsDiff = pointsDiff;
        this.abbreviation = abbrev;
	}
    public String getTeamName() {
        return teamName;
    }
	
	public String getTeamID() {
		return teamID;
	}
	
	public String getWins() {
		return wins;
	}
	
	public String getLosses() {
		return losses;
	}
	
	public String getPointsDiff(){
		return pointsDiff;
	}

    public String getAbbreviation() {
        return abbreviation;
    }
	public String toString(){
		return teamName + " " + wins + " " + losses;
	}
	
}
