<?php
require('dbconnector.php');
// Define and perform the SQL query
$allTasks = $db->prepare("SELECT TaskID, Name, Description, DueDate, RCD FROM Task WHERE (COALESCE(RCD, DueDate) < CURDATE())");
$allTasks->execute();

// Create the beginning of HTML table, and the first row with columns title
$html_table = '<table border="1" cellspacing="0" cellpadding="2"><tr><th>Task</th><th>Name</th><th>Description</th><th>DueDate</th></tr>';

// Parse the result set, and adds each row and columns in HTML table
$result = $allTasks->get_result();

while ($row = $result->fetch_assoc()) {
    $html_table .= '<tr><td>'. $row['TaskID'] .'</td><td>' . $row['Name'] . '</td><td>' . $row['Description'] . '</td><td>' . $row['DueDate'] . '</td><td>' . $row['RCD'] . '</td></tr>';
}
$html_table .= '</table>';
// ends the HTML table

echo $html_table;

$allTasks->close();
// Disconnect ?>