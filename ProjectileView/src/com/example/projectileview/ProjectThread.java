package com.example.projectileview;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Random;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;

public class ProjectThread extends Thread 
{
	private SurfaceHolder  surfaceHolder;
	Handler appHandler;
    DecimalFormat tempFormatter = new DecimalFormat(FORMAT);
    Message tempMessage;
    String resultString;
	Paint textPaint = new Paint();
	Bitmap bitmap;
	int ballNo;
	//
    public final static  double frameRate =2.0;
    public final static  double frameTime = 1.00/frameRate;

	
	public Handler getAppHandler() {
		return appHandler;
	}

	public void setAppHandler(Handler appHandler) {
		this.appHandler = appHandler;
	}
	boolean isRunning = true;
	private boolean ballOnScreen; 
	private double elapsedTime; // the number of seconds elapsed	 
    Paint backgroundPaint = new Paint(); // start of current target section
    Paint ballPaint = new Paint(); // start of current target section
    Paint linePaint = new Paint();
	private Point ball =new Point(0,0); 
	private Point scaledBall =new Point(0,0); 
    int ballRadius=15;
    int[] colors={Color.BLUE,Color.RED, Color.GREEN, Color.YELLOW};
    

    double initialAngle=0;
    double initialvelocity=0;
    
	public final static String FORMAT = "00.00"; // 01.25
    
    public boolean isBallOnScreen() {
		return ballOnScreen;
	}

	public void setBallOnScreen(boolean ballOnScreen) {
		this.ballOnScreen = ballOnScreen;
	}
	public final static double G_CONSTANT = 9.81;
 	   
 	public double getInitialAngle() {
 		return initialAngle;
 	}

 	public void setInitialAngle(double initialAngle) {
 		this.initialAngle = initialAngle;
 	}
 	
 	public double getballNo() {
 		return ballNo;
 	}

 	public void setballNo(int ballNo) {
 		this.ballNo = ballNo;
 	}

 	public double getInitialvelocity() {
 		return initialvelocity;
 	}

 	public void setInitialvelocity(double initialvelocity) {
 		this.initialvelocity = initialvelocity;
 	}

	public ProjectThread(SurfaceHolder sHolder, Context context) {
    surfaceHolder = sHolder;
    Random rd = new Random();
	backgroundPaint.setColor(Color.DKGRAY) ;
	ballPaint .setColor(colors[rd.nextInt(4)]);	
	textPaint.setColor(Color.GREEN);
	textPaint.setTextSize(30);
	try
	{
		AssetManager assets = context.getAssets();
		InputStream iStream = assets.open("mkq4.png");
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		bitmap =BitmapFactory.decodeStream(iStream,null,options);
		iStream.close();
	}

	catch(IOException e)
	{
		e.printStackTrace();
	}

	}
	
	public void setRunning(boolean run) {
		this.isRunning = run;
	}
	
	@Override
	public void run() {
		float yscale;
		float xscale;
		long previousTime;
		long startTime = previousTime= System.currentTimeMillis(); 
		long currentTime;
		double elapsedTimeMS;
		double diffTime;
		Canvas canvas=null;


        canvas = surfaceHolder.lockCanvas(null); 
		 yscale = getScaleY(canvas);
		 xscale = getScaleX(canvas);
        surfaceHolder.unlockCanvasAndPost(canvas);
        
        // Initial Position
        double simulationstartTime=0;
        double currentSimulationTime=0;        
        
        resultString = "Houston...projectile has launched\n";
        tempMessage = appHandler.obtainMessage();
        tempMessage.obj = resultString;
        appHandler.sendMessage(tempMessage);
        while (isRunning)
        {
           try
           {
              currentTime = System.currentTimeMillis();
               
              elapsedTimeMS = currentTime - startTime;
              elapsedTime = elapsedTimeMS / 1000.00; 
             
               updatePosition(elapsedTime); 
              //updatePosition(currentSimulationTime); 


              canvas = surfaceHolder.lockCanvas(null);
              diffTime = currentTime - previousTime;
              
              
              scaledBall = scaleData(ball,canvas,xscale,yscale );
              resultString = "X: " + tempFormatter.format(ball.x) + " | Y: " + tempFormatter.format(ball.y) + "\n";
              canvas.drawText(resultString, 10, 10,textPaint);
              drawBall(canvas); 
              previousTime = currentTime;
              
              currentSimulationTime += frameTime;
           } // end try
           finally
           {
              if (canvas != null) 
                 surfaceHolder.unlockCanvasAndPost(canvas);
           } // end finally
        }
        resultString = "Did it in: "+ Double.valueOf(elapsedTime).toString()+"; One step for Angry bird, One giant step for all birds..\n";
        tempMessage = appHandler.obtainMessage();
        tempMessage.obj = resultString;
        appHandler.sendMessage(tempMessage);
	}
	
	
	//draw the seven-segment display
	
	
	
	   // draws the game to the given Canvas
	
	   public void drawBall(Canvas canvas)
	   {
		   // clear the background
		   canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(),backgroundPaint);
		   
		   linePaint.setColor(Color.GREEN);
		   linePaint.setStrokeWidth(10);
		    /*int startx = 1000;
		    int starty = 40;
		    int endx = 1000;
		    int endy = 80;
		   // canvas.drawLine(startx, starty, endx, endy, ballPaint);
		   // canvas.drawLine(canvas.getWidth()-50 , canvas.getHeight(),canvas.getWidth(),canvas.getHeight()-40 , linePaint);
		    canvas.drawLine(startx, starty, endx, endy, linePaint);*/
		  
		  

		   if (ballOnScreen)
		   {
			   if (ballNo==1)
					   {
				   canvas.drawBitmap(bitmap,scaledBall.x-15, scaledBall.y-15, ballPaint);
				   
				   canvas.drawLine(1050, 40, 1050, 78, linePaint);
				   canvas.drawLine(1050, 80, 1050, 120, linePaint);
				   
					   }
			   else if (ballNo==2)
			   {
	    	canvas.drawBitmap(bitmap,scaledBall.x-15, scaledBall.y-15, ballPaint);
	    	
	    	canvas.drawBitmap(bitmap,scaledBall.x-40, scaledBall.y-40, ballPaint);
	    	
	    	  canvas.drawLine(1000, 40, 1050, 40, linePaint);
			   canvas.drawLine(1050, 40, 1050, 78, linePaint);
			 // canvas.drawLine(1050, 80, 1050, 118, linePaint);
			   canvas.drawLine(1000, 120, 1050, 118, linePaint);
			 canvas.drawLine(1000, 78, 1000, 118, linePaint);
			 // canvas.drawLine(1000, 40, 1000, 78, linePaint);
			canvas.drawLine(1000, 78, 1050, 78, linePaint);
			   
	    	
	    	
			   }
			   else if (ballNo==3)
			   {
	    	canvas.drawBitmap(bitmap,scaledBall.x-15, scaledBall.y-15, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-30, scaledBall.y-30, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-45, scaledBall.y-45, ballPaint);
	    	
	    	canvas.drawLine(1000, 40, 1050, 40, linePaint);
			   canvas.drawLine(1050, 40, 1050, 78, linePaint);
			  canvas.drawLine(1050, 80, 1050, 118, linePaint);
			   canvas.drawLine(1000, 120, 1050, 118, linePaint);
			// canvas.drawLine(1000, 78, 1000, 118, linePaint);
			//  canvas.drawLine(1000, 40, 1000, 78, linePaint);
			canvas.drawLine(1000, 78, 1050, 78, linePaint);
			
			   }
			   else if (ballNo==4)
			   {
	    	canvas.drawBitmap(bitmap,scaledBall.x-15, scaledBall.y-15, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-30, scaledBall.y-30, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-45, scaledBall.y-45, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-60, scaledBall.y-60, ballPaint);
	    	
	    	//canvas.drawLine(1000, 40, 1050, 40, linePaint);
			   canvas.drawLine(1050, 40, 1050, 78, linePaint);
			  canvas.drawLine(1050, 80, 1050, 118, linePaint);
			//   canvas.drawLine(1000, 120, 1050, 118, linePaint);
			// canvas.drawLine(1000, 78, 1000, 118, linePaint);
			  canvas.drawLine(1000, 40, 1000, 78, linePaint);
			canvas.drawLine(1000, 78, 1050, 78, linePaint);
	    	
			   }
			   else if (ballNo==5)
			   {
	    	canvas.drawBitmap(bitmap,scaledBall.x-15, scaledBall.y-15, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-30, scaledBall.y-30, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-45, scaledBall.y-45, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-60, scaledBall.y-60, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-75, scaledBall.y-75, ballPaint);
	    	
	    	canvas.drawLine(1000, 40, 1050, 40, linePaint);
			  // canvas.drawLine(1050, 40, 1050, 78, linePaint);
			  canvas.drawLine(1050, 80, 1050, 118, linePaint);
			   canvas.drawLine(1000, 120, 1050, 118, linePaint);
			// canvas.drawLine(1000, 78, 1000, 118, linePaint);
			  canvas.drawLine(1000, 40, 1000, 78, linePaint);
			canvas.drawLine(1000, 78, 1050, 78, linePaint);
	    	
			   }
			   else if (ballNo==6)
			   {
	    	canvas.drawBitmap(bitmap,scaledBall.x-15, scaledBall.y-15, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-30, scaledBall.y-30, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-45, scaledBall.y-45, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-60, scaledBall.y-60, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-75, scaledBall.y-75, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-90, scaledBall.y-90, ballPaint);
	    	
	    	canvas.drawLine(1000, 40, 1050, 40, linePaint);
			  // canvas.drawLine(1050, 40, 1050, 78, linePaint);
			  canvas.drawLine(1050, 80, 1050, 118, linePaint);
			   canvas.drawLine(1000, 120, 1050, 118, linePaint);
			 canvas.drawLine(1000, 78, 1000, 118, linePaint);
			  canvas.drawLine(1000, 40, 1000, 78, linePaint);
			canvas.drawLine(1000, 78, 1050, 78, linePaint);
	    	
			   }
			   else if (ballNo==7)
			   {
	    	canvas.drawBitmap(bitmap,scaledBall.x-15, scaledBall.y-15, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-30, scaledBall.y-30, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-45, scaledBall.y-45, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-60, scaledBall.y-60, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-75, scaledBall.y-75, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-90, scaledBall.y-90, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-105, scaledBall.y-105, ballPaint);
	    	
	    	canvas.drawLine(1000, 40, 1050, 40, linePaint);
			   canvas.drawLine(1050, 40, 1050, 78, linePaint);
			  canvas.drawLine(1050, 80, 1050, 118, linePaint);
			 //  canvas.drawLine(1000, 120, 1050, 118, linePaint);
			// canvas.drawLine(1000, 78, 1000, 118, linePaint);
			 // canvas.drawLine(1000, 40, 1000, 78, linePaint);
			//canvas.drawLine(1000, 78, 1050, 78, linePaint);
			
			
	    	
			   }
			   else if (ballNo==8)
			   {
	    	canvas.drawBitmap(bitmap,scaledBall.x-15, scaledBall.y-15, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-30, scaledBall.y-30, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-45, scaledBall.y-45, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-60, scaledBall.y-60, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-75, scaledBall.y-75, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-90, scaledBall.y-90, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-105, scaledBall.y-105, ballPaint);
	    	canvas.drawBitmap(bitmap,scaledBall.x-120, scaledBall.y-120, ballPaint);
	    	
	    	canvas.drawLine(1000, 40, 1050, 40, linePaint);
			   canvas.drawLine(1050, 40, 1050, 78, linePaint);
			  canvas.drawLine(1050, 80, 1050, 118, linePaint);
			   canvas.drawLine(1000, 120, 1050, 118, linePaint);
			 canvas.drawLine(1000, 78, 1000, 118, linePaint);
			  canvas.drawLine(1000, 40, 1000, 78, linePaint);
			canvas.drawLine(1000, 78, 1050, 78, linePaint);
	    	
			   }
			   else if (ballNo==9)
			   {
				   canvas.drawBitmap(bitmap,scaledBall.x-15, scaledBall.y-15, ballPaint);
			    	canvas.drawBitmap(bitmap,scaledBall.x-30, scaledBall.y-30, ballPaint);
			    	canvas.drawBitmap(bitmap,scaledBall.x-45, scaledBall.y-45, ballPaint);
			    	canvas.drawBitmap(bitmap,scaledBall.x-60, scaledBall.y-60, ballPaint);
			    	canvas.drawBitmap(bitmap,scaledBall.x-75, scaledBall.y-75, ballPaint);
			    	canvas.drawBitmap(bitmap,scaledBall.x-90, scaledBall.y-90, ballPaint);
			    	canvas.drawBitmap(bitmap,scaledBall.x-105, scaledBall.y-105, ballPaint);
			    	canvas.drawBitmap(bitmap,scaledBall.x-120, scaledBall.y-120, ballPaint);
			    	canvas.drawBitmap(bitmap,scaledBall.x-135, scaledBall.y-135, ballPaint);
			    	
			    	
			    	canvas.drawLine(1000, 40, 1050, 40, linePaint);
					   canvas.drawLine(1050, 40, 1050, 78, linePaint);
					  canvas.drawLine(1050, 80, 1050, 118, linePaint);
					   canvas.drawLine(1000, 120, 1050, 118, linePaint);
					// canvas.drawLine(1000, 78, 1000, 118, linePaint);
					  canvas.drawLine(1000, 40, 1000, 78, linePaint);
					canvas.drawLine(1000, 78, 1050, 78, linePaint);
			   }
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
		   }
		   
	   }
	   
	   public void updatePosition(double elapsedTimeSeconds)
	   {
	      if (ballOnScreen) // if there is currently a shot fired
	      {
	    	 ball.x = (int) (initialvelocity * Math.cos(Math.toRadians(initialAngle))*elapsedTimeSeconds);
	    	 ball.y = (int) (initialvelocity * Math.sin(Math.toRadians(initialAngle))*elapsedTimeSeconds - (G_CONSTANT*Math.pow(elapsedTimeSeconds, 2.0))/2);

	         if(ball.x > 0 && ball.y <= 0)
	         {
	               ballOnScreen = false;
	               setRunning(false);
	         }
	       } 
	   }
	   
	   public Point scaleData(Point ball,Canvas canvas, float xscale,float yscale)
	   {
		   Point scaledBall = new Point();
		   //Scale the dimesnions
		   scaledBall.x = (int) (ball.x * xscale);
		   scaledBall.y = (int) (-(ball.y * yscale) + canvas.getHeight());
		   return scaledBall;
	   }
	   
	    public float getScaleX(Canvas c) {
	        int maxspeed = 100;
	        float sinTerm, cosTerm, timeToImpact, maximumRange, scalex;

	        sinTerm = (float) Math.sin(Math.toRadians(45));
	        cosTerm = (float) Math.cos(Math.toRadians(45));
	        timeToImpact = (float) (2.00 * maxspeed * sinTerm / 9.80f);
	        maximumRange = (float) (maxspeed * timeToImpact * cosTerm);

	        scalex = (float) (c.getWidth() / maximumRange * 8.0 / 10);
	        return scalex;
	    }

	    public float getScaleY(Canvas c) {
	        int maxspeed = 100;
	        float maximumHeight, scaley;

	        maximumHeight = (float) (maxspeed * maxspeed / (2.0 * 9.80f));

	        scaley = (float) ((c.getHeight() / maximumHeight) * 9.0 / 10);
	        return scaley;
	    }
	

	  
}
