package ese05_iscritticorsiDB.model;

public class Course {
	
	private String codins,nome;
	private int crediti,pd;
	
	/**
	 * 
	 * @param codins
	 * @param nome
	 * @param crediti
	 * @param pd
	 */
	public Course(String codins, String nome, int crediti, int pd) {
		super();
		this.codins = codins;
		this.nome = nome;
		this.crediti = crediti;
		this.pd = pd;
	}

	public String getCodins() {
		return codins;
	}

	public String getNome() {
		return nome;
	}

	public int getCrediti() {
		return crediti;
	}

	public int getPd() {
		return pd;
	}

	@Override
	public String toString() {
		return "[" + codins + "] " + nome + ", "
				+ crediti + ", " + pd;
	}
	
	
	
}
