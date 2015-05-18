package hexInterpreter;

import java.io.IOException;
import java.math.BigInteger;

final public class OpcodeInstructions {
	/**
	 * Data members -
	 * initialize some variables 
	 */
    final String[] Registers;
    final String[] Memory;
    
    static int pc = 0;
    static int m = 0;
    
    //final 
    static int sourceSReg = 0;
    //final 
    static int sourceTReg = 0;
    //final 
    static int desReg = 0;
    
    //final 
    static String desReg2 = "";
    //final 
    static String memaddress = "";
    //final 
    static String sourceTReg2 = "";
    
	/**
	 * Constructor
	 */
	public OpcodeInstructions(String address){
    	Registers = new String[16];
    	Memory = new String[256];
    	pc = hexToDec(address);
    	Registers[0] = "0";
    	m = 0;
	}
	
	/**
	 * Opcode 0, halt, format1
	 * exit
	 */
	public void halt(){
		ensureZero();
		System.exit(0);
	}
	
	/**
	 * Opcode 1, add, format1
	 *  R[d] <- R[s] + R[t]
	 */
	public void add(int d, int s, int t){
		String sString = Registers[s];
		String tString = Registers[t];
		int sum = hexToDec(sString) + hexToDec(tString);
		if(sum <= 65535 && sum >= -65535){
			Registers[d] = Integer.valueOf(sum).toString();
			getNextAddress();
			ensureDesRegDoesNotExceed16(d);
			ensureZero();
		}else{
			ensureDesRegDoesNotExceed16(d);
			ensureZero();
			System.err.println("Overflow-Underflow");
		}
		/*DEBUG*///System.out.println(" " + Registers[d] + " = " + Registers[s]+  " + " + Registers[t] );
	}
	
	/**
	 *  Opcode 2, subtract, format1
	 *  R[d] <- R[s] - R[t]
	 */
	public void subtract(int d, int s, int t){
		String sString = Registers[s];
		String tString = Registers[t];
		int sub = hexToDec(sString) - hexToDec(tString);
		if(sub <= 65535 && sub >= -65535){
			Registers[d] = Integer.valueOf(sub).toString();
			ensureDesRegDoesNotExceed16(d);
			ensureZero();
			getNextAddress();
		}else{
			ensureDesRegDoesNotExceed16(d);
			ensureZero();
			System.err.println("Overflow-Underflow");
		}
		/*DEBUG*///System.out.println(" " + Registers[d] + " = " + Registers[s]+  " - " + Registers[t] );
	}
	
	/**
	 *  Opcode 3, and, format1
	 *  R[d] <- R[s] & R[t]
	 */
	public void and(int d, int s, int t){
		String sString = Registers[s];
		String tString = Registers[t];
		int andBitWise = hexToDec(sString) & hexToDec(tString);
		Registers[d] = Integer.valueOf(andBitWise).toString();
		ensureDesRegDoesNotExceed16(d);
		ensureZero();
		getNextAddress();
		/*DEBUG*///System.out.println(" " + Registers[d] + " = " + Registers[s]+  " & " + Registers[t] );
		}
	
	/**
	 *  Opcode 4, xor, format1
	 *  R[d] <- R[s] ^ R[t]
	 */
	public void xor(int d, int s, int t){
		String sString = Registers[s];
		String tString = Registers[t];
		int xorBitWise = hexToDec(sString) ^ hexToDec(tString);
		Registers[d] = Integer.valueOf(xorBitWise).toString();
		ensureDesRegDoesNotExceed16(d);
		ensureZero();
		getNextAddress();
		/*DEBUG*///System.out.println(" " + Registers[d] + " = " + Registers[s]+  " ^ " + Registers[t] );
	}
	
	/**
	 *  Opcode 5, left shift, format1
	 *  R[d] <- R[s] << R[t]
	 */
	public void leftShift(int d, int s, int t){
		String sString = Registers[s];
		String tString = Registers[t];
		int lsBitWise = hexToDec(sString) << hexToDec(tString);
		Registers[d] = Integer.valueOf(lsBitWise).toString();
		ensureDesRegDoesNotExceed16(d);
		ensureZero();
		getNextAddress();
		/*DEBUG*///System.out.println(" " + Registers[d] + " = " + Registers[s]+  " << " + Registers[t] );
	}
	
	/**
	 *  Opcode 6, right shiift, format1
	 *  R[d] <- R[s] >> R[t]
	 */
	public void rightShift(int d, int s, int t){
		String sString = Registers[s];
		String tString = Registers[t];
		int lsBitWise = hexToDec(sString) >> hexToDec(tString);
		Registers[d] = Integer.valueOf(lsBitWise).toString();
		ensureDesRegDoesNotExceed16(d);
		ensureZero();
		getNextAddress();
		/*DEBUG*///System.out.println(" " + Registers[d] + " = " + Registers[s]+  " >> " + Registers[t] );
	}
	
	/**
	 *  Opcode 7, load address, format2
	 *  R[d] <- addr.
	 */
	public void loadAddress(String des, String address){
		int d = hexToDec(des); 
		Registers[d] = address;
		ensureDesRegDoesNotExceed16(d);
		ensureZero();
		getNextAddress();
		/*DEBUG*///System.out.println("d: " + d);
		/*DEBUG*///System.out.println("Registers[d]: " + Registers[d] + " = address: " + address);
	}
	
	/**
	 *  Opcode 8, load, format2
	 *  R[d] <- mem[addr]
	 */
	public void load(String des, String address){
		int addr = Integer.parseInt(address);
		int d = hexToDec(des);
		if(address.equals("255")){
			try {
				System.out.println("Enter in a 16 bit Hexadecimal value to load into register " + d + ": ");
				int ascii = 0;
				String userInput = "";
				while((ascii = System.in.read()) != '\n'){
					/*DEBUG*///System.out.println("ascii: "+ascii + ", charascii: " +(char)ascii + ", asciiToHexString: " + Integer.toHexString(ascii));
					userInput += (char)ascii;
				}
				userInput = userInput.substring(0, 4);
				/*DEBUG*///System.out.println("userInput: " + userInput);
				Memory[addr] = userInput;
				/*DEBUG*///System.out.println("Memory[addr]: " + Memory[addr] + ", addr: " + addr + ", d: " + d);
				Registers[d] = Memory[addr];
				ensureDesRegDoesNotExceed16(d);
				ensureZero();
				getNextAddress();
				/*DEBUG*///System.out.println("addr: " + addr + ", d: " + d);
				/*DEBUG*///System.out.println("Registers[d]: " + Registers[d] + " = Memory[addr]: " + Memory[addr]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			Registers[d] = Memory[addr];
			ensureDesRegDoesNotExceed16(d);
			ensureZero();
			getNextAddress();
			/*DEBUG*///System.out.println("addr: " + addr + ", d: " + d);
			/*DEBUG*///System.out.println("Registers[d]: " + Registers[d] + " = Memory[addr]: " + Memory[addr]);
		}
	}
	
	/**
	 *  Opcode 9, store, format2
	 *  mem[addr] <- R[d]
	 */
	public void store(String des, String address){
		int addr = Integer.parseInt(address);
		int d = hexToDec(des);
		Memory[addr] = Registers[d];
		if(address.equals("255")){
			System.out.println("Standard Output: Registers["+d+"]: "+Registers[d]);
		}
		ensureDesRegDoesNotExceed16(d);
		ensureZero();
		getNextAddress();
	}
	
	/**
	 *  Opcode A (10), load indirect, format1
	 *  R[d] <-  mem[R[t]]
	 */
	public void loadIndirect(int d, int t){
		Registers[d] = Memory[Integer.parseInt(Registers[t])];
		ensureDesRegDoesNotExceed16(d);
		ensureZero();
		getNextAddress();
		/*DEBUG*///System.out.println("t: " + t + ", d: " + d);
		/*DEBUG*///System.out.println("Registers[d]: " + Registers[d] + " = Memory[Integer.parseInt(Registers[t])]: " + Memory[Integer.parseInt(Registers[t])]);
	}
	
	/**
	 *  Opcode B (11) store indirect, format1
	 *  mem[R[t]] <- R[d]
	 */
	public void storeIndirect (int d, int t){ 
		Memory[Integer.parseInt(Registers[t])] = Registers[d];
		ensureDesRegDoesNotExceed16(d);
		ensureZero();
		getNextAddress();
		/*DEBUG*///System.out.println("t: " + t + ", d: " + d); 
		/*DEBUG*///System.out.println("Integer.parseInt(Registers[t]): "+Integer.parseInt(Registers[t])+", Memory[Integer.parseInt(Registers[t])]: " + Memory[Integer.parseInt(Registers[t])] + " = Registers[d]: " + Registers[d]);
	}
	
	/**
	 *  Opcode C (12), branch zero, format2
	 *  if (R[d] == 0) pc <- addr 
	 */
	public void branchZero(String des, String address){
		int d = hexToDec(des);
		/*DEBUG*///System.out.println("address: " + address + ", d: " + d);
		if(Integer.parseInt(Registers[d]) == 0){
			ensureDesRegDoesNotExceed16(d);
			ensureZero();
			pc = Integer.parseInt(address);
			/*DEBUG*///System.out.println("pc - 1: " + pc);
		}else{
			ensureDesRegDoesNotExceed16(d);
			ensureZero();
			pc = getNextAddress();
		}
	}
	
	/**
	 *  Opcode D (13), branch positive, format2
	 *  if (R[d] > 0) pc <- addr
	 */
	public void branchPos(String des, String address){
		int d = hexToDec(des);
		/*DEBUG*///System.out.println("d: " + d + ", address: " + address);
		if(Integer.parseInt(Registers[d]) > 0){
			ensureDesRegDoesNotExceed16(d);
			ensureZero();
			pc = Integer.parseInt(address);
		}else{
			ensureDesRegDoesNotExceed16(d);
			ensureZero();
			pc = getNextAddress();
		}
	}
	
	/**
	 *  Opcode E (14), jump register, format2
	 *  pc < - R[d]
	 */
	public void jump(String des){
		int d = hexToDec(des);
		ensureDesRegDoesNotExceed16(d);
		ensureZero();
		pc = Integer.parseInt(Registers[d]);
		/*DEBUG*///System.out.println("pc: " + pc + ", d: " + d);
	}
	
	/**
	 *  Opcode F (15), jump and link, format2
	 *  R[d]  <- pc; pc <- addr
	 */
	public void jumpAndLink(String des, String address){
		int d = hexToDec(des);
		Registers[d] = Integer.valueOf(pc).toString();
		ensureDesRegDoesNotExceed16(d);
		ensureZero();
		pc = Integer.parseInt(address);
	    /*DEBUG*///System.out.println("pc: " + pc + ", d: " + d + ", address: " + address + ", Registers[d]: " + Registers[d]);
	    
	}
	
	/**
	 * loadInitialMemory method - 
	 * load the memory  
	 */
	public void loadInitialMemory(String line){
		Memory[m] = line;
		//System.out.println(m + ": " + Memory[m]);
		m++;
	}
	
	/**
	 * Integrity method 
	 */
	public void ensureZero(){
		if(Registers[0] != "0"){
			Registers[0] = "0";
			System.out.println("Register[0] reset to 0. This may cause issues in the program!");
		}
	}
	
	/**
	 * Integrity method 
	 */
	public void ensureDesRegDoesNotExceed16(int des){
		int d = des;
		if((hexToDec(Registers[d]) > 65535) || (hexToDec(Registers[d]) < -65535)){
			System.err.println("The value in Register["+d+"] exceeds 16 bit hexadecimal value");
		}
	}
	
	/**
	 * print method 
	 */
	public void printMemory(){
		for(int p = 0; p < 256; p++){
			if(Memory[p] != null){
				System.out.println("Memory Array @ "+ p +": " + Memory[p]);
			}
		}
	}
	
	/**
	 * print method 
	 */
	public void printRegisters(){
		for(int p = 0; p < 16; p++){
			System.out.println("Registers Array @ "+ p +": " + Registers[p]);
		}
	}
	
	/**
	 * Mutator method 
	 */
	public int hexToDec(String hex){
		/*DEBUG*///System.out.println("Inside hexToDec method: " + hex + "| " + new BigInteger(hex, 16));
		return new BigInteger(hex, 16).intValue();
	}
	
	/**
	 * Mutator method 
	 */
	public String decToHex(int dec){
		return Integer.toHexString(dec);
	}
	
	/**
	 * getNextAddress method - 
	 *  increment the pc to the next address by 1
	 */
	public int getNextAddress(){
	   if(pc != 256){
		   /*DEBUG*///System.out.println("pc - 1: " + pc);
		   return pc++; 
	   }else{
		   return pc = 0;
	   }
	}
	
	/**
	 * Accessor method - 
	 *  
	 */
	public int getNextInstruction(){
		/*DEBUG*///System.out.println("getNextInstruction, pc: " + pc);
		return pc;
	}
	
	/**
	 * format1 method - 
	 * extract the correct index for different opcodes 
	 */
	public void format1(String line){
		//int 
		desReg = hexToDec(line.substring(1, 2));
		//int 
		sourceSReg = hexToDec(line.substring(2, 3));
		//int 
		sourceTReg = hexToDec(line.substring(3, 4));
	}
	
	/**
	 * format2 method - 
	 * extract the correct index for different opcodes 
	 */
	public void format2(String line){
		//String 
		desReg2 = line.substring(1, 2);
		//String 
		memaddress = Integer.valueOf(hexToDec(line.substring(2, 4))).toString();
		/*DEBUG*///System.out.println("memaddress: " + memaddress);
	}
	
	/**
	 * Accessor method 
	 */
	public int getsourceSReg(){
		return sourceSReg;
	}
	
	/**
	 * Accessor method 
	 */
	public int getsourceTReg(){
		return sourceTReg;
	}
	
	/**
	 * Accessor method 
	 */
	public int getdesReg(){
		return desReg;
	}
	
	/**
	 * Accessor method 
	 */
	public String getdesReg2(){
		return desReg2;
	}
	
	/**
	 * Accessor method 
	 */
	public String getaddress(){
		return memaddress;
	}
	
	/**
	 * Accessor method 
	 */
	public String getsourceTReg2(){
		return sourceTReg2;
	}
}