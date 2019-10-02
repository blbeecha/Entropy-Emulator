/**
 * Author: Brendan Beecham
 * Course: CS3421 Comp Org
 * Assignment: Implement an instruction memory to hold all instructions
 * 		from now on. Additionally, implement lw and sw instructions
 * 		to be completed every 5 clock ticks.
 *
 * Component: data_memory.java
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class data_memory {

    public int[] create(int[] storage, int size) {
        //creates memory with the input hex integer size
        storage = new int[size];
        return storage;
    }

    public int[] reset(int[] storage) {
        for( int i = 0; i < storage.length; i++) {
            storage[i] = 0;
        }
        return storage;
    }

    public void dump(int[] storage, int address, int count) {
        //prints the memory values starting from address and
        System.out.println("Addr   00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F");
        int rowLimit = 16;
        int x = 0;  //row counter
        int limit = address + count;
        int first_addr_label = address;
        while(first_addr_label % 16 != 0) {     //decrements until address is a multiple of 16
            first_addr_label--;
        }
        String first_address = (String.format("0x%04X", first_addr_label) + ' ').toUpperCase();
        int first_address_len = first_address.length();
        System.out.print(first_address );        //prints decremented label
        if(first_address_len == 5) {
            System.out.print("  ");
        }
        int address_catchup = 1;
        int temp_address = first_addr_label;
        while(address < limit) {
            if(address_catchup == 0){           //triggers when whitespace has been filled for address values we don't care about
                if (address % 16 == 0) {
                    String format = (String.format("0x%04X", address) + ' ').toUpperCase();
                    System.out.print(format);
                }
            } else if(address_catchup == 1) {       //fills in don't care address values with whitespace
                while(temp_address < address) {
                    System.out.print("   ");
                    x++;
                    temp_address++;
                }
                address_catchup = 0;
            }

            if (storage[address] != 0) {
                String toHex = String.format("%02X", storage[address]);
                System.out.print(toHex + " ");
            } else if (storage[address] != 0) {
                System.out.print("   ");
            }
            x++;

            if(x == rowLimit) {
                System.out.println();
                x = 0;  //resets row count
            }
            address++;
        }
        System.out.println();
    }

    public int[] set (int[] storage, int address, int count, int...hexByte){
        //sets memory to count number of values starting at address
        for (int i = 0; i < count; i++) {
            storage[address] = hexByte[i];
            address++;
        }
        return storage;
    }

    public  int[] set (int[] storage, int address, File datafile){
        //same as above but the memory values are contained in a datafile
        //count is determined by the num of entries
        try (Scanner read = new Scanner(datafile)) {
            while(read.hasNext()) {
                storage[address] = Integer.parseInt(read.next(), 16);
                address++;
            }
        } catch (FileNotFoundException s) {
            System.out.println("File not found Please try again.");
        }
        return storage;
    }

    public int[] tickReceive(int[] storage, int increment) {
        //will use later
        return storage;
    }

}
