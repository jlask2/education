<?php 

    // Connect to the database
    require('dbconnector.php');
    $db = get_connection();

    function do_work($post_data) {
        global $db;
        $rows = array();
        $temp_results = array();
        
        $tempName = '%' . $post_data["search-task-template-name"] . '%';
        if($searchQuery = $db->prepare("SELECT * FROM tasktemplate WHERE Name LIKE ?")) {
            $searchQuery->bind_param("s", $tempName);
            if($searchQuery->execute()) {
                $result = $searchQuery->get_result();
                $rows['action'] = 'search';
                $rows['status'] = 'success_QUERY';
                while($r = $result->fetch_assoc()) {             
                    $temp_results[] = $r;
                }
                $rows["templates"] = $temp_results;
                $searchQuery->close();
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