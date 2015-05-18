<?php

// Connect to the database
require('dbconnector.php');
$db = get_connection();

function do_work($post_data) {

	global $db;
	$rows = array();
	
	// Get the task name, category, description, and completion
	$template_code = $post_data["PrimaryKey"];
	$template_name = $post_data["template-name"];
	$template_description = $post_data["template-desc"];
	$template_CBED = $post_data["CBED"];

	if($editStmt = $db->prepare("UPDATE `tasktemplate` SET `Name`= ?,`Description`= ?,`CBED`= ? WHERE `TemplateCode` = ?")) {
		$editStmt->bind_param('ssss', $template_name, $template_description, $template_CBED, $template_code);
		if($editStmt->execute()) {
			$rows['action'] = 'edit-template';
			$rows['status'] = 'success_QUERY';
			$rows['target'] = $post_data["PrimaryKey"];
			$rows['name'] = $template_name;
			$rows['desc'] = $template_description;
			$rows['cbed'] = $template_CBED;

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