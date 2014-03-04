package hr.foi.air.StudentAssistant.types;

public class Obavijest {


	private String naslov;
	private String autor;
	private String obavijest;

	public Obavijest(String naslov, String autor, String obavijest) {
		this.setNaslov(naslov);
		this.setAutor(autor);
		this.setObavijest(obavijest);
	}

	public String getNaslov() {
		return naslov;
	}


	public String getAutor() {
		return autor;
	}

	public String getObavijest() {
		return obavijest;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}
	
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	public void setObavijest(String obavijest) {
		this.obavijest = obavijest;
	}

}
