/**
 * 
 * THIS THE MODEL OF THE GAME IT CONTAINS :
 * list of the 45 Coordinate
 * list of the 45 Coordinate with autorized point
 * 
 * the two  player 's Piece 
 * 
 * 
 * some method which crud(create , insert ,update,search) into these when an certain event occured
 * 
 * 
 * 
 */
package mg.fanorona.models;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.util.ArrayList;
import mg.fanorona.activity.R;
import mg.fanorona.observer.Observable;
import mg.fanorona.observer.Observer;
import mg.fanorona.view.Drawer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.widget.Toast;


/**
 * @author andryfama
 * 
 */
public class GameData implements Observable
{
	
	/*******
	 *    all attribute that game need for survive !!!!!!
	 * 
	 * **/
	//list of the 45 Coordinate 
	private Coordinate listPoints[] = new Coordinate[45];
	//list of the 45 Coordinate With destination
	private CoordinatesWithDest point_with_dest[] = new CoordinatesWithDest[45];	
	//the two bitmaps representing the 2 opposite pieces
	private Bitmap bitmap1;
	private Bitmap bitmap2;
	
	ArrayList<Piece> playerlocation = new ArrayList<Piece>();
	
	private Socket socket;
	//
	 private ObjectOutputStream oos;
	 private ObjectInputStream iis;
	 private DataToTransfer data;
	 
	 private Context context;
	 
	 
	 
	 
	 //for implements the Observer design pattern
	 private ArrayList<Observer> listObserver = new ArrayList<Observer>();
	 
	
	 
	 
	 int departure;
	 int destination;
	 String idPlayer;
	
     private Piece selectedPiece=null;
     private boolean isMyturn;
     private boolean isManaraka;

	/**
	 * 
	 */
	public GameData(Socket socket,Drawer drawer,String idPlayer,boolean isMyTurn,Context context) 
	{
		super();
		// TODO Auto-generated constructor stub
		this.socket = socket;
		this.context=context;
		this.idPlayer =idPlayer;
		this.isMyturn=isMyTurn;
		
	
		
		
                 listenForMessage();
	}
	
	/********
	 * 
	 * 
	 *   ALL GETTERS AND SETTERS
	 * 
	 * ***********************************/
	
	
	

	/**
	 * @return the listPoints
	 */
	public Coordinate[] getListPoints() {
		return listPoints;
	}

	/**
	 * @param listPoints
	 *            the listPoints to set
	 */
	public void setListPoints(Coordinate[] listPoints) {
		this.listPoints = listPoints;
	}

	/**
	 * @return the point_with_dest
	 */
	public CoordinatesWithDest[] getPoint_with_dest() {
		return point_with_dest;
	}

	/**
	 * @param point_with_dest
	 *            the point_with_dest to set
	 */
	public void setPoint_with_dest(CoordinatesWithDest[] point_with_dest)
	{
		this.point_with_dest = point_with_dest;
	}
	/**
	 * @return the playerlocation
	 */
	public ArrayList<Piece> getPlayerlocation() {
		return playerlocation;
	}

	/**
	 * @param playerlocation the playerlocation to set
	 */
	public void setPlayerlocation(ArrayList<Piece> playerlocation) {
		this.playerlocation = playerlocation;
	}

	/**
	 * @return the bitmap1
	 */
	public Bitmap getBitmap1() {
		return bitmap1;
	}

	/**
	 * @param bitmap1 the bitmap1 to set
	 */
	public void setBitmap1(Bitmap bitmap1) {
		this.bitmap1 = bitmap1;
	}

	/**
	 * @return the bimap2
	 */
	public Bitmap getBitmap2() {
		return bitmap2;
	}

	/**
	 * @param bimap2 the bimap2 to set
	 */
	public void setBitmap2(Bitmap bitmap2) {
		this.bitmap2 = bitmap2;
	}
	
	/********
	 *  
	 *   HERE WE BUILD MANY METHOD FOR SEARCHING AND UPDATING DATA IN THESE MANY 
	 *   
	 *   ARRAY 
	 * 
	 *  
	 * 
	 * ****************************/

	
	// It search a piece when you precise the abs and the ord
	
	public Piece searchPiece(float abs,float ord)
	{
		Piece toReturn=null;
		for(Piece p:playerlocation)
		{
			if(p.getCoordinates().getAbs()==abs&&p.getCoordinates().getOrd()==ord)
			{
				toReturn=p;
				break;
				
				
			}
			
		}
		
		return toReturn;
	}
	
	public Piece searchPieceIndex(int index)
	{
		Piece toReturn=null;
		for(Piece p:playerlocation)
		{
			if(p.getCoordinates().getIndex()==index)
			{
				toReturn=p;
				break;
				
				
			}
			
		}
		
		return toReturn;
	}
	public int numberOfPiece(String idPlayer)
	{
		int toReturn=0;
		for(Piece p:playerlocation)
		{
			if(p.getIdPlayer()==idPlayer)
			{
				toReturn++;
				
				
				
			}
			
		}
		
		return toReturn;
	}
	
	
	
	//it search coordonate with dest when we give the abs and ord
	public CoordinatesWithDest searchCoordinate(float x,float y)
	{
		CoordinatesWithDest toReturn =null;
		
		for(CoordinatesWithDest coord:point_with_dest)
		{
			if(coord.getAbs()==x&&coord.getOrd()==y)
			{
				toReturn=coord;
				break;
			}
			
		}
		return toReturn;
	}
	
	public CoordinatesWithDest searchCoordinateIndex(int index)
	{
		CoordinatesWithDest toReturn =null;
		
		for(CoordinatesWithDest coord:point_with_dest)
		{
			if(coord.getIndex()==index)
			{
				toReturn=coord;
				break;
			}
			
		}
		return toReturn;
	}
	
	// it verify if an coordinate is free i.e no piece had it 
	public boolean isFreeCoordinate(Coordinate coord)
	{
		boolean toReturn=true;
		
		
		for(Piece p:playerlocation)
		{
			if(p.getCoordinates().getAbs()==coord.getAbs()&&p.getCoordinates().getOrd()==coord.getOrd())
			{
				toReturn=false;
			
				break;
				
				
			}
			
		}
		
		return toReturn;
	}

	/*******
	 * 
	 *  IT INITIALIZE ALL TWO ' PIECE INTO IT' S LIST 
	 * 
	 * 
	 * ******/
	public void initializeAllPieces()
	{
		
		
		 for (int i = 0; i <22; i++) {
	            if (i <= 18) {
	                playerlocation.add(new Piece(point_with_dest[i], bitmap1, "player1"));
	                
	                

	            } else {
	                if (i == 19) {
	                	playerlocation.add(new Piece(point_with_dest[20], bitmap1, "player1"));


	                }
	                if (i == 20) {
	                	playerlocation.add(new Piece(point_with_dest[23], bitmap1, "player1"));

	                }
	                if (i == 21) {
	                	playerlocation.add(new Piece(point_with_dest[25], bitmap1, "player1"));

	                }
	            }
	        }
	        for (int i = 0; i < 22; i++) {
	            if (i == 0) {
	            	playerlocation.add(new Piece(point_with_dest[19], bitmap2, "player2"));

	            }
	            if (i == 1) {
	            	playerlocation.add(new Piece(point_with_dest[21], bitmap2, "player2"));

	            }
	            if (i == 2) {
	            	playerlocation.add(new Piece(point_with_dest[24], bitmap2, "player2"));

	            } else {
	                if ((i != 0) && (i != 1) && (i != 2)) {
	                	playerlocation.add(new Piece(point_with_dest[i+23], bitmap2, "player2"));

	                }
	            }
	        }
	        
	        

	}
	
	public void updatePiece(Piece selectedPiece,Coordinate destPoint)
	{
		
		
		 
		//here we are ................
		if(isMyturn)
		 {
			//if null, give it value
			if(this.selectedPiece==null)
			{
			  this.selectedPiece=selectedPiece;
			}
			 if(AllMyPiecehasMorePeaceToeat())
			 {
				 if(PieceCanEat(this.selectedPiece, (CoordinatesWithDest)destPoint)
						 )
				 {
					
			
					        data = new DataToTransfer(selectedPiece.getCoordinates().getIndex(), 
							destPoint.getIndex(),selectedPiece.getIdPlayer(), false,false);
					
					
						    if(this.selectedPiece.getCoordinates().equals(selectedPiece.getCoordinates()))
						    {
							        this.departure=selectedPiece.getCoordinates().getIndex();
							        this.destination=destPoint.getIndex();
							       for(Piece p:playerlocation)                                                      
							       {                                                                                              
							            if(p.getCoordinates().equals(selectedPiece.getCoordinates()))                               
							            {                                                                                           
								                                                               
								             p.setCoordinates((CoordinatesWithDest) destPoint);  
					                         this.selectedPiece.setCoordinates(p.getCoordinates());
					                         
					                         this.selectedPiece.getHistorical().add(departure);
								              break;                                                                                    
								                                                                                          
							            }                                                                                           
							        }  
							       eat(departure, destination, selectedPiece.getIdPlayer());
							    
							         
						      
					
					        }
					
					
				 }
				 else
				 {
					 this.selectedPiece=null;
				 }
				
			 }
			 else
			 {
				 if(this.selectedPiece.getCoordinates().equals(selectedPiece.getCoordinates()))
			     {
					 this.departure=selectedPiece.getCoordinates().getIndex();
						this.destination=destPoint.getIndex();
				     for(Piece p:playerlocation)                                                      
				     {                                                                                              
				        if(p.getCoordinates().equals(selectedPiece.getCoordinates()))                               
				        {                                                                                           
					 
					       data = new DataToTransfer(selectedPiece.getCoordinates().getIndex(), 
					    		   destPoint.getIndex(),selectedPiece.getIdPlayer(), true,isManaraka);
					       p.setCoordinates((CoordinatesWithDest) destPoint);  
		                   this.selectedPiece.setCoordinates(p.getCoordinates()); 
		                   
					       break;                                                                                    
					                                                                                          
				        }                                                                                           
				     }  
				     eat(departure, destination, selectedPiece.getIdPlayer()); 
				       
				       
			     }
			 }
		 }
		 else
		 {
			 
				this.departure=selectedPiece.getCoordinates().getIndex();
				this.destination=destPoint.getIndex();
				 for(Piece p:playerlocation)                                                      
				 {                                                                                              
				  if(p.getCoordinates().equals(selectedPiece.getCoordinates()))                               
				  {                                                                                           
					                                                               
					p.setCoordinates((CoordinatesWithDest) destPoint);
					eat(departure, destination, selectedPiece.getIdPlayer());
					break;                                                                                    
					                                                                                          
				  }                                                                                           
				 }  
				 
		 }
		
		
		
		
		 
	}
	
	public void eat(int departure,int destination,String idPlayer)
	{
		
		
		

		if(isMyturn)
		{
			 ArrayList<Integer> indexOfPieceToEat =MihinanaManaraka.getEatablePiece(idPlayer, playerlocation, destination,departure,listPoints);
			   
			 ArrayList<Integer> indexOfPieceToEat1=MihinanaMifanohitra.getEatablePiece(idPlayer, playerlocation, destination, departure,listPoints);
			 if(indexOfPieceToEat.size()>0&&indexOfPieceToEat1.size()>0)
			 {
                    showEatChoiceDialog();
			 }
			 else
			 {
				 if(indexOfPieceToEat.size()>0)
				 {
					eatManaraka(departure, destination, idPlayer); 
				 }
				 if(indexOfPieceToEat1.size()>0)
				 {
					 eatMifanohitra(departure, destination, idPlayer);
				 }
				 if(indexOfPieceToEat1.size()==0&&indexOfPieceToEat.size()==0)
				 {
					  isMyturn=false;
				       updateObserverByTurnLost(isMyturn);
				       data.setFinished(true);
				       this.selectedPiece=null;
				       sendObject(false);
				 }
				 
			 }
			 
			   
		}
		else
		{
			if(isManaraka)
				eatManaraka(departure, destination, idPlayer);
			else
				eatMifanohitra(departure, destination, idPlayer);
		}
		
	  
	 
	
	
	   
		  
	
		
	}
	
	
	 /*****
     *   send Object by writing  into an object writeObject(Object object)
     * */
    private void sendObject(boolean isManaraka)
    {
    	data.setManaraka(isManaraka);
    	Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() 
			{
				// TODO Auto-generated method stub
				try {
					
					oos=new ObjectOutputStream(socket.getOutputStream());
					oos.writeObject(data);
					
				
					
					
				} catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
    	t.start();
    	
    }
    
    /*****!*
     *  listen for incomming message by readObject() and show them in an Toast
     * */
    private void listenForMessage()
    {
          Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() 
			{
				try {
					iis=new ObjectInputStream(socket.getInputStream());
				} catch (StreamCorruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				while(true){
				// TODO Auto-generated method stub
				try {
					
					data =(DataToTransfer) iis.readObject();
					int departure =data.getDeparture();
					int destination = data.getDestination();
					isManaraka=data.isManaraka();
					isMyturn = data.isFinished();
					if(isMyturn)
					{
						isMyturn=false;
						updatePiece(searchPieceIndex(departure), searchCoordinateIndex(destination)); 
						isMyturn=true;
						updateObserverByTurnLost(true);
					}
					else
					{
					updatePiece(searchPieceIndex(departure), searchCoordinateIndex(destination));
					}
					
	
				} catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				}
			}
		});
    	t.start();
    	
    	
    
    }
    
    private void showEatChoiceDialog()
    {
		  AlertDialog alertDialog = new AlertDialog.Builder (context).create ();

            //title of the dialogue box
           alertDialog.setTitle ( "safidy" );

           //message to handle
           alertDialog.setMessage ( "hinana hanaraka ve sa mifanohitra enao tompoko ?" );

             // icon of the box, not necessary but for design
           alertDialog.setIcon (R.drawable.ic_launcher);
           alertDialog.setCancelable(false);
  
           // button follow
             alertDialog.setButton( "manaraka" , new DialogInterface.OnClickListener () 
             {
                  public void onClick ( DialogInterface d, int qui) 
                {
                  eatManaraka(departure, destination, idPlayer);
                 Toast.makeText (context, "mihinana manaraka izany e" , Toast.LENGTH_SHORT).show ();
                }
          });
  
              alertDialog.setButton2("mifanohitra", new DialogInterface.OnClickListener() 
              {
	               @Override
	             public void onClick(DialogInterface arg0, int arg1)
	             {
		            eatMifanohitra(departure, destination, idPlayer);
                    Toast.makeText (context, "mihinana mifanohitra " , Toast.LENGTH_SHORT).show ();
		
	              }
                 });

                    //Affichage des messages d'alerte
                  alertDialog.show ();
    }
    
    private void eatManaraka(int departure,int destination,String idPlayer)
    {
    	ArrayList<Integer> indexOfPieceToEat =MihinanaManaraka.getEatablePiece(idPlayer, playerlocation, destination,departure,listPoints);
    	ArrayList<Piece> temp=new ArrayList<Piece>();
    	 for(Piece p:playerlocation)
		 {
			 for(Integer i:indexOfPieceToEat)
			   {
				   if(p.getCoordinates().getIndex()==i)
				   {
					   temp.add(p);
					   
				   }
			   }
		 }
    	 for(Piece p:temp)
  	   {
  		   playerlocation.remove(p);
  		   
  	   }
  	   temp.clear();
  	   
  	   
  	   updateObserver();
  	   if(isMyturn)
  	   {
  	     if((!SelectedPiecehasMorePieceToeat(this.selectedPiece))||(onlyPossibleEatInHistoric(this.selectedPiece)))
         {
	          isMyturn=false;
	          this.selectedPiece=null;
              updateObserverByTurnLost(isMyturn);
            data.setFinished(true);
            sendObject(true);
             
       
   	
          }
  	     else
  	     {
  	    	sendObject(true);
  	     }
  	   }
  	   
    }
    private void eatMifanohitra(int departure,int destination,String idPlayer)
    {
    	ArrayList<Integer> indexOfPieceToEat =MihinanaMifanohitra.getEatablePiece(idPlayer, playerlocation, destination,departure,listPoints);
    	ArrayList<Piece> temp=new ArrayList<Piece>();
    	 for(Piece p:playerlocation)
		 {
			 for(Integer i:indexOfPieceToEat)
			   {
				   if(p.getCoordinates().getIndex()==i)
				   {
					   temp.add(p);
					   
				   }
			   }
		 }
    	 for(Piece p:temp)
  	   {
  		   playerlocation.remove(p);
  		   
  	   }
  	   temp.clear();
  	   
  	   
  	   updateObserver();
  	   if(isMyturn)
  	   {
  	     if((!SelectedPiecehasMorePieceToeat(this.selectedPiece))||(onlyPossibleEatInHistoric(this.selectedPiece)))
         {
	          isMyturn=false;
	          this.selectedPiece=null;
              updateObserverByTurnLost(isMyturn);
            data.setFinished(true);
            sendObject(false);
             
       
   	
          }
  	     else
  	     {
  	    	sendObject(false);
  	     }
  	   }
    }
    
    public boolean SelectedPiecehasMorePieceToeat(Piece selectedPiece)
	{
		ArrayList<Integer> PossibleindexOfPieceToEat=new ArrayList<Integer>();
		
		for(Coordinate coordinate:selectedPiece.getCoordinates().getListOfAutorizedPoint())
		{
			if(isFreeCoordinate(coordinate))
			{
				 ArrayList<Integer> indexOfPieceToEat =MihinanaManaraka.
						 getEatablePiece(idPlayer, playerlocation, coordinate.getIndex(),
								 selectedPiece.getCoordinates().getIndex(),listPoints);
				   
				 ArrayList<Integer> indexOfPieceToEat1=MihinanaMifanohitra.
						 getEatablePiece(idPlayer, playerlocation, coordinate.getIndex(), 
								 selectedPiece.getCoordinates().getIndex(),listPoints);
				 for(int i:indexOfPieceToEat)
				 {
					 PossibleindexOfPieceToEat.add(i);
				 }
				 for(int i:indexOfPieceToEat1)
				 {
					 PossibleindexOfPieceToEat.add(i);
				 }
				
			}
			
		}
		if(PossibleindexOfPieceToEat.size()>0)
			return true;
		return false;
	}
    
    public boolean PieceCanEat(Piece selectedPiece,Coordinate destPoint)
	{
		ArrayList<Integer> PossibleindexOfPieceToEat=new ArrayList<Integer>();
		
		
		
			if(isFreeCoordinate(destPoint))
			{
				 ArrayList<Integer> indexOfPieceToEat =MihinanaManaraka.
						 getEatablePiece(idPlayer, playerlocation, destPoint.getIndex(),
								 selectedPiece.getCoordinates().getIndex(),listPoints);
				   
				 ArrayList<Integer> indexOfPieceToEat1=MihinanaMifanohitra.
						 getEatablePiece(idPlayer, playerlocation, destPoint.getIndex(), 
								 selectedPiece.getCoordinates().getIndex(),listPoints);
				 for(int i:indexOfPieceToEat)
				 {
					 PossibleindexOfPieceToEat.add(i);
				 }
				 for(int i:indexOfPieceToEat1)
				 {
					 PossibleindexOfPieceToEat.add(i);
				 }
				
			}
			
		
		if(PossibleindexOfPieceToEat.size()>0)
			return true;
		return false;
	}
    private boolean onlyPossibleEatInHistoric(Piece selectedPiece)
    {
    	ArrayList<Integer> temp =new ArrayList<Integer>();
         for(Coordinate coord:selectedPiece.getCoordinates().getListOfAutorizedPoint())
         {
        	 if(isFreeCoordinate(coord)&&PieceCanEat(selectedPiece, coord))
        	 {
        		 temp.add(coord.getIndex());
        	 }
         }
    	
    	if(temp.size()==1)
    	{
    		if(selectedPiece.getHistorical().contains(temp.get(0)))
    		return true;
    	}
    	return false;
    }
    
    public boolean AllMyPiecehasMorePeaceToeat()
	{
    	
    	for(Piece p:playerlocation)
    	{
    	   	if(p.getIdPlayer().equals(idPlayer))
    		{
    			if(SelectedPiecehasMorePieceToeat(p))
    			{
    				return true;
    			}
    		}
    	}
	   
		return false;
	}
	@Override
	public void addObserver(Observer obs) {
		// TODO Auto-generated method stub
		
		listObserver.add(obs);
		
	}

	@Override
	public void updateObserver() 
	{
		// TODO Auto-generated method stub
		for(Observer obs:listObserver)
			obs.update();
		
	}
	
    
    
	
	/* (non-Javadoc)
	 * @see mg.fanorona.observer.Observable#updateObserverByTurnLost()
	 */
	@Override
	public void updateObserverByTurnLost(boolean b) {
		// TODO Auto-generated method stub
		for(Observer obs:listObserver)
			obs.updateWhenlostTurn(b);
		
	}

	/**********
	 * 
	 * 
	 * 
	 * 
	 *     DON'T READ THESE METHOD BECAUSE IT MAKE YOUR EYES SICK !
	 *     void fillCompletPoint(): FILL THE LIST OF COODONATE WITH DESTINATION when drawing all line in the view
	 *     
	 * 
	 * *********************************/
	
	
	public void fillCompletPoint() {
		CoordinatesWithDest temp_point;
	
			for (int i = 0; i < 45; i++) {

				/***
				 * IF i UNDER 9
				 **/
				if (i < 9) {
					if (i == 0) {
						temp_point = new CoordinatesWithDest(
								listPoints[i].getAbs(), listPoints[i].getOrd(),i);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 1]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 9]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 10]);
						point_with_dest[i] = temp_point;
					} else {
						if (i < 8) {
							if (((i % 2) != 0)) {
								temp_point = new CoordinatesWithDest(
										listPoints[i].getAbs(),
										listPoints[i].getOrd(),i);
								temp_point.getListOfAutorizedPoint().add(
										listPoints[i - 1]);
								temp_point.getListOfAutorizedPoint().add(
										listPoints[i + 1]);
								temp_point.getListOfAutorizedPoint().add(
										listPoints[i + 9]);
								point_with_dest[i] = temp_point;
							}
							if (((i % 2) == 0)) {
								temp_point = new CoordinatesWithDest(
										listPoints[i].getAbs(),
										listPoints[i].getOrd(),i);
								temp_point.getListOfAutorizedPoint().add(
										listPoints[i - 1]);
								temp_point.getListOfAutorizedPoint().add(
										listPoints[i + 1]);
								temp_point.getListOfAutorizedPoint().add(
										listPoints[i + 8]);
								temp_point.getListOfAutorizedPoint().add(
										listPoints[i + 9]);
								temp_point.getListOfAutorizedPoint().add(
										listPoints[i + 10]);
								point_with_dest[i] = temp_point;
							}
						}
						if (i == 8) {
							temp_point = new CoordinatesWithDest(
									listPoints[i].getAbs(),
									listPoints[i].getOrd(),i);
							temp_point.getListOfAutorizedPoint().add(
									listPoints[i - 1]);
							temp_point.getListOfAutorizedPoint().add(
									listPoints[i + 8]);
							temp_point.getListOfAutorizedPoint().add(
									listPoints[i + 9]);
							point_with_dest[i] = temp_point;
						}
					}
				}

				/*
				 * IF i BETWEEN 9 AND 17
				 */
				if ((i >= 9) && (i < 18)) {
					if (i == 9) {
						temp_point = new CoordinatesWithDest(
								listPoints[i].getAbs(), listPoints[i].getOrd(),i);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 9]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 1]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 9]);
						point_with_dest[i] = temp_point;

					}

					if ((i % 2) == 0) {

						temp_point = new CoordinatesWithDest(
								listPoints[i].getAbs(), listPoints[i].getOrd(),i);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 10]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 9]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 8]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 1]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 1]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 8]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 9]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 10]);
						point_with_dest[i] = temp_point;
					}
					if (((i % 2) != 0) && (i != 17) && (i != 9)) {
						temp_point = new CoordinatesWithDest(
								listPoints[i].getAbs(), listPoints[i].getOrd(),i);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 9]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 1]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 1]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 9]);
						point_with_dest[i] = temp_point;

					}

					if (i == 17) {
						temp_point = new CoordinatesWithDest(
								listPoints[i].getAbs(), listPoints[i].getOrd(),i);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 9]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 1]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 9]);
						point_with_dest[i] = temp_point;
					}

				}
				/****
				 * IF i BETWEEN 18 AND 26
				 * 
				 ****/
				if ((i >= 18) && (i < 27)) {

					if (i == 18) {
						temp_point = new CoordinatesWithDest(
								listPoints[i].getAbs(), listPoints[i].getOrd(),i);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 9]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 1]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 9]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 10]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 8]);
						point_with_dest[i] = temp_point;
					}
					if (((i % 2) != 0)) {
						temp_point = new CoordinatesWithDest(
								listPoints[i].getAbs(), listPoints[i].getOrd(),i);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 9]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 1]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 1]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 9]);
						point_with_dest[i] = temp_point;

					}
					if (((i % 2) == 0) && (i != 18) && (i != 26)) {
						temp_point = new CoordinatesWithDest(
								listPoints[i].getAbs(), listPoints[i].getOrd(),i);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 10]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 9]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 8]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 1]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 1]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 8]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 9]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 10]);
						point_with_dest[i] = temp_point;
					}
					if (i == 26) {
						temp_point = new CoordinatesWithDest(
								listPoints[i].getAbs(), listPoints[i].getOrd(),i);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 9]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 1]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 9]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 8]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 10]);
						point_with_dest[i] = temp_point;
					}

				}

				/*********
				 * IF i BETWEEN 27 AND 35
				 * 
				 *****/
				if ((i >= 27) && (i < 36)) {
					if (i == 27) {
						temp_point = new CoordinatesWithDest(
								listPoints[i].getAbs(), listPoints[i].getOrd(),i);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 9]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 1]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 9]);
						point_with_dest[i] = temp_point;
					}

					if ((i % 2) == 0) {
						temp_point = new CoordinatesWithDest(
								listPoints[i].getAbs(), listPoints[i].getOrd(),i);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 10]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 9]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 8]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 1]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 1]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 8]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 9]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 10]);
						point_with_dest[i] = temp_point;
					}
					if (((i % 2) != 0) && (i != 27) && (i != 35)) {
						temp_point = new CoordinatesWithDest(
								listPoints[i].getAbs(), listPoints[i].getOrd(),i);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 9]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 1]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 1]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 9]);
						point_with_dest[i] = temp_point;

					}

					if (i == 35) {
						temp_point = new CoordinatesWithDest(
								listPoints[i].getAbs(), listPoints[i].getOrd(),i);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 9]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 1]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 9]);
						point_with_dest[i] = temp_point;
					}

				}

				/******
				 * IF i BETWEEN 36 AND 44 for finishing......
				 *****************/
				if ((i >= 36) && (i <= 44)) {
					if (i == 36) {
						temp_point = new CoordinatesWithDest(
								listPoints[i].getAbs(), listPoints[i].getOrd(),i);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 8]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i - 9]);
						temp_point.getListOfAutorizedPoint().add(
								listPoints[i + 1]);
						point_with_dest[i] = temp_point;
					} else {
						if (i < 44) {
							if ((i % 2) != 0) {
								temp_point = new CoordinatesWithDest(
										listPoints[i].getAbs(),
										listPoints[i].getOrd(),i);
								temp_point.getListOfAutorizedPoint().add(
										listPoints[i - 1]);
								temp_point.getListOfAutorizedPoint().add(
										listPoints[i + 1]);
								temp_point.getListOfAutorizedPoint().add(
										listPoints[i - 9]);
								point_with_dest[i] = temp_point;
							}
							if (((i % 2) == 0)) {
								temp_point = new CoordinatesWithDest(
										listPoints[i].getAbs(),
										listPoints[i].getOrd(),i);
								temp_point.getListOfAutorizedPoint().add(
										listPoints[i - 1]);
								temp_point.getListOfAutorizedPoint().add(
										listPoints[i + 1]);
								temp_point.getListOfAutorizedPoint().add(
										listPoints[i - 8]);
								temp_point.getListOfAutorizedPoint().add(
										listPoints[i - 9]);
								temp_point.getListOfAutorizedPoint().add(
										listPoints[i - 10]);
								point_with_dest[i] = temp_point;
							}
						}
						if (i == 44) {
							temp_point = new CoordinatesWithDest(
									listPoints[i].getAbs(),
									listPoints[i].getOrd(),i);
							temp_point.getListOfAutorizedPoint().add(
									listPoints[i - 1]);
							temp_point.getListOfAutorizedPoint().add(
									listPoints[i - 9]);
							temp_point.getListOfAutorizedPoint().add(
									listPoints[i - 10]);
							point_with_dest[i] = temp_point;

						}

					}

				}

			}

		}



	

	

}
