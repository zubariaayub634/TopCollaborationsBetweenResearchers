package TopCollaborationsBetweenResearchers;

public class Researcher {
	private Integer id;
	private String name;

	public Researcher(Integer id, String name) {
		this.id = id;
		this.name = name;
		// System.out.print("Researcher created ");
		// System.out.println(this.id);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return id.toString() + " " + name;
	}
}
