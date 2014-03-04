package hr.foi.air.StudentAssistant.types;

public class Kolegij {
	private String naziv;
	private int id;



	public Kolegij(String naziv) {
		this.setName(naziv);
	}

	public Kolegij(Integer id, String naziv) {
		this.setId(id);
		this.setName(naziv);
	}


	private void setId(Integer id) {
		this.id=id;

	}

	public int getId() {
		return id;
	}


	public String getName() {
		return naziv;
	}

	public void setName(String naziv) {
		this.naziv = naziv;
	}

}
