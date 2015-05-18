<?php
    //Connect to the database 
    require('dbconnector.php');
    $db = get_connection();
    //global $db;
    $rows = array();
    $lib_results = array();
    $proj_results = array();
    $task_results = array();
    $tableName = $_POST['table_id'];
    $pKey = $_POST['p_key'];

    // Get the name and description sent by the user from the query
    if($searchStmt = $db->prepare("SELECT * FROM $tableName ORDER BY $pKey DESC LIMIT 10")) {
        if($searchStmt->execute()) {
            $result = $searchStmt->get_result();
            $rows['action'] = 'search';
            $rows['status'] = 'success_QUERY';
            $rows['move_view'] = 'false';
            if($tableName === 'tasklibrary') {
                while($r = $result->fetch_assoc()) {
                    $template_results = array();

                    $lib_results[$r["LibraryID"]] = $r;

                    $stmnt = "SELECT tt.TemplateCode, tt.Name, tt.Description, tt.CBED FROM tasklibrary lb, tasklibtotasktemp t, tasktemplate tt WHERE t.TemplateCode = tt.TemplateCode AND t.LibraryID = ? AND (lb.LibraryID = ?)";

                    // Query for All Associated Task Templates
                    if($assocQuery = $db->prepare($stmnt)) {
                        $assocQuery->bind_param("ss", $r["LibraryID"], $r["LibraryID"]);
                        if($assocQuery->execute()) {
                            $temp_result = $assocQuery->get_result();
                            while($temp = $temp_result->fetch_assoc()) {
                                $template_results[] = $temp;
                            }
                            $assocQuery->close();
                            $lib_results[$r["LibraryID"]]["templates"] = $template_results;            
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
                    $rows["libraries"] = $lib_results;
                }
                echo json_encode($rows);
            } else if($tableName === 'project') {
                while($r = $result->fetch_assoc()) {
                    $task_results = array();

                    $proj_results[$r["ProjectCode"]] = $r;
                    
                    $stmnt = "SELECT t.TaskID,
                    t.TaskCode,
                    t.Name,
                    t.ProjectCode,
                    t.Description,
                    t.DueDate,
                    t.CBED,
                    t.Status,
                    t.ACD,
                    t.RCD FROM project p, task t WHERE p.ProjectCode = ? AND t.ProjectCode = p.ProjectCode";

                    // Query for All Associated Tasks
                    if($assocQuery = $db->prepare($stmnt)) {
                        $assocQuery->bind_param("s", $r["ProjectCode"]);
                        if($assocQuery->execute()) {
                            $task_result = $assocQuery->get_result();
                            while($task = $task_result->fetch_assoc()) {
                                $task_results[] = $task;
                            }
                            $assocQuery->close();
                            $proj_results[$r["ProjectCode"]]["tasks"] = $task_results;          
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
                    $rows["projects"] = $proj_results;
                }
                echo json_encode($rows);
            } else if($tableName === 'tasktemplate') {
            } else if($tableName === 'task') {
                
            } else {
                //error message
            }
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