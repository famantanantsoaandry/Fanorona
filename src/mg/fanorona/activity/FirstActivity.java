package mg.fanorona.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class FirstActivity extends Activity 
{
	
	  
    ProgressBar bar;
    private RadioGroup group;
    private RadioButton serverRadio;
    private RadioButton clientRadio;
    
    private final String PROGRESS_BAR_INCREMENT="ProgreesBarIncrementId";
    public static final String IS_SERVER_ID ="server";
	 Handler handler = new Handler() 
	    {
	        @Override
	        public void handleMessage(Message msg) {
	            int progress=msg.getData().getInt(PROGRESS_BAR_INCREMENT);
	            
	            bar.incrementProgressBy(progress);
	            
	            
	        }
	    };

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_layout);
		bar =(ProgressBar) findViewById(R.id.progressBar); 
        bar.setProgress(0);
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		
       
        Thread background = new Thread(new Runnable() 
        {
            
            Bundle messageBundle=new Bundle();
           
            Message myMessage;
            
            public void run() {
                try {
                	
                    for (int i = 0; i <= bar.getMax() ; i++) 
                    {
                        Thread.sleep(20);
                       
                        myMessage=handler.obtainMessage();    
                      
                        messageBundle.putInt(PROGRESS_BAR_INCREMENT, 1);
                       //Ajouter le Bundle au message
                        myMessage.setData(messageBundle);
                        //Envoyer le message
                        handler.sendMessage(myMessage);
                        
                        	
                    }
                    handler.post(contentViewSetter);
                } catch (Throwable t) {
                    
                }
            }
        });
        
        
        background.start();

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	private Runnable contentViewSetter = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
				setContentView(R.layout.explain_layout);
				handler.post(chooseBetweenServerOrClient);
			
			
			
		}
	};
	private Runnable chooseBetweenServerOrClient=new Runnable(
			) {
		
		@Override
		public void run() 
		{
			// TODO Auto-generated method stub
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			initializeRadioButton();
			
		}
	};
	
	private void initializeRadioButton()
	{
		setContentView(R.layout .choose_layout);
		group=(RadioGroup) findViewById(R.id.group);
		serverRadio =(RadioButton) findViewById(R.id.serverRadio);
		clientRadio =(RadioButton) findViewById(R.id.clientRadio);
		
		serverRadio.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			  Intent i = new Intent(getApplicationContext(),FanoronaActivity.class);
			  i.putExtra(IS_SERVER_ID,true );
			  startActivity(i);
				
			}
		});
		
      clientRadio.setOnClickListener(new View.OnClickListener()
      {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 Intent i = new Intent(getApplicationContext(),FanoronaActivity.class);
				  i.putExtra(IS_SERVER_ID,false );
				  startActivity(i);
				
			}
		});
	}

	
}
