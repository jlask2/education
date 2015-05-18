<?php

// Connect to the database
require('dbconnector.php');
$db = get_connection();

function do_work($post_data) {

	global $db;

	if($delStmt = $db->prepare("DELETE FROM tasktemplate WHERE TemplateCode = ?")) {
		$delStmt->bind_param('s', $post_data['PrimaryKey']);
		if($delStmt->execute()) {
			$rows['action'] = 'delete';
			$rows['status'] = 'success_QUERY';
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
}

?>