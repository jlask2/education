// tabs=4
// 
//************************************************************
//	COPYRIGHT 2014 Sandeep Mitra and Stephanie Cruz
//    The College at Brockport, State University of New York. -
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot
// be reproduced, copied, or used in any shape or form without
// the express written consent of The College at Brockport.
//specify the package
package userinterface;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class StudentBorrowerTableModel extends AbstractTableModel implements TableModel{
	private Vector<Vector<String>> myState;

	public StudentBorrowerTableModel(Vector<Vector<String>> studentBorrowerData){
		myState = studentBorrowerData;
	}

	public int getColumnCount(){
		return 6;
	}

	public int getRowCount(){
		return myState.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex){
		Vector<String> studentBorrower = (Vector<String>)myState.elementAt(rowIndex);
		return "  " + studentBorrower.elementAt(columnIndex);
	}

	public String getColumnName(int columnIndex){
		if(columnIndex == 0){
			return "     BannerID";
		}
		else if(columnIndex == 1){
			return "    First Name";
		}
		else if(columnIndex == 2){
			return "    Last Name";
		}
		else if(columnIndex == 3){
			return " Phone Number";
		}
		else if (columnIndex == 4){
			return "       Email";
		}
		else if(columnIndex == 5){
			return "      Status";
		}
		else{
			return "??";
		}
	}
}