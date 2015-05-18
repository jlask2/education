<?php
	// Connect to the database
    require('dbconnector.php');
    $db = get_connection();

    function do_work($post_data) {
        
        global $db;
        $rows = array();
        
        // Get the name and description sent by the user from the query
        $templatename = $post_data["template-name"];
        $desc = $post_data["template-desc"];
        $cbed = $post_data["CBED"];

        if($insStmt = $db->prepare("INSERT INTO tasktemplate (name, description, CBED) VALUES (?,?,?)")) {
            $insStmt->bind_param('sss', $templatename, $desc, $cbed);
            if($insStmt->execute()) {
                $rows['action'] = 'add';
                $rows['status'] = 'success_QUERY';
                $rows['name'] = $templatename;

                echo json_encode($rows);

                $insStmt->close();
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