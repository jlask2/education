<?php
   /*processEnterBook.php
   A PHP script to insert a new book into the 
   jlask2_fall14_1.book database*/

require('./enterBookResult.inc');

// Main control logic
insert_book();

//-------------------------------------------------------------
function insert_book()
{
	/* Connect to the 'book' database 
         The parameters are defined in the enterBookResult.inc file
         These are global constants */

	connect_and_select_db(DB_SERVER, DB_UN, DB_PWD,DB_NAME);

	/* Get the information entered into the webpage by the user
         These are available in the super global variable $_POST
	 This is actually an associative array, indexed by a string*/

	$barcode = $_POST['barcodename'];
		/*DEBUG*///echo "$barcode \n";
	$title = $_POST['titlename'];
		/*DEBUG*///echo "$title \n";
	$discipline = $_POST['disciplinename'];
		/*DEBUG*///echo "$discipline \n";
	$author1 = $_POST['author1name'];
		/*DEBUG*///echo "$author1 \n";
	$author2 = $_POST['author2name'];
		/*DEBUG*///echo "$author2 \n";        
	$author3 = $_POST['author3name'];
		/*DEBUG*///echo "$author3 \n";
	$author4 = $_POST['author4name'];
		/*DEBUG*///echo "$author4 \n";
	$publisher = $_POST['publishername'];
		/*DEBUG*///echo "$publisher \n";
	$yearpub = $_POST['yearofpublicationname'];  
		/*DEBUG*///echo "$notes \n";
	$isbn = $_POST['isbnname'];  
		/*DEBUG*///echo "$notes \n";
	$condition = $_POST['bookconditionname'];  
		/*DEBUG*///echo "$notes \n";
	$price = $_POST['suggestedpricename'];  
		/*DEBUG*///echo "$notes \n";
	$notes = $_POST['notesname'];  
		/*DEBUG*///echo "$notes \n";
		    
	/* Create a String consisting of the SQL command. Remember that
         . is the concatenation operator. $varname within double 
	 quotes will be evaluated by PHP*/
	
	//$result = false;

	//if ($barcode == null || $barcode == ""){
	//	echo "Invalid Barcode entered. Cannot be empty."
	//}else{
		$insertStmt = "INSERT INTO jlask2_fall14_1.book (Barcode, 
Title, Discipline, Author1, Author2, Author3, Author4, Publisher, 
YearOfPublication, ISBN, BookCondition, SuggestedPrice, Notes) values 
('$barcode', '$title', '$discipline', '$author1', '$author2', '$author3', 
'$author4', '$publisher',
'$yearpub', '$isbn', '$condition', '$price', '$notes')";
		//Execute the query. The result will just be true or false
       		$result = mysql_query($insertStmt);
	//}

	$message = "";

	if (!$result) 
	{
  	  $message = "Error in inserting book: $barcode : ". mysql_error();
	}
	else
	{
	  $message = "Data for book: $barcode : inserted successfully";
	}
	/*DEBUG*/echo "$message";
	
	$insertStmt->close_connection();		   
}

function connect_and_select_db($server, $username, $pwd, $dbname)
{
	// Connect to db server
	$conn = mysql_connect($server, $username, $pwd);

	if (!$conn) {
	    echo "Unable to connect to DB: " . mysql_error();
    	    exit;
	}

	// Select the database	
	$dbh = mysql_select_db($dbname);
	if (!$dbh){
    		echo "Unable to select ".$dbname.": " . mysql_error();
		exit;
	}
}

?>

