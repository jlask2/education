<?php
//Connect to the database 
require('dbconnector.php');
$db = get_connection();

function do_work($post_data) {

    global $db;
    $rowsResult = array();

    // Get the name and description sent by the user from the query
    $projectName = $post_data["project-name"];
    $projectCategory = $post_data["project-cat"];
    $projectEndDate = $post_data["project-end"];
    $assocTaskLibrary = $post_data["project-lib"];
    $projectDesc = $post_data["project-desc"];


    // this is for inserting the Name of the selected
    // library, not the LibraryID
    $libNameQuery = $db->prepare("SELECT Name FROM tasklibrary WHERE LibraryID = ?");
    $libNameQuery->bind_param('s', $assocTaskLibrary);
    $libNameQuery->execute();
    $libNameResult = $libNameQuery->get_result();
    $libRow = $libNameResult->fetch_assoc();
    $libName = $libRow['Name'];
    $libNameQuery->close();

    // this is for creating the ProjectCode
    $alphabetString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    $bannerCode = '';
    if (strcmp($projectCategory, "Bags") == 0 ) {
        $bannerCode = "BG";
    }
    else {
        $bannerCode = strtoupper(substr($projectCategory, 0, 2));
    }
    $todayDate = date("mdY");
    $projectCodeBeg = $bannerCode . $todayDate;
    $projectCodeBegQ = $projectCodeBeg . "%";
    $countProjectsQuery = $db->prepare("SELECT * FROM project WHERE ProjectCode LIKE ?");
    $countProjectsQuery->bind_param('s', $projectCodeBegQ);
    $countProjectsQuery->execute();
    $countProjectsQuery->store_result();
    $rowCo = $countProjectsQuery->num_rows;
    $projCode = '';
    if ($rowCo < 26) {
        $projCode = $projectCodeBeg . $alphabetString[$rowCo];
    }
    else {
        $numberCount = $rowCo - 26 + 1;
        $numberCountStr = (string)$numberCount;
        $projCode = $projectCodeBeg . $numberCount;
    }
    $countProjectsQuery->close();

    $insStmt = null;
    if (strcmp($assocTaskLibrary, "empty") == 0) {
        // insert the new project
       if( $insStmt = $db->prepare("INSERT INTO project (ProjectCode, Name, Description, Category, EndDate) VALUES (?,?,?,?,?)")) {
            $insStmt->bind_param('sssss', $projCode, $projectName, $projectDesc, $projectCategory, $projectEndDate);
            if($insStmt->execute()) {

                $rowsResult['action'] = 'add';
                $rowsResult['status'] = 'success_QUERY';
                $rowsResult['name'] = $projectName;
                echo json_encode($rowsResult);

                $insStmt->close();

            }
            else {
                // Couldn't execute the query
                $rows['status'] = 'failed_QUERY';
                echo json_encode($rowsResult);
            }
       } else {
            // Couldn't prepare the statement
            $rows['status'] = 'failed_PREAPRE';
            echo json_encode($rowsResult);
        }
    }
    else {
        // insert the new project
        if ($insStmt = $db->prepare("INSERT INTO project (ProjectCode, Name, Description, Category, EndDate, Library) VALUES (?,?,?,?,?,?)")) {
            $insStmt->bind_param('ssssss', $projCode, $projectName, $projectDesc, $projectCategory, $projectEndDate, $libName);
        if($insStmt->execute()) {

            // find the task templates associated with the
            // selected task library
            $taskTemplQuery = $db->prepare("SELECT t.TemplateCode, t.Name, t.Description, t.CBED FROM tasklibtotasktemp tt, tasktemplate t WHERE ((tt.LibraryID = ?) AND (tt.TemplateCode = t.TemplateCode))");
            $taskTemplQuery->bind_param("s", $assocTaskLibrary);
            $taskTemplQuery->execute();
            $taskResult = $taskTemplQuery->get_result();


            while ($row = $taskResult->fetch_assoc()) {

                // let's calculate the Due Date
                $date = date_create($projectEndDate);
                date_sub($date,date_interval_create_from_date_string($row['CBED']." days"));
                $dateStr = date_format($date,"Y/m/d");

                // insert each task template result one by one into
                // the task table as new tasks
                $query = "INSERT INTO task (TaskCode, Name, ProjectCode, Description, DueDate, CBED, Status) VALUES ('".$row['TemplateCode']."', '".$row['Name']."', '".$projCode."', '".$row['Description']."', '".$dateStr."', '".$row['CBED']."', 'In Progress')"; 
                $tasks = mysqli_query($db,$query);
            }
            $rowsResult['action'] = 'add';
            $rowsResult['status'] = 'success_QUERY';
            $rowsResult['name'] = $projectName;
            echo json_encode($rowsResult);

            $taskTemplQuery->close();
            $insStmt->close();

        }
        else {
            // Couldn't execute the query
            $rows['status'] = 'failed_QUERY';
            echo json_encode($rowsResult);
        }
        } else {
            // Couldn't prepare the statement
            $rows['status'] = 'failed_PREAPRE';
            echo json_encode($rowsResult);
        }
    }

    close_connection();
}
?>