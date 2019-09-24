/**
 * IT CLASS REPRESENT THE BASIC MODEL OF THE GAME WITH ABS AND ORD
 */
package mg.fanorona.models;

/**
 * @author andryfama 
 *
 */
public class Coordinate 
{
   private float abs;
   private float ord;
   private int index;
/**
 * @return the index
 */
public int getIndex() {
	return index;
}
/**
 * @param index the index to set
 */
public void setIndex(int index) {
	this.index = index;
}
/**
 * 
 */
public Coordinate() 
{
	
}
/**
 * @param abs
 * @param ord
 */
public Coordinate(float abs, float ord,int index) 
{
	
	this.abs = abs;
	this.ord = ord;
	this.index=index;
}
/**
 * @return the abs
 */
public float getAbs() 
{
	return abs;
}
/**
 * @param abs the abs to set
 */
public void setAbs(float abs)
{
	this.abs = abs;
}
/**
 * @return the ord
 */
public float getOrd() 
{
	return ord;
}
/**
 * @param ord the ord to set
 */
public void setOrd(float ord) 
{
	this.ord = ord;
}

   
   
}
