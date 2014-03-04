package hr.foi.air.StudentAssistant.types;

public class Evidencija {
	private String evidencija;
	private String datum;
	private Integer id_kolegija, id_evidencije;



	public Evidencija(Integer id, String evidencija, String datum) {
		this.setIdKolegija(id);
		this.setEvidencija(evidencija);
		this.setDatum(datum);

	}

	public Evidencija(Integer idEvidencije, Integer idKolegija, String evidencija, String datum) {
		this.setIdEvidencije(idEvidencije);
		this.setIdKolegija(idKolegija);
		this.setEvidencija(evidencija);
		this.setDatum(datum);

	}

	private void setIdEvidencije(Integer idEvidencije) {
		this.id_evidencije=idEvidencije;

	}

	public int getIdEvidencije() {
		return id_evidencije;
	}

	public String getEvidencija() {
		return evidencija;
	}


	public void setDatum(String datum) {
		this.datum = datum;
	}


	public String getDatum() {
		return datum;
	}

	public void setEvidencija(String evidencija) {
		this.evidencija = evidencija;
	}

	public Integer getIdKolegija() {
		return id_kolegija;
	}

	public void setIdKolegija(Integer id) {
		this.id_kolegija = id;
	}

}
