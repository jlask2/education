<?php
//deletetasklibrary.php
//A PHP script to delete a task library from the pms database.

// Connect to the database
require('dbconnector.php');
$db = get_connection();

if($delStmt = $db->prepare("DELETE FROM tasklibtotasktemp WHERE TemplateCode = ? AND LibraryID = ?")) {
    $delStmt->bind_param('ss', $_POST['PrimaryKey'], $_POST['LibraryID']);
    if($delStmt->execute()) {
        $rows['action'] = 'delete-lib-temp-assoc';
        $rows['status'] = 'success_QUERY';
        $rows['move_view'] = 'false';

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
?>