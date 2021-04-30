package TopCollaborationsBetweenResearchers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class ResearcherGraph {

	Graph g = new Graph();
	HashMap<Integer, Researcher> researcherRef = new HashMap<Integer, Researcher>();
	HashMap<Researcher, Integer> indexRef = new HashMap<Researcher, Integer>();
	Graph mst = null;

	public void getAllResearcherLabels(String fileName) {
		System.out.print("Reading Researcher Labels");
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fileName))); // creates a buffering character
			String line;
			Integer lineCount = 0;
			while ((line = br.readLine()) != null) {
				String[] vals = line.split(" ", 2);
				Integer id = Integer.parseInt(vals[0]);
				Researcher r = new Researcher(id, vals[1]);
				researcherRef.put(id, r);
				indexRef.put(r, id);
				// mst.addVertex(r);
				g.addVertex(r);
				lineCount++;
				if (lineCount % 50000 == 0)
					System.out.print(".");
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("\nCompleted");
	}

	public Researcher getResearcherById(Integer id) {
		return researcherRef.get(id);
	}

	public void getAllResearcherEdges(String fileName) {
		System.out.print("Adding researcher edges");
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fileName))); // creates a buffering character
			String line;
			Integer lineCount = 0;
			while ((line = br.readLine()) != null) {
				String[] vals = line.split(" ", 3);
				Edge e = new Edge(getResearcherById(Integer.parseInt(vals[0])),
						getResearcherById(Integer.parseInt(vals[1])), Integer.parseInt(vals[2]));
				g.addEdge(e, true);
				lineCount++;
				if (lineCount % 50000 == 0)
					System.out.print(".");
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("\nCompleted");
	}

	public String toString() {
		return g.toString();
	}

	public Graph getMST() {
		if (mst != null) {
			return mst;
		}
		System.out.print("Finding MST");
		mst = new Graph();
		Integer[] setNumber = new Integer[researcherRef.size()];
		for (int i = 0; i < setNumber.length; i++) {
			setNumber[i] = i;
			mst.addVertex(researcherRef.get(i));
		}
		ArrayList<Edge> allEdges = g.getAllEdges();
		Collections.sort(allEdges, new Comparator<Edge>() { // sort in descending order

			@Override
			public int compare(Edge o1, Edge o2) {
				return -o1.compareTo(o2);
			}
		});
		System.out.print(".");
		int eCount = 0;
		int maxEdges = researcherRef.size() - 1;
		for (Edge e : allEdges) {
			Integer srcIndex = indexRef.get(e.src);
			Integer destIndex = indexRef.get(e.dest);
			Integer srcSet = setNumber[srcIndex];
			Integer destSet = setNumber[destIndex];
			srcSet = setNumber[indexRef.get(e.src)];
			if (!srcSet.equals(destSet)) {
				Integer smallerSetValue = Math.min(srcSet, destSet);
				setNumber[srcIndex] = smallerSetValue;
				setNumber[destIndex] = smallerSetValue;
				mst.addEdge(e, true);
				eCount++;
				if (eCount % 50000 == 0)
					System.out.print(".");
				if (eCount >= maxEdges) {
					break;
				}
			}
		}
		System.out.println();
		return mst;
	}

	public String getTopCollaboration(Integer rank) {
		Graph mst = getMST();
		ArrayList<Edge> allEdges = mst.getAllEdges();
		Collections.sort(allEdges, new Comparator<Edge>() { // sort in descending order

			@Override
			public int compare(Edge o1, Edge o2) {
				return -o1.compareTo(o2);
			}
		});
		return allEdges.get(rank - 1).toString();
	}

	public static void main(String[] args) {
		long startTime = System.nanoTime();

		ResearcherGraph g = new ResearcherGraph();
		g.getAllResearcherLabels("assets/coauth-DBLP-proj-graph/coauth-DBLP-node-labels.txt");
		g.getAllResearcherEdges("assets/coauth-DBLP-proj-graph/coauth-DBLP-proj-graph.txt");
		Graph mst = g.getMST();
		System.out.print("Edges in MST: ");
		System.out.println(mst.getEdgesCount(true));
		System.out.println("\n\n----------------------------- Top 5 Collaborators -----------------------------");
		for (int i = 1; i <= 5; i++) {
			System.out.print(i);
			System.out.println(". " + g.getTopCollaboration(i));
		}

		long endTime = System.nanoTime();
		System.out.print("Time taken: ");

		System.out.print(TimeUnit.NANOSECONDS.toHours(endTime - startTime));
		System.out.print(":");
		System.out.print(TimeUnit.NANOSECONDS.toMinutes(endTime - startTime));
		System.out.print(":");
		System.out.print(TimeUnit.NANOSECONDS.toSeconds(endTime - startTime));
		System.out.print(".");
		System.out.println(TimeUnit.NANOSECONDS.toMillis(endTime - startTime));
	}
}
