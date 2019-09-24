œœœœœœœœœœœœœœœœœœœœœœpackage mg.fanorona.activity;

import java.net.ServerSocket;
import java.net.Socket;

import mg.fanorona.view.Lakapanorona;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FanoronaActivity extends Activity {
    
	
	 //socket and ServerSocket
	 private  ServerSocket serversocket;
	 private Socket socket;
	 private boolean isServer;
	 private String ip;
	 private int port;
	 private EditText editPort;
	 private EditText editIp;
	 private Button buttonOk;
	 
	
	
	 
	//a good handler 
	 
	 private Handler handler = new Handler();
	 private String idPlayer;
	 private boolean isMyTurn;
	
	 
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isServer=getIntent().getBooleanExtra(FirstActivity.IS_SERVER_ID, false);
    }
    

	
	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
		
		if(isServer)
		{
			setContentView(R.layout.server_layout);
			 editPort =(EditText) findViewById(R.id.portServer_editText);
			 buttonOk = (Button) findViewById(R.id.server_valid_button);
			buttonOk.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "ca ecoute",Toast.LENGTH_SHORT).show();
					setupServer(Integer.parseInt(editPort.getText().toString()));
					
				}
			});
		}
		else
		{
			setContentView(R.layout.client_layout);
			editPort =(EditText) findViewById(R.id.port_editText);
			buttonOk =(Button) findViewById(R.id.client_buttonOk);
			editIp =(EditText) findViewById(R.id.ip_editText);
buttonOk.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "ca ecoute aussi",Toast.LENGTH_SHORT).show();
					setupClient(editIp.getText().toString(),Integer.parseInt(editPort.getText()
							.toString()) );
					
				}
			});
			
		}
	}
	
	/****
     *   when destroying , close socket for garbage collector
     * **/
    @Override
    protected void onDestroy() {
    	try {
			socket.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	super.onDestroy();
    
    }

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
	}

	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	


	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
  
	 
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
	{
 
        MenuInflater inflater = getMenuInflater();
        
        inflater.inflate(R.menu.main_menu, menu);
 
       
    	menu.getItem(0).getSubMenu().setHeaderIcon(android.R.drawable.btn_radio);
 
        return true;
     }
	

   public boolean onOptionsItemSelected(MenuItem item) 
   {
      
      switch (item.getItemId()) {
         case R.id.option:
            Toast.makeText(this, "Option", Toast.LENGTH_SHORT).show();
            return true;
         
             
         case R.id.stats:
             Toast.makeText(this, "vous avez cedé votre tour à l'adversaire", Toast.LENGTH_SHORT).show();
             return true;
        case R.id.quitter:
           
            finish();
            return true;
      }
      return false;
      }
   
   /***
    * prepare server and open port by server socket
    * **/ 
   private void setupServer(int Port)
   {
	   this.port=Port;
	   
				Thread t = new Thread(new Runnable() {
					
					@Override
					public void run() {
						
		    		
					try {
						serversocket = new ServerSocket(port);
						socket=serversocket.accept();
						serversocket.close();
						idPlayer="player1";
						isMyTurn=true;
						handler.post(VerboseSClientServeur);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		
		    		
						
					}
				});
				
			  t.start();
				
			
   	
   }
   /*****
    *   prepare client and open connection via socket
    * 
    * **/
   private void setupClient(String Ip,int Port)
   {
   				this.ip=Ip;
   				this.port=Port;
				Thread t = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
					
		    		
					try {
					
							socket=new Socket(ip,port);
							idPlayer="player2";
							isMyTurn=false;
							handler.post(VerboseSClientServeur);
					
					    
			    		 
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		
		    		
						
					}
				});
				
			  t.start();
				
			
		
   	
   	
   }
   

   private Runnable VerboseSClientServeur = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
	    	  Context context = getApplicationContext();
	    	  String msg = " connexion has successful";
	    	   int duration = Toast.LENGTH_SHORT;
	    	   Toast.makeText(context, msg.toUpperCase(), duration).show();
	    	   
	    	   
	    	  
			
	      AlertDialog alertDialog = new AlertDialog.Builder (
                     FanoronaActivity.this).create ();

     //Configuration Titre de dialogue
     alertDialog.setTitle ( "commencement de la partie" );

     //Définition de message de dialogue
     alertDialog.setMessage ( "Bonjour ,ok, c 'est partie" );

     // Réglage Icône de dialogue
     alertDialog.setIcon (R.drawable.ic_launcher);
     //alertDialog.setContentView();
     // Mise sur le bouton OK
     alertDialog.setButton( "OK" , new DialogInterface.OnClickListener () {
             public void onClick ( DialogInterface d, int qui) {
             // Tapez votre code ici à exécuter après de dialogue fermée
             Toast.makeText (getApplicationContext (), "Vous avez cliqué sur OK" , Toast.LENGTH_SHORT).show ();
             setContentView(new Lakapanorona(FanoronaActivity.this,socket,idPlayer,isMyTurn));
             }
     });

     //Affichage des messages d'alerte
     alertDialog.show ();
	    	  
		}
	};
}
