package com.example.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class CrimeCameraActivity extends SingleFragmentActivity {

	
	@Override
	protected void onCreate(Bundle saveInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(saveInstanceState);
	}
	
	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub

		Log.d("nimei", "enter take_1");
		return new CrimeCameraFragment();
	}

}
