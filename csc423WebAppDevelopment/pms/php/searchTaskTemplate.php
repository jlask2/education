<?php 

// Connect to the database
require('dbconnector.php');
$db = get_connection();


global $db;
$rows = array();

if($searchQuery = $db->prepare("SELECT * FROM tasktemplate")) {
    if($searchQuery->execute()) {
        $result = $searchQuery->get_result();
        //$rows['action'] = 'search';
        //$rows['status'] = 'success_QUERY';
        //$rows['move_view'] = 'false';
        while($r = $result->fetch_assoc()) {
            $rows[] = $r;
        }
        echo json_encode($rows);
    } else {
        // Couldn't execute the query
        //$rows['status'] = 'failed_QUERY';
        echo json_encode($rows);
    }
} else {
    // Couldn't prepare the statement
    //$rows['status'] = 'failed_PREAPRE';
    echo json_encode($rows);
}
close_connection();
?>