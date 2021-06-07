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

	public List<Player> getVertici(Map<Integer, Player> mGiocatori, double soglia) {
		String sql = "Select p.PlayerID, p.Name "
				+ "From Players p, Actions a "
				+ "Where p.PlayerID = a.PlayerID "
				+ "Group by p.PlayerID, p.Name "
				+ "Having AVG(a.Goals) > ?";
		List<Player> risultato = new ArrayList<>();
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDouble(1, soglia);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				risultato.add(player);
				mGiocatori.put(player.getPlayerID(), player);
			}
			
			conn.close();
			return risultato;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Adiacenza> getArchi(double soglia, Map<Integer, Player> mGiocatori) {
		String sql = "Select a1.PlayerID as p1, a2.PlayerID as p2, (sum(a1.TimePlayed) - sum(a2.TimePlayed)) as peso "
				+ "From Actions a1, Actions a2 "
				+ "Where a1.PlayerID <> a2.PlayerID and a1.MatchID = a2.MatchID and a1.Starts = 1 and a2.Starts = 1 "
				+ "Group by a1.PlayerID, a2.PlayerID ";
		List<Adiacenza> risultato = new ArrayList<>();
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(mGiocatori.containsKey(res.getInt("p1")) && mGiocatori.containsKey(res.getInt("p2"))) {
					Adiacenza nuova = new Adiacenza(mGiocatori.get(res.getInt("p1")), mGiocatori.get(res.getInt("p2")), res.getInt("peso"));
					risultato.add(nuova);
				}
			}
			conn.close();
			return risultato;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
