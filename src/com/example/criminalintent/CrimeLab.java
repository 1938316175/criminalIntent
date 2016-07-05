package com.example.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.util.Log;


public class CrimeLab {

	private static CrimeLab sCrimeLab;
	private Context mAppContext;
	private ArrayList<Crime> mCrimes;
	
	
	private CrimeLab(Context appContext){
		mAppContext = appContext;
	
		mCrimes = new ArrayList<Crime>();
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
