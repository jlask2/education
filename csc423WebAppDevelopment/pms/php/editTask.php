<?php

// Connect to the database
require('dbconnector.php');
$db = get_connection();

function do_work($post_data) {

	global $db;
	$rows = array();
	
	// Get the task name, category, description, and completion
    $task_ID = $post_data["PrimaryKey"];
	$task_name = $post_data["task-name"];
	$task_description = $post_data["task-desc"];
	$task_CBED = $post_data["CBED"];
    $task_RCD = $post_data["RCD"];
	    
    $projCodeQuery = $db->prepare("SELECT ProjectCode FROM task WHERE TaskID = ?");
    $projCodeQuery->bind_param('s', $task_ID);
    $projCodeQuery->execute();
    $projCodeResult = $projCodeQuery->get_result();
    $projRow = $projCodeResult->fetch_assoc();
    $projName = $projRow['ProjectCode'];
    $projCodeQuery->close();
    
    $dueDateQuery = $db->prepare("SELECT EndDate FROM project WHERE ProjectCode = ?");
	$dueDateQuery->bind_param('s', $projName);
    $dueDateQuery->execute();
    $dueDateResult = $dueDateQuery->get_result();
    $dateRow = $dueDateResult->fetch_assoc();
    $dateDue = $dateRow['EndDate'];
    $dueDateQuery->close();
    
    // let's calculate the task Due Date
    $date = date_create($dateDue);
    date_sub($date,date_interval_create_from_date_string($task_CBED." days"));
    $dateStr = date_format($date,"Y-m-d");
    
    if (strcmp($task_RCD, "") == 0 ) {
        if($editStmt = $db->prepare("UPDATE `task` SET `Name`= ?,`Description`= ?, `CBED`= ?, `DueDate` = ? WHERE `TaskID` = ?")) {
            $editStmt->bind_param('sssss', $task_name, $task_description, $task_CBED, $dateStr, $task_ID);
            if($editStmt->execute()) {
                $rows['action'] = 'edit-task';
                $rows['status'] = 'success_QUERY';
                $rows['target'] = $post_data["PrimaryKey"];
                $rows['name'] = $task_name;
                $rows['desc'] = $task_description;
                $rows['cbed'] = $task_CBED;
				$rows['due'] = $dateStr;

                echo json_encode($rows);

                $editStmt->close();
            } else {
                // Couldn't execute the query
                $rows['status'] = 'failed_QUERY';
                echo json_encode($rows);
            }
        } else {
            // Couldn't prepare the statement
            $rows['status'] = 'failed_PREAPRE';
            echo json_encode($rows);
        }
    }
   else { 
       if($editStmt = $db->prepare("UPDATE `task` SET `Name`= ?,`Description`= ?, `CBED`= ?, `DueDate` = ?, `RCD` = ? WHERE `TaskID` = ?")) {
		   $editStmt->bind_param('ssssss', $task_name, $task_description, $task_CBED, $dateStr, $task_RCD, $task_ID);
           if($editStmt->execute()) {
               $rows['action'] = 'edit-task';
               $rows['status'] = 'success_QUERY';
               $rows['target'] = $post_data["PrimaryKey"];
               $rows['name'] = $task_name;
               $rows['desc'] = $task_description;
               $rows['cbed'] = $task_CBED;
			   $rows['due'] = $task_RCD;

               echo json_encode($rows);

               $editStmt->close();
           } else {
               // Couldn't execute the query
               $rows['status'] = 'failed_QUERY';
               echo json_encode($rows);
           }
       } else {
           // Couldn't prepare the statement
           $rows['status'] = 'failed_PREAPRE';
           echo json_encode($rows);
       }
   }
	close_connection();
}
?>