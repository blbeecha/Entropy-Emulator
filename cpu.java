/**
 * Author: Brendan Beecham
 * Course: CS3421 Comp Org
 * Assignment: Implement an instruction memory to hold all instructions
 * 		from now on. Additionally, implement lw and sw instructions
 * 		to be completed every 5 clock ticks.
 *
 * Component: cpu.java
 */


public class cpu {
    public cpu() {
        exec = false;
        waitState = 5;
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

    private int checkReg(String reg) {
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

        if(imm_val.matches("00000000")) {}          //decoding immediate value

        if( instruction.matches("lw") ) {
            result = instruction + " " + dest_reg + " " + targ_reg;     //printing lw decoded result
            return result;
        } else if( instruction.matches("sw") ) {
            result = instruction + " " + src_reg_sel + " " + targ_reg;  //sw decoded result
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



}
