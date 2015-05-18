package userinterface;

import impresario.IModel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventObject;
import java.util.Properties;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class RentalProcessLostBookCollectionView extends ViewL implements ActionListener{
	protected MessageView statusLog;
	protected JTable tableOfRentalsStillCheckedOut;
	protected JTable tableOfRentalsToProcessAsLost;
	protected JButton[] actionButtons;
	private Rectangle[] rs;
	private Rectangle[] rt;
	private int aHeight;
	private int aWidthC0;
	private int aWidth;
	private int aVertSp;
	private int aHorzSp;
	private int aMarginLeft;
	private int aMarginBottom;
	private int aMarginRight;
	private int b0h;
	private int b1h;
	private int heightMinusButtons;
	private RentalBooksLBStillCheckedOutTableModel rentalBooksLBStillCheckedOutTableModel;
	private RentalBooksLBToProcessAsLostTableModel rentalBooksLBToProcessAsLostTableModel;
	private Vector<Vector<String>> rentalsLBStillCheckedOutVectorString;
	private Vector<Vector<String>> rentalsLBToProcessAsLostVectorString;
	private Properties guiProps;
	public RentalProcessLostBookCollectionView(IModel model) {
		super(model, "RentalProcessLostBookCollectionView");
		rentalsLBStillCheckedOutVectorString = new Vector<Vector<String>>();
		rentalsLBToProcessAsLostVectorString = new Vector<Vector<String>>();
		setPreferredSize(prefSize);
		setLayout(null);
		constructVars();
		getEntryTableModelValuesStillCheckedOut();
		getEntryTableModelValuesToProcessAsLost();
		createBooksStillCheckedOutTable();
		createBooksToProcessAsLostTable();
		createFields();
		myModel.subscribe("TransactionError", this);
		viewTitle = "Lost Book Rental Collection";
		refreshLogoTitle();
		guiProps = new Properties();
		setGuiState("initial");
	}
	private void setGuiState(String state){
		guiProps.setProperty("state_request", state);
		if (state.equals("initial")){
			actionButtons[0].setVisible(true);
			actionButtons[1].setVisible(true);
			actionButtons[2].setVisible(false);
			actionButtons[0].setIcon(ImageHolder.ICB_CANCEL);
			actionButtons[0].setRolloverIcon(ImageHolder.ICB_CANCEL_HOVER);
			actionButtons[1].setIcon(ImageHolder.ICB_BACK);
			actionButtons[1].setRolloverIcon(ImageHolder.ICB_BACK_HOVER);
			guiProps.setProperty("are_books_lost", "no");
			guiProps.setProperty("b0", "cancel");
			guiProps.setProperty("b1", "back");
			guiProps.setProperty("b2", "none");
		} else if (state.equals("update_move")){
			int rci = rentalsLBToProcessAsLostVectorString.size();
			if (rci > 0){
				actionButtons[0].setVisible(true);
				actionButtons[1].setVisible(true);
				actionButtons[2].setVisible(true);
				actionButtons[0].setIcon(ImageHolder.ICB_PROCESSFEE);
				actionButtons[0].setRolloverIcon(ImageHolder.ICB_PROCESSFEE_HOVER);
				actionButtons[1].setIcon(ImageHolder.ICB_CANCEL);
				actionButtons[1].setRolloverIcon(ImageHolder.ICB_CANCEL_HOVER);
				actionButtons[2].setIcon(ImageHolder.ICB_BACK);
				actionButtons[2].setRolloverIcon(ImageHolder.ICB_BACK_HOVER);
				guiProps.setProperty("are_books_lost", "yes");
				guiProps.setProperty("b0", "process_fee");
				guiProps.setProperty("b1", "cancel");
				guiProps.setProperty("b2", "back");
			} else {
				actionButtons[0].setVisible(true);
				actionButtons[1].setVisible(true);
				actionButtons[2].setVisible(false);
				actionButtons[0].setIcon(ImageHolder.ICB_CANCEL);
				actionButtons[0].setRolloverIcon(ImageHolder.ICB_CANCEL_HOVER);
				actionButtons[1].setIcon(ImageHolder.ICB_BACK);
				actionButtons[1].setRolloverIcon(ImageHolder.ICB_BACK_HOVER);
				guiProps.setProperty("are_books_lost", "no");
				guiProps.setProperty("b0", "cancel");
				guiProps.setProperty("b1", "back");
				guiProps.setProperty("b2", "none");
			}
		} else if (state.equals("try_commit")){
			actionButtons[0].setEnabled(false);
			actionButtons[1].setEnabled(false);
			actionButtons[2].setEnabled(false);
		} else if (state.equals("commit_fail")){
			actionButtons[0].setEnabled(true);
			actionButtons[0].setVisible(true);
			actionButtons[1].setVisible(false);
			actionButtons[2].setVisible(false);
			actionButtons[0].setIcon(ImageHolder.ICB_CANCEL);
			actionButtons[0].setRolloverIcon(ImageHolder.ICB_CANCEL_HOVER);
			guiProps.setProperty("b0", "cancel");
		} else if (state.equals("commit_partial")){
			actionButtons[0].setEnabled(true);
			actionButtons[0].setVisible(true);
			actionButtons[1].setVisible(false);
			actionButtons[2].setVisible(false);
			actionButtons[0].setIcon(ImageHolder.ICB_DONE);
			actionButtons[0].setRolloverIcon(ImageHolder.ICB_DONE_HOVER);
			guiProps.setProperty("b0", "done");
		} else if (state.equals("commit_success")){
			actionButtons[0].setEnabled(true);
			actionButtons[0].setVisible(true);
			actionButtons[1].setVisible(false);
			actionButtons[2].setVisible(false);
			actionButtons[0].setIcon(ImageHolder.ICB_DONE);
			actionButtons[0].setRolloverIcon(ImageHolder.ICB_DONE_HOVER);
			guiProps.setProperty("b0", "done");
		}
	}
	private void constructVars(){
		rs = ALayout.generate(this);
		rt = ALayout.generateHdrMain(rs);
		aHeight = ImageHolder.ICB_CANCEL.getIconHeight();
		aWidth = ImageHolder.ICB_CANCEL.getIconWidth();
		aWidthC0 = 125;
		aVertSp = 5;
		aHorzSp = 5;
		aMarginLeft = 5;
		aMarginBottom = 5;
		aMarginRight = 5;
		int h = rt[1].height;
		b0h = aVertSp;
		b1h = aMarginBottom + aHeight + aVertSp;
		h -= (b0h + b1h);
		heightMinusButtons = h / 2;
	}
	protected void createFields(){
		actionButtons = new JButton[3];
		for(int i = 0;i < actionButtons.length;i++){
			actionButtons[i] = new JButton();
			actionButtons[i].addActionListener(this);
			add(actionButtons[i]);
			actionButtons[i].setBounds((rt[1].x + rt[1].width) - (aWidth * (i + 1) + aHorzSp * i + aMarginRight),
					(rt[1].y + rt[1].height) - (aMarginBottom + aHeight),
					aWidth,
					aHeight);
			actionButtons[i].setBackground(bkColor);
			actionButtons[i].setOpaque(false);
			actionButtons[i].setMargin(new Insets(0, 0, 0, 0));
			actionButtons[i].setBorder(null);
		}
		JPanel slog = createStatusLog("   ");
		slog.setBounds(rt[1].x, 
				(rt[1].y + rt[1].height) - (aHeight + 5 + slog.getInsets().top + slog.getInsets().bottom), 
				rt[1].width, 
				aHeight + 5 + slog.getInsets().top + slog.getInsets().bottom);
		add(slog);
	}
	protected void getEntryTableModelValuesStillCheckedOut(){
		rentalsLBStillCheckedOutVectorString.removeAllElements();
		Vector<Properties> vecPropsRCo = (Vector<Properties>)myModel.getState("deep_props_vec_rentals_still_checked_out");
		Vector<Properties> vecPropsBCo = (Vector<Properties>)myModel.getState("deep_props_vec_rentals_book_still_checked_out");
		for(int i = 0;i < vecPropsRCo.size();i++){
			Properties propsR = vecPropsRCo.get(i);
			Properties propsB = vecPropsBCo.get(i);
			Vector<String> v = new Vector<String>();//check fields below for null
			v.add(propsR.getProperty("BookID"));
			v.add(propsB.getProperty("Title"));
			v.add(propsR.getProperty("DueDate"));
			v.add(propsB.getProperty("SuggestedPrice"));
			rentalsLBStillCheckedOutVectorString.add(v);
		}
	}
	protected void getEntryTableModelValuesToProcessAsLost(){
		rentalsLBToProcessAsLostVectorString.removeAllElements();
		Vector<Properties> vecPropsRCo = (Vector<Properties>)myModel.getState("deep_props_vec_rentals_to_process_as_lost");
		Vector<Properties> vecPropsBCo = (Vector<Properties>)myModel.getState("deep_props_vec_rentals_book_to_process_as_lost");
		for(int i = 0;i < vecPropsRCo.size();i++){
			Properties propsR = vecPropsRCo.get(i);
			Properties propsB = vecPropsBCo.get(i);
			Vector<String> v = new Vector<String>();//check fields below for null
			v.add(propsR.getProperty("BookID"));
			v.add(propsB.getProperty("Title"));
			v.add(propsR.getProperty("DueDate"));
			v.add(propsB.getProperty("SuggestedPrice"));
			rentalsLBToProcessAsLostVectorString.add(v);
		}
	}
	protected void createBooksStillCheckedOutTable(){
		JPanel entries = new JPanel();
		Font borderFont = new Font("Helvetica", Font.BOLD, 24);
		entries.setBorder(BorderFactory.createTitledBorder(getBorder(), "Books Still Checked Out", 0, 0, borderFont));
		Rectangle er = new Rectangle(rt[1]);
		er.height = heightMinusButtons;//(aHeight + aMarginBottom + aVertSp + aHeight + aVertSp);
		entries.setBounds(er);
		entries.setLayout(new BoxLayout(entries, BoxLayout.Y_AXIS));
		JPanel tablePan = new JPanel();
		rentalBooksLBStillCheckedOutTableModel = new RentalBooksLBStillCheckedOutTableModel(rentalsLBStillCheckedOutVectorString);
		tableOfRentalsStillCheckedOut = new JTable(rentalBooksLBStillCheckedOutTableModel);
		tableOfRentalsStillCheckedOut.addMouseListener(new TableStillCheckedOutMouseListener());
		tableOfRentalsStillCheckedOut.setRowHeight(20);
		TableColumn column;
		for(int i = 0; i < rentalBooksLBStillCheckedOutTableModel.getColumnCount(); i++){
			column = tableOfRentalsStillCheckedOut.getColumnModel().getColumn(i);
			column.setPreferredWidth(40);
		}
		tablePan.add(tableOfRentalsStillCheckedOut);
		JScrollPane scrollPane = new JScrollPane(tableOfRentalsStillCheckedOut);
		scrollPane.setPreferredSize(new Dimension(entries.getBounds().width  - (entries.getInsets().left + entries.getInsets().right), 
				entries.getBounds().height - (entries.getInsets().top + entries.getInsets().bottom)));
		tablePan.add(scrollPane);
		entries.add(tablePan);
		add(entries);
	}
	protected void createBooksToProcessAsLostTable(){
		JPanel entries = new JPanel();
		Font borderFont = new Font("Helvetica", Font.BOLD, 24);
		entries.setBorder(BorderFactory.createTitledBorder(getBorder(), "Books To Process As Lost", 0, 0, borderFont));
		Rectangle er = new Rectangle(rt[1]);
		er.y += heightMinusButtons + b0h;
		er.height = heightMinusButtons;//(aHeight + aMarginBottom + aVertSp + aHeight + aVertSp);
		entries.setBounds(er);
		entries.setLayout(new BoxLayout(entries, BoxLayout.Y_AXIS));
		JPanel tablePan = new JPanel();
		rentalBooksLBToProcessAsLostTableModel = new RentalBooksLBToProcessAsLostTableModel(rentalsLBToProcessAsLostVectorString);
		tableOfRentalsToProcessAsLost = new JTable(rentalBooksLBToProcessAsLostTableModel);
		tableOfRentalsToProcessAsLost.addMouseListener(new TableToProcessAsLostMouseListener());
		tableOfRentalsToProcessAsLost.setRowHeight(20);
		TableColumn column;
		for(int i = 0; i < rentalBooksLBToProcessAsLostTableModel.getColumnCount(); i++){
			column = tableOfRentalsToProcessAsLost.getColumnModel().getColumn(i);
			column.setPreferredWidth(40);
		}
		tablePan.add(tableOfRentalsToProcessAsLost);
		JScrollPane scrollPane = new JScrollPane(tableOfRentalsToProcessAsLost);
		scrollPane.setPreferredSize(new Dimension(entries.getBounds().width - (entries.getInsets().left + entries.getInsets().right), 
				entries.getBounds().height - (entries.getInsets().top + entries.getInsets().bottom)));
		tablePan.add(scrollPane);
		entries.add(tablePan);
		add(entries);
	}
	protected void populateFields(){
		getEntryTableModelValuesStillCheckedOut();
		getEntryTableModelValuesToProcessAsLost();
		setGuiState("update_move");
	}
	private int longMessagePopUp(String PU_title, String PU_message) {
		Object[] options = { "Ok" };
		return JOptionPane.showOptionDialog(MainFrame.getInstance(),
				PU_message, PU_title, JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}
	@Override
	protected void processAction(EventObject evt) {
		int i = 0;
		while(i < actionButtons.length){
			if (evt.getSource() == actionButtons[i]){
				String btnFn = guiProps.getProperty("b" + Integer.toString(i));
				System.out.println("button " + btnFn);
				if(btnFn.equals("back")){
					myModel.stateChangeRequest("CancelThisView", null);
				} else if (btnFn.equals("cancel")){
					myModel.stateChangeRequest("cancel", null);
				} else if (btnFn.equals("process_fee")){
					setGuiState("try_commit");
					myModel.stateChangeRequest("commit_lost_books", null);
					Properties statusProps = (Properties)myModel.getState("commit_lost_books_status");
					if (statusProps.getProperty("Status").equals("0")){
						setGuiState("commit_success");
						longMessagePopUp("Success", "fee assessed: " + statusProps.getProperty("TotalFee"));
						displayMessage("fee assessed " + statusProps.getProperty("TotalFee"));
					} else if (statusProps.getProperty("Status").equals("1")){
						setGuiState("commit_partial");//
						longMessagePopUp("Warning", 
								"fee assessed: " + statusProps.getProperty("TotalFee") + "\nWarnings:\n" + statusProps.getProperty("LongMessage"));
						displayMessage("fee assessed " + statusProps.getProperty("TotalFee"));
					} else {
						setGuiState("commit_fail");
					}
				} else if (btnFn.equals("done")){
					myModel.stateChangeRequest("cancel", null);
				}
				break;
			} else {
				i++;
			}
		}
	}
	@Override
	public void updateState(String key, Object value) {
		if (key.equals("refresh")){
			populateFields();
		}
	}
	@Override
	protected void processListSelection(EventObject evt) {
		// TODO Auto-generated method stub

	}
	protected JPanel createStatusLog(String initialMessage){
		statusLog = new MessageView(initialMessage);
		return statusLog;
	}

	public void displayErrorMessage(String message){
		statusLog.displayErrorMessage(message);
	}

	public void displayMessage(String message){
		statusLog.displayMessage(message);
	}

	public void clearErrorMessage(){
		statusLog.clearErrorMessage();
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	public class RentalBooksLBStillCheckedOutTableModel extends AbstractTableModel implements TableModel{
		private Vector myState;
		public RentalBooksLBStillCheckedOutTableModel(Vector rentalData){
			myState = rentalData;
		}
		public int getColumnCount(){
			return 4;
		}
		public int getRowCount(){
			return myState.size();
		}
		public Object getValueAt(int rowIndex, int columnIndex){
			Vector rental = (Vector)myState.elementAt(rowIndex);
			return "  " + rental.elementAt(columnIndex);
		}
		public String getColumnName(int columnIndex){
			if(columnIndex == 0){
				return "Book Barcode";
			}
			else if(columnIndex == 1){
				return "Book Title";
			}
			else if(columnIndex == 2){
				return "Due Date";
			}
			else if (columnIndex == 3){
				return "Price";
			}
			else{
				return "??";
			}
		}
	}
	public class RentalBooksLBToProcessAsLostTableModel extends AbstractTableModel implements TableModel{
		private Vector myState;
		public RentalBooksLBToProcessAsLostTableModel(Vector rentalData){
			myState = rentalData;
		}
		public int getColumnCount(){
			return 4;
		}
		public int getRowCount(){
			return myState.size();
		}
		public Object getValueAt(int rowIndex, int columnIndex){
			Vector rental = (Vector)myState.elementAt(rowIndex);
			return "  " + rental.elementAt(columnIndex);
		}
		public String getColumnName(int columnIndex){
			if(columnIndex == 0){
				return "Book Barcode";
			}
			else if(columnIndex == 1){
				return "Book Title";
			}
			else if(columnIndex == 2){
				return "Due Date";
			}
			else if (columnIndex == 3){
				return "Price";
			}
			else{
				return "??";
			}
		}
	}
	public class TableStillCheckedOutMouseListener implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() >= 2){
				myModel.stateChangeRequest("rental_move_rental_co_to_lb", tableOfRentalsStillCheckedOut.getSelectedRow());
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}
	}
	public class TableToProcessAsLostMouseListener implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() >= 2){
				myModel.stateChangeRequest("rental_move_rental_lb_to_co", tableOfRentalsToProcessAsLost.getSelectedRow());
				Properties statusProps = (Properties)myModel.getState("rental_move_rental_lb_to_co_status");
				if (statusProps.getProperty("Status").equals("0")){
					populateFields();
					rentalBooksLBStillCheckedOutTableModel.fireTableDataChanged();
					rentalBooksLBToProcessAsLostTableModel.fireTableDataChanged();
					clearErrorMessage();
				} else {
					displayErrorMessage(statusProps.getProperty("Message"));
				}
				System.out.println("TPL double clicked");
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}
	}
}
