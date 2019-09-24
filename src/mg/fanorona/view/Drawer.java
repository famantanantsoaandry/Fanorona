package mg.fanorona.view;

/****
 *  THIS CLASS IS RESPONSIBLE FOR DRAWING ANYTHING IN THE ACTIVITY'SCREEN
 * */

import java.net.Socket;

import mg.fanorona.activity.R;
import mg.fanorona.models.Coordinate;
import mg.fanorona.models.GameData;
import mg.fanorona.models.Piece;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class Drawer 
{
	/*****
	 * attribute
	 * */
	//An drawer need the model: gamedata
	private GameData gamedata;
	//for painting
	private Paint mRowPaint;
	//it draw anything in a view:here the lakapanorona
	private Lakapanorona viewCurrent;
	//an boolean for verifying the gamedata has initialized
	private boolean isGameDataInitialized;
	
	private Coordinate depPoint;
	private Coordinate destPoint;
	private  int mRow ;
	private  int mCol;

	private Bitmap bitmap1;
	private Bitmap bitmap2;
	private Bitmap temp;
	
	private int width;
	private int height;
	private int bitmapWidth;
	// the currentPiece selected by user !
	private Piece selectedPiece;
	//	the depart and destination index
	int departure,destination;

	boolean isMyTurn;
	
	private Socket socket;
	
	
	




	/**
	 * @param gamedata
	 * @param mRowPaint
	 * @param viewCurrent
	 * @param mRow
	 * @param mCol
	 * @param bitmap1
	 * @param bitmap2
	 */
	public Drawer( Paint mRowPaint, Lakapanorona viewCurrent,
			int mRow, int mCol,Socket socket,String idPlayer,boolean isMyturn,Context c) 
	{
		super();
		
		this.mRowPaint = mRowPaint;
		this.viewCurrent = viewCurrent;
		this.mRow = mRow;
		this.mCol = mCol;
		this.socket=socket;
		this.isMyTurn = isMyturn;
		this.gamedata = new GameData(socket,this,idPlayer,isMyturn,c);
	}
	
	/**
	 * @return the selectedPiece
	 */
	public Piece getSelectedPiece() {
		return selectedPiece;
	}


	/**
	 * @param selectedPiece the selectedPiece to set
	 */
	public void setSelectedPiece(Piece selectedPiece) {
		this.selectedPiece = selectedPiece;
	}

	
	/**
	 * @return the gamedata
	 */
	public GameData getGamedata() {
		return gamedata;
	}


	/**
	 * @param gamedata the gamedata to set
	 */
	public void setGamedata(GameData gamedata) 
	{
		this.gamedata = gamedata;
	}
	
	/**
	 * @return the depPoint
	 */
	public Coordinate getDepPoint() {
		return depPoint;
	}


	/**
	 * @param depPoint the depPoint to set
	 */
	public void setDepPoint(Coordinate depPoint) {
		this.depPoint = depPoint;
		
		
		
		
	}


	/**
	 * @return the destPoint
	 */
	public Coordinate getDestPoint() {
		return destPoint;
	}
	
	
	
	public void setDestPoint(Coordinate destPoint) 
	{
		this.destPoint = destPoint;
		boolean toSet=false;
		if(selectedPiece!=null)
		{
		
		   for(Coordinate coord:selectedPiece.getCoordinates().getListOfAutorizedPoint())
		   {
			   if(coord.getAbs()==destPoint.getAbs()&&coord.getOrd()==destPoint.getOrd())
			   {
				   toSet=true;
				   break;
			   }
		   }
		   if(toSet)
		   {
		   for(Piece p:gamedata.getPlayerlocation())
		   {
			  if(p.getCoordinates().equals(selectedPiece.getCoordinates()))
			  {
				
				  
				gamedata.updatePiece(p, destPoint);
				
				
				toSet=false;
				selectedPiece=null;
				break;
				
			  }
		   }
		   }
		}
		
	}


	/****
	 * 
	 *   PREPARE THE BITMAP TO BE DRAWN FOR IMPROVING THE SPEED 
	 *   @author CYRILmOTTIER 
	 * 
	 * *************/
	/**
	 * @param drawable
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap prepareBitmap(Drawable drawable, int width, int height)
	{
		
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        drawable.setBounds(0, 0, width, height);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return bitmap;
    }
	
	public void showAllDestPoint(Canvas canvas)
	{
		if(selectedPiece!=null)
		{
		for(Coordinate coord:selectedPiece.getCoordinates().getListOfAutorizedPoint())
		{
			if(gamedata.isFreeCoordinate(coord))
			canvas.drawBitmap(temp, coord.getAbs()-(bitmapWidth/2),coord.getOrd()-(bitmapWidth/2), null);
		}
		}
		
	}
	


	/*
	 * 
	 * 
	 *   IT METHOD JUST DRAW ALL FANORONA ' S LINE AND INITIALIZE GAMEDATA I.E:
	 *   COORDONATE
	 *   COORDONATE WITH DEST 
	 *   THE TWO PLAYER' PIECE WITH THEIR PROPERTY 
	 *   
	 *   void drawAllLineAndInitializeGameData(Canvas canvas)
	 * 
	 * 
	 *   REMEMBER DON' T READ IT' BODY BECAUSE .........IT MAKE YOUR EYES SICK !
	 * 
	 * *******************************/
	/**
	 * @param canvas
	 */
	public void drawAllLineAndInitializeGameData(Canvas canvas)
	{
		//initialize and fill game data
	 if(!isGameDataInitialized)
	 {
		
		Coordinate listPoint[]=new Coordinate[45]; 
				
		
		width=viewCurrent.getWidth();
		height = viewCurrent.getHeight();
		
		int k =width/mCol;
		int n=height/mRow;	
		bitmapWidth=3*k/10;
		int compt=0;		
				
	     for(int j=0;j<height;j+=n)
		 {
			for(int i=0;i<width;i+=k)
			{	
				
				
				listPoint[compt]=new Coordinate(i+(width/15),j+(height/12),compt);
				compt++;
		        if(i==(8*k))
				i=width;
		        
		    	
					
			}
			
			 if(j==(4*n))		
			 j=height;
					
					
		 }
	        bitmap1=prepareBitmap(viewCurrent.getResources().getDrawable(R.drawable.player1), bitmapWidth, bitmapWidth);
			bitmap2=prepareBitmap(viewCurrent.getResources().getDrawable(R.drawable.player2), bitmapWidth, bitmapWidth) ;
			temp=prepareBitmap(viewCurrent.getResources().getDrawable(R.drawable.temp), bitmapWidth,bitmapWidth);
			gamedata.setListPoints(listPoint);
			gamedata.fillCompletPoint();
			gamedata.setBitmap1(bitmap1);
			gamedata.setBitmap2(bitmap2);
			gamedata.initializeAllPieces();
			isGameDataInitialized =true;
	 }		

	  /**** We draw all game 's Lines when calling the oneDraw Method ! ****
	   * 
	   *  (ps : I don 't know if the grammar is correct but I don' t take care OK?)View
	   * 
	   * ****/
		
		
		for(int i=0;i<5;i++)
		{
			depPoint = gamedata.getListPoints()[9*i];
			destPoint = gamedata.getListPoints()[8+(9*i)];
			
			canvas.drawLine(depPoint.getAbs(), depPoint.getOrd(), destPoint.getAbs(), destPoint.getOrd(), mRowPaint);
			
		}
		
		for(int j=0;j<9;j++)
		{
			depPoint = gamedata.getListPoints()[j];
			destPoint = gamedata.getListPoints()[36+j];
			
			canvas.drawLine(depPoint.getAbs(), depPoint.getOrd(), destPoint.getAbs(), destPoint.getOrd(), mRowPaint);
			
		}
		
		for(int j=0;j<3;j++)
		{
			depPoint = gamedata.getListPoints()[2*j];
			destPoint = gamedata.getListPoints()[40+2*j];
			
			canvas.drawLine(depPoint.getAbs(), depPoint.getOrd(), destPoint.getAbs(), destPoint.getOrd(), mRowPaint);
			
		}
		
		for(int j=0;j<2;j++)
		{
			depPoint = gamedata.getListPoints()[6+j*12];
			destPoint = gamedata.getListPoints()[26+j*12];
			
			canvas.drawLine(depPoint.getAbs(), depPoint.getOrd(), destPoint.getAbs(), destPoint.getOrd(), mRowPaint);
			
		}
		
		for(int j=0;j<3;j++)
		{
			depPoint = gamedata.getListPoints()[4+j*2];
			destPoint = gamedata.getListPoints()[36+j*2];
			
			canvas.drawLine(depPoint.getAbs(), depPoint.getOrd(), destPoint.getAbs(), destPoint.getOrd(), mRowPaint);
			
		}
		
		for(int j=0;j<2;j++)
		{
			depPoint = gamedata.getListPoints()[2+j*24];
			destPoint = gamedata.getListPoints()[18+j*24];
			
			canvas.drawLine(depPoint.getAbs(), depPoint.getOrd(), destPoint.getAbs(), destPoint.getOrd(), mRowPaint);
			
		}

	}
	
	
	/*********
	 * 
	 *   DRAW ALL PION INTO THE CANVAS WITH THEIR PROPERTY !
	   
	 * 
	 * 
	 * ********************/
	
	/**
	 * @param canvas
	 */
	public void drawAllPion(Canvas canvas)
	{
	
	
		
		for(Piece p:gamedata.getPlayerlocation())
		{
			canvas.drawBitmap(p.getIcon(), p.getCoordinates().getAbs()-(bitmapWidth/2),
					p.getCoordinates().getOrd()-(bitmapWidth/2), null);
		}
		
	}

	/**
	 * @return the viewCurrent
	 */
	public Lakapanorona getViewCurrent() {
		return viewCurrent;
	}

	/**
	 * @param viewCurrent the viewCurrent to set
	 */
	public void setViewCurrent(Lakapanorona viewCurrent) {
		this.viewCurrent = viewCurrent;
	}
	
	
	
}
