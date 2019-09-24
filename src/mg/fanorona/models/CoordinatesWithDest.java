/**
 * IT CLASS AN COORDONATE THAT KNOW THE OTHER COORDONATE IT CAN SET FOR DESTINATION
 */
package mg.fanorona.models;

import java.util.ArrayList;

/**
 * @author andryfama
 *
 */
public class CoordinatesWithDest extends Coordinate 
{
	 private ArrayList<Coordinate> listOfAutorizedPoint=new ArrayList<Coordinate>();
	    public CoordinatesWithDest(float x,float y,int index)
	    {
	      super(x,y,index);
	    }

	    public ArrayList<Coordinate> getListOfAutorizedPoint() 
	    {
	        return listOfAutorizedPoint;
	    }

	    public void setListOfAutorizedPoint(ArrayList<Coordinate> listOfAutorizedPoint)
	    {
	        this.listOfAutorizedPoint = listOfAutorizedPoint;
	    }

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object coord) {
			// TODO Auto-generated method stub
			if(this.getAbs()==((CoordinatesWithDest)coord).getAbs()&&this.getOrd()==((CoordinatesWithDest)coord).getOrd())
					return true;
			
			return false;
			
		}
	    

}
