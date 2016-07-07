package com.example.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.util.Log;


public class CrimeLab {

	private static CrimeLab sCrimeLab;
	private Context mAppContext;
	private ArrayList<Crime> mCrimes;
	
	private static final String FILENAME = "crime.json";
	private CriminalIntentJSONSerializer mSerializer;
	
	private CrimeLab(Context appContext){
		mAppContext = appContext;
	
		mSerializer = new CriminalIntentJSONSerializer(mAppContext, FILENAME);
		try {
			mCrimes = mSerializer.loadCrimes();
		} catch (Exception e) {
			mCrimes = new ArrayList<Crime>();
			Log.d("wangbin", "Error loading crimes");
			// TODO: handle exception
		}
		
	//	mCrimes = new ArrayList<Crime>();
		/*for (int i = 0; i < 100; i++) {
			Crime crime = new Crime();
			crime.setmTitle("Crime #" + i);
			crime.setmSolved(i%2 == 0);
			mCrimes.add(crime);
		}*/
		
	}
	
	public static CrimeLab get(Context c){
		if(sCrimeLab == null){
			sCrimeLab = new CrimeLab(c.getApplicationContext());
		}
		return sCrimeLab;
	}
	
	
	public void addCrime(Crime c){
		mCrimes.add(c);
	}
	
	public void deleteCrime(Crime c) {
		mCrimes.remove(c);
	}
	
	public boolean saveCrimes() {
		try {
			mSerializer.saveCrimes(mCrimes);
			Log.d("wangbin_1", "crimes saved to file");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("wangbin", "Error saving crimes: ");
			return false;
		} 
	}  
	
	public ArrayList<Crime> getCrimes(){
		return mCrimes;
	}
	
	public Crime getCrime(UUID id){
		for(Crime c : mCrimes){
			if(c.getmId().equals(id)){
				return c;
			}
		}
		return null;
	}
	
}
