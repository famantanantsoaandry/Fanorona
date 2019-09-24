/**
 * 
 */
package mg.fanorona.models;

import java.util.ArrayList;

/**
 * @author tanàka34
 *
 */
public class MihinanaMifanohitra 
{
	
	private static ArrayList<Piece> playerlocation;
	private static Coordinate points[];
	public static ArrayList<Integer> getEatablePiece(String string, ArrayList<Piece> playerLocation,
			int selected, int firstSelected,Coordinate []point) {
		// TODO Auto-generated method stub
		
		String contrary=null;
		if(string.equals("player1"))contrary="player2";
		if(string.equals("player2"))contrary="player1";
		playerlocation =playerLocation;
		points =point;
		ArrayList<Integer> temp=new ArrayList<Integer>();
		if(selected-firstSelected==9){
			int loop=firstSelected-9;
			while(loop>=0 &&(isListPieceContainsPiece(loop, contrary))
					){
				temp.add(loop);
				
				loop=loop-9;
			}
			
			return temp;
		}
		if(selected-firstSelected==-9){
			int loop=firstSelected+9;
			while(loop<45 && (isListPieceContainsPiece(loop, contrary))){
				temp.add(loop);
				
				loop=loop+9;
			}
			
			return temp;
		}
		if(selected-firstSelected==8){
			int loop=firstSelected-8;
			while(loop>=0 && (isListPieceContainsPiece(loop, contrary))
					&&getAbs(loop)>getAbs(loop+8)){
				temp.add(loop);
				
				loop=loop-8;
			}
			
			return temp;
		}
		if(selected-firstSelected==-8){
			int loop=firstSelected+8;
			while(loop<45 && (isListPieceContainsPiece(loop, contrary))
					
					&&getAbs(loop)<getAbs(loop-8)){
				temp.add(loop);
				
				loop=loop+8;
			}
			
			return temp;
		}
		if(selected-firstSelected==10){
			int loop=firstSelected-10;
			while(loop>=0 && (isListPieceContainsPiece(loop, contrary))
					
					&&getAbs(loop)<getAbs(loop+10)){
				temp.add(loop);
				
				loop=loop-10;
			}
			
			return temp;
		}
		if(selected-firstSelected==-10){
			int loop=firstSelected+10;
			while(loop<45 && (isListPieceContainsPiece(loop, contrary))
					
					&&getAbs(loop)>getAbs(loop-10)){
				temp.add(loop);
				
				loop=loop+10;
			}				
			
			return temp;
		}		
		if(selected-firstSelected==1){
			int loop=firstSelected-1;
			while(loop>=0 && (isListPieceContainsPiece(loop, contrary))
					
					&&getAbs(loop)<getAbs(loop+1)){
				temp.add(loop);
				
				loop=loop-1;
			}
			
			return temp;
		}
		if(selected-firstSelected==-1){
			int loop=firstSelected+1;
			while(loop<45 && (isListPieceContainsPiece(loop, contrary))
					&&
					getAbs(loop)>getAbs(loop-1)){
				temp.add(loop);
				
				loop=loop+1;
			}
			
			return temp;
		}
		return temp;
	}
	
	private static boolean isListPieceContainsPiece(int index,String idPlayer)
	{
		boolean toReturn=false;
		for(Piece p:playerlocation)
		{
			if(p.getIdPlayer().equals(idPlayer)&&p.getCoordinates().getIndex()==index)
			{
				toReturn = true;
				break;
			}
				
		}
		return toReturn;
	}
	
	private static float getAbs(int index)
	{
		
		return points[index].getAbs();
	}

}
