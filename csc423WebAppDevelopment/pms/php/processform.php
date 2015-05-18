<?php 

    $form_id = $_POST['FormID'];
    

    switch($form_id) {
        case "add-task-lib" :
            require("addTaskLibrary.php");
        break;
        case "del-task-lib" :
            require("deleteTaskLibrary.php");
        break;
        case "search-task-library" :
            require("searchTaskLibrary.php");
        break;
        case "add-temp-lib" :
            require("addTaskTemplateToLib.php");
        break;
        case "del-temp-lib" :
            require("deleteTaskTemplateFromLib.php");
        break;
        case "new-proj" :
            require("addProject.php");
        break;
        case "search-project" :
            require("searchProject.php");
        break;
        case "edit-proj" :
        	require("editProject.php");
        break;
        case "del-proj" :
            require("deleteProject.php");
        break;
        case "add-task" :
            require("addTask.php");
        break;
        case "add-task-temp" :
        	require("addTaskTemplate.php");
        break;
        case "search-task-temp" :
        	require("searchTaskTemplatesForSearch.php");
        break;
		case "edit-task-temp" :
			require("editTaskTemplate.php");
		break;
		case "del-task-temp" :
			require("deleteTaskTemplate.php");
        break;
		case "edit-task" :
			require("editTask.php");
		break;
        case "search-completed-tasks" :
            require("searchReports.php");
        break;
        case "search-over-due-tasks" :
            require("searchReports.php");
        break;
        case "search-late-tasks" :
            require("searchReports.php");
        break;
        case "search-task-due-dates" :
            require("searchReports.php");
        break;
    }
    
    do_work($_POST);

?>