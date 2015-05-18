<?php

	//Connect to the database 
    require('dbconnector.php');
    $db = get_connection();
    
    $rows = array();
    
    $taskID = $_POST["PrimaryKey"];
    
    if($statusQuery = $db->prepare("DELETE FROM `task` WHERE `TaskID` = ?")) {
	    $statusQuery->bind_param('s', $taskID);
		if($statusQuery->execute()) {
			$statusQuery->close();
			$rows['status'] = 'success_QUERY';
			$rows['target'] = $taskID;
			echo json_encode($rows);
		}
		else {
			$rows['status'] = 'failed_QUERY';
			echo json_encode($rows);
		}
    } else {
	    $rows['status'] = "failed_PREPARE";
	    echo json_encode($rows);
    }
    
    close_connection();

	
?>