// tabs=4
//************************************************************
//	COPYRIGHT 2009 Sandeep Mitra and Josh Swanson/Anna Migitskiy, The
//    College at Brockport, State University of New York. - 
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot 
// be reproduced, copied, or used in any shape or form without 
// the express written consent of The College at Brockport.
//************************************************************
//
// specify the package
package userinterface;

// system imports
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Vector;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.text.SimpleDateFormat;

// project imports
import impresario.IView;
import impresario.IModel;
import impresario.IControl;
import impresario.ControlRegistry;

//==============================================================
public abstract class ViewL extends View implements KeyListener{
	public static boolean VIEW_USE_DEF_SZ = false; 
	private String viewClassName;
	protected Dimension prefSize;
	protected String viewTitle;
	protected static Image DEF_BK_IMG = null;
	protected boolean useDefBkImg;
	protected String logoTitleBar;
	protected JLabel validateLabel;
	protected JLabel validateLabelMessage;
	protected JLabel requiredMessage;
	protected Vector<JLabel> validateVec;
	protected Vector<JLabel> validateVecMessage;
	public ViewL(IModel model, String classname) {
		super(model, classname);
		viewClassName = classname;
		logoTitleBar = "default";
		viewTitle = "    TITLE NOT SET [" + classname + "]";
		prefSize = new Dimension(800, 500);
		if (ViewL.VIEW_USE_DEF_SZ){
			this.setPreferredSize(prefSize);
		}
		useDefBkImg = true;
		//		repaint();
	}
	public String getLogoTitleBar() {
		return logoTitleBar;
	}
	public void setLogoTitleBar(String logoTitleBar) {
		this.logoTitleBar = logoTitleBar;
	}
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		if (useDefBkImg && DEF_BK_IMG != null){
			g2d.drawImage(DEF_BK_IMG, 0, 0, null);
		}
	}
	public void refreshLogoTitle(){
		MainFrame mf = (MainFrame)MainFrame.getInstance();
		mf.getLogoPanel().getLeftLabel().setText(viewTitle);
	}
	public String getViewClassName(){
		return viewClassName;
	}
	public Dimension getPrefSize() {
		return prefSize;
	}
	public void setPrefSize(Dimension prefSize) {
		this.prefSize = prefSize;
	}
	public String getViewTitle() {
		return viewTitle;
	}
	public void setViewTitle(String viewTitle) {
		this.viewTitle = viewTitle;
	}
	public void onPreSetVisible(boolean isVisible){

	}
	public void onPostSetVisible(boolean isVisible){

	}

	//pop up acknowledgment of validation errors
	protected int acknowledgeErrorsPopUp(String PU_title, String PU_message){
		Object[] options = {"Ok"};
		return JOptionPane.showOptionDialog(MainFrame.getInstance(),
				PU_message,
				PU_title,
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.ERROR_MESSAGE,
				null,
				options,
				options[0]);
	}

	//regex patterns for validation
	private static Pattern patternAuthor = Pattern.compile("^([a-zA-Z][a-zA-Z0-9\\.', ]{0,99})$");
	private static Pattern patternPublisher = Pattern.compile("^([a-zA-Z][a-zA-Z0-9\\.', ]{0,99})$");
	private static Pattern patternISBN = Pattern.compile("^([0-9][0-9x\\-]{0,15})$");
	private static Pattern patternSuggestedPrice = Pattern.compile("^[0-9]{1,3}(\\.[0-9]{2})?$");
	private static Pattern patternFirstName = Pattern.compile("^([a-zA-Z][a-zA-Z0-9\\.', ]{0,99})$");
	private static Pattern patternLastName = Pattern.compile("^([a-zA-Z][a-zA-Z0-9\\.',-]{0,99})$");
	private static Pattern patternEmail = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	private static Pattern patternPhoneNumber = Pattern.compile("^[0-9]{3}\\-[0-9]{3}\\-[0-9]{4}$");
	private static Pattern patternMonetaryPenalty = Pattern.compile("^[0-9]{1,3}(\\.[0-9]{2})?$");
	//private static Pattern patternPassword = Pattern.compile("^$");

	//validate method to validate all appropriate fields
	protected Properties validateAllFields (Properties fieldsToValidate){
		String errorMessage = "";
		Enumeration allKeys = fieldsToValidate.propertyNames();
		while (allKeys.hasMoreElements() == true){
			String nextKey = (String)allKeys.nextElement();
			String nextValue = fieldsToValidate.getProperty(nextKey);
			String currentError = "";
			if(nextKey.equals("barcode")){
				currentError = validateBarcode(nextValue);
				if(!(currentError.equals(""))){
					errorMessage = errorMessage + "\n" + currentError;
					fieldsToValidate.setProperty(nextKey + ".errorMessage", currentError);
				}
			}
			else if(nextKey.equals("title")){
				currentError = validateTitle(nextValue);
				if(!(currentError.equals(""))){
					errorMessage = errorMessage + "\n" + currentError;
					fieldsToValidate.setProperty(nextKey + ".errorMessage", currentError);
				}
			}
			else if(nextKey.equals("author1")){
				currentError = validateAuthor1(nextValue);
				if(!(currentError.equals(""))){
					errorMessage = errorMessage + "\n" + currentError;
					fieldsToValidate.setProperty(nextKey + ".errorMessage", currentError);
				}
			}
			else if(nextKey.equals("author2")){
				currentError = validateAuthor2(nextValue);
				if(!(currentError.equals(""))){
					errorMessage = errorMessage + "\n" + currentError;
					fieldsToValidate.setProperty(nextKey + ".errorMessage", currentError);
				}
			}
			else if(nextKey.equals("author3")){
				currentError = validateAuthor3(nextValue);
				if(!(currentError.equals(""))){
					errorMessage = errorMessage + "\n" + currentError;
					fieldsToValidate.setProperty(nextKey + ".errorMessage", currentError);
				}
			}
			else if(nextKey.equals("author4")){
				currentError = validateAuthor4(nextValue);
				if(!(currentError.equals(""))){
					errorMessage = errorMessage + "\n" + currentError;
					fieldsToValidate.setProperty(nextKey + ".errorMessage", currentError);
				}
			}
			else if(nextKey.equals("publisher")){
				currentError = validatePublisher(nextValue);
				if(!(currentError.equals(""))){
					errorMessage = errorMessage + "\n" + currentError;
					fieldsToValidate.setProperty(nextKey + ".errorMessage", currentError);
				}
			}
			else if(nextKey.equals("publicationYear")){
				currentError = validatePubYear(nextValue);
				if(!(currentError.equals(""))){
					errorMessage = errorMessage + "\n" + currentError;
					fieldsToValidate.setProperty(nextKey + ".errorMessage", currentError);
				}
			}
			else if(nextKey.equals("isbn")){
				currentError = validateISBN(nextValue);
				if(!(currentError.equals(""))){
					errorMessage = errorMessage + "\n" + currentError;
					fieldsToValidate.setProperty(nextKey + ".errorMessage", currentError);
				}
			}
			else if(nextKey.equals("suggestedPrice")){
				currentError = validateSuggestedPrice(nextValue);
				if(!(currentError.equals(""))){
					errorMessage = errorMessage + "\n" + currentError;
					fieldsToValidate.setProperty(nextKey + ".errorMessage", currentError);
				}
			}
			else if(nextKey.equals("notes")){
				currentError = validateNotes(nextValue);
				if(!(currentError.equals(""))){
					errorMessage = errorMessage + "\n" + currentError;
					fieldsToValidate.setProperty(nextKey + ".errorMessage", currentError);
				}
			}
			else if(nextKey.equals("bannerID")){
				currentError = validateBannerID(nextValue);
				if(!(currentError.equals(""))){
					errorMessage = errorMessage + "\n" + currentError;
					fieldsToValidate.setProperty(nextKey + ".errorMessage", currentError);
				}
			}
			else if(nextKey.equals("firstName")){
				currentError = validateFirstName(nextValue);
				if(!(currentError.equals(""))){
					errorMessage = errorMessage + "\n" + currentError;
					fieldsToValidate.setProperty(nextKey + ".errorMessage", currentError);
				}
			}
			else if(nextKey.equals("lastName")){
				currentError = validateLastName(nextValue);
				if(!(currentError.equals(""))){
					errorMessage = errorMessage + "\n" + currentError;
					fieldsToValidate.setProperty(nextKey + ".errorMessage", currentError);
				}
			}
			else if(nextKey.equals("monetaryPenalty")){
				currentError = validateMonetaryPenalty(nextValue);
				if(!(currentError.equals(""))){
					errorMessage = errorMessage + "\n" + currentError;
					fieldsToValidate.setProperty(nextKey + ".errorMessage", currentError);
				}
			}
			else if(nextKey.equals("email")){
				currentError = validateEmail(nextValue);
				if(!(currentError.equals(""))){
					errorMessage = errorMessage + "\n" + currentError;
					fieldsToValidate.setProperty(nextKey + ".errorMessage", currentError);
				}
			}
			else if(nextKey.equals("phoneNumber")){
				currentError = validatePhoneNumber(nextValue);
				if(!(currentError.equals(""))){
					errorMessage = errorMessage + "\n" + currentError;
					fieldsToValidate.setProperty(nextKey + ".errorMessage", currentError);
				}
			}
			else if(nextKey.equals("password")){
				currentError = validatePassword(nextValue);
				if(!(currentError.equals(""))){
					errorMessage = errorMessage + "\n" + currentError;
					fieldsToValidate.setProperty(nextKey + ".errorMessage", currentError);
				}
			}
		}

		fieldsToValidate.setProperty("error", errorMessage);
		return fieldsToValidate;
	}

	public String validateBarcode (String barcodeInput){
		String errMsg = "";
		int barCd;
		try{
			barCd = Integer.parseInt(barcodeInput);
		}
		catch (NumberFormatException e){
			errMsg = "Barcode not in the correct format.";
			return errMsg;
		}
		if(barCd < 10000 || barCd > 99999){
			errMsg = "Barcode not in the correct format.";
		}
		return errMsg;
	}

	public String validateTitle(String titleInput){
		String errMsg = "";
		if(titleInput.length() < 1 || titleInput.length() > 50){
			errMsg = "Error in title field (note max characters: 50).";
		}
		return errMsg;
	}

	public String validateAuthor1(String author1Input){
		String errMsg = "";
		if(author1Input.length() < 1 || author1Input.length() > 30){
			errMsg = "Error in author1 field (note max characters: 30).";
		}
		Matcher m = patternAuthor.matcher(author1Input);
		if(!(m.find())){
			errMsg = "Error in author1 field.";
		}
		return errMsg;
	}

	public String validateAuthor2(String author2Input){
		String errMsg = "";
		if(author2Input.length() == 0){
			return "";
		}
		Matcher m = patternAuthor.matcher(author2Input);
		if(!(m.find())){
			errMsg = "Error in author2 field.";
		}
		else if(author2Input.length() > 30){
			errMsg = "Error in author 2 field (note max characters: 30).";
		}
		return errMsg;
	}

	public String validateAuthor3(String author3Input){
		String errMsg = "";
		if(author3Input.length() == 0){
			return "";
		}
		Matcher m = patternAuthor.matcher(author3Input);
		if(!(m.find())){
			errMsg = "Error in author3 field.";
		}
		else if(author3Input.length() > 30){
			errMsg = "Error in author 3 field (note max characters: 30).";
		}
		return errMsg;
	}

	public String validateAuthor4(String author4Input){
		String errMsg = "";
		if(author4Input.length() == 0){
			return "";
		}
		Matcher m = patternAuthor.matcher(author4Input);
		if(!(m.find())){
			errMsg = "Error in author4 field.";
		}
		else if(author4Input.length() > 30){
			errMsg = "Error in author 4 field (note max characters: 30).";
		}
		return errMsg;
	}

	public String validatePublisher(String publisherInput){
		String errMsg = "";
		Matcher m = patternPublisher.matcher(publisherInput);
		if(!(m.find())){
			errMsg = "Error in publisher field.";
		}
		else if (publisherInput.length() < 0 || publisherInput.length() > 30){
			errMsg = "Error in publisher field (note max characters: 30).";
		}
		return errMsg;
	}

	public String validatePubYear (String pubYear){
		String errMsg = "";
		int publicationYear;
		try{
			publicationYear = Integer.parseInt(pubYear);
		}
		catch(NumberFormatException e){
			errMsg = "Publication Year not in the correct format.";
			return errMsg;
		}
		int myDate;
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		myDate = year + 1;
		if((publicationYear < 1800 && publicationYear != 0) || publicationYear > myDate){
			errMsg = "Error in publication year field.";
		}
		return errMsg;
	}

	public String validateISBN(String isbnInput){
		String errMsg = "";
		Matcher m = patternISBN.matcher(isbnInput);
		if(!(m.find())){
			errMsg = "ISBN is not in the correct format.";
		}
		else if (isbnInput.length() > 15){
			errMsg = "Error in ISBN field (note max characters: 15).";
		}
		return errMsg;
	}

	public String validateSuggestedPrice(String suggestedPriceInput){
		String errMsg = "";
		Matcher m = patternSuggestedPrice.matcher(suggestedPriceInput);
		if(!(m.find())){
			errMsg = "Suggested Price is not in the correct format.";
		}
		else if (suggestedPriceInput.length() > 6){
			errMsg = "Error in Suggested Price field (note max characters: 6).";
		}
		return errMsg;
	}

	public String validateNotes(String notesInput){
		String errMsg = "";
		if(notesInput.length() > 300){
			errMsg = "Error in notes field (note max characters: 300).";
		}
		return errMsg;
	}

	public String validateBannerID(String bannerIDInput){
		String errMsg = "";
		int banner;
		try{
			banner = Integer.parseInt(bannerIDInput);
		}
		catch(NumberFormatException e){
			errMsg = "Banner ID is not in the correct format.";
			return errMsg;
		}
		if(banner < 800000000 || banner > 899999999){
			errMsg = "Banner ID is not in the correct format.";
		}
		return errMsg;
	}

	public String validateFirstName(String firstNameInput){
		String errMsg = "";
		Matcher m = patternFirstName.matcher(firstNameInput);
		if(!(m.find())){
			errMsg = "First Name is not in the correct format.";
		}
		else if (firstNameInput.length() < 1 || firstNameInput.length() > 25){
			errMsg = "Error in First Name field (note max characters: 25).";
		}
		return errMsg;
	}

	public String validateLastName(String lastNameInput){
		String errMsg = "";
		Matcher m = patternLastName.matcher(lastNameInput);
		if(!(m.find())){
			errMsg = "Last Name is not in the correct format.";
		}
		else if (lastNameInput.length() < 1 || lastNameInput.length() > 30){
			errMsg = "Error in Last Name field (note max characters: 30).";
		}
		return errMsg;
	}

	public String validateMonetaryPenalty(String monetaryPenaltyInput){
		String errMsg = "";
		Matcher m = patternMonetaryPenalty.matcher(monetaryPenaltyInput);
		if(!(m.find())){
			errMsg = "Monetary Penalty is not in the correct format.";
		}
		else if (monetaryPenaltyInput.length() > 6){
			errMsg = "Error in Monetary Penalty field (note max characters: 6).";
		}
		return errMsg;
	}

	public String validateEmail(String emailInput){
		String errMsg = "";
		if(emailInput.length() == 0){
			return errMsg;
		}
		Matcher m = patternEmail.matcher(emailInput);
		if(!(m.find())){
			errMsg = "Email is not in the correct format.";
		}
		return errMsg;
	}
	public String validatePhoneNumber(String phoneNumberInput){
		String errMsg = "";
		if(phoneNumberInput.length() == 0){
			return errMsg;
		}
		Matcher m = patternPhoneNumber.matcher(phoneNumberInput);
		if(!(m.find())){
			errMsg = "Phone Number is not in the correct format.";
		}
		return errMsg;
	}

	public String validatePassword(String passwordInput){
		String errMsg = "";
		if(passwordInput.length() == 0 || passwordInput.length() > 25){
			return ("Error in password field."); 
		}
		//Matcher m = patternPassword.matcher(passwordInput);
		//if(!(m.find())){
		//	errMsg = "Password not in the correct format.";
		//}
		return errMsg;
	}

	protected void createValidate(int x, int y, int w, int h, String associatedTextField){
		validateLabel = new JLabel();
		add(validateLabel);
		validateLabel.setBounds(x, y, w, h);
		validateLabel.setBackground(MainFrame.BK_COLOR);
		validateLabel.setBorder(null);
		validateLabel.setIcon(ImageHolder.ICB_VALIDATE);
		validateLabel.putClientProperty("TextField", associatedTextField);
		validateLabel.setOpaque(false);
		validateLabel.setVisible(false);
		validateVec.addElement(validateLabel);
	}

	protected void createValidateMessage(int x, int y, int w, int h, String associatedTextField){
		validateLabelMessage = new JLabel();
		add(validateLabelMessage);
		validateLabelMessage.setBounds(x, y, w, h);
		validateLabelMessage.setBackground(MainFrame.BK_COLOR);
		validateLabelMessage.setBorder(null);
		//validateLabelMessage.putClientProperty("TextField", associatedTextField);
		validateLabelMessage.setOpaque(false);
		validateLabelMessage.setVisible(false);
		validateVecMessage.addElement(validateLabelMessage);
	}

	protected void markValidationErrors(Properties allErrorMessages){
		Enumeration allKeys = allErrorMessages.propertyNames();
		while (allKeys.hasMoreElements() == true){
			String nextKey = (String)allKeys.nextElement();
			String nextValue = allErrorMessages.getProperty(nextKey);
			for(int i = 0; i < validateVec.size(); i++){
				/*DEBUG*///System.out.println("JLabelProp: "+(String)validateVec.elementAt(i).getClientProperty("TextField"));
				/*DEBUG*///System.out.println("nextKey: "+nextKey);
				if(nextKey.equals((String)validateVec.elementAt(i).getClientProperty("TextField"))){
					validateVec.elementAt(i).setVisible(true);
					validateVecMessage.elementAt(i).setVisible(true);
					validateVec.elementAt(i).setOpaque(false);
					//validateVecMessage.elementAt(i).setOpaque(true);
					validateVecMessage.elementAt(i).setText(nextValue);
					//Font myFont = new Font("Arial", Font.ITALIC, 12);
					Font myFont = new Font("Arial", Font.BOLD, 11);
					validateVecMessage.elementAt(i).setFont(myFont);
					//validateVecMessage.elementAt(i).setBackground(Color.lightGray);
					//validateVecMessage.elementAt(i).setForeground(Color.magenta);
					validateVecMessage.elementAt(i).setForeground(Color.red);
				}
			}   
		}
	}

	protected void setValidationMarkersOff(){
		for(int i = 0; i < validateVec.size(); i++){
			validateVec.elementAt(i).setVisible(false);
			validateVecMessage.elementAt(i).setVisible(false);
		}
	}
}