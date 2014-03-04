package hr.foi.air.StudentAssistant.types;

public class Biljeska {
	private String biljeska;
	private Integer id_kolegija,id_biljeske;



	public Biljeska(Integer id, String biljeska) {
		this.setIdKolegija(id);
		this.setBiljeska(biljeska);

	}

	public Biljeska(Integer id_biljeske, Integer idKolegija, String biljeska) {
		this.setIdBiljeske(id_biljeske);
		this.setIdKolegija(idKolegija);
		this.setBiljeska(biljeska);

	}

	private void setIdBiljeske(Integer id_biljeske) {
		this.id_biljeske=id_biljeske;

	}

	public Integer getIdBiljeske() {
		return id_biljeske;
	}

	public Integer getIdKolegija() {
		return id_kolegija;
	}


	public void setIdKolegija(Integer id) {
		this.id_kolegija = id;
	}


	public String getBiljeska() {
		return biljeska;
	}

	public void setBiljeska(String biljeska) {
		this.biljeska = biljeska;
	}


}
