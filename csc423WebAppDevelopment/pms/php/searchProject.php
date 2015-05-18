<?php 
    // Connect to the database
    require('dbconnector.php');
    $db = get_connection();

    function do_work($post_data) {
        global $db;
        $rows = array();
        $proj_results = array();

        $projName = '%' . $post_data["search-project-name"] . '%';
        if($searchQuery = $db->prepare("SELECT * FROM project WHERE Name LIKE ?")) {
            $searchQuery->bind_param("s", $projName);
            if($searchQuery->execute()) {
                $result = $searchQuery->get_result();
                $rows['action'] = 'search-project';
                $rows['status'] = 'success_QUERY';
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
                    t.RCD FROM project p, task t WHERE p.ProjectCode = ? AND t.ProjectCode = ?";
                    if($assocQuery = $db->prepare($stmnt)) {
                        $assocQuery->bind_param("ss", $r["ProjectCode"],$r["ProjectCode"]);
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
        close_connection();
    }
?>