<?php 

    // Connect to the database
    require('dbconnector.php');
    $db = get_connection();

    function do_work($post_data) {
        global $db;
        $rows = array();
        $lib_results = array();
        
        $libName = '%' . $post_data["search-task-library-name"] . '%';
        if($searchQuery = $db->prepare("SELECT * FROM tasklibrary WHERE Name LIKE ?")) {
            $searchQuery->bind_param("s", $libName);
            if($searchQuery->execute()) {
                $result = $searchQuery->get_result();
                $rows['action'] = 'search';
                $rows['status'] = 'success_QUERY';
                $rows['move_view'] = 'false';
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