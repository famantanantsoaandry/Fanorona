/**
 * 
 */
package mg.fanorona.observer;

/**
 * @author andryfama
 *
 */
public interface Observer 
{
	
	public void update();
	public void updateWhenlostTurn(boolean myTurn);

}
