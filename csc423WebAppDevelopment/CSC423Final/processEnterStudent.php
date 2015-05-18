<?php
   /*processEnterBook.php
   A PHP script to insert a new student into the
   jlask2_fall14_1.studentborrower database*/

require('./enterStudentResults.inc');

// Main control logic
insert_student_borrower();

//-------------------------------------------------------------
function insert_student_borrower()
{
        /* Connect to the 'book' database
         The parameters are defined in the enterStudentResults.inc file
         These are global constants */

        connect_and_select_db(DB_SERVER, DB_UN, DB_PWD,DB_NAME);

        /* Get the information entered into the webpage by the user
         These are available in the super global variable $_POST
         This is actually an associative array, indexed by a string*/

        $bannerid = $_POST['bannerid'];
                /*DEBUG*///echo "$bannerid \n";
        $phonenumber = implode('-', $_POST['phonenumber']);
                /*DEBUG*///echo "$phonenumber \n";
        $firstname = $_POST['firstname'];
                /*DEBUG*///echo "$firstname \n";
        $lastname = $_POST['lastname'];
                /*DEBUG*///echo "$lastname \n";
        $email = $_POST['email'];
                /*DEBUG*///echo "$email \n";
        $notes = $_POST['notes'];
                /*DEBUG*///echo "$notes \n";
        
        /* Create a String consisting of the SQL command. Remember that
         . is the concatenation operator. $varname within double
         quotes will be evaluated by PHP*/

        $insertStmt = "INSERT INTO jlask2_fall14_1.studentborrower(`BannerID`, `FirstName`, `LastName`, `ContactPhone`, `Email`, 
		`DateOfLatestBorrowerStatus`, `DateOfFirstRegistration`, `Notes`, `Status`, `DateOfLastUpdate`) VALUES
                ('$bannerid', '$firstname', '$lastname', '$phonenumber', '$email', CURDATE(), CURDATE(), '$notes', 'Active', CURDATE())";
                    
        //Execute the query. The result will just be true or false
        $result = mysql_query($insertStmt);
        
	$message = "";

        if (!$result)
        {
          $message = "Error in inserting student borrower: $bannerid : ". mysql_error();
        }
        else
        {
          $message = "Data for book: $bannerid : inserted successfully";
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
