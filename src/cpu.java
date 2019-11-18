/**
 * Author: Brendan Beecham
 * Course: CS3421 Comp Org
 * Assignment: Implement a functioning clock, cpu, and memory interface that
 *              can be used in future assignments.
 * Component: cpu.java
 */


public class cpu {
    private int tc;

    public cpu() {
        exec = false;
        waitState = 5;
        tc = 0;
    }

    /*
        cpu array key:
            PC = comp[0]
            RA = comp[1]
            RB = comp[2]
            RC = comp[3]
            RD = comp[4]
            RE = comp[5]
            RF = comp[6]
            RG = comp[7]
            RH = comp[8]
     */

    public int checkReg(String reg) {
        if(reg.matches("RA")) {
            return 1;
        } else if(reg.matches("RB")) {
            return 2;
        } else if(reg.matches("RC")) {
            return 3;
        } else if(reg.matches("RD")) {
            return 4;
        } else if(reg.matches("RE")) {
            return 5;
        } else if(reg.matches("RF")) {
            return 6;
        } else if(reg.matches("RG")) {
            return 7;
        } else if(reg.matches("RH")) {
            return 8;
        } else {
            return 0;
        }
    }


    public int[] reset(int[] comp){
        //resets all cpu registers values to zero
        for(int i = 0; i < comp.length; i++) {
            comp[i] = 0;
        }
        tc = 0;
        return comp;
    }

    public int[] setreg(int[] comp, String reg, int regByte){
        if(reg.contains("RA")){comp[1] = regByte;}
        else if(reg.contains("RB")){comp[2] = regByte;}
        else if(reg.contains("RC")){comp[3] = regByte;}
        else if(reg.contains("RD")){comp[4] = regByte;}
        else if(reg.contains("RE")){comp[5] = regByte;}
        else if(reg.contains("RF")){comp[6] = regByte;}
        else if(reg.contains("RG")){comp[7] = regByte;}
        else if(reg.contains("RH")){comp[8] = regByte;}
        else if(
                reg.contains("PC")){comp[0] = regByte;
                this.waitState = 0;
        }

        return comp;

    }

    public void dump(int[] comp){
        //prints reg values
        System.out.println("PC: " + String.format("0x%02X", comp[0]));
        System.out.println("RA: " + String.format("0x%02X", comp[1]));
        System.out.println("RB: " + String.format("0x%02X", comp[2]));
        System.out.println("RC: " + String.format("0x%02X", comp[3]));
        System.out.println("RD: " + String.format("0x%02X", comp[4]));
        System.out.println("RE: " + String.format("0x%02X", comp[5]));
        System.out.println("RF: " + String.format("0x%02X", comp[6]));
        System.out.println("RG: " + String.format("0x%02X", comp[7]));
        System.out.println("RH: " + String.format("0x%02X", comp[8]));
        System.out.println("TC: " + tc);
        System.out.println();
    }

    public boolean exec;
    public int waitState;
    public String nextInstr;

    private void tickWait() {
        if(4 > this.waitState) {
            this.exec = true;
            this.waitState++;
        } else {
            this.exec = false;
        }
    }

    public int[] tickReceive(int[] comp, int[] inst) {
        this.tickWait();
        if( getExec() ) { return comp; }       //return if instruction is still executing

        //else proceed with instruction decode


        try {
            if( inst[comp[0]] != 0) {
                this.nextInstr = decode(inst[comp[0]]);

                this.waitState = 0;
                comp[0]++;
            }
        } catch(NullPointerException e ) {       //make sure there is another instruction in comp[0]
            return comp;
        }

        return comp;
    }

    public boolean getExec() {
        return this.exec;
    }

    public String getNextInstr() {
        return this.nextInstr;
    }

    /** decodes the instructions to return for processing **/
    public String decode(int hex) {

        String result = "";
        String toHex = Integer.toBinaryString(hex);

        String instruction = toHex.substring(0, 3);

        if(instruction.matches("101")) {                //decoding instruction
            instruction = "lw";
        } if(instruction.matches("110")) {
            instruction = "sw";
        }

        String dest_reg = toHex.substring(3, 6);

            if (dest_reg.matches("000")) {                       //decoding destination register
                dest_reg = "RA";
            } else if (dest_reg.matches("001")) {
                dest_reg = "RB";
            } else if (dest_reg.matches("010")) {
                dest_reg = "RC";
            } else if (dest_reg.matches("011")) {
                dest_reg = "RD";
            } else if (dest_reg.matches("100")) {
                dest_reg = "RE";
            } else if (dest_reg.matches("101")) {
                dest_reg = "RF";
            } else if (dest_reg.matches("110")) {
                dest_reg = "RG";
            } else if (dest_reg.matches("111")) {
                dest_reg = "RH";
            }

        String src_reg_sel = toHex.substring(6, 9);

            if(src_reg_sel.matches("000")) {                       //decoding target register
                src_reg_sel = "RA";
            }else if (src_reg_sel.matches("001")) {
                src_reg_sel = "RB";
            }else if (src_reg_sel.matches("010")) {
                src_reg_sel = "RC";
            }else if (src_reg_sel.matches("011")) {
                src_reg_sel = "RD";
            }else if (src_reg_sel.matches("100")) {
                src_reg_sel = "RE";
            }else if (src_reg_sel.matches("101")) {
                src_reg_sel = "RF";
            }else if (src_reg_sel.matches("110")) {
                src_reg_sel = "RG";
            }else if (src_reg_sel.matches("111")) {
                src_reg_sel = "RH";
            }

        //Will be implemented with future instructions

        String targ_reg = toHex.substring(9, 12);


            if (targ_reg.matches("000")) {                       //decoding target register
                targ_reg = "RA";
            } else if (targ_reg.matches("001")) {
                targ_reg = "RB";
            } else if (targ_reg.matches("010")) {
                targ_reg = "RC";
            } else if (targ_reg.matches("011")) {
                targ_reg = "RD";
            } else if (targ_reg.matches("100")) {
                targ_reg = "RE";
            } else if (targ_reg.matches("101")) {
                targ_reg = "RF";
            } else if (targ_reg.matches("110")) {
                targ_reg = "RG";
            } else if (targ_reg.matches("111")) {
                targ_reg = "RH";
            }

        String imm_val = toHex.substring(12, 20);

        if( instruction.matches("lw") ) {
            result = instruction + " " + dest_reg + " " + targ_reg;     //printing lw decoded result
            return result;
        } else if( instruction.matches("sw") ) {
            result = instruction + " " + src_reg_sel + " " + targ_reg;  //sw decoded result
            return result;
        } else if( instruction.matches("add") ) {
            result = instruction + " " + dest_reg + " " + src_reg_sel + " " + targ_reg;     //add decoded result
            return result;
        } else if( instruction.matches("addi") ) {
            result = instruction + " " + dest_reg + " " + src_reg_sel + " " + imm_val;      //addi decoded result
            return result;
        } else if( instruction.matches("halt") ) {          //halt decoded result
            result = instruction;
            return result;
        } else if( instruction.matches("mul") ) {           //mul decoded result
            result = instruction + " " + dest_reg + " " + src_reg_sel;
            return result;
        } else if( instruction.matches( "beq") ) {          //beq decoded result
            result = instruction + " " + src_reg_sel + " " + targ_reg + " " + imm_val;
            return result;
        }



        return result;


    }

    /** LOAD WORD INSTRUCTION **/
    public int[] lw(int[] comp, int[] data_mem, String dest_reg, String start_reg) {

        this.setreg(comp, dest_reg, data_mem[comp[this.checkReg(start_reg)]]);

        return comp;

    }

    /** STORE WORD INSTRUCTION **/
    public int[] sw(int[] comp, int[] data_mem, String target, String start) {

        data_mem[comp[this.checkReg(target)]] = comp[this.checkReg(start)];

        return data_mem;
    }

    /** ADD INSTRUCTION **/
    public int[] add(int[] comp, String dest_reg, String start_reg, String target_reg) {

        int temp = comp[this.checkReg(start_reg)] + comp[this.checkReg(target_reg)];
        this.setreg(comp, dest_reg, temp);

        return comp;
    }

    /** ADD IMMEDIATE INSTRUCTION **/
    public int[] addi(int[] comp, String dest_reg, String start_reg, String imm_val) {

        int actual_imm_val = 256;           //max value for 8-bit data word

        for(int parse = 0; parse < imm_val.length(); parse++) {     //decoding immediate value
            if( imm_val.substring(parse).matches("1")) {
                actual_imm_val = actual_imm_val / 2;
            }
        }

        int temp = comp[this.checkReg(start_reg)] + actual_imm_val;
        this.setreg(comp, dest_reg, temp);

        return comp;
    }

    /** INVERT INSTRUCTION **/
    public int[] inv(int[] comp, String dest_reg, String start_reg) {

        int actual_inv_val = ~comp[this.checkReg(start_reg)];       //stores inverted value of start_reg
        this.setreg(comp, dest_reg, actual_inv_val);

        return comp;
    }

    /** HALT INSTRUCTION **/
    //this is handled in the main class (cs3421_emul.java)

    /** MULTIPLY INSTRUCTION **/
    public int[] mul(int[] comp, String dest_reg, String start_reg) {

        String temp_val = Integer.toBinaryString(comp[this.checkReg(start_reg)]);

        if( temp_val.length() < 8 ) {           //if comp[start_reg] < 256
            String add_zero = "0";
            for( int i = temp_val.length(); i <= 8; i++ ) {     //iterate until 8 values are in temp_val
                temp_val = add_zero + temp_val;                     //add zero to the front of temp_val
            }
        }

        String upper_bits = temp_val.substring(0, 3);
        String lower_bits = temp_val.substring(4, 7);

        int product = Integer.valueOf(upper_bits) * Integer.valueOf(lower_bits);
        this.setreg(comp, dest_reg, product);

        return comp;
    }

    /** BRANCH-IF-EQUAL INSTRUCTION **/
    public int[] beq(int[] comp, String start_reg, String target_reg, String imm_val) {
        if( comp[this.checkReg(start_reg)] == comp[this.checkReg(target_reg)] ) {
            this.setreg(comp, "PC", Integer.valueOf(imm_val));
        }

        return comp;
    }

    public boolean cpu_inst_run(int [] cpu_storage, int [] inst_storage, int [] mem_storage, int increment) {

        boolean halt_called = false;
        inst_memory inst = new inst_memory();
        data_memory mem = new data_memory();
        String instruction;

        /** Instruction (clock tick) loop **/
        for (int k = 0; k < increment; k++) {
            cpu_storage = this.tickReceive(cpu_storage, inst_storage);
            instruction = this.getNextInstr();

            if (instruction.substring(0, 2).matches("lw") || instruction.substring(0, 2).matches("sw")) {
                if (instruction.substring(0, 2).matches("lw")) {
                    String dest_reg = instruction.substring(3, 5);
                    String from_reg = instruction.substring(6, 8);
                    cpu_storage = this.lw(cpu_storage, mem_storage, dest_reg, from_reg);
                }

                if (instruction.substring(0, 2).matches("sw")) {
                    String target_reg = instruction.substring(3, 5);
                    String start_reg = instruction.substring(6, 8);
                    mem_storage = this.sw(cpu_storage, mem_storage, target_reg, start_reg);
                }

                if (k % 5 == 0) {                                  //runs every 5 ticks
                    mem_storage = mem.tickReceive(mem_storage, increment);
                }
                inst_storage = inst.tickReceive(inst_storage, increment);
            } else if (instruction.substring(0, 2).matches("add") || instruction.substring(0, 2).matches("addi") ||
                    instruction.substring(0, 2).matches("inv") || instruction.substring(0, 2).matches("halt")) {

                if (instruction.substring(0, 2).matches("add")) {
                    String dest_reg = instruction.substring(3, 5);
                    String src_reg = instruction.substring(6, 8);
                    String target_reg = instruction.substring(9, 11);
                    cpu_storage = this.add(cpu_storage, dest_reg, src_reg, target_reg);
                }

                if (instruction.substring(0, 2).matches("addi")) {
                    String dest_reg = instruction.substring(3, 5);
                    String src_reg = instruction.substring(6, 8);
                    String imm_val = instruction.substring(9, 16);
                    cpu_storage = this.addi(cpu_storage, dest_reg, src_reg, imm_val);
                }

                if (instruction.substring(0, 2).matches("inv")) {
                    String dest_reg = instruction.substring(3, 5);
                    String src_reg = instruction.substring(6, 8);
                    cpu_storage = this.inv(cpu_storage, dest_reg, src_reg);
                }

                if (instruction.substring(0, 2).matches("halt")) {
                    halt_called = true;
                }
                //runs every 1 ticks
            } else if (instruction.substring(0, 2).matches("mul")) {
                if (instruction.substring(0, 2).matches("mul")) {
                    String dest_reg = instruction.substring(3, 5);
                    String src_reg = instruction.substring(6, 8);
                    cpu_storage = this.mul(cpu_storage, dest_reg, src_reg);
                }

                if (k % 2 == 0) {
                    mem_storage = mem.tickReceive(mem_storage, increment);
                }
            } else if (instruction.substring(0, 2).matches("beq")) {
                int pc_before_beq = cpu_storage[this.checkReg("PC")];
                if (instruction.substring(0, 2).matches("beq")) {
                    String src_reg = instruction.substring(3, 5);
                    String target_reg = instruction.substring(6, 8);
                    String imm_val = instruction.substring(9, 16);
                    cpu_storage = this.beq(cpu_storage, src_reg, target_reg, imm_val);
                }

                if (pc_before_beq == cpu_storage[this.checkReg("PC")]) {           //if branch was taken
                    if (k % 2 == 0) {                                                    //runs every 2 ticks
                        mem_storage = mem.tickReceive(mem_storage, increment);
                    }
                }

                tc += increment;    //increment cpu tick counter

                if( halt_called ) {
                    return true;    //halt was called
                }
            }
        }
        return false;   //halt was NOT called
    }

}
