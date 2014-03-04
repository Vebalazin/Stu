package hr.foi.air.StudentAssistant.types;

public class Bodovi {
	private Float bod;
	private String oznaka;
	private Integer id_kolegija,id_boda;



	public Bodovi(Integer id, Float bod,String oznaka) {
		this.setIdKolegija(id);
		this.setBod(bod);
		this.setOznaka(oznaka);

	}

	public Bodovi(Integer idBoda, Integer idKolegija, Float bod,String oznaka) {
		this.setIdBoda(idBoda);
		this.setIdKolegija(idKolegija);
		this.setBod(bod);
		this.setOznaka(oznaka);

	}

	private void setIdBoda(Integer idBoda) {
		this.id_boda=idBoda;

	}

	public int getIdBoda(){
		return id_boda;
	}

	public Integer getIdKolegija() {
		return id_kolegija;
	}


	public void setIdKolegija(Integer id) {
		this.id_kolegija = id;
	}


	public Float getBod() {
		return bod;
	}

	public void setBod(Float bod) {
		this.bod = bod;
	}

	public String getOznaka() {
		return oznaka;
	}

	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}

}
