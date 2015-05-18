<?php

    require('dbconnector.php');
    $db = get_connection();

    $rows = array();

    // Prepare result header
    $rows['action'] = 'search_single';
    $rows['move_view'] = 'false'; // May soon be unused

    $table_name = $_POST['table'];
    $primary_key = $_POST['pk'];

    // Decide what we're searching for and set the
    // primary row's name so we can access it
    switch($table_name) {
        case 'tasklibrary' :
            $row_name = 'LibraryID';
        break;
        case 'tasktemplate' :
            $row_name = 'TemplateCode';
        break;
        case 'task' :
            $row_name_one = 'ProjectCode';
            $row_name_two = 'Name';
        break;
    }
    
    if($table_name == 'task') {
        
        $secondary_key = $_POST['sk'];
        if($searchStmt = $db->prepare('SELECT * FROM ' . $table_name . ' WHERE ' . $row_name_one . ' = "' . $primary_key . '" AND ' . $row_name_two . ' = "' . $secondary_key . '"')) {
            if($searchStmt->execute()) {
                $result = $searchStmt->get_result();
                $rows['status'] = "success_QUERY";
                
                // We don't need to do anything special, just return results
                while($r = $result->fetch_assoc()) {
                    $rows[] = $r;
                }
                $searchStmt->close();
                echo json_encode($rows);
            } else {
                $rows['status'] = "failed_QUERY";
                echo json_encode($rows);
            }
        } else {
            $rows['status'] = "failed_PREPARE";
            echo json_encode($rows);
        }

        close_connection();
    } else {
        if($searchStmt = $db->prepare("SELECT * FROM $table_name WHERE $row_name = $primary_key")) {
            if($searchStmt->execute()) {
                $result = $searchStmt->get_result();
                $rows['status'] = "success_QUERY";
                if($table_name === "tasklibrary") {
                    // We need to get all associated templates too
                    while($r = $result->fetch_assoc()) {
                        $library = array();
                        $templates = array();

                        $library[$r["LibraryID"]] = $r;

                        $stmnt = "SELECT tt.TemplateCode, tt.Name, tt.Description, tt.CBED " .
                                 "FROM tasklibrary lb, tasklibtotasktemp t, tasktemplate tt " .
                                 "WHERE t.templateCode = tt.TemplateCode AND t.LibraryID = ? " .
                                 "AND lb.LibraryID = ?";

                        //Query for ALL associated Task Templates
                        if($assocQuery = $db->prepare($stmnt)) {
                            $assocQuery->bind_param("ss", $r["LibraryID"], $r["LibraryID"]);
                            if($assocQuery->execute()) {
                                $template_results = $assocQuery->get_result();
                                while($temp = $template_results->fetch_assoc()) {
                                    $templates[] = $temp;
                                }
                                $assocQuery->close();
                                $library[$r["LibraryID"]]["templates"] = $templates;
                            } else {
                                $rows['status'] = "failed_QUERY";
                                echo json_encode($rows);
                            }
                        } else {
                            $rows['status'] = "failed_PREPARE";
                            echo json_encode($rows);
                        }
                        $rows[] = $library;
                    }
                    $searchStmt->close();
                } else {
                    // We don't need to do anything special, just return results
                    while($r = $result->fetch_assoc()) {
                        $rows[] = $r;
                    }
                    $searchStmt->close();
                }
                echo json_encode($rows);
            } else {
                $rows['status'] = "failed_QUERY";
                echo json_encode($rows);
            }
        } else {
            $rows['status'] = "failed_PREPARE";
            echo json_encode($rows);
        }

        close_connection();
    }
?>
