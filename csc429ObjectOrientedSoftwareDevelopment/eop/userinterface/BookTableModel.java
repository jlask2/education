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
//************************************************************
//specify the package
package userinterface;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class BookTableModel extends AbstractTableModel implements TableModel{
	private Vector myState;

	public BookTableModel(Vector bookData){
		myState = bookData;
	}

	public int getColumnCount(){
		return 5;
	}

	public int getRowCount(){
		return myState.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex){
		Vector book = (Vector)myState.elementAt(rowIndex);
		return "  " + book.elementAt(columnIndex);
	}

	public String getColumnName(int columnIndex){
		if(columnIndex == 0){
			return "     Barcode";
		}
		else if(columnIndex == 1){
			return "    Title";
		}
		else if(columnIndex == 2){
			return "    Author";
		}
		else if(columnIndex == 3){
			return " Discipline";
		}
		else if (columnIndex == 4){
			return "       Status";
		}
		else{
			return "??";
		}
	}
}