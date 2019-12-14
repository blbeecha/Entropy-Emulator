import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class iodev {
    private static int reg;
    private static int tickStorage[];
    private static String valStorage[];
    static int tickLimit;
    static boolean loadCheck;

    public iodev() {
        reg = 0;
        loadCheck = false;
        //constructor
    }

    public void reset() {
        reg = 0;
    }

    public void dump() {
        System.out.println("IO Device: 0x" + (String.format("%02X", reg) + ' ').toUpperCase());
        System.out.println();
    }

    public void load(File dataFile) {
        int i = 0;
        int count = 0;
        try( Scanner scan = new Scanner(dataFile) ) {
            while (scan.hasNextLine()) {
                count++;
                scan.nextLine();
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found. Try again.");
        }

        tickStorage = new int[count];
        valStorage = new String[count];

        try( Scanner loadScan = new Scanner(dataFile) ) {
            while(loadScan.hasNext()) {
                tickStorage[i] = loadScan.nextInt();    //ticks into tickStorage
                String op = loadScan.next();
                if(op.compareTo("read") == 0) {                  //0 if 'read'
                    op = "0";
                } else {                            //1 if 'write'
                    op = "1";
                }
                valStorage[i] = op + loadScan.next();   //operation and address stored in valStorage
                if(op.compareTo("1") == 0) {
                    valStorage[i] += loadScan.next();   //for write, append value after address
                }
                i++;
                loadScan.nextLine();
            }
            tickLimit = i;
            loadCheck = true;
        } catch( FileNotFoundException e) {
            System.out.println("File not found. Try again.");
        }
    }

    public boolean isLoaded() {
        return loadCheck;
    }

    public boolean getTick(int tick) {              //check if current CPU tick is in in tickStorage
        for( int i = 0; i < tickLimit; i++ ) {
            if (tick == tickStorage[i]) {
                return true;
            }
        }
        return false;
    }

    public int[] getop(int tick, int[] data_mem) {
        for( int i = 0; i < tickLimit; i++ ) {      //repeating getTick above to get value of i
            if(tickStorage[i] == tick) {
                if(valStorage[i].substring(0, 1).compareTo("0") == 0 ) {       //'read'
                    int address = Integer.parseInt(valStorage[i].substring(3, 5), 16);

                    reg = data_mem[address];

                } else if(valStorage[i].substring(0, 1).compareTo("1") == 0) {     //'write'
                    int address = Integer.parseInt(valStorage[i].substring(3, 5), 16);
                    int value = Integer.parseInt(valStorage[i].substring(7, 9), 16);

                    data_mem[address] = value;
                }
            }
        }
        return data_mem;
    }

}
