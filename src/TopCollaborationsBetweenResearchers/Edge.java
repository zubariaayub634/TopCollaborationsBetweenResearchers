package TopCollaborationsBetweenResearchers;

class Edge implements Comparable<Edge> {
	public Researcher src;
	public Researcher dest;
	public Integer weight;

	Edge() {

	}

	Edge(Researcher s, Researcher n, Integer w) {
		this.src = s;
		this.dest = n;
		this.weight = w;
	}

	public String toString() {
		return this.src.toString() + " -> " + this.dest.toString() + " (" + this.weight.toString() + ")";
	}

	public boolean equals(Object n) {
		if (n.getClass() != this.getClass())
			return false;
		Edge ne = (Edge) n;
		return this.dest.equals(ne.dest);
	}

	public int compareTo(Edge o) {
		if (this.weight < o.weight)
			return -1;
		if (this.weight > o.weight)
			return 1;
		return 0;
//		return this.weight - o.weight;
	}
}