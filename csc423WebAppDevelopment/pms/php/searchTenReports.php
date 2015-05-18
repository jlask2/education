<?php
//Connect to the database 
require('dbconnector.php');
$db = get_connection();
$rows = array();
$task_results = array();
//$tableName = $_POST['table_id'];
$pKey = $_POST['p_key'];
$status = $_POST['task_status'];
$reportType = $_POST['calling_report'];
$sqlQuery = "";
    
switch($reportType){
    case "completedTasks" :
    $sqlQuery = "SELECT DISTINCT t.TaskID, t.TaskCode, t.Name, t.ProjectCode, t.Description, t.DueDate, t.CBED, t.Status, t.ACD, t.RCD, p.Category FROM task t, project p WHERE p.ProjectCode=t.ProjectCode AND t.Status = ? AND (COALESCE(t.RCD, t.DueDate) >= t.ACD) ORDER BY t.$pKey DESC LIMIT 10";
    break;
    case "lateTasks" :
    $sqlQuery = "SELECT DISTINCT t.TaskID, t.TaskCode, t.Name, t.ProjectCode, t.Description, t.DueDate, t.CBED, t.Status, t.ACD, t.RCD, p.Category FROM task t, project p WHERE p.ProjectCode=t.ProjectCode AND t.Status = ? AND (COALESCE(t.RCD, t.DueDate) < t.ACD) ORDER BY t.$pKey DESC LIMIT 10";
    break;
    case "overDueTasks" :
    $sqlQuery = "SELECT DISTINCT t.TaskID, t.TaskCode, t.Name, t.ProjectCode, t.Description, t.DueDate, t.CBED, t.Status, t.ACD, t.RCD, p.Category FROM task t, project p WHERE p.ProjectCode=t.ProjectCode AND t.Status = ? AND (COALESCE(t.RCD, t.DueDate) < CURDATE()) ORDER BY t.$pKey DESC LIMIT 10";
    break;
    case "taskDueDates" :
    $sqlQuery = "SELECT DISTINCT t.TaskID, t.TaskCode, t.Name, t.ProjectCode, t.Description, t.DueDate, t.CBED, t.Status, t.ACD, t.RCD, p.Category FROM task t, project p WHERE p.ProjectCode=t.ProjectCode AND t.Status = ? AND (COALESCE(t.RCD, t.DueDate) >= CURDATE()) ORDER BY t.$pKey DESC LIMIT 10";
    break;
}
    

// Get the name and description sent by the user from the query
if($searchStmt = $db->prepare($sqlQuery)) {
    $searchStmt->bind_param("s", $status);
    if($searchStmt->execute()) {
        $result = $searchStmt->get_result();
        $rows['action'] = 'search';
        $rows['status'] = 'success';
        $rows['move_view'] = 'false';
        while($r = $result->fetch_assoc())
        {
            $task_results[$r['TaskID']] = $r;    
        }   
        $rows['libraries'] = $task_results;
        echo json_encode($rows);
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
$searchStmt->close();
close_connection();
?>