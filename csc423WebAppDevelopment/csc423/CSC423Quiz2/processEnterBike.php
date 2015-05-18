<?php
   /*processEnterBike.php
   A PHP script to insert a new bicycle into the 
   jlask2_fall14_1.bicycle database*/

require('./enterBikeResult.inc');

// Main control logic
insert_bicycle();

//-------------------------------------------------------------
function insert_bicycle()
{
	/* Connect to the 'bicycle' database 
         The parameters are defined in the enterBikeResult.inc file
         These are global constants */

	connect_and_select_db(DB_SERVER, DB_UN, DB_PWD,DB_NAME);

	/* Get the information entered into the webpage by the user
         These are available in the super global variable $_POST
	 This is actually an associative array, indexed by a string*/

	$bike_id = $_POST['bike_id'];
		/*DEBUG*/echo "$bike_id \n";
	$make = $_POST['make'];
		/*DEBUG*/echo "$make \n";
	$model_number = $_POST['model_number'];
		/*DEBUG*/echo "$model_number \n";
	$serial_number = $_POST['serial_number'];
		/*DEBUG*/echo "$serial_number \n";
	$color = $_POST['color'];
		/*DEBUG*/echo "$color \n";        
	$description = $_POST['description'];
		/*DEBUG*/echo "$description \n";
	$location = $_POST['location'];
		/*DEBUG*/echo "$location \n";
	$physical_condition = $_POST['physical_condition'];
		/*DEBUG*/echo "$physical_condition \n";
	$notes = $_POST['notes'];  
		/*DEBUG*/echo "$notes \n";
        
	/* Create a String consisting of the SQL command. Remember that
         . is the concatenation operator. $varname within double 
	 quotes will be evaluated by PHP*/
	
	$result = false;

	if ($bike_id == null || $bike_id == ""){
		echo "Invalid Bike ID entered. Cannot be empty."
	}else{
		$insertStmt = "INSERT INTO jlask2_fall14_1.bicycle (ID, Make, ModelNumber, SerialNumber, Color, Description, Location, PhysicalCondition, Notes) values ('$bike_id', '$make', '$model_number', '$serial_number', '$color', '$description', '$location', '$physical_condition', '$notes')";
		//Execute the query. The result will just be true or false
       		$result = mysql_query($insertStmt);
	}

	$message = "";

	if (!$result) 
	{
  	  $message = "Error in inserting bicycle: $bike_id, $make, $model_number, $serial_number, $color, $description, $location, $physical_condition, $notes: ". mysql_error();
	}
	else
	{
	  $message = "Data for bicycle: $bike_id, $make, $model_number, $serial_number, $color, $description, $location, $physical_condition, $notes inserted successfully";
	}
	/*DEBUG*/echo "$message";
	
	$insertStmt->close_connection();
	display_enter_bike_results();		   
}

function display_enter_bike__results()
{
	echo "<html><head><title></title></head>"
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
