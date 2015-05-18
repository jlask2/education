<?php
//Connect to the database 
require('dbconnector.php');
$db = get_connection();

function do_work($post_data) {

    global $db;
    $rowsResult = array();

    // Get the templateCode and projectCode sent by 
    //the user from the query
    //$taskTempCode = $post_data["task-temp"];
    $projCode = $post_data["PrimaryKey"];
    $taskName = $post_data["task-name"];
    $taskDesc = $post_data["task-desc"];
    $taskCBED = $post_data["task-CBED"];
    $taskStatus = "In Progress";

    // get the Project's End Date
    // and set to $projectEndDate
    $projQuery = $db->prepare("SELECT EndDate FROM project WHERE (ProjectCode = ?)");
    $projQuery->bind_param("s", $projCode);
    $projQuery->execute();
    $projResult = $projQuery->get_result();
    $projRow = $projResult->fetch_assoc();
    $projectEndDate = $projRow['EndDate'];
    $projQuery->close();
    
   /* // find information on the user selected task template
    $taskTemplQuery = $db->prepare("SELECT Name, Description, CBED FROM tasktemplate WHERE (TemplateCode = ?)");
    $taskTemplQuery->bind_param("s", $taskTempCode);
    $taskTemplQuery->execute();
    $taskResult = $taskTemplQuery->get_result();
    $row = $taskResult->fetch_assoc();*/
    
    // let's calculate the task Due Date
    $date = date_create($projectEndDate);
 date_sub($date,date_interval_create_from_date_string($taskCBED." days"));
    $dateStr = date_format($date,"Y/m/d");

    // insert the task template as a
    // new task in the task table
    if($taskQuery = $db->prepare("INSERT INTO task (Name, ProjectCode, Description, DueDate, CBED, Status) VALUES (?, ?, ?, ?, ?, ?)")) {
    $taskQuery->bind_param("ssssss", $taskName, $projCode, $taskDesc, $dateStr, $taskCBED, $taskStatus);
    if ($taskQuery->execute()) {

        $rowsResult['action'] = 'add-task-project';
        $rowsResult['status'] = 'success_QUERY';
        $rowsResult['move_view'] = 'false';
        $rowsResult['ProjectCode'] = $post_data['PrimaryKey'];
        $rowsResult['TaskName'] = $post_data['task-name'];

        echo json_encode($rowsResult);

    } else {
        // Couldn't execute the query
        $rows['status'] = 'failed_QUERY';
        echo json_encode($rowsResult);
    }
} else {
    // Couldn't prepare the statement
    $rows['status'] = 'failed_PREPARE';
    echo json_encode($rowsResult);
}
close_connection();
}
?>