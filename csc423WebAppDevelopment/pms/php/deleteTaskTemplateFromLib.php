<?php
// deleteTaskTemplateFromLib.php
// A PHP script to delete a task template from a task library in the pms database(tasklibtotasktemp).


// Connect to the database
require('dbconnector.php');
$db = get_connection();

function do_work($post_data) {

    global $db;

    if($delStmt = $db->prepare("DELETE FROM tasklibtotasktemp WHERE LibraryID=? AND TemplateCode=?")) {
        $delStmt->bind_param('ss', $post_data['PrimaryKey'], $post_data['primary-key-temp']);
        //$delstmt->bind_param('ss', $_POST["deletetasktemp-libid"], $_POST["deletetasktemp-//tempcode"]);
        if($delStmt->execute()) {
            $rows['action'] = 'delete';
            $rows['status'] = 'success_QUERY';
            $rows['move_view'] = 'false';
            //$rows['target'] = $post_data["PrimaryKey"];

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
}

?>