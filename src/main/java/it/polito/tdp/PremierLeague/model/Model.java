package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	PremierLeagueDAO dao;
	Graph<Player, DefaultWeightedEdge> grafo;
	Map<Integer, Player> mGiocatori;
	Player top;
	
	List<Player> dreamTaem;
	
	public Model(){
		
		dao = new PremierLeagueDAO();
		mGiocatori = new HashMap<>();
	}
	
	public void creaGrafo(double soglia) {
		
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, dao.getVertici(mGiocatori, soglia));
		
		for(Adiacenza a: dao.getArchi(soglia, mGiocatori)) {
			if(grafo.getEdge(mGiocatori.get(a.getP1().getPlayerID()), mGiocatori.get(a.getP2().getPlayerID())) == null){
				if(a.getDeltaMinuti() > 0) 
					Graphs.addEdge(grafo, mGiocatori.get(a.getP1().getPlayerID()), mGiocatori.get(a.getP2().getPlayerID()), a.getDeltaMinuti());
				if(a.getDeltaMinuti() < 0) 
					Graphs.addEdge(grafo, mGiocatori.get(a.getP2().getPlayerID()), mGiocatori.get(a.getP1().getPlayerID()), a.getDeltaMinuti() * -1);
			}
		}
	}
	
	public int vertexNumber() {
		return grafo.vertexSet().size();
	}
	
	public int edgeNumber() {
		return grafo.edgeSet().size();
	}
	
	public Player topPlayer() {
		
		Player topPlayer = null;
		int delta = -1000000000;
		for(Player p: grafo.vertexSet()) {
			if((grafo.outDegreeOf(p) - grafo.inDegreeOf(p)) > delta) {
				topPlayer = p;
				delta = grafo.outDegreeOf(p) - grafo.inDegreeOf(p);
			}
		}
		top = topPlayer;
		return topPlayer;
	}
	
	public List<PlayerEDelta> Sconfitti(){
		
		List<PlayerEDelta> sconfitti = new LinkedList<>();
		for(DefaultWeightedEdge e: grafo.outgoingEdgesOf(top)) {
			PlayerEDelta nuovo = new PlayerEDelta(grafo.getEdgeTarget(e), grafo.getEdgeWeight(e));
			sconfitti.add(nuovo);
		}
		Collections.sort(sconfitti, new Comparator<PlayerEDelta>() {
			
			@Override	
			public int compare(PlayerEDelta p1, PlayerEDelta p2) {
			return (int) -(p1.getDelta() - p2.getDelta());
		}
		
	});
		return sconfitti;
	}
	
	public List<Player> DreamTeam(int nPlayer){
		
		dreamTaem = null;
		List<Player> parziale = new ArrayList<>();
		
		cerca(parziale, 1, nPlayer);
		return dreamTaem;
	}
	
	public void cerca(List<Player> parziale, int livello, int nPlayer) {
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}