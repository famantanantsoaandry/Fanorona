/***
 *   ALL THING BEGIN HERE
 *   
 *   
 *   HERE IS THE MAIN VIEW 
 * 
 * 
 * **/
package mg.fanorona.view;
import java.net.Socket;

import mg.fanorona.models.Coordinate;
import mg.fanorona.models.Piece;
import mg.fanorona.observer.Observer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * @author andryfama
 *
 */
public class Lakapanorona extends View  implements Observer
{
	private Paint mRowPaint;
	private final int mRow =5;
	private final int mCol=9;
	private int mLineColor;
	private Drawer drawer;
	private Piece selectedPiece;
	private boolean timeToshow;
	private Socket socket;
	private Handler handler=new Handler();
	private String idPlayer;
	private boolean isMyturn=false;
	
	
   /***/
	/**
	 * @param context
	 */
	public Lakapanorona(Context context,Socket socket,String idPlayer,boolean isMyTurn)
	{
		super(context);
		// TODO Auto-generated constructor stub
		this.idPlayer=idPlayer;
		this.isMyturn=isMyTurn;
		this.socket =socket;
		initialize(context);
		
		
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public Lakapanorona(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initialize(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public Lakapanorona(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initialize(context);
	}
	
	private void initialize(Context context)
	{
		
		mLineColor=0xff808080;
		mRowPaint =new Paint(Paint.ANTI_ALIAS_FLAG);
		
		mRowPaint.setColor(mLineColor);
		
		
		
		drawer =new Drawer(mRowPaint, this, mRow, mCol,socket,idPlayer,isMyturn,context);
		drawer.getGamedata().addObserver(this);
		
    }
	/***
	 * @param canvas to draw the lakapanorona with the drawLine Method
	 * 
	 * ***/

	
	

	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) 
	{
		
		
		drawer.drawAllLineAndInitializeGameData(canvas);
		drawer.drawAllPion(canvas);
		if(timeToshow)
		{
			drawer.showAllDestPoint(canvas);
			timeToshow=false;
		}
		// TODO Auto-generated method stub
	
		
	}
	
	
	
	/**
	 * @return the isMyturn
	 */
	public boolean isMyturn() {
		return isMyturn;
	}

	/**
	 * @param isMyturn the isMyturn to set
	 */
	public void setMyturn(boolean isMyturn) {
		this.isMyturn = isMyturn;
	}

	/* (non-Javadoc)
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		// TODO Auto-generated method stub
		
		/***
		 * get the action and it' Coordonate
		 * */
		
		final int action =event.getAction();
		final float x = event.getX();
		final float y=event.getY();
		
		switch(action)
		{
		case MotionEvent.ACTION_DOWN:
			
			if(isMyturn)
			{
			
			   for(Coordinate coord:drawer.getGamedata().getListPoints())
			   {
				 if((x<=coord.getAbs()+30)&&(x>=coord.getAbs()-30)&&(y<=coord.getOrd()+30)&&(y>=coord.getOrd()-30))
				 {
					
					selectedPiece=drawer.getGamedata().searchPiece(coord.getAbs(), coord.getOrd());
					if(selectedPiece!=null)
					{
					 if(selectedPiece.getIdPlayer()!=idPlayer)
					 {
						 Toast.makeText(getContext(), "ny anao ihany kitihana !", Toast.LENGTH_SHORT).show();
						 break;
					 }
					 else
					 {
					 drawer.setSelectedPiece(selectedPiece);
					 timeToshow=true;
					 update();
					 break;
					 }
					}
					else
					{
						drawer.setDestPoint(drawer.getGamedata().searchCoordinate(coord.getAbs(),
								coord.getOrd()));
						update();
						break;
					}
					
					
					
				 }
			  }
			   
			
			}
			else
			{
				Toast.makeText(getContext(), "mianara fa tsy anao instony izao!", Toast.LENGTH_SHORT).show();
			}
		
			
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			
			
			break;
		
		}
		return super.onTouchEvent(event);
	}

	public void refresh(Canvas canvas)
	{
		
	}

	@Override
	public void update() 
	{
		// TODO Auto-generated method stub
		handler.post(monitor);
		
		
	}
	
	
	Runnable monitor = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			invalidate();
			
		}
	};


	@Override
	public void updateWhenlostTurn(boolean myTurn)
	{
		// TODO Auto-generated method stub
	    setMyturn(myTurn);
		
	}
	
	
	
	
	
	
	
	

}
