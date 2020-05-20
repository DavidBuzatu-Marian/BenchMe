package com.davidmarian_buzatu.benchme.benchmark.ram.memory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

//import bench.MemoryMapper;
//import benchmark.ram.RamBenchmark;
//import sun.misc.Unsafe;

public class MemoryHandler {

//    private Unsafe unsafe;
    private long memp;
    MemoryMapper mapper = null;

    /**
     * Creates a new instance of MemoryHandler and initializes the unsafe instance;
     */
    public MemoryHandler() {
        // try {
        // unsafe = getUnsafe();
        // } catch (Exception e) {
        // e.printStackTrace();
        // throw new RuntimeException("Unable to get unsafe!");
        // }
        memp = 0;
    }

    /**
     * Allocates memory of size sizeInBytes . If there was already an alocated block, it will be deallocated.
     * For the user, the block addresses begin from 0 to size-1 !
     * @param sizeInBytes
     * @return if the operation was successful or not
     */
    public boolean allocateMemory(long sizeInBytes){
        // try{
        // 	if(memp != 0)
        // 	unsafe.freeMemory(memp);
        // 	memp = unsafe.allocateMemory(sizeInBytes);
        // }catch(Exception e){
        // 	return false;
        // }

        if(mapper != null){
            mapper.purge();
            mapper = null;
        }

        try {
            mapper = new MemoryMapper(null, sizeInBytes);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }

    /**
     * Deallocates the last allocated memory block.
     */
    public void freeMemory(){
        // if(memp != 0){
        // 	unsafe.freeMemory(memp);
        // 	memp = 0;
        // }

        mapper.purge();
        mapper = null;
    }

    public boolean writeByte(long addr,byte value){
        // try{
        // 	unsafe.putByte(null,addr+memp, value);
        // }catch(Exception e){
        // 	e.printStackTrace();
        // 	return false;
        // }

        byte []v = new byte[1];
        v[0] = value;

        try {
            mapper.put(addr, v);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }

    public Byte readByte(long addr){
        // try{
        // 	return unsafe.getByte(addr+memp);
        // }catch(Exception e){
        // 	e.printStackTrace();
        // 	return null;
        // }

        try {
            return mapper.get(addr, 1)[0];
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;

    }


    public boolean writeInt(long addr,int value){
        // try{
        // 	unsafe.putInt(null,addr+memp, value);
        // }catch(Exception e){
        // 	e.printStackTrace();
        // 	return false;
        // }

        byte[] bytes = ByteBuffer.allocate(4).putInt(value).array();

        try {
            mapper.put(addr, bytes);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }


    public Integer readInt(long addr){
        // try{
        // 	return unsafe.getInt(addr+memp);
        // }catch(Exception e){
        // 	e.printStackTrace();
        // 	return null;
        // }

        byte[] arr = null;
        try {
            arr = mapper.get(addr, 4);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        ByteBuffer wrapped = ByteBuffer.wrap(arr); // big-endian by default
        int num = wrapped.getInt();

        return num;
    }

//    private static Unsafe getUnsafe() throws Exception {
//        // Get the Unsafe object instance
//        Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
//        field.setAccessible(true);
//        return (sun.misc.Unsafe) field.get(null);
//    }
}