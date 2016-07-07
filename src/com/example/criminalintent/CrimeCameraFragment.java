package com.example.criminalintent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CrimeCameraFragment extends Fragment {

	private static final String TAG = "CrimeCameraFragment";
	public static final String EXTRA_PHOTO_FILENAME = 
			"com.example.crimialIntent.photo_filename";
	private SurfaceView mSurfaceView;
	private Camera mCamera;
	
	private View mProgressContainer;
	
	private Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
		
		@Override
		public void onShutter() {
			// TODO Auto-generated method stub
			mProgressContainer.setVisibility(View.VISIBLE);
		}
	};
	
	private Camera.PictureCallback mJpegCallback = new Camera.PictureCallback() {
		
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			String filename = UUID.randomUUID().toString() + ".jpg";
			FileOutputStream os = null;
			boolean successs = true;
			
			try {
				os = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
				os.write(data);
			} catch (Exception e) {
				Log.e("wangbin", "Error writing to file " + filename, e);
				successs = false;
			}finally{
				try {
					if(os != null){
						os.close();
					}
				} catch (Exception e) {
					Log.e("wangbin", "Error close the file " + filename, e);
					successs = false;
				}
			}
			if(successs){
				//Log.i("wangbin", "jpg saves at " + filename);
				Intent intent = new Intent();
				intent.putExtra(EXTRA_PHOTO_FILENAME, filename);
				getActivity().setResult(Activity.RESULT_OK, intent);
			}else {
				getActivity().setResult(Activity.RESULT_CANCELED);			
			}
			getActivity().finish();
		}
	};
	
	@Override
	@Nullable
	@SuppressWarnings("deprecation")
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		

	//	Log.d("nimei", "enter take_2");
		
		View view = inflater.inflate(R.layout.fragment_crime_camera, container, false);
		
		mProgressContainer = view.findViewById(R.id.crime_camera_progressContainer);
		mProgressContainer.setVisibility(View.INVISIBLE);
		
		Button takeButton = (Button)view.findViewById(R.id.crime_camera_take);
		
		takeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			//	getActivity().finish();
		//		Log.d("nimei", "take");
				
				if(mCamera!=null){
					mCamera.takePicture(mShutterCallback, null, mJpegCallback);
				}
			}
		});
		
		mSurfaceView = (SurfaceView)view.findViewById(R.id.crime_camera_surfaceView);
		
		SurfaceHolder holder = mSurfaceView.getHolder();
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		Log.d("nimei", "enter take_3");
		holder.addCallback(new SurfaceHolder.Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				Log.d("nimei", "enter take surfaceDestroyed");
				if(mCamera != null){
					mCamera.stopPreview();
				}
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				Log.d("nimei", "enter take surfaceCreated");
				try {
					if(mCamera != null){
						mCamera.setPreviewDisplay(holder);
					}
				} catch (IOException e) {
					// TODO: handle exception
				}
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				Log.d("nimei", "enter take surfaceChanged");
				if(mCamera == null){
					return;
				}
				
				Camera.Parameters parameters = mCamera.getParameters();
				Size size = getBestSupporSize(parameters.getSupportedPreviewSizes(), width, height);//null;
				parameters.setPreviewSize(size.width, size.height);
				
				size = getBestSupporSize(parameters.getSupportedPictureSizes(), width, height);
				parameters.setPictureSize(size.width, size.height);
				
				mCamera.setParameters(parameters);
				try {
					mCamera.startPreview();
				} catch (Exception e) {
					// TODO: handle exception
					mCamera.release();
					mCamera = null;
				}
			}
		
		});
		
		return view;
	}

	private Size getBestSupporSize(List<Size> sizes, int width, int height) {
		Size bestsSize = sizes.get(0);
		int largestArea = bestsSize.width * bestsSize.height;
		for (Size size : sizes) {
			int area = size.width*size.height;
			if(area > largestArea){
				bestsSize = size;
				largestArea = area;
			}
		}
		return bestsSize;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {    
            mCamera = Camera.open(0);
        } else {
            mCamera = Camera.open();
		Log.d("nimei","!!!!");
        }
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if(mCamera != null){
			mCamera.release();
			mCamera = null;
		}
	}
}
