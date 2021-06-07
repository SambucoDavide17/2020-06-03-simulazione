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
		dao.listAllPlayers(mGiocatori);
	}
	
	public void creaGrafo(double soglia) {
		
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, dao.getVertici(mGiocatori, soglia));
		
		
	}
	
	public int vertexNumber() {
		return grafo.vertexSet().size();
	}
	
	public int edgeNumber() {
		return grafo.edgeSet().size();
	}
	

}
