/**
 * Author: Brendan Beecham
 * Course: CS3421 Comp Org
 * Assignment: Implement a functioning cache interface that
 *              can communicate between the data memory
 *              and cpu.
 * Component: clock.java
 */

public class clock {


    public int count = 0;

    public int getCount() {
        return count;
    }

    public void reset(){
        count = 0xff & 0;
    }

    public void tick(int increment){
        count += increment;
    }

    public void dump(){
        System.out.println("Clock: " + count);
        System.out.println();
    }
}
