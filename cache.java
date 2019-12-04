/**
 * Author: Brendan Beecham
 * Course: CS3421 Comp Org
 * Assignment: Implement a functioning cache interface that
 *              can communicate between the data memory
 *              and cpu.
 * Component: cache.java
 */

public class cache {

    private static int state;
    private static int[] cache_memory = new int[8];
    private static char[] valid_memory = new char[8];
    private static char[] write_memory = new char[8];
    private static int CLO;

    /*
        cache_memory[0] is CLO
          Each CLO bit represents that block of 8 bytes of data:
            0 -> 0-7
            1 -> 8-15
            ...
            31 -> 248-255
     */

    /*
        valid_memory[i] legend:
            I -> Invalid
            V -> Valid
            W -> Written
     */

    public cache(){
        state = 0;
        reset();
    }

    public void reset() {
        //
        off();
        CLO = 0;
        for(int i = 0; i < 8; i++ ) {
            valid_memory[i] = 'I';
        }
    }

    public void on() {
        state = 1;
    }

    public void off() {
        state = 0;
    }

    public int checkState() {
        return state;
    }

    private int findAddress(int address) {
        int address_section = -1, low_test, high_test;  //high and low thresholds for testing CLO bit range
        for( int i = 0; i < 32; i++ ) {
            low_test = i * 8;
            high_test = low_test + 8;
            if( low_test <= address && address < high_test ) {
                address_section = i;
                break;
            }
        }
        return address_section;
    }

    private int findDataOffset(int address, int address_section) {
        int section_start = address_section * 8;
        return address - (section_start * address_section);
    }

    public int read(int address, int[] data_memory) {

        if( address == 255 ) {
            for (int i = 0; i < 8; i++) {
                valid_memory[i] = 'I';
            }
            return 2;
        }

        int address_section = findAddress(address);
        int data_offset = findDataOffset(address, address_section);

        int hit_or_miss = 0, not_valid = 1;

        if( address_section == CLO ) {      //computed cache line = CLO
            if(String.valueOf(valid_memory[data_offset]).compareTo("V") == 0) {     //valid data check
                hit_or_miss = 1;
                not_valid = 0;
            }
        } if(not_valid == 1) {      //not valid OR computed cache line != CLO
            int address_iterable = address_section;
            for(int i = 0; i < 8; i++ ) {
                cache_memory[i] = data_memory[address_iterable + i];
                valid_memory[i] = 'V';
            }

            CLO = address_section;
        }


        return hit_or_miss;
    }

    public int read_hit(int address) {
        return cache_memory[address];
    }

    public int write(int start_address, int destination_address, int[] data_memory) {

        if( destination_address == 255 ) {  //address is 0xFF
            return 2;
        }

        int address_section = findAddress(destination_address);
        int data_offset = findDataOffset(destination_address, address_section);

        boolean valid = validCheck();   //return true if ANY valid flag exists


        if( (address_section == CLO) || (!valid) ) {      //computed cache line = CLO or no valid flags raised
            cache_memory[data_offset] = start_address;
            write_memory[data_offset] = 'W';
            valid_memory[data_offset] = 'V';

            return 1;

        } else {
            if(writeCheck()) {
                return 2;
            } else {
                for(int i = 0; i < 8; i++ ) {
                    if( i != data_offset) {
                        valid_memory[i] = 'I';
                    }
                }
                cache_memory[data_offset] = start_address;
                write_memory[data_offset] = 'W';
                CLO = address_section;
                return 0;
            }

            //flush will be called next if needed
        }
    }

    private boolean validCheck() {
        for(int i = 0; i < 8; i++ ) {
            if( valid_memory[i] == 'V') {
                return true;
            }
        }
        return false;
    }

    private boolean writeCheck() {      //check if any Write flags are written
        for(int i = 0; i < 8; i++) {
            if( write_memory[i] == 'W' ) {
                return true;
            }
        }
        return false;
    }

    private int flushFlag;

    public int[] flush(int start_addr, int dest_address, int[] data_memory) {
        int address_section;
        if(dest_address == 255) {
            address_section = CLO;
        } else address_section = findAddress(dest_address);

        flushFlag = 0;
        for( int i = 0; i < 8; i++ ) {      //iterate thru cache memory
            if( write_memory[i] == 'W') {       //check for write flag
                flushFlag = 1;
                data_memory[(CLO * 8) + i] = cache_memory[i];     //set data to the subindex value
                write_memory[i] = 'F';      //reset write flag
                valid_memory[i] = 'I';
            }
        }

        if(dest_address != 255) {
            int data_offset = findDataOffset(dest_address, address_section);
            CLO = address_section;
            cache_memory[data_offset] = start_addr;
            write_memory[data_offset] = 'W';
            valid_memory[data_offset] = 'V';
        }

        return data_memory;
    }

    public int getFlushFlag() {
        return flushFlag;
    }

    public void dump() {
        System.out.println("CLO        : " + (String.format("0x%02X", CLO) + ' ').toUpperCase());
        System.out.print(  "cache data : ");
        int i;
        for( i = 0; i < 8; i++) {
            System.out.print((String.format("0x%02X", cache_memory[i]) + ' ').toUpperCase());
        }
        System.out.println();
        System.out.print("Flags      :   ");
        for( i = 0; i < 8; i++ ) {
            if( write_memory[i] == 'W') {
                System.out.print( (write_memory[i]));
                System.out.print("    ");
            } else {
                System.out.print( (valid_memory[i]));
                System.out.print("    ");
            }
        }
        System.out.println();
        System.out.println();
    }

}
