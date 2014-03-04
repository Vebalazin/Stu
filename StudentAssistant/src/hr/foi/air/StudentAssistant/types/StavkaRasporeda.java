package hr.foi.air.StudentAssistant.types;

public class StavkaRasporeda {

	private String vrijeme_p;
	private String vrijeme_k;
	private Integer dvorana;
	private String dan, nastava;
	private Integer id_kolegija, id_stavke;



	public StavkaRasporeda(Integer id, Integer idK, String vrijeme_p, String vrijeme_k,Integer dvorana, String dan, String nastava) {
		this.setIdStavke(id);
		this.setIdKolegija(idK);
		this.setVrijemeP(vrijeme_p);
		this.setVrijemeK(vrijeme_k);
		this.setDvorana(dvorana);
		this.setDan(dan);
		this.setNastava(nastava);

	}

	public StavkaRasporeda( Integer idK, String vrijeme_p, String vrijeme_k,Integer dvorana, String dan, String nastava) {
		this.setIdKolegija(idK);
		this.setVrijemeP(vrijeme_p);
		this.setVrijemeK(vrijeme_k);
		this.setDvorana(dvorana);
		this.setDan(dan);
		this.setNastava(nastava);
	}

	private void setIdStavke(Integer id) {
		this.id_stavke=id;
	}

	private void setNastava(String nastava) {
		this.nastava=nastava;
	}

	public String getNastava() {
		return nastava;
	}

	private void setDan(String dan) {
		this.dan=dan;
	}
	
	public String getDan() {
		return dan;
	}

	private void setDvorana(Integer dvorana) {
		this.dvorana=dvorana;
	}
	
	public Integer getDvorana() {
		return dvorana;
	}

	public String getVrijemeP() {
		return vrijeme_p;
	}

	public void setVrijemeP(String vrijeme_p) {
		this.vrijeme_p = vrijeme_p;
	}

	public void setVrijemeK(String vrijeme_k) {
		this.vrijeme_k = vrijeme_k;
	}
	
	public String getVrijemeK() {
		return vrijeme_k;
	}

	public Integer getIdKolegija() {
		return id_kolegija;
	}

	public Integer getIdStavke() {
		return id_stavke;
	}

	public void setIdKolegija(Integer id) {
		this.id_kolegija = id;
	}

}


