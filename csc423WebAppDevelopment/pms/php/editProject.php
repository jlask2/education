<?php

// Connect to the database
require('dbconnector.php');
$db = get_connection();

function do_work($post_data) {

	global $db;
	$rows = array();
	
	// Get the task name, category, description, and completion
	$proj_code = $post_data["PrimaryKey"];
	$proj_name = $post_data["project-name"];
	$proj_description = $post_data["project-desc"];

	if($editStmt = $db->prepare("UPDATE `project` SET `Name`= ?,`Description`= ? WHERE `ProjectCode` = ?")) {
		$editStmt->bind_param('sss', $proj_name, $proj_description, $proj_code);
		if($editStmt->execute()) {
			$rows['action'] = 'edit-project';
			$rows['status'] = 'success_QUERY';
			$rows['target'] = $post_data["PrimaryKey"];
			$rows['name'] = $proj_name;
			$rows['desc'] = $proj_description;

			echo json_encode($rows);

			$editStmt->close();
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