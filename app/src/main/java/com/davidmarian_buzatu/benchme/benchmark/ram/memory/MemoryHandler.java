package com.davidmarian_buzatu.benchme.benchmark.ram.memory;

import android.util.Log;

import com.ironz.unsafe.UnsafeAndroid;

import java.lang.reflect.Field;

public class MemoryHandler {

    private UnsafeAndroid unsafe;
    private Long memp;
    private Long size;

    /**
     * Creates a new instance of MemoryHandler and initializes the unsafe instance;
     */
    public MemoryHandler() {
        try {
            unsafe = getUnsafe();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to get unsafe!");
        }
        memp = null;
    }

    /**
     * Allocates memory of size sizeInBytes . If there was already an alocated block, it will be deallocated.
     * For the user, the block addresses begin from 0 to size-1 !
     *
     * @param sizeInBytes
     * @return if the operation was successful or not
     */
    public boolean allocateMemory(Long sizeInBytes) {
        size = sizeInBytes;
        try {
            if (memp != null)
                unsafe.freeMemory(memp);
            memp = unsafe.allocateMemory(sizeInBytes);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Deallocates the last allocated memory block.
     */
    public void freeMemory() {
        if (memp != null) {
            unsafe.freeMemory(memp);
            memp = null;
        }
    }

    public boolean writeByte(Long addr, byte value) {
        try {
            unsafe.putByte(null, addr + memp, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Byte readByte(Long addr) {
        try {
            return unsafe.getByte(addr + memp);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean writeInt(Long addr, int value) {
        try {
            unsafe.putInt(null, addr + memp, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public Integer readInt(Long addr) {
        try {
            return unsafe.getInt(addr + memp);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static UnsafeAndroid getUnsafe() throws Exception {
        // Get the Unsafe object instance
        return new UnsafeAndroid();
    }
}
