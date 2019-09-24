package mg.fanorona.models;

import java.io.*;

/**
 * @author tanakà34
 *
 */

@SuppressWarnings("serial")

public class DataToTransfer implements Serializable{
	private int departure;
	private int destination;
	private boolean finished;
	private String idPlayer;
	private boolean isManaraka;
	
	/***/
	/**
	 * @param departure
	 * @param destination
	 * @param finished
	 */
	public DataToTransfer(int departure, int destination, String idPlayer,boolean finished,boolean isManaraka) {
		super();
		this.departure = departure;
		this.destination = destination;
		this.finished = finished;
		this.idPlayer =idPlayer;
		this.isManaraka=isManaraka;
	}
    public boolean isManaraka() {
		return isManaraka;
	}
	public void setManaraka(boolean isManaraka) {
		this.isManaraka = isManaraka;
	}
	/**/
	/**
	 * @return
	 */
	public int getDeparture() {
		return departure;
	}
	
	/***/

	/**
	 * @param departure
	 */
	public void setDeparture(int departure) {
		this.departure = departure;
	}
	/**
	 * **/

	/**
	 * @return
	 */
	public int getDestination() {
		return destination;
	}

	 /*
	  * **/
	/**
	 * @param destination
	 */
	public void setDestination(int destination) {
		this.destination = destination;
	}

	/****/
	/**
	 * @return
	 */
	public boolean isFinished() {
		return finished;
	}

	/****/
	/**
	 * @param finished
	 */
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	/**
	 * @return the idPlayer
	 */
	public String getIdPlayer() {
		return idPlayer;
	}
	/**
	 * @param idPlayer the idPlayer to set
	 */
	public void setIdPlayer(String idPlayer) {
		this.idPlayer = idPlayer;
	}
	
	
	
	
}
