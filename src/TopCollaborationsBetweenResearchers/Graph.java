package TopCollaborationsBetweenResearchers;

import java.util.*;

class Graph {

	private Map<Researcher, List<Edge>> map = new HashMap<Researcher, List<Edge>>();
	ArrayList<Edge> allEdges = new ArrayList<Edge>();

	public void addVertex(Researcher s) {
		map.put(s, new ArrayList<Edge>());
	}

	public List<Edge> getEdges(Researcher key) {
		return map.get(key);
	}

	public void addEdge(Researcher source, Researcher destination, Integer weight, boolean bidirectional) {
		addEdge(new Edge(source, destination, weight), bidirectional);
	}

	public boolean addEdge(Edge e, boolean bidirectional) {
		if (!map.containsKey(e.src))
			addVertex(e.src);
		if (!map.containsKey(e.dest))
			addVertex(e.dest);
		map.get(e.src).add(e);
		if (bidirectional == true) {
			map.get(e.dest).add(e);
		}
		allEdges.add(e);
		return true;
	}

	public Integer getVertexCount() {
		return map.keySet().size();
	}

	public ArrayList<Edge> getAllEdges() {
		return allEdges;
	}

	public Integer getEdgesCount(boolean bidirection) {
		int count = 0;
		for (Researcher v : map.keySet()) {
			count += map.get(v).size();
		}
		if (bidirection == true) {
			count = count / 2;
		}
		return count;
	}

	public boolean hasVertex(Researcher s) {
		if (map.containsKey(s)) {
			return true;
		} else {
			return false;
		}
	}

	public Set<Researcher> getVertices() {
		return map.keySet();
	}

	public boolean hasEdge(Researcher s, Researcher d) {
		if (map.get(s).contains(new Edge(s, d, (Integer) null))) {
			return true;
		} else {
			return false;
		}
	}

	public boolean removeEdge(Researcher src, Researcher dest) {
		boolean a = map.get(src).remove(new Edge(src, dest, (Integer) null));
		boolean b = false;
		if (a)
			b = map.get(dest).remove(new Edge(dest, src, (Integer) null));
		return a && b;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Researcher v : map.keySet()) {
			builder.append(v.toString() + ": ");
			for (Edge w : map.get(v)) {
				builder.append(w.toString() + " ");
			}
			builder.append("\n");
		}
		return (builder.toString());
	}

}