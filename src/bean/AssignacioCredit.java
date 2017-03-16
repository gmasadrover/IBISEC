package bean;

import java.util.Date;

public class AssignacioCredit {
	private int idAssignacio;	
	private int idActuacio;
	private int idInf;
	private boolean reserva;
	private User usuReserva;
	private Date datareserva;
	private boolean assignacio;
	private User usuAssignacio;
	private Date dataAssignacio;
	private String comentari;
	private User usuCre;
	private Date dataCre;
	private String idPartida;
	private double valorPA;
	private double valorPD;
	  
	public AssignacioCredit(){
		
	}

	public int getIdAssignacio() {
		return idAssignacio;
	}

	public void setIdAssignacio(int idAssignacio) {
		this.idAssignacio = idAssignacio;
	}

	public int getIdActuacio() {
		return idActuacio;
	}

	public void setIdActuacio(int idActuacio) {
		this.idActuacio = idActuacio;
	}

	public int getIdInf() {
		return idInf;
	}

	public void setIdInf(int idInf) {
		this.idInf = idInf;
	}

	public boolean isReserva() {
		return reserva;
	}

	public void setReserva(boolean reserva) {
		this.reserva = reserva;
	}

	public User getUsuReserva() {
		return usuReserva;
	}

	public void setUsuReserva(User usuReserva) {
		this.usuReserva = usuReserva;
	}

	public Date getDatareserva() {
		return datareserva;
	}

	public void setDatareserva(Date datareserva) {
		this.datareserva = datareserva;
	}

	public boolean isAssignacio() {
		return assignacio;
	}

	public void setAssignacio(boolean assignacio) {
		this.assignacio = assignacio;
	}

	public User getUsuAssignacio() {
		return usuAssignacio;
	}

	public void setUsuAssignacio(User usuAssignacio) {
		this.usuAssignacio = usuAssignacio;
	}

	public Date getDataAssignacio() {
		return dataAssignacio;
	}

	public void setDataAssignacio(Date dataAssignacio) {
		this.dataAssignacio = dataAssignacio;
	}

	public String getComentari() {
		return comentari;
	}

	public void setComentari(String comentari) {
		this.comentari = comentari;
	}

	public User getUsuCre() {
		return usuCre;
	}

	public void setUsuCre(User usuCre) {
		this.usuCre = usuCre;
	}

	public Date getDataCre() {
		return dataCre;
	}

	public void setDataCre(Date dataCre) {
		this.dataCre = dataCre;
	}

	public String getIdPartida() {
		return idPartida;
	}

	public void setIdPartida(String idPartida) {
		this.idPartida = idPartida;
	}

	public double getValorPA() {
		return valorPA;
	}

	public void setValorPA(double valorPA) {
		this.valorPA = valorPA;
	}

	public double getValorPD() {
		return valorPD;
	}

	public void setValorPD(double valorPD) {
		this.valorPD = valorPD;
	}
}
