<?php
    //Connect to the database 
    require('dbconnector.php');
    $db = get_connection();
    
    $rows = array();
    
    $taskID = $_POST["PrimaryKey"];
    $comp = "Complete";
    $todayDate = date("Y-m-d");

    
if($statusQuery = $db->prepare("UPDATE `task` SET `Status` = ?, `ACD` = ? WHERE `TaskID` = ?")) {
	    $statusQuery->bind_param('sss', $comp, $todayDate, $taskID);
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