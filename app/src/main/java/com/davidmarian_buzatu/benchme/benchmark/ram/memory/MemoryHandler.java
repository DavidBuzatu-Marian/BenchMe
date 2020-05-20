package com.davidmarian_buzatu.benchme.benchmark.ram.memory;

public class MemoryHandler{

    private volatile int[] memmap = null;

    int mask1[] = {0x00FFFFFF,0xFF00FFFF,0xFFFF00FF,0xFFFFFF00};
    int mask2[] = {0xFF000000,0x00FF0000,0x0000FF00,0x000000FF};

    public MemoryHandler() {
    }

    public boolean allocateMemory(long sizeInBytes){

        int intSize = (int)(sizeInBytes/4);

        memmap = new int[intSize];


        return true;
    }

    public void freeMemory(){

        if(memmap != null)
            memmap = null;


    }

    public boolean writeByte(long addr,byte value){

        int intaddr = (int)(addr/4);

        int offset =(int)(addr & 3);

        int v = value;

        memmap[intaddr] = memmap[intaddr] & mask1[offset] | v << (3-offset);

        return true;
    }


    public boolean writeInt(long addr,int value){

        int intaddr = (int)(addr/4);

        memmap[intaddr] = value;

        return true;
    }

    public byte readByte(long addr){

        int intaddr = (int)(addr/4);

        int offset =(int)(addr & 3);

        int v = memmap[intaddr];

        return (byte)(v & mask2[offset] >> (3-offset));
    }

    public int readInt(long addr){

        int intaddr = (int)(addr/4);
        return memmap[intaddr];

    }
}