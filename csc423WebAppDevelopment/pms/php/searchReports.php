<?php 
// Connect to the database
require('dbconnector.php');
$db = get_connection();

function do_work($post_data) {
    global $db;
    global $sqlQuery;
    
    $rows = array();
    $task_results = array();

    $projectCode = '%' . $post_data["search-tasks-by-project-name"] . '%';
    $projectCategory = $post_data["project-cat"];
    
    $form_id = $post_data['FormID'];

    //echo "form_id: " . $form_id . " . " ;
    
    switch($form_id){
        case "search-completed-tasks":
        if(($projectCategory === "Select a Category") || ($projectCategory === "---------")) {
            $sqlQuery = "SELECT DISTINCT t.TaskID, t.TaskCode, t.Name, t.ProjectCode, t.Description, t.DueDate, t.CBED, t.Status, t.ACD, t.RCD, p.Category FROM task t, project p WHERE t.ProjectCode LIKE ?  AND p.ProjectCode=t.ProjectCode AND  t.Status='Complete' AND (COALESCE(t.RCD, t.DueDate) >= t.ACD)";
        } else {
            $sqlQuery = "SELECT DISTINCT t.TaskID, t.TaskCode, t.Name, t.ProjectCode, t.Description, t.DueDate, t.CBED, t.Status, t.ACD, t.RCD, p.Category FROM task t, project p WHERE p.Category= ? AND t.ProjectCode LIKE ? AND p.ProjectCode=t.ProjectCode AND t.Status='Complete' AND (COALESCE(t.RCD, t.DueDate) >= t.ACD)";
        }
        break;
        case "search-late-tasks":
        if(($projectCategory === "Select a Category") || ($projectCategory === "---------")) {
            $sqlQuery = "SELECT DISTINCT t.TaskID, t.TaskCode, t.Name, t.ProjectCode, t.Description, t.DueDate, t.CBED, t.Status, t.ACD, t.RCD, p.Category FROM task t, project p WHERE t.ProjectCode LIKE ?  AND p.ProjectCode=t.ProjectCode AND  t.Status='Complete' AND (COALESCE(t.RCD, t.DueDate) < t.ACD)";        
        } else {
            $sqlQuery = "SELECT DISTINCT t.TaskID, t.TaskCode, t.Name, t.ProjectCode, t.Description, t.DueDate, t.CBED, t.Status, t.ACD, t.RCD, p.Category FROM task t, project p WHERE p.Category= ? AND t.ProjectCode LIKE ? AND p.ProjectCode=t.ProjectCode AND t.Status='Complete' AND (COALESCE(t.RCD, t.DueDate) < t.ACD)";
        }
        break;
        case "search-over-due-tasks":
        if(($projectCategory === "Select a Category") || ($projectCategory === "---------")) {
            $sqlQuery = "SELECT DISTINCT t.TaskID, t.TaskCode, t.Name, t.ProjectCode, t.Description, t.DueDate, t.CBED, t.Status, t.ACD, t.RCD, p.Category FROM task t, project p WHERE t.ProjectCode LIKE ?  AND p.ProjectCode=t.ProjectCode AND t.Status='In Progress' AND (COALESCE(t.RCD, t.DueDate) < CURDATE())";
        } else {
            $sqlQuery = "SELECT DISTINCT t.TaskID, t.TaskCode, t.Name, t.ProjectCode, t.Description, t.DueDate, t.CBED, t.Status, t.ACD, t.RCD, p.Category FROM task t, project p WHERE p.Category= ? AND t.ProjectCode LIKE ? AND p.ProjectCode=t.ProjectCode AND t.Status='In Progress' AND (COALESCE(t.RCD, t.DueDate) < CURDATE())";
        }
        break;
        case "search-task-due-dates":
        if(($projectCategory === "Select a Category") || ($projectCategory === "---------")) {
            $sqlQuery = "SELECT DISTINCT t.TaskID, t.TaskCode, t.Name, t.ProjectCode, t.Description, t.DueDate, t.CBED, t.Status, t.ACD, t.RCD, p.Category FROM task t, project p WHERE t.ProjectCode LIKE ?  AND p.ProjectCode=t.ProjectCode AND t.Status='In Progress' AND (COALESCE(t.RCD, t.DueDate) >= CURDATE())";
        } else {
            $sqlQuery = "SELECT DISTINCT t.TaskID, t.TaskCode, t.Name, t.ProjectCode, t.Description, t.DueDate, t.CBED, t.Status, t.ACD, t.RCD, p.Category FROM task t, project p WHERE p.Category= ? AND t.ProjectCode LIKE ? AND p.ProjectCode=t.ProjectCode AND t.Status='In Progress' AND (COALESCE(t.RCD, t.DueDate) >= CURDATE())";
        }
        break; 
    }

    // Get the name and description sent by the user from the query
    if($searchStmt = $db->prepare($sqlQuery)) {
        if(($projectCategory === "Select a Category") || ($projectCategory === "---------")) {
            $searchStmt->bind_param("s", $projectCode);
        } else {
            $searchStmt->bind_param("ss", $projectCategory, $projectCode);
        }
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
}
?>