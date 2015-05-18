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
// specify the package
package userinterface;

import model.TransactionL;
import impresario.IModel;


//==============================================================================
public class ViewFactory {

	public static ViewL createView( String viewName, IModel model )
	{
		if (TransactionL.VIEW_DBG){
			System.out.println("ViewFactory.createView(\"" + viewName + "\")");
		}
		//---------------------------------------------------
		// Librarian
		//---------------------------------------------------
		if( viewName.equals( "LoginView" ) == true){
			return new LoginView(model);
		}
		else if(viewName.equals("TransactionChoiceView") == true){
			return new TransactionChoiceView(model);
		}
		else if(viewName.equals("WorkerSearchView") == true){
			return new WorkerSearchView(model);
		}
		else if(viewName.equals("WorkerCollectionView") == true){
			return new WorkerCollectionView(model);
		}	
		else if(viewName.equals("ModifyWorkerView") == true){
			return new ModifyWorkerView(model);
		}
		else if(viewName.equals("AddWorkerView") == true){
			return new AddWorkerView(model);
		}
		else if(viewName.equals("AddBookView") == true){
			return new AddBookView(model);
		}
		else if(viewName.equals("AddStudentBorrowerView") == true){
			return new AddStudentBorrowerView(model);
		}
		else if(viewName.equals("StudentBorrowerSearchView") == true){
			return new StudentBorrowerSearchView(model);
		}
		else if(viewName.equals("StudentBorrowerCheckInSearchView")) {
			return new StudentBorrowerCheckInSearchView(model);
		}
		else if(viewName.equals("StudentBorrowerCollectionView") == true){
			return new StudentBorrowerCollectionView(model);
		}
		else if(viewName.equals("ModifyStudentBorrowerView") == true){
			return new ModifyStudentBorrowerView(model);
		}
		else if(viewName.equals("DeleteWorkerView")==true){
			return new DeleteWorkerView(model);
		}
		else if(viewName.equals("BookSearchView") == true){
			return new BookSearchView(model);
		}
		else if(viewName.equals("BookCollectionView") == true){
			return new BookCollectionView(model);
		}
		else if(viewName.equals("ModifyBookView") == true){
			return new ModifyBookView(model);
		}
		else if(viewName.equals("DeleteBookView") == true){
			return new DeleteBookView(model);
		}
		else if(viewName.equals("DeleteStudentBorrowerView") == true){
			return new DeleteStudentBorrowerView(model);
		}
		else if(viewName.equals("RequestBarcodeView") == true){
			return new RequestBarcodeView(model);
		}
		else if(viewName.equals("ChangePasswordView") == true){
			return new ChangePasswordView(model);

		}
		else if(viewName.equals("AvailableBooksView") == true){
			return new AvailableBooksView(model);
		}
		else if(viewName.equals("StudentBorrowerReports") == true){
			return new StudentBorrowerReports(model);
		}
		else if(viewName.equals("CheckedOutBooksView") == true){
			return new CheckedOutBooksView(model);
		}
		else if(viewName.equals("RentalCheckInCollectionView")){
			return new RentalCheckInCollectionView(model);
		}
		else if(viewName.equals("RentalProcessLostBookCollectionView")){
			return new RentalProcessLostBookCollectionView(model);
		}
		else if(viewName.equals("ModifyLostBookView")){
			return new ModifyLostBookView(model);
		}
		else return null; 		
	}


	/*
	public static Vector createVectorView(String viewName, IModel model)
	{
		if(viewName.equals("SOME VIEW NAME") == true)
		{
			//return [A NEW VECTOR VIEW OF THAT NAME TYPE]
		}
		else
			return null;
	}
	 */

}
