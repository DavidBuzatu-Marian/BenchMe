package com.davidmarian_buzatu.benchme.benchmark;

import com.ironz.unsafe.UnsafeAndroid;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MemoryHandler {

	private UnsafeAndroid unsafe;
	private long memp;


	public MemoryHandler() {
		try {
			unsafe = new UnsafeAndroid();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		memp = 0;
	}

	public boolean allocateMemory(long sizeInBytes){
		try{
			if(memp != 0)
			unsafe.freeMemory(memp);
			memp = unsafe.allocateMemory(sizeInBytes);
		}catch(Exception e){
			return false;
		}
		return true;
	} 

	public void close(){
		if(memp != 0){
			unsafe.freeMemory(memp);
		}
	}

	// public boolean writeByte(){

	// }


}