package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Adiacenza;
import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.PlayerAndGoal;

public class PremierLeagueDAO {
	
	public void listAllPlayers(Map<Integer, Player> mGiocatori){
		String sql = "SELECT * FROM Players";
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(!mGiocatori.containsKey(res.getInt("PlayerID"))) {

					Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
					mGiocatori.put(player.getPlayerID(), player);
				}
			}
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<PlayerAndGoal> getVertici(Map<Integer, Player> mGiocatori, double soglia) {
		String sql = "Select p.PlayerID, p.Name, sum(a.Goals)/count(*) as gpp "
				+ "From Players p, Actions a "
				+ "Where p.PlayerID = a.PlayerID "
				+ "Group by p.PlayerID, p.Name";
		List<PlayerAndGoal> risultato = new ArrayList<>();
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				if(mGiocatori.containsKey(res.getInt("PlayerID"))) {
					if(res.getDouble("gpp") > soglia) {
						PlayerAndGoal nuovo = new PlayerAndGoal(mGiocatori.get(res.getInt("PlayerID")), res.getDouble("gpp"));
						risultato.add(nuovo);
					}
				}
			}
			conn.close();
			return risultato;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Adiacenza> getArchi(double soglia) {
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
