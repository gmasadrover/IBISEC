package bean;

import java.util.List;

public class Resultat {
	public class Estadistiques {
		private int aprovades;
		private int pendents;
		private int tancades;
		
		public Estadistiques() {
			
		}
		
		public int getAprovades() {
			return aprovades;
		}

		public void setAprovades(int aprovades) {
			this.aprovades = aprovades;
		}

		public int getPendents() {
			return pendents;
		}

		public void setPendents(int pendents) {
			this.pendents = pendents;
		}

		public int getTancades() {
			return tancades;
		}

		public void setTancades(int tancades) {
			this.tancades = tancades;
		}
	}
	
	
	private Estadistiques estad;
	private List<Actuacio> llistaActuacions;
	public Resultat(){
		
	}
	public Estadistiques getEstad() {
		return estad;
	}
	public void setEstad(Estadistiques estad) {
		this.estad = estad;
	}
	public List<Actuacio> getLlistaActuacions() {
		return llistaActuacions;
	}
	public void setLlistaActuacions(List<Actuacio> llistaActuacions) {
		this.llistaActuacions = llistaActuacions;
	}
	
}
