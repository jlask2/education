<?php
   /*processSearchStudent.php
   A PHP script to search a Student in the
   jlask2_fall14_1.studentborrower database*/

require('./searchStudentResults.inc');

// Main control logic
search_student_borrower();

//-------------------------------------------------------------
function search_student_borrower()
{
        /* Connect to the 'studentborrower' database
         The parameters are defined in the searchStudentResults.inc file
         These are global constants */

        connect_and_select_db(DB_SERVER, DB_UN, DB_PWD,DB_NAME);

        /* Get the information entered into the webpage by the user
         These are available in the super global variable $_POST
         This is actually an associative array, indexed by a string*/

        $bannerid = $_POST['bannerid'];
                /*DEBUG*///echo "$bannerid \n";
        $firstname = $_POST['firstname'];
                /*DEBUG*///echo "$firstname \n";
        $lastname = $_POST['lastname'];
                /*DEBUG*///echo "$lastname \n";
	$phoneNoEmpty = array_filter($_POST['phonenumber']);
	
	$phone = implode('-', $phoneNoEmpty);
		/*DEBUG*///echo "$phone \n";
        $email = $_POST['email'];
                /*DEBUG*///echo "$email \n";

        /* Create a String consisting of the SQL command. Remember that
         . is the concatenation operator. $varname within double
         quotes will be evaluated by PHP*/
		
	if (strcmp("", $_POST['bannerid']) !== 0) {
	   $searchStmt = "SELECT *  FROM jlask2_fall14_1.studentborrower WHERE BannerID LIKE '%$bannerid%'";
        	//echo "Invalid BannerID entered. Cannot be empty."
        }else{
	   if (!empty($_POST['phonenumber'])) {
	   	$searchStmt = "SELECT * FROM jlask2_fall14_1.studentborrower WHERE FirstName LIKE '%$firstname%' AND LastName LIKE '%$lastname%' AND ContactPhone LIKE '%$phone%' AND Email LIKE '%$email%'";
	   	/*DEBUG*///echo $searchStmt;
	   } else {
           	$searchStmt = "SELECT * FROM jlask2_fall14_1.studentborrower WHERE FirstName LIKE '%$firstname%' AND LastName LIKE '%$lastname%' AND Email LIKE '%$email%'";
	   	/*DEBUG*///echo $searchStmt;
	   }
		/*DEBUG*///echo $searchStmt;
        }
	//Execute the query. The result will just be true or false
        $results = mysql_query($searchStmt);

        $message = "";

        if (!$results)
        {
          $message = "Error in searching for studentborrower: $bannerid : ". mysql_error();
        }
        else
        {
          $message = "Search results of studentborrower with the given input: $bannerid  found successfully";
        }
        /*DEBUG*/echo "$message";
	display_search_student_borrower_results($results);

        $searchStmt->close_connection();
}

function display_search_student_borrower_results($results){
	echo '<html><head><title>Search Student Borrower Results</title></head>';
	echo '<body>';
	echo '<table border="1" align="center" width="100%">';
  	echo '<th width="12.5%">BannerID</th>';
  	echo '<th width="12.5%">FirstName</th>';
  	echo '<th width="12.5%">LastName</th>';
  	echo '<th width="12.5%">ContactPhone</th>';
  	echo '<th width="12.5%">Email</th>';
	echo '<th width="12.5%">BorrowerStatus</th>';
        echo '<th width="12.5%">Notes</th>';
        echo '<th width="12.5%">Status</th>';
	echo '<thead>'; 
  	echo '</thead>'; 
  	echo '<tbody>'; 

	//While there are more rows in the $result, get the next row
   	//as an associative array
  	while ($row = mysql_fetch_assoc($results)) 
   	{
     		$bannerid = $row['BannerID']; 
     		$firstname = $row['FirstName']; 
    		$lastname = $row['LastName']; 
     		$phone = $row['ContactPhone'];  
 		$email = $row['Email'];
		$borrowerstatus = $row['BorrowerStatus'];
                $notes = $row['Notes'];
                $status = $row['Status'];

      		//Put these in a table row. The htmlentities function converts any
      		//special chars in the string to a html-displayable form.
      		echo '<tr>'; 
      		echo '<td><span align=center>'.htmlentities($bannerid).'</span></td>'; 
      		echo '<td><span algin=center>'.htmlentities($firstname).'</span></td>'; 
      		echo '<td><span align=center>'.htmlentities($lastname).'</span></td>'; 
      		echo '<td><span align=center>'.htmlentities($phone).'</span></td>'; 
      		echo '<td><span align=center>'.htmlentities($email).'</span></td>';
		echo '<td><span align=center>'.htmlentities($borrowerstatus).'</span></td>';
                echo '<td><span align=center>'.htmlentities($notes).'</span></td>';
                echo '<td><span align=center>'.htmlentities($status).'</span></td>';
		echo '</tr>'; 
   	} 
   	echo '</tbody>'; 
   	echo '</table>';
   	echo '</body></html>';
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

