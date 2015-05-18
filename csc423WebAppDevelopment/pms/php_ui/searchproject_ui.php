<h1 class="page-header">Search Existing Projects</h1>

<form role="form" id="searchproject-form" name="searchproject-form">
    <input type="hidden" name="FormID" value="search-project">
    <div class="row">
         <div class="col-md-12 col-lg-12">
            <div class="form-group">
                <label for="search-project-name">Search For A Project</label>
                <div class="input-group">
                    <input type="text" name="search-project-name" id="search-project-name" class="form-control">
                    <span class="input-group-btn">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-search"></span>
                            Search
                        </button>
                    </span>
                </div>
            </div>
        </div>
    </div>
    <hr/>
</form>

<div class="row">
	<div class="col-md-12 col-lg-12">
		<div id="notification-area">
		</div>
	</div>
</div>

<div class="row">
    <div class="col-md-12 col-lg-12">
        <div id="results-wrapper" class="results-wrapper"></div>
    </div>
</div>

<script type="text/javascript" src="/js/clickhandler.js"></script>
<script>
	//=========================================================
    // Global Variables
    //=========================================================
    var searchString = "";
    
    $(function() {
        
        // Focus the initial search box
        $('#search-project-name').focus();
        
        
        //=========================================================
	    // Recent Project Search Code
	    //=========================================================
        call_searchTen("project", "ProjectCode").done(function(result) {
            var results = JSON.parse(result);
            var status = results.status;
			
            if(status.indexOf("success") >= 0) {
	            $('#results-wrapper').empty();
                $('#results-wrapper').append('<h3 class="no-results">Recent Projects</h3>');
                display_project_results(results);
            } else if(status.indexOf("failed") >=0) {
                // Display an error somewhere on the screen
            }
        })
        .fail(function() {
	        // Send the user off to the connection error page
        });
        //=========================================================
	    // End Recent Project Search Code
	    //=========================================================
		
		//=========================================================
	    // Project Search Code
	    //=========================================================
        $("#searchproject-form").submit(function(event) {
            var theForm = $(this);
            
            process_form_new(theForm, event).done(function(result) {
                var results = JSON.parse(result);
                var status = results.status;
                
                if(status.indexOf("success") >= 0) {
	                $('#results-wrapper').empty();
                    if($('#search-project-name').val() === "") {
                        searchString = "All Projects";
                    } else {
                        searchString = 'Projects Matching "' + $('#search-project-name').val() + '"';
                    }
                    $('#results-wrapper').append('<h3 class="no-results">' + searchString + '</h3>');
                    display_project_results(results);
                } else if(status.indexOf("failed") >=0) {
                    // Show an error somewhere on the page
                }
            })
            .fail(function() {
                inputs.prop("disabled", false);
            });
            event.preventDefault();
        });
        //=========================================================
	    // End Project Search Code
	    //=========================================================
		
		//=========================================================
	    // Click Handling and Animation Code
	    //=========================================================
        $(document).off().on("click",".result-item",function(e){
            if($(e.target).hasClass('btn')) {
                var target_project = $(e.target).parent().parent();
                var primary_key = $(this).attr("id");
                var button_id = $(e.target).attr("id");

                click_event(button_id, primary_key, target_project);
                return;
            }

            if($(e.target).hasClass('glyphicon')) {
                var target_project = $(e.target).parent().parent().parent();
                var primary_key = $(this).attr("id");
                var button_id = $(e.target).parent().attr("id");
                click_event(button_id, primary_key, target_project);
                return;
            }

            if(!$(this).hasClass('active')) {
                $(this).stop().addClass('active').animate(
                    {
                        'max-height': '500px',
                        'height': '500px'
                    }, 200
                );
            } else {
                $(this).stop().removeClass('active').animate(
                    {
                        'max-height': '56px',
                        'height': '56px'
                    }, 200
                );
            }
        });
        //=========================================================
	    // End Click Handling and Animation Code
	    //=========================================================
	    
	    //=========================================================
	    // Results Display Code
	    //=========================================================
	    function display_project_results(results) {
	        if(typeof results.projects === "undefined") {
	            // No Search Results
	            $('#results-wrapper').empty();
	            $('#results-wrapper').append('<h3 class="no-results">No Results Found</h3>');
	        } else {
	            // Display Results            
	            $.each(results.projects, function(i, item) {
	
	                var item_id = item.ProjectCode;
	
	                var result_box = '<div id="' + item_id + 
	                                 '" class="result-item"><h3>' + 
	                    item.Name + '</h3>' +
	                                '<div id="content">' +
	                                '<span id="text-info">' +
	                                '<h4>Description</h4>' +
	                                '<p>' + item.Description + '</p> ' + 
	                                '</span>' +
	                                '<h4>Project End Date</h4>' +
	                                '<p><span id="end-date">' + item.EndDate + '</span></p> ' + 
	                                '<h4>Tasks</h4>' +
	                                '<div id="tasks-wrapper">' + 
	                                '</div>' + 
	                                '</div>' +
	                                '<div class="action-group">' + 
	                                '<a id="add-task" class="btn btn-default">' + 
	                                '<span class="glyphicon glyphicon-plus"></span>' +
	                                ' Add Task</a>' +
	                                '<a id="edit-proj" class="btn btn-default">' + 
	                                '<span class="glyphicon glyphicon-pencil"></span>' +
	                                ' Edit</a>' +
	                                '<a id="delete-proj" class="btn btn-default">' +
	                                '<span class="glyphicon glyphicon-trash"></span>' +
	                                ' Delete</a>' + '</div>' +
	                                '</div>';
	                $('#results-wrapper').append(result_box);
	                if(item.tasks.length > 0) {
	                    $.each(item.tasks, function(i, task) {
		                    var status_class;
		                    var due_date;
							
							if(task.RCD === "0000-00-00") {
								due_date = task.DueDate;
							} else {
								due_date = task.RCD;
							}
							
		                    if(task.Status == 'Complete') {
			                    status_class = 'task-status-complete';
		                    } else {
			                    status_class = 'task-status-ip';
		                    }
	                        $('#' + item_id + " #tasks-wrapper").append('<div id="' + task.TaskID +'" ' + 
	                                                                    'class="task-result">' + 
																		'<span id="task-info">' +
																		task.Name + ' - ' +
	                                                                    ' <span class="due-date"> Due By ' + due_date + 
																		' </span>' + 
																		'</span>' +
	                                                                    '<span class="pull-right">' +
	                                                                    '<a id="comp-task" class="btn task-status ' + status_class + '">' + 
	                                                                    task.Status + '</a>' +
	                                                                    '<a id="edit-task" class="btn">Edit</a>' +
	                                                                    '<a id="del-task" class="btn">Delete</a>' +
	                                                                    '</span>' +
	                                                                    '</div>');
	                    });
	                } else {
	                    $('#' + item_id + " #tasks-wrapper").append('<h3 class="no-results">No Tasks Found</h3>');
	                }
	                $('#' + item_id).fadeIn(300);
	            });
	        }
	    }
	    //=========================================================
	    // End Results Display Code
	    //=========================================================
    });
</script>