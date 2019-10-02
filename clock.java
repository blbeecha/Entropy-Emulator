/**
 * Author: Brendan Beecham
 * Course: CS3421 Comp Org
 * Assignment: Implement an instruction memory to hold all instructions
 * 		from now on. Additionally, implement lw and sw instructions
 * 		to be completed every 5 clock ticks.
 *
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
