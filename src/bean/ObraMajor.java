package bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ObraMajor {
	private String id;
	private int idActuacio;
	private Date dataInici;
	private boolean obert;
	private Date dataTancament;
	private String motiuTancament;
	
	public ObraMajor(){
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIdActuacio() {
		return idActuacio;
	}

	public void setIdActuacio(int idActuacio) {
		this.idActuacio = idActuacio;
	}

	public Date getDataInici() {
		return dataInici;
	}

	public String getDataIniciString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");	
		String dataString = "";
		if (this.dataInici != null) dataString = df.format(this.dataInici);
		return dataString;
	}
	
	public void setDataInici(Date dataInici) {
		this.dataInici = dataInici;
	}

	public boolean isObert() {
		return obert;
	}

	public void setObert(boolean obert) {
		this.obert = obert;
	}

	public Date getDataTancament() {
		return dataTancament;
	}

	public String getDataTancamentString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");	
		String dataString = "";
		if (this.dataTancament != null) dataString = df.format(this.dataTancament);
		return dataString;
	}
	
	public void setDataTancament(Date dataTancament) {
		this.dataTancament = dataTancament;
	}

	public String getMotiuTancament() {
		return motiuTancament;
	}

	public void setMotiuTancament(String motiuTancament) {
		this.motiuTancament = motiuTancament;
	}
}
