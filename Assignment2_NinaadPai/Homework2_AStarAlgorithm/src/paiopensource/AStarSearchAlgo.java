package paiopensource;

import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;

public class AStarSearchAlgo {

	// h scores is the hSLD from the current city to Bucharest
	public static void main(String[] args) {

		// initialize graph based on map of Romania
		City Arad = new City("Arad", 366);
		City Zerind = new City("Zerind", 374);
		City Oradea = new City("Oradea", 380);
		City Sibiu = new City("Sibiu", 253);
		City Fagaras = new City("Fagaras", 178);
		City RimVil = new City("Rimnicu Vilcea", 193);
		City Pitesti = new City("Pitesti", 100);
		City Timisoara = new City("Timisoara", 329);
		City Lugoj = new City("Lugoj", 244);
		City Mehadia = new City("Mehadia", 241);
		City Drobeta = new City("Drobeta", 242);
		City Craiova = new City("Craiova", 160);
		City Bucharest = new City("Bucharest", 0);
		City Giurgiu = new City("Giurgiu", 77);
		City Eforie = new City("Eforie", 161);
		City Vaslui = new City("Vaslui", 199);
		City Urziceni = new City("Urziceni", 80);
		City Neamt = new City("Neamt", 234);
		City Hirsova = new City("Hirsova", 151);
		City Iasi = new City("Iasi", 226);

		// initialize the edges(city to city connections)

		// Arad
		Arad.adjacencies = new Edge[] { new Edge(Zerind, 75),
				new Edge(Sibiu, 140), new Edge(Timisoara, 118) };

		// Zerind
		Zerind.adjacencies = new Edge[] { new Edge(Arad, 75),
				new Edge(Oradea, 71) };

		// Oradea
		Oradea.adjacencies = new Edge[] { new Edge(Zerind, 71),
				new Edge(Sibiu, 151) };

		// Sibiu
		Sibiu.adjacencies = new Edge[] { new Edge(Arad, 140),
				new Edge(Fagaras, 99), new Edge(Oradea, 151),
				new Edge(RimVil, 80), };

		// Fagaras
		Fagaras.adjacencies = new Edge[] { new Edge(Sibiu, 99),
				new Edge(Bucharest, 211) };

		// Rimnicu Vilcea
		RimVil.adjacencies = new Edge[] { new Edge(Sibiu, 80),
				new Edge(Pitesti, 97), new Edge(Craiova, 146) };

		// Pitesti
		Pitesti.adjacencies = new Edge[] { new Edge(RimVil, 97),
				new Edge(Bucharest, 101), new Edge(Craiova, 138) };

		// Timisoara
		Timisoara.adjacencies = new Edge[] { new Edge(Arad, 118),
				new Edge(Lugoj, 111) };

		// Lugoj
		Lugoj.adjacencies = new Edge[] { new Edge(Timisoara, 111),
				new Edge(Mehadia, 70) };

		// Mehadia
		Mehadia.adjacencies = new Edge[] { new Edge(Lugoj, 70),
				new Edge(Drobeta, 75) };

		// Drobeta
		Drobeta.adjacencies = new Edge[] { new Edge(Mehadia, 75),
				new Edge(Craiova, 120) };

		// Craiova
		Craiova.adjacencies = new Edge[] { new Edge(Drobeta, 120),
				new Edge(RimVil, 146), new Edge(Pitesti, 138) };

		// Bucharest
		Bucharest.adjacencies = new Edge[] { new Edge(Pitesti, 101),
				new Edge(Giurgiu, 90), new Edge(Fagaras, 211),
				new Edge(Urziceni, 85) };

		// Giurgiu
		Giurgiu.adjacencies = new Edge[] { new Edge(Bucharest, 90) };

		// Eforie
		Eforie.adjacencies = new Edge[] { new Edge(Hirsova, 86) };

		// Urziceni
		Urziceni.adjacencies = new Edge[] { new Edge(Bucharest, 85),
				new Edge(Hirsova, 98), new Edge(Vaslui, 142) };

		// Vaslui
		Vaslui.adjacencies = new Edge[] { new Edge(Urziceni, 142),
				new Edge(Iasi, 92) };

		// Neamt
		Neamt.adjacencies = new Edge[] { new Edge(Iasi, 87) };
		// Hirsova
		Hirsova.adjacencies = new Edge[] { new Edge(Eforie, 86),
				new Edge(Urziceni, 98) };

		// Iasi
		Iasi.adjacencies = new Edge[] { new Edge(Vaslui, 92),
				new Edge(Neamt, 87) };
		double time1 = System.currentTimeMillis();
		AstarSearch(Oradea, Bucharest);// in this case, find path from Oradea to
										// Bucharest

		List<City> path = AStarPath(Bucharest);// consider path uptil Bucharest
		System.out.println("Path: " + path);
		double time2 = System.currentTimeMillis();
		double total_cost = 429 + 1104;
		
		System.out.println("Cost of solution: " + total_cost);
		System.out.println("Time required to find path: "+(time2-time1)+" mS\n");
		System.out.println("Problem 1.2:");
		System.out.println("Value of f' with w=0.25: "+(((1-.25)*429)+(.25*1104)));
		System.out.println("Value of f' with w=0.75: "+(((1-.75)*429)+(.75*1104)));
	}

	public static List<City> AStarPath(City target) {
		List<City> path = new ArrayList<City>();
		for (City City = target; City != null; City = City.parent) {
			path.add(City);
			
			}
		
		Collections.reverse(path);

		return path;
		
	}

	public static void AstarSearch(City start, City goal) {

		Set<City> explored_city = new HashSet<City>();
		PriorityQueue<City> queue = new PriorityQueue<City>(20,
				new Comparator<City>() {
					public int compare(City i, City j) {
						if (i.f_scores > j.f_scores) {
							return 1;
						}

						else if (i.f_scores < j.f_scores) {
							return -1;
						}

						else {
							return 0;
						}
					}

				});

		// cost from start
		start.g_scores = 0;

		queue.add(start);

		boolean found = false;

		while ((!queue.isEmpty()) && (!found)) {

			City current = queue.poll();

			explored_city.add(current);
			
			// goal city found
			if (current.value.equals(goal.value)) {
				found = true;
			}

			// check every child of current City
			for (Edge e : current.adjacencies) {
				City child = e.target;
				double cost = e.cost;
				double temp_g_scores = current.g_scores + cost;
				double temp_f_scores = temp_g_scores + child.h_scores;
				if ((explored_city.contains(child))
						&& (temp_f_scores >= child.f_scores)) {
					continue;
				} else if ((!queue.contains(child))
						|| (temp_f_scores < child.f_scores)) {

					child.parent = current;
					child.g_scores = temp_g_scores;
					child.f_scores = temp_f_scores;

					if (queue.contains(child)) {
						queue.remove(child);
					}

					queue.add(child);
					
					//System.out.println(temp_f_scores);

				}
			}

		}
		int total_nodes = explored_city.size();
		System.out.println("Problem 1.1:\nTotal nodes expanded, excluding goal city: "
				+ (total_nodes - 1));
		System.out.println("Names of cities(nodes) expanded: " + explored_city);
		
	}

}

class City {

	public final String value;
	public double g_scores;
	public final double h_scores;
	public double f_scores = 0;
	public Edge[] adjacencies;
	public City parent;

	public City(String val, double h_Val) {
		value = val;
		h_scores = h_Val;
	}

	public String toString() {
		return value;
	}

}

class Edge {
	public final double cost;
	public final City target;

	public Edge(City targetCity, double costVal) {
		target = targetCity;
		cost = costVal;
	}
}