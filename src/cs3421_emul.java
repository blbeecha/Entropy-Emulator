/**
 * Author: Brendan Beecham
 * Course: CS3421 Comp Org
 * Assignment: Implement a functioning clock, cpu, and memory interface that
 *              can be used in future assignments.
 *
 * Component: Main.java
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class cs3421_emul {

    public static void main(String[] args) {
        clock time = new clock();
        cpu comp = new cpu();
        data_memory mem = new data_memory();
        inst_memory inst = new inst_memory();

        int[] mem_storage = new int[0];
        int[] cpu_storage = new int[9];
        int[] inst_storage = new int[0];
        int create_check = 0;
        boolean halt_called = false;


        String filename = args[0];
        File file = new File(filename);
        try (Scanner scan = new Scanner(file)) {
            while(scan.hasNext()) {
                int increment;
                while (scan.hasNextLine()) {
                    String component = scan.next();
                    if (component.compareTo("clock") == 0) {
                        String command = scan.next();
                        if (command.compareTo("reset") == 0) {
                            time.reset();
                        } else if (command.compareTo("tick") == 0) {
                            if(!halt_called) {
                                increment = Integer.parseInt(scan.next());
                                time.tick(increment);
                                if (create_check == 1) {
                                    //run instruction loop
                                    halt_called = comp.cpu_inst_run(cpu_storage, inst_storage, mem_storage, increment);
                                }
                            }
                        } else if (command.compareTo("dump") == 0) {
                            time.dump();
                        }
                    }

                    //Checks for Data memory commands
                    else if (component.compareTo("memory") == 0) {
                        String command = scan.next();
                        if (command.compareTo("create") == 0) {
                            mem_storage = mem.create(mem_storage, Integer.parseInt(scan.next().replaceAll("0x", ""), 16));
                            create_check = 1;
                        }
                        if (create_check == 1) {
                            if (command.compareTo("reset") == 0) {   //memory reset
                                mem_storage = mem.reset(mem_storage);

                            } else if (command.compareTo("dump") == 0) {   //memory dump
                                int dumpAddr = Integer.parseInt(scan.next().replaceAll("0x", ""), 16);
                                int dumpCnt = Integer.parseInt(scan.next().replaceAll("0x", ""), 16);
                                mem.dump(mem_storage, dumpAddr, dumpCnt);

                            } else if (command.compareTo("set") == 0) {    //memory set
                                int hexAdrr = Integer.parseInt(scan.next().replaceAll("0x", ""), 16);
                                String fileTest = scan.next();
                                if (fileTest.contains("file")) {                   //check for filename in input
                                    File memInput = new File(scan.next());
                                    mem_storage = mem.set(mem_storage, hexAdrr, memInput);
                                } else {                                             //use stdin if no filename given
                                    int[] memInput;
                                    int hexCnt = Integer.parseInt(fileTest.replaceAll("0x", ""), 16);
                                    memInput = new int[hexCnt];
                                    memInput[0] = Integer.parseInt(scan.next().replaceAll("0x", ""), 16);
                                    for (int i = 1; i < hexCnt; i++) {
                                        String next = scan.next().replaceAll("0x", "");
                                        int hex = Integer.parseInt(next, 16);
                                        memInput[i] = hex;
                                    }
                                    mem_storage = mem.set(mem_storage, hexAdrr, hexCnt, memInput);
                                }
                            }
                        }
                    }

                    //Checks for Instruction memory commands
                    else if (component.compareTo("imemory") == 0) {
                        String command = scan.next();
                        if (command.compareTo("create") == 0) {
                            inst_storage = inst.create(inst_storage, Integer.parseInt(scan.next().replaceAll("0x", ""), 16));
                            create_check = 1;
                        }
                        if (create_check == 1) {
                            if (command.compareTo("reset") == 0) {   //memory reset
                                inst_storage = inst.reset(inst_storage);

                            } else if (command.compareTo("dump") == 0) {   //memory dump
                                int dumpAddr = Integer.parseInt(scan.next().replaceAll("0x", ""), 16);
                                int dumpCnt = Integer.parseInt(scan.next().replaceAll("0x", ""), 16);
                                inst.dump(inst_storage, dumpAddr, dumpCnt);

                            } else if (command.compareTo("set") == 0) {    //memory set
                                int inst_hexAdrr = Integer.parseInt(scan.next().replaceAll("0x", ""), 16);
                                String fileTest = scan.next();
                                if (fileTest.contains("file")) {                   //check for filename in input
                                    File instInput = new File(scan.next());
                                    inst_storage = inst.set(inst_storage, inst_hexAdrr, instInput);
                                } else {                                             //use stdin if no filename given
                                    int[] instInput;
                                    int inst_hexCnt = Integer.parseInt(fileTest.replaceAll("0x", ""), 16);
                                    instInput = new int[inst_hexCnt];
                                    instInput[0] = Integer.parseInt(scan.next().replaceAll("0x", ""), 16);
                                    for (int i = 1; i < inst_hexCnt; i++) {
                                        String inst_next = scan.next().replaceAll("0x", "");
                                        int inst_hex = Integer.parseInt(inst_next, 16);
                                        instInput[i] = inst_hex;
                                    }
                                    inst_storage = inst.set(inst_storage, inst_hexAdrr, inst_hexCnt, instInput);
                                }
                            }
                        }
                    }

                    //Checks for cpu commands
                    else if (component.compareTo("cpu") == 0) {
                        String command = scan.next();
                        if (command.compareTo("reset") == 0) {
                            cpu_storage = comp.reset(cpu_storage);
                            time.reset();
                        } else if (command.compareTo("set") == 0) {
                            String garbage = scan.next();
                            String reg = scan.next();
                            int regByte = Integer.parseInt(scan.next().replaceAll("0x", ""), 16);
                            cpu_storage = comp.setreg(cpu_storage, reg, regByte);
                        } else if (command.compareTo("dump") == 0) {

                            comp.dump(cpu_storage);
                        }
                    }
                }
            }
            System.exit(0);
        } catch (FileNotFoundException s) {
            System.out.println("File not found. Try again.");
        }
    }
}
