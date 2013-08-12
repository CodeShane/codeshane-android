package com.codeshane.data.types;

import java.io.Serializable;
import java.util.ArrayList;

import android.os.CountDownTimer;

//import codeshane.data.state._Pauseable;
//import codeshane.data.state._Stoppable;

public class Counter implements Serializable { // , _Pauseable, _Stoppable {
	//Converter xl;
	//android.os.CountDownTimer b;
	protected long mValue = 0L;

	protected boolean mIsCounting = false;
	protected long mCountingFrom = 0L;

	public Counter(){}

	public Counter(long value){
		mValue = value;
	}

	public long getValue() { return mValue; }
	public void setValue(long value) { this.mValue = value; }

	public void resumeCountingFrom(long fromInclusive) {
		if (!isPaused()) throw new IllegalStateException();
		mCountingFrom = fromInclusive;
		mIsCounting=true;
	}

	public void pauseCountingAt(long toExclusive){
		if (isPaused()) throw new IllegalStateException();
		long d = com.codeshane.util.Math.change(mCountingFrom, toExclusive);
		// 10,15 = 5
		mValue += d;
		mCountingFrom = toExclusive;
		mIsCounting=false;
	}

	public boolean isPaused() { return !mIsCounting; }
	public void pause(){ pauseCountingAt(System.currentTimeMillis()); }
	public void resume(){ resumeCountingFrom(System.currentTimeMillis()); }
	public void toggle(){
		if(mIsCounting) { pause(); }
		else { resume(); }
	}

	public void start() { resume(); }
	public void stop() { pause(); }

	public boolean isActive() { return mIsCounting; }

}

//public Timer parent;
//public boolean running = false;
//
//public void start(){
//	if (null!=parent) parent.start();
//}
//public void stop(){
//	if (null!=parent) parent.stop();
//}
//public void start(long millis){
//	set(millis);
//	start();
//}
//public void stop(long millis){
//	set(millis);
//	stop();
//}
//public void set(long millis){

//public abstract class AbsTimer extends Counter {
//	public abstract long getValueMillis(); //{ return mTime - now(); }
//	public long getTimeMillis(){ return mTime; };
//	public void setTimeMillis(long millis){
//		mTime = millis;
//	};
//	public void setTimeNow(){
//		mTime = System.currentTimeMillis();
//	};
//	public void setTimeZero(){
//		mTime = 0;
//	}
//}

//CountdownTimer//CountupTimer

// Stopwatch - Quintessential Count-Up Tool
//	ArrayList<Long> mLaps;
//	public ArrayList<Long> getLaps() {
//		return mLaps;
//	}
//	public Long getLap(int id){
//		if (id<mLaps.size()) return mLaps.get(id);
//		else return null;
//	}