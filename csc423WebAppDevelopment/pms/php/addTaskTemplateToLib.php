<?php

// Connect to the database
require('dbconnector.php');
$db = get_connection();

function do_work($post_data) {

    global $db;

    if($addStmt = $db->prepare("INSERT INTO tasklibtotasktemp VALUES (? , ?)")) {
        $addStmt->bind_param("ss", $post_data['PrimaryKey'], $post_data['primary-key-temp']);
        if($addStmt->execute()) {
            $rows['action'] = 'add-templatetolib';
            $rows['status'] = 'success_QUERY';
            $rows['move_view'] = 'false';
            $rows['TemplateCode'] = $post_data['primary-key-temp'];

            echo json_encode($rows);

            $addStmt->close();
        } else {
            // Couldn't execute the query
            $rows['status'] = 'failed_QUERY';
            echo json_encode($rows);
        }
    } else {
        // Couldn't prepare the statement
        $rows['status'] = 'failed_PREPARE';
        echo json_encode($rows);
    }
       close_connection();
       }

?>