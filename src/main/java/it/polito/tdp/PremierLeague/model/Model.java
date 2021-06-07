package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
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
					Graphs.addEdge(grafo, mGiocatori.get(a.getP1().getPlayerID()), mGiocatori.get(a.getP2().getPlayerID()), soglia);
				if(a.getDeltaMinuti() < 0) 
					Graphs.addEdge(grafo, mGiocatori.get(a.getP2().getPlayerID()), mGiocatori.get(a.getP1().getPlayerID()), soglia * -1);
			}
		}
	}
	
	public int vertexNumber() {
		return grafo.vertexSet().size();
	}
	
	public int edgeNumber() {
		return grafo.edgeSet().size();
	}
	

}
