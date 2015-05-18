// Global Variables
var msg;

function init_dashboard() {
    $('.main-content-area').load('dashboard.php', function(responseText,textStatus,req) {
        // If the dashboard cannot be initialized, load the 404 page
        if(textStatus == "error") {
            /*
                If the dashboard cannot initialize, then something went
                terribly wrong. We should probably create some kind of
                module that will reload the page after so many seconds
                to retry the initialization.
            */
        }

        $('#dash').addClass('active');

        // Bring the dashboard into view
        $('.main-content-area').animate(
            {
                'opacity':'1.0'
            }, 500
        );
    });
}

function transition_view(menuItem) {
    var selectedView = view_selector(menuItem);

    $('.main-content-area').animate(
        {
            'margin-left': $(document).width()/2 + 'px',
            'opacity':'0.0'
        }, 200, function() {
            $('.main-content-area').load(selectedView, function(responseText,textStatus,req) {
                // Bring the view back into the page
                $('.main-content-area').animate(
                    {
                        'margin-left':'0px',
                        'opacity':'1.0'
                    }, 200);
            });
        }
    );
}

function view_selector(menuItem) {
    switch(menuItem) {
        case 'dash' : return 'dashboard.php';
            break;
        case 'settings' : return 'settings.php';
            break;
        case 'new-proj' : return 'php_ui/newproject_ui.php';
            break;
        case 'search-proj' : return 'php_ui/searchproject_ui.php';
            break;
        /*case 'del-proj' : return 'deleteproject.php';
            break;*/
        case 'new-task-lib' : return 'php_ui/newtasklibrary_ui.php';
            break;
        case 'search-task-lib' : return 'php_ui/searchtasklibrary_ui.php';
            break;
        /*case 'del-task-lib' : return 'deletetasklibrary_ui.php';
            break;
        case 'add-task-tatm' : return 'addtasktemplatetotasklib_ui.php';
            break;*/
        case 'new-tatm' : return 'php_ui/newtasktemplate_ui.php';
            break;
        case 'search-tatm' : return 'php_ui/searchtasktemplate_ui.php';
            break;
        /*case 'del-tatm' : return 'deletetasktemplate_ui.php';
            break;*/
        case 'task-due-dates-rep' : return 'php_ui/runTaskDueDates_ui.php';
            break;
        case 'over-due-tasks-rep' : return 'php_ui/runOverDueTasks_ui.php';
            break;
        case 'late-tasks-rep' : return 'php_ui/runLateTasks_ui.php';
            break;
        case 'completed-tasks-rep' : return 'php_ui/runCompletedTasks_ui.php';
            break;
        default : return 'error404.html';
    }
}

function search_single(table_name, primary_key) {
    var data = {table: table_name, pk: primary_key};
    return $.ajax(
        {
            url: "/php/searchSingle.php",
            type: "POST",
            data: data
        });
}

function search_single(table_name, primary_key, secondary_key) {
    var data = {table: table_name, pk: primary_key, sk: secondary_key};
    return $.ajax(
        {
            url: "/php/searchSingle.php",
            type: "POST",
            data: data
        });
}

function complete_task(task_id) {
	var data = {PrimaryKey: task_id};
	return $.ajax(
        {
            url: "/php/setStatus.php",
            type: "POST",
            data: data
        });
}

function delete_task(task_id) {
	var data = {PrimaryKey: task_id};
	return $.ajax(
        {
            url: "/php/deleteTask.php",
            type: "POST",
            data: data
        });
}

function get_all_templates() {
    return $.ajax(
        {
            url : "/php/searchTaskTemplate.php",
            type: "POST"
        });
}

function call_searchTen(tableName, pKey) {

    var search_id = {table_id: tableName , p_key: pKey};

    return $.ajax(
        {
            url : "/php/searchTen.php",
            type: "POST",
            data : search_id
        });   
}

function call_searchTenReports(tableName, pKey, status, report) {

    var search_id = {table_id: tableName , p_key: pKey , task_status: status , calling_report: report};

    return $.ajax(
        {
            url : "/php/searchTenReports.php",
            type: "POST",
            data : search_id
        });   
}

function process_form_new(theForm, event) {

    var formData = theForm.serialize();

    return $.ajax(
        {
            url : "/php/processform.php",
            type: "POST",
            data : formData
        });
}

function process_form(theForm, event) {

    var formData = theForm.serialize();
    console.log(formData);
    var inputs = theForm.find("input, select, button, textarea");
    inputs.prop("disabled", true);

    $.ajax(
        {
            url : "/php/processform.php",
            type: "POST",
            data : formData
        })
    .done(function(result) {
        /*
            Fires when the PHP file is successfully
            found
        */
        console.log(result);
        analyze_results(result, inputs);
    })
    .fail(function() {
        /*
            Fires when PHP file cannot be found
        */
        msg = "Could Not Find PHP File";
        inputs.prop("disabled", false);
    })
    .always(function() {
        /* 
            This happens every time a database transaction
            occurs 
        */
        $.sticky(msg);
    });
    event.preventDefault();
}

/*
    Handles analyzing PHP results from the AJAX call
*/
function analyze_results(result, inputs) {
    var results = JSON.parse(result);
    var status = results.status;
    var action = results.action;
    var move_view = results.move_view;
    if(status.indexOf("success") >= 0) {
        // Success Status
        if(action === "search") {
            // Search Action
            if(inputs !== "none") {
                inputs.prop("disabled", false);
            }
            if(typeof results.libraries === "undefined") {
                // No Search Results
                $('#results-wrapper').empty();
                $('#results-wrapper').append('<h3 class="no-results">No Results Found</h3>');
                msg = "No search results!";
            } else {
                // Display Results
                msg = "Results displayed!";
                display_results(results);
            }
        /*} else if(action === "search_ten") {
            // SearchTen Action
            //inputs.prop("disabled", false);
            if(typeof results.libraries === "undefined") {
                // No Search Results
                $('#results-wrapper').empty();
                $('#results-wrapper').append('<h3 class="no-results">No Results Found</h3>');
                msg = "No search results!";
            } else {
                // Display Results
                msg = "Results displayed!";
                display_results(results);
            }*/
        } else if(action === "delete") { 
            $('#' + results.target).fadeOut(300, function() {
                $('#' + results.target).remove();
            });
        } else if (action === "add-templatetolib") {
            var template_code = results.TemplateCode;
            search_single("tasktemplate", template_code).done(function(result) {
                var results = JSON.parse(result);
                $('.active-library #templates-wrapper').find('h3').remove();
                $('.active-library #templates-wrapper').append('<div class="temp-result">' +                                                                            results[0].Name + 
                                                               '<span class="pull-right">' +                                                                            results[0].CBED + 
                                                               ' Days</span> </div>')
                $('.active-library').removeClass('active-library');
            })
            .fail(function() {
                // Fires when we can't find searchSingle.php
            });
        } else if(action === "add-task-project") {
            var project_code = results.ProjectCode;
            var task_name = results.TaskName;
            
            search_single("task", project_code, task_name).done(function(result) {
                var results = JSON.parse(result);
                $('.active-project #tasks-wrapper').find('h3').remove();
                $('.active-project #tasks-wrapper').append('<div id="' + results[0].TaskCode +'" ' + 
	                                                                    'class="task-result">' + results[0].Name + ' - ' +
	                                                                    ' <span class="due-date"> Due By ' + results[0].DueDate + ' </span>' + 
	                                                                    '<span class="pull-right">' +
	                                                                    '<a class="btn task-status-ip">' + 
	                                                                    results[0].Status + '</a>' +
	                                                                    '<a class="btn">Edit</a>' +
	                                                                    '<a class="btn">Delete</a>' +
	                                                                    '</span>' +
	                                                                    '</div>');
                $('.active-project').removeClass('active-project');
            })
            .fail(function() {
                // Fires when we can't find searchSingle.php
            });
        }
        
        if(move_view === "true") {
            // Move the view
            transition_view('dash');
            $('.nav-sidebar li').removeClass('active');
            $('#dash').addClass('active');
        }
        msg = "Successful Action!";
    } else if(status.indexOf("failed") >=0) {
        // Failed Status
        inputs.prop("disabled", false);
        msg = "Something went wrong!";
    }
}

function display_results(results) {
    $('#results-wrapper').empty();
    $.each(results.libraries, function(i, item) {

        if(item.Name != null) {
            var item_id = item.LibraryID;

            var result_box = '<div id="' + item.LibraryID + '" class="result-item"><h3>' + item.Name + '</h3>' +
                '<div id="content">' +
                '<h4>Description</h4>' +
                '<p>' + item.Description + '</p> ' + 
                '<h4>Associated Templates</h4>' +
                '<div id="templates-wrapper">' + 
                '</div>' + 
                '</div>' +
                '<div class="action-group">' + 
                '<a id="add-temp" class="btn btn-default">' + 
                '<span class="glyphicon glyphicon-plus"></span>' +
                ' Add Template</a>' +
                '<a id="edit-lib" class="btn btn-default">' + 
                '<span class="glyphicon glyphicon-pencil"></span>' +
                ' Edit</a>' +
                '<a id="delete-lib" class="btn btn-default">' +
                '<span class="glyphicon glyphicon-trash"></span>' +
                ' Delete</a>' + '</div>' +
                '</div>';
            $('#results-wrapper').append(result_box);
            if(item.templates.length > 0) {
                $.each(item.templates, function(i, temp) {
                    $('#' + item_id + " #templates-wrapper").append('<div id="' + temp.TemplateCode +'" ' + 
                                                                    'class="temp-result">' + temp.Name + 
                                                                    '<span class="temp-items pull-right">' +                                                                            temp.CBED + ' Days</span> </div>');
                });
            } else {
                $('#' + item_id + " #templates-wrapper").append('<h3 class="no-results">No Templates Found</h3>');
            }
            $('#' + item_id).fadeIn(300);
        }
    });
}

function final_validation(theForm) {
	var result = true;
	$(theForm).find(':input').each(function(){
		if($(this).attr('required')) {
			if($(this).val() == "" || $(this).val() == null || $(this).prop("selectedIndex") == 0) {
				$(this).parent().addClass('has-error');
			}
			if($(this).parent().hasClass('has-error')) {
				result = false;
			}
		}
	});
	return result;
}

function validate_field(field) {
	if(field.attr('type') === "text") {
		var field_name = field.attr('name');
		var field_text = field.val();
		switch(field_name) {
			case 'project-name' :
				var regTester = new RegExp('^([A-Za-z0-9\'\\s])+$');
				if(regTester.test(field_text)) {
					field.parent().removeClass('has-error').addClass('has-success');
				} else {
					field.parent().removeClass('has-success').addClass('has-error');
				}
			break;
			case 'project-end' :
				var regTester = new RegExp('^([0-9]{4})([\/])([0-9]{2})([\/])([0-9]{2})$');
				if(regTester.test(field_text)) {
					field.parent().parent().removeClass('has-error').addClass('has-success');
				} else {
					field.parent().parent().removeClass('has-success').addClass('has-error');
				}
				break;
		}
	} else if(field.is('select')) {
		if(field.prop("selectedIndex") == 0) {
			field.parent().removeClass('has-success').addClass('has-error');
		} else {
			field.parent().removeClass('has-error').addClass('has-success');
		}
	}
}
