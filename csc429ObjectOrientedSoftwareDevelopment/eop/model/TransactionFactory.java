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
package model;

//system imports
import java.util.Stack;
import java.util.Vector;

import javax.swing.JFrame;

public class TransactionFactory{

	/**
	 * @param this string key identifies the type of transaction
	 * @param this is the logged in worker
	 * @param this is our fresh history stack for this transaction
	 * @return a transaction based on the given key, and passing the worker and history
	 * @throws Exception
	 */
	public static TransactionL createTransaction(String transType, Worker worker) throws Exception{
		TransactionL retValue = null;
		//DEBUG System.out.println("trans type "+transType);
		if (transType.equals("ModifyWorker")){
			retValue = new ModifyWorkerTransaction(worker);
		}
		if(transType.equals("AddWorker")){
			retValue = new AddWorkerTransaction(worker);
		}
		if(transType.equals("AddBook")){
			retValue = new AddBookTransaction(worker);
		}
		if(transType.equals("AddStudentBorrower")){
			retValue = new AddStudentBorrowerTransaction(worker);
		}
		if(transType.equals("ModifyStudentBorrower")){
			retValue = new ModifyStudentBorrowerTransaction(worker);
		}
		if(transType.equals("DeleteWorker")){
			retValue = new DeleteWorkerTransaction(worker);
		}
		if(transType.equals("ModifyBook")){
			retValue = new ModifyBookTransaction(worker);
		}
		if(transType.equals("DeleteBook")){
			retValue = new DeleteBookTransaction(worker);
		}
		if(transType.equals("DeleteStudentBorrower")){
			retValue = new DeleteStudentBorrowerTransaction(worker);
		}
		if(transType.equals("CheckInABook")){
			retValue = new CheckInBooksTransaction(worker);
		}
		if(transType.equals("CheckOutBook")){
			retValue = new CheckOutBookTransaction(worker);
		}
		if(transType.equals("ProcessLostBook")){
			retValue = new ProcessLostBooksTransaction(worker);
		}
		if(transType.equals("ChangePassword")){
			retValue = new ChangePasswordTransaction(worker);
		}
		if(transType.equals("AvailableBooks")){
			retValue = new AvailableBooksTransaction(worker);
		}
		if(transType.equals("CheckedOutBooks")){
			retValue = new CheckedOutBooksTransaction(worker);
		}
		if(transType.equals("SBWithCheckedOutBooks")){
			retValue = new SBWithCheckedOutBooksTransaction(worker);
		}
		if(transType.equals("DelinquencyCheck")){
			retValue = new DelinquencyCheckTransaction(worker);
		}
		return retValue;
	}
}