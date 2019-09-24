/**
 * 
 */
package mg.fanorona.observer;

/**
 * @author andryfama
 *
 */
public interface Observable 
{
	public void addObserver(Observer obs);
	public void updateObserver();
	public void updateObserverByTurnLost(boolean b);
	

}
