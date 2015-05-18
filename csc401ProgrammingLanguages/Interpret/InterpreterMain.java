/**
 * TestGit
 */
package hexInterpreter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * @author Jay
 * Class interpreter Main 
 * where main method resides
 */
public class InterpreterMain {

	/**
	 * @param args
	 * construct Prompt object
	 */
  static Prompt prompt;
  
  /**
   * Main Method - calls start method 
   */
  public static void main(String[] args) {
	  SwingUtilities.invokeLater(new Runnable()
	  {
		  public void run(){
			  start();
		  }
	  });
  }
  
  /**
   * start method to help initialize
   * the file readers and writers
   * calls prompt to create the GUI
   */
  public static void start(){
	  //BufferedReader buffIn = null;
	  BufferedWriter buffOut = null;
	  //FileReader fileIn = null;
	  FileWriter fileOut = null;
	  
	  prompt = new Prompt();
	  
	  try {
		   buffOut = new BufferedWriter(fileOut = new FileWriter("C:\\Users\\Jay\\Desktop\\Education\\csc401\\Assignments\\Assignment1\\outFile.txt"));
	  } catch (IOException e) {
		   e.printStackTrace();
	  } 
  }
  
  /**
   * runProgram method -
   * contains logic to handle the 
   * case instructions
   */
  public static void runProgram(){
   final OpcodeInstructions opcode;	
   
   BufferedReader buffIn = null;
   BufferedWriter buffOut = null;
   FileReader fileIn = null;
   FileWriter fileOut = null;
 
   prompt.getText();//extract the text from the JTextArea
   
   try {
	 buffIn = new BufferedReader(fileIn = new FileReader("C:\\Users\\Jay\\Desktop\\Education\\csc401\\Assignments\\Assignment1\\inFile.txt"));
   } catch (FileNotFoundException e2) {
		e2.printStackTrace();
   }
   
   try {
	   buffOut = new BufferedWriter(fileOut = new FileWriter("C:\\Users\\Jay\\Desktop\\Education\\csc401\\Assignments\\Assignment1\\outFile.txt"));
   } catch (IOException e) {
	   e.printStackTrace();
   }
   
   /*DEBUG*///int y = 0;
   String line = "";   
   opcode = new OpcodeInstructions(prompt.getPcTextFieldValue());//get the value from the JTextField to store in the pc
  
	 try {
			while((line = buffIn.readLine()) != null){
				/*DEBUG*///System.out.println(y++);
				opcode.loadInitialMemory(line);
			}
			
			opcode.printMemory();
			opcode.printRegisters();
	       
				
			while(opcode.Memory[opcode.getNextInstruction()] != null){//loop until no more instructions
					
				   char firstChar = opcode.Memory[opcode.getNextInstruction()].charAt(0);
				   /*DEBUG*///System.out.println(opcode.Memory[i]  + ": " +  firstChar);
				   
			       switch(firstChar){
					   case '0':
						   System.out.println("\nCase: 0 - halt - "+opcode.Memory[opcode.getNextInstruction()]);
						   opcode.printMemory();
						   opcode.printRegisters();
						   opcode.halt();
						   break;
					   case '1':
						   System.out.println("\nCase: 1 - add - "+opcode.Memory[opcode.getNextInstruction()]);
						   opcode.format1(opcode.Memory[opcode.getNextInstruction()]);
						   opcode.add(opcode.getdesReg(), opcode.getsourceSReg(), opcode.getsourceTReg());
						   break;
					   case '2':
						   System.out.println("\nCase: 2 - sub - "+opcode.Memory[opcode.getNextInstruction()]);
						   opcode.format1(opcode.Memory[opcode.getNextInstruction()]);
						   opcode.subtract(opcode.getdesReg(), opcode.getsourceSReg(), opcode.getsourceTReg());
						   break;
					   case '3':
						   System.out.println("\nCase: 3 - and - "+opcode.Memory[opcode.getNextInstruction()]);
						   opcode.format1(opcode.Memory[opcode.getNextInstruction()]);
						   opcode.and(opcode.getdesReg(), opcode.getsourceSReg(), opcode.getsourceTReg());
						   break;
					   case '4':
						   System.out.println("\nCase: 4 - xor - "+opcode.Memory[opcode.getNextInstruction()]);
						   opcode.format1(opcode.Memory[opcode.getNextInstruction()]);
						   opcode.xor(opcode.getdesReg(), opcode.getsourceSReg(), opcode.getsourceTReg());
						   break;
					   case '5':
						   System.out.println("\nCase: 5 - leftShift - "+opcode.Memory[opcode.getNextInstruction()]);
						   opcode.format1(opcode.Memory[opcode.getNextInstruction()]);
						   opcode.leftShift(opcode.getdesReg(), opcode.getsourceSReg(), opcode.getsourceTReg());
						   break;
					   case '6':
						   System.out.println("\nCase: 6 - rightShift - "+opcode.Memory[opcode.getNextInstruction()]);
						   opcode.format1(opcode.Memory[opcode.getNextInstruction()]);
						   opcode.rightShift(opcode.getdesReg(), opcode.getsourceSReg(), opcode.getsourceTReg());
						   break;
					   case '7':
						   System.out.println("\nCase: 7 - loadaddress - "+opcode.Memory[opcode.getNextInstruction()]);
						   opcode.format2(opcode.Memory[opcode.getNextInstruction()]);
						   opcode.loadAddress(opcode.getdesReg2(), opcode.getaddress());
						   break;
					   case '8':
						   System.out.println("\nCase: 8 - load - "+opcode.Memory[opcode.getNextInstruction()]);
						   opcode.format2(opcode.Memory[opcode.getNextInstruction()]);
						   opcode.load(opcode.getdesReg2(), opcode.getaddress());
						   break;
					   case '9':
						   System.out.println("\nCase: 9 - store - "+opcode.Memory[opcode.getNextInstruction()]);
						   opcode.format2(opcode.Memory[opcode.getNextInstruction()]);
						   opcode.store(opcode.getdesReg2(), opcode.getaddress());
						   break;
					   case 'A':
						   System.out.println("\nCase: 10 - loadindirect - "+opcode.Memory[opcode.getNextInstruction()]);
						   opcode.format1(opcode.Memory[opcode.getNextInstruction()]);
						   opcode.loadIndirect(opcode.getdesReg(), opcode.getsourceTReg());
						   break;
					   case 'B':
						   System.out.println("\nCase: 11 - storeindirect - "+opcode.Memory[opcode.getNextInstruction()]);
						   opcode.format1(opcode.Memory[opcode.getNextInstruction()]);
						   opcode.storeIndirect(opcode.getdesReg(), opcode.getsourceTReg());
						   break;
					   case 'C':
						   System.out.println("\nCase: 12 - branch0 - "+opcode.Memory[opcode.getNextInstruction()]);
						   opcode.format2(opcode.Memory[opcode.getNextInstruction()]);
						   opcode.branchZero(opcode.getdesReg2(), opcode.getaddress());
						   break;
					   case 'D':
						   System.out.println("\nCase: 13 - branchpos - "+opcode.Memory[opcode.getNextInstruction()]);
						   opcode.format2(opcode.Memory[opcode.getNextInstruction()]);
						   opcode.branchPos(opcode.getdesReg2(), opcode.getaddress());
						   break;
					   case 'E':
						   System.out.println("\nCase: 14 - jump - "+opcode.Memory[opcode.getNextInstruction()]);
						   opcode.format2(opcode.Memory[opcode.getNextInstruction()]);
						   opcode.jump(opcode.getdesReg2());
						   break;
					   case 'F':
						   System.out.println("\nCase: 15 - jumpandlink - "+opcode.Memory[opcode.getNextInstruction()]);
						   opcode.format2(opcode.Memory[opcode.getNextInstruction()]);
						   opcode.jumpAndLink(opcode.getdesReg2(), opcode.getaddress());
						   break;
					
					   default:
							   System.err.println("error");
					       break;
				    }
			        buffOut.write(opcode.Memory[opcode.getNextInstruction()] + "\n");
			}	   
		} catch (IOException e1) {
				e1.printStackTrace();
		}
		
	 	opcode.printMemory();
		opcode.printRegisters();
	 
		try {
			buffIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		   
		try {
			buffOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

/**
 * Class Prompt - Is the frame for the GUI
 * constructs the frame for the GUI  
 */
class Prompt extends JFrame implements ActionListener{
	  
      /**
	 * Data Members - GUI Components
	 */
	  private static final long serialVersionUID = 1L;
	  JButton okButton = new JButton("OK");
      JButton cancelButton = new JButton("Cancel");
      JTextArea jta = new JTextArea();
      JTextPane subPane = new JTextPane();
      JTextField pcTextField = new JTextField();
      JLabel pcLabel = new JLabel("pc start value: ");
      boolean userInputIsValid = false; // check for vallid input, not fully implemented
      
      /**
       * Prompt Constructor - calls the create JDialogPrompt method 
       */
	  public Prompt(){
		  createJDialogPrompt();
	  }
	  
	  /**
	   * createJDialogPrompt method -
	   * calls the createAndShowGUI method
	   */
	  public void createJDialogPrompt(){
		  createAndShowGUI();
	  }
	  
	  /**
	   * createAndShowGUI method -
	   * handles the set up of the components in the GUI
	   */
	  public void createAndShowGUI(){
	      this.setTitle("Interpreter for the AL language as defined by M");
		  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  this.setLocationRelativeTo(null);
		  this.setLayout(new FlowLayout());
		  this.setSize(500, 300);
		  this.setVisible(true);
		  
		  JPanel initialPrompt = new JPanel();
		  
		  initialPrompt.setSize(500, 300);
		  initialPrompt.setLayout(new GridBagLayout());
		  GridBagConstraints gbc = new GridBagConstraints();
		  
		  subPane.setText("Enter as many as 256 lines of 16 bit \nhexadecimal data and instructions, \n" +
		  						"followed by a 8 bit hexadecimal address \nin the text field." + 
		  						"corresponding to the starting \ncontents of the program counter:");
		  subPane.setEditable(false);
		  gbc.fill = GridBagConstraints.BOTH;
		  gbc.anchor = GridBagConstraints.NORTH;
		  gbc.gridx = 0;
		  gbc.gridy = 0;
		  gbc.gridwidth = 3;
		  gbc.gridheight = 1;
		  gbc.weightx = 1;
		  
		  initialPrompt.add(subPane, gbc);
		  jta.setPreferredSize(new Dimension(100, 400));
		  jta.setFocusable(true);
		  gbc.fill = GridBagConstraints.BOTH;
		  gbc.anchor = GridBagConstraints.CENTER;
		  gbc.gridx = 0;
		  gbc.gridy = 1;
		  gbc.weightx = 1;
		  gbc.weighty = 2;
		  
		  initialPrompt.add(jta, gbc);
		  
		  gbc.fill = GridBagConstraints.BOTH;
		  gbc.anchor = GridBagConstraints.CENTER;
		  gbc.gridx = 0;
		  gbc.gridy = 2;
		  gbc.gridwidth = 1;
		  gbc.gridheight = 1;
		  gbc.weightx = 0.5;
		  gbc.weighty = 0.25;
		  initialPrompt.add(pcLabel, gbc);
		  
		  gbc.fill = GridBagConstraints.BOTH;
		  gbc.anchor = GridBagConstraints.CENTER;
		  gbc.gridx = 1;
		  gbc.gridy = 2;
		  gbc.gridwidth = 1;
		  gbc.gridheight = 1;
		  gbc.weightx = 0.5;
		  gbc.weighty = 0.25;
		  initialPrompt.add(pcTextField, gbc);
		  
		  gbc.fill = GridBagConstraints.BOTH;
		  gbc.anchor = GridBagConstraints.SOUTHWEST;
		  gbc.gridx = 0;
		  gbc.gridy = 3;
		  gbc.gridwidth = 1;
		  gbc.gridheight = 1;
		  gbc.weightx = 0.5;
		  gbc.weighty = 0.25;
		  
		  okButton.addActionListener(this);
		  initialPrompt.add(okButton, gbc);
		  
		  gbc.fill = GridBagConstraints.BOTH;
		  gbc.anchor = GridBagConstraints.SOUTHEAST;
		  gbc.gridx = 1;
		  gbc.gridy = 3;
		  gbc.gridwidth = 1;
		  gbc.gridheight = 1;
		  gbc.weightx = 0.5;
		  gbc.weighty = 0.25;
		  cancelButton.addActionListener(this);
		  initialPrompt.add(cancelButton, gbc);
		  
		  initialPrompt.setVisible(true);
		  
		  this.add(initialPrompt);
		  this.pack();
		  
		  jta.grabFocus();
	}
	
	/**
	 * getPcTextFieldValue method -
	 * access the value input in the pcTextField
	 */
	public String getPcTextFieldValue(){
		return pcTextField.getText();
	}
	  
	/**
	 * getText method - 
	 * access the text from JTextArea 
	 */
	public void getText(){
	 try{
	  BufferedWriter buffInitialFile = null;
      FileWriter initialFile = null;
	  
		  if(userInputIsValid){
			  buffInitialFile = new BufferedWriter(initialFile = new FileWriter("C:\\Users\\Jay\\Desktop\\Education\\csc401\\Assignments\\Assignment1\\inFile.txt"));
			  buffInitialFile.write(jta.getText());
			  buffInitialFile.close();
			}else{
			  System.out.println("invalid input");
			  System.exit(0);
		  }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	  * actionPerformed method -
	  * needed implemented interface to
	  * handle the components in the GUI
	  */
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == okButton){
			userInputIsValid = true;
			InterpreterMain.runProgram();
		}else if(arg0.getSource() == cancelButton){
			System.out.println("exiting");
			System.exit(0);
		}
	}	
}