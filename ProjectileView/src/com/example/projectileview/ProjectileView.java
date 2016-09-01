package com.example.projectileview;
import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ProjectileView extends SurfaceView implements SurfaceHolder.Callback {

  public ProjectThread pThread;
	public ProjectThread getpThread() {
		return pThread;
	}

	public void setpThread(ProjectThread pThread) {
		this.pThread = pThread;
	}  
	
	public ProjectileView(Context context, AttributeSet attrs) {
		super(context, attrs);
		getHolder().addCallback(this);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	      // ensure that thread terminates properly
	      boolean retry = true;
	      pThread.setRunning(false);
	      while (retry)
	      {
	         try
	         {
	        	pThread.join();
	            retry = false;
	         } // end try
	         catch (InterruptedException e)
	         {
	         } // end catch
	      } // end while
	}

}
