package com.example.projectileview;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	ProjectileView	 myp;
	TextView velocity_view ;
	TextView angle_view;
	TextView projectile_dataView;
	
	SeekBar angle_seekBar;
	SeekBar velocity_seekBar;
	
	double initialAngle;
	double initialvelocity;
	
	Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
	
	private SoundPool soundPool; // plays sound effects
	int projectileSoundID1 = -1;
	int projectileSoundID2 = -1;
	int projectileSoundID3 = -1;
	int projectileSoundID4 = -1;
	int projectileSoundID5 = -1;
	int projectileSoundID6 = -1;
	int projectileSoundID7 = -1;
	int projectileSoundID8 = -1;
	int projectileSoundID9 = -1;
	//Handler class
	Handler appHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) 
		{
			super.handleMessage(msg);
			// get the message
			String projData = (String) msg.obj;
			
			// updating GUI
			//projectile_dataView.setText(projData);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		myp =  (ProjectileView) findViewById(R.id.projectileView1);
		
		btn1 = (Button) findViewById(R.id.btn1);
		btn1.setOnClickListener(this);
		
		btn2 = (Button) findViewById(R.id.btn2);
		btn2.setOnClickListener(this);
		
		btn3 = (Button) findViewById(R.id.btn3);
		btn3.setOnClickListener(this);
		
		btn4 = (Button) findViewById(R.id.btn4);
		btn4.setOnClickListener(this);
		
		btn5 = (Button) findViewById(R.id.btn5);
		btn5.setOnClickListener(this);
		
		btn6 = (Button) findViewById(R.id.btn6);
		btn6.setOnClickListener(this);
		
		btn7 = (Button) findViewById(R.id.btn7);
		btn7.setOnClickListener(this);
		
		btn8 = (Button) findViewById(R.id.btn8);
		btn8.setOnClickListener(this);
		
		btn9 = (Button) findViewById(R.id.btn9);
		btn9.setOnClickListener(this);
		/*angle_view    = (TextView) findViewById(R.id.angle_view);
		velocity_view = (TextView) findViewById(R.id.velocity_view);
		
		angle_seekBar = (SeekBar) findViewById(R.id.angle_seekBar);
		velocity_seekBar = (SeekBar) findViewById(R.id.velocity_seekBar);
		
		angle_seekBar.setOnSeekBarChangeListener(this);
		velocity_seekBar.setOnSeekBarChangeListener(this);
		projectile_dataView    = (TextView) findViewById(R.id.projectile_dataView);
		*/
	    // initialize SoundPool to play the app's three sound effects
	    soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
	    
	    // allow volume keys to set game volume
	    setVolumeControlStream(AudioManager.STREAM_MUSIC);
        
	    //Ready to Fire some noise
    	try
    	{
    		AssetManager assetManager = getAssets();
    		AssetFileDescriptor fileSescriptor1 = assetManager.openFd("1.wav");
    		projectileSoundID1 = soundPool.load(fileSescriptor1, 1);
    		
    		AssetFileDescriptor fileSescriptor2 = assetManager.openFd("2.wav");
    		projectileSoundID2 = soundPool.load(fileSescriptor2, 1);
    		
    		AssetFileDescriptor fileSescriptor3 = assetManager.openFd("3.wav");
    		projectileSoundID3 = soundPool.load(fileSescriptor3, 1);
    		
    		AssetFileDescriptor fileSescriptor4 = assetManager.openFd("4.wav");
    		projectileSoundID4 = soundPool.load(fileSescriptor4, 1);
    		
    		AssetFileDescriptor fileSescriptor5 = assetManager.openFd("5.wav");
    		projectileSoundID5 = soundPool.load(fileSescriptor5, 1);
    		
    		AssetFileDescriptor fileSescriptor6 = assetManager.openFd("6.wav");
    		projectileSoundID6 = soundPool.load(fileSescriptor6, 1);
    		
    		AssetFileDescriptor fileSescriptor7 = assetManager.openFd("7.wav");
    		projectileSoundID7 = soundPool.load(fileSescriptor7, 1);
    		
    		AssetFileDescriptor fileSescriptor8 = assetManager.openFd("8.wav");
    		projectileSoundID8 = soundPool.load(fileSescriptor8, 1);
    		
    		AssetFileDescriptor fileSescriptor9 = assetManager.openFd("9.wav");
    		projectileSoundID9 = soundPool.load(fileSescriptor9, 1);
    	}
    	catch(IOException e)
    	{
    		e.printStackTrace();
    	}	
	}
	
	@Override
	public void onClick(View v) 
	{
		
		//
		if( myp.getpThread() != null && myp.getpThread().isBallOnScreen()==true)
			return;
		
		if (myp.getHolder() !=null)
		{
			// Toast.makeText(getApplicationContext(), "getHolder is not null..", Toast.LENGTH_LONG).show();
			ProjectThread pThread = new ProjectThread(myp.getHolder(), this);
	        pThread.setRunning(true);	
	        pThread.setAppHandler(appHandler);
			pThread.setInitialAngle(45);
			pThread.setInitialvelocity(100);
			pThread.setBallOnScreen(true);
	        myp.setpThread(pThread);
	        
	        if (v.getId()==btn1.getId())
			{
				pThread.setballNo(1);
				   if(projectileSoundID1 != -1)
			        	soundPool.play(projectileSoundID1, 1, 1, 1, 1, 1f);
				
			}
	        
	        if (v.getId()==btn2.getId())
			{
				pThread.setballNo(2);
				if(projectileSoundID2 != -1)
		        	soundPool.play(projectileSoundID2, 1, 1, 1, 1, 1f);
				
			}
	        
	        if (v.getId()==btn3.getId())
			{
				pThread.setballNo(3);
				if(projectileSoundID3 != -1)
		        	soundPool.play(projectileSoundID3, 1, 1, 1, 1, 1f);
			}
	        
	        if (v.getId()==btn4.getId())
			{
				pThread.setballNo(4);
				if(projectileSoundID4 != -1)
		        	soundPool.play(projectileSoundID4, 1, 1, 1, 1, 1f);
				
			}
	        
	        if (v.getId()==btn5.getId())
			{
				pThread.setballNo(5);
				if(projectileSoundID5 != -1)
		        	soundPool.play(projectileSoundID5, 1, 1, 1, 1, 1f);
			}
	        
	        if (v.getId()==btn6.getId())
			{
				pThread.setballNo(6);
				if(projectileSoundID6 != -1)
		        	soundPool.play(projectileSoundID6, 1, 1, 1, 1, 1f);
				
			}
	        
	        if (v.getId()==btn7.getId())
			{
				pThread.setballNo(7);
				if(projectileSoundID7 != -1)
		        	soundPool.play(projectileSoundID7, 1, 1, 1, 1, 1f);
			}
	        
	        if (v.getId()==btn8.getId())
			{
				pThread.setballNo(8);
				if(projectileSoundID8 != -1)
		        	soundPool.play(projectileSoundID8, 1, 1, 1, 1, 1f);
				
			}
	        
	        if (v.getId()==btn9.getId())
			{
				pThread.setballNo(9);
				if(projectileSoundID9 != -1)
		        	soundPool.play(projectileSoundID9, 1, 1, 1, 1, 1f);
				
			}
	        
	        
	        //Fire some noise
	   /*   if(projectileSoundID != -1)
	        	soundPool.play(projectileSoundID, 1, 1, 1, 1, 1f);*/
	        
	        pThread.start();
		}
		else
		Toast.makeText(getApplicationContext(), "getHolder is null..", Toast.LENGTH_LONG).show();
	}
		
		
	

/*	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) 
	{
		if (seekBar==angle_seekBar)
		{
			angle_view.setText(Integer.valueOf(seekBar.getProgress()).toString());
			initialAngle = Double.parseDouble(angle_view.getText().toString());
		}
		else if (seekBar==velocity_seekBar)
		{
			velocity_view.setText(Integer.valueOf(seekBar.getProgress()).toString());
			initialvelocity = Double.parseDouble(velocity_view.getText().toString());
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
	}*/

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		soundPool.release();
	}
	

}
