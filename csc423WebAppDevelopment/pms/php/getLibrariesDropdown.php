<?php
//Connect to the database 
require('dbconnector.php');
$db = get_connection();


// build query
$query = "SELECT LibraryID, Name FROM tasklibrary";
$result = mysqli_query($db,$query);

function build_dd() {
    global $result;
    while($row = mysqli_fetch_array($result))
    {
        echo '<option value="'.$row['LibraryID'].'">' . $row['Name'] . '</option>';
    }
}

close_connection();
?>