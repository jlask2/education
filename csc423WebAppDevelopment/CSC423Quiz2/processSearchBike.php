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
                /*DEBUG*///echo "$bike_id \n";
        $make = $_POST['make'];
                /*DEBUG*///echo "$make \n";
        $color = $_POST['color'];
                /*DEBUG*///echo "$color \n";
        $description = $_POST['description'];
                /*DEBUG*///echo "$description \n";
        $location = $_POST['location'];
                /*DEBUG*///echo "$location \n";
        $physical_condition = $_POST['physical_condition'];
                /*DEBUG*///echo "$physical_condition \n";

        /* Create a String consisting of the SQL command. Remember that
         . is the concatenation operator. $varname within double
         quotes will be evaluated by PHP*/
		
	if ($bike_id == null || $bike_id == ""){
	   $searchStmt = "SELECT *  FROM jlask2_fall14_1.bicycle WHERE ID LIKE
                '%$bike_id%'";
        //      echo "Invalid Bike ID entered. Cannot be empty."
        }else{
           $searchStmt = "SELECT *  FROM jlask2_fall14_1.bicycle WHERE Make LIKE '%$make%' AND Color LIKE '%$color%' AND 
		Description LIKE '%$description%' AND Location LIKE '%$location%' AND 
		PhysicalCondition LIKE '%$physical_condition%'";

		//Execute the query. The result will just be true or false
		//$results = mysql_query($searchStmt);
        }
	//Execute the query. The result will just be true or false
        $results = mysql_query($searchStmt);

        $message = "";

        if (!$results)
        {
          $message = "Error in searching for bicycle: $bike_id : ". mysql_error();
        }
        else
        {
          $message = "Search results of bicycles with the given input: $bike_id  found successfully";
        }
        /*DEBUG*/echo "$message";
	display_search_bike_results($results);

        $searchStmt->close_connection();
}

function display_search_bike_results($results){
	echo '<html><head><title>Search Bike Results</title></head>';
	echo '<body>';
	echo '<table border="1" align="center" width="100%">';
  	echo '<th width="16%">ID</th>';
  	echo '<th width="16%">Make</th>';
  	echo '<th width="16%">Color</th>';
  	echo '<th width="20%">Description</th>';
  	echo '<th width="16%">Location</th>';
	echo '<th width="16%">Condition</th>';
	echo '<thead>'; 
  	echo '</thead>'; 
  	echo '<tbody>'; 

	//While there are more rows in the $result, get the next row
   	//as an associative array
  	while ($row = mysql_fetch_assoc($results)) 
   	{
     		$id = $row['ID']; 
     		$make = $row['Make']; 
    		$color = $row['Color']; 
     		$description = $row['Description'];  
 		$location = $row['Location'];
		$condition = $row['PhysicalCondition'];

      		//Put these in a table row. The htmlentities function converts any
      		//special chars in the string to a html-displayable form.
      		echo '<tr>'; 
      		echo '<td><span align=center>'.htmlentities($id).'</span></td>'; 
      		echo '<td><span algin=center>'.htmlentities($make).'</span></td>'; 
      		echo '<td><span align=center>'.htmlentities($color).'</span></td>'; 
      		echo '<td><span align=center>'.htmlentities($description).'</span></td>'; 
      		echo '<td><span align=center>'.htmlentities($location).'</span></td>';
		echo '<td><span align=center>'.htmlentities($condition).'</span></td>';
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
