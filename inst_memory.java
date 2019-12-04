import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Author: Brendan Beecham
 * Course: CS3421 Comp Org
 * Assignment: Implement a functioning cache interface that
 *              can communicate between the data memory
 *              and cpu.
 * Component: inst_memory.java
 */

public class inst_memory {

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
        System.out.println("Addr       0     1     2     3     4     5     6     7");
        int rowLimit = 8;
        int x = 0;  //row counter
        int limit = address + count;
        int first_addr_label = address;
        while(first_addr_label % rowLimit != 0) {     //decrements until address is a multiple of 7
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
                if (address % rowLimit == 0) {
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
                String toHex = String.format("%05X", storage[address]);
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
        int nextInt;
        try (Scanner read = new Scanner(datafile)) {
            while(read.hasNext()) {
                nextInt = Integer.parseInt(read.next(), 16);
                //System.out.println(nextInt);
                storage[address] = nextInt;
                address++;
                this.tickReceive(storage, 1);
            }
        } catch (FileNotFoundException s) {
            System.out.println("File not found Please try again.");
        }
        /*for(int i = 0; i < storage.length; i++ ) {
            System.out.println(storage[i]);
        }*/
        return storage;
    }

    public int[] tickReceive(int[] storage, int increment) {
        //will use later
        return storage;
    }

}
