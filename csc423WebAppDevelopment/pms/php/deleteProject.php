<?php
//deleteProject.php
//A PHP script to delete a Project in the pms database (project).

// Connect to the database
require('dbconnector.php');
$db = get_connection();

    function do_work($post_data) {

        global $db;

        if($delstmt = $db->prepare("DELETE FROM project WHERE ProjectCode = ?")) {
            $delstmt->bind_param('s', $_POST["PrimaryKey"]);
            if($delstmt->execute()) { 
                $rows['action'] = 'delete';
                $rows['status'] = 'success_QUERY';
                $rows['move_view'] = 'false';
                $rows['target'] = $post_data["PrimaryKey"];
                $delstmt->close();
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

        if($delStmt = $db->prepare("DELETE FROM task WHERE ProjectCode = ?")) {
            $delStmt->bind_param('s', $post_data['PrimaryKey']);
            if($delStmt->execute()) {
                $rows['action'] = 'delete';
                $rows['status'] = 'success_QUERY';
                $rows['move_view'] = 'false';
                $rows['target'] = $post_data["PrimaryKey"];

                echo json_encode($rows);

                $delStmt->close();
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
        //searchproject_ui.php.update_display();
    }
?>