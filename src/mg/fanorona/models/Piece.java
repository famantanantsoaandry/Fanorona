/******
 * 
 * 
 *  IT REPRESENT THE BASIC MODEL OF THE GAME : THE PIECE
 * 
 * 
 * 
 * 
 * *****************/


package mg.fanorona.models;

import java.util.ArrayList;

import android.graphics.*;


public class Piece 
{
	private CoordinatesWithDest coordinates;
	private Bitmap icon;
	private String idPlayer;
	private boolean hasMorePieceToEat;
	private ArrayList<Integer> historical=new ArrayList<Integer>();


	
	public Piece(CoordinatesWithDest coordinates, Bitmap icon, String idPlayer) {
		
		this.coordinates = coordinates;
		this.icon = icon;
		this.idPlayer = idPlayer;
	}

	public CoordinatesWithDest getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(CoordinatesWithDest coordinates) {
		this.coordinates = coordinates;
	}

	public Bitmap getIcon() {
		return icon;
	}

	public void setIcon(Bitmap icon) {
		this.icon = icon;
	}

	public String getIdPlayer() {
		return idPlayer;
	}

	public void setIdPlayer(String idPlayer) {
		this.idPlayer = idPlayer;
	}

	/**
	 * @return the historical
	 */
	public ArrayList<Integer> getHistorical() 
	{
		return historical;
	}

	/**
	 * @param historical the historical to set
	 */
	public void setHistorical(ArrayList<Integer> historical)
	{
		this.historical = historical;
	}
	
	
	
	
}
