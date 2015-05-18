<h1 class="page-header">Search For Completed Tasks</h1>

<form role="form" id="searchtaskscompleted-form" name="searchtaskscompleted-form">
    <input type="hidden" name="FormID" value="search-completed-tasks">
    <div class="row">
        <div class="col-md-6 col-lg-6">
            <div class="form-group">
                <label for="search-tasks-by-project-name">Search For A Task By Project Name</label>
                <div class="input-group">
                    <input type="text" name="search-tasks-by-project-name" id="search-tasks-by-project-name" class="form-control">
                    <span class="input-group-btn">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-search"></span>
                            Search
                        </button>
                    </span>
                </div>
            </div>
        </div>
        <div class="col-md-6 col-lg-6">
            <div class="form-group">
                <label class="control-label" for="project-cat">Search For A Task By Project Category</label>
                <select name="project-cat" id="project-cat" class="form-control">
                    <option>Select a Category</option>
                    <option>---------</option>
                    <option>Bags</option>
                    <option>Banner</option>
                    <option>Billboard</option>
                    <option>Brochures</option>
                    <option>Catalog</option>
                    <option>Coupon Book</option>
                    <option>Digital</option>
                    <option>Grand Opening Banner</option>
                    <option>Handout</option>
                    <option>Hanging Signs</option>
                    <option>Insert (Newspaper)</option>
                    <option>Magazine Ad</option>
                    <option>Radio</option>
                    <option>Television</option>
                </select>
            </div>
        </div>
    </div>
    <hr/>
</form>

<div class="row">
    <div class="col-md-12">
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
        $('#search-tasks-by-project-name').focus();
        
        //=========================================================
	    // Recent Completed Tasks Search
	    //=========================================================
        call_searchTenReports("task", "TaskID", "Complete", "completedTasks").done(function(result) {
            var results = JSON.parse(result);
            var status = results.status;
            
            if(status.indexOf("success") >= 0) {
	            $('#results-wrapper').empty();
	            $('#results-wrapper').append('<h3 class="no-results">Recent Completed Tasks</h3>');
	            display_library_results(results);
	        } else if(status.indexOf("failed") >= 0) {
		        // Show an error somewhere on the page
	        }
        })
        .fail(function() {
			// Send the user off to the connection error page
        });
        //=========================================================
	    // End Recent Completed Tasks Search
	    //=========================================================
        
        //=========================================================
        // Task Library Search Code
        //=========================================================
        $("#searchtaskscompleted-form").submit(function(event) {
            var theForm = $(this);
            
            process_form_new(theForm, event).done(function(result) {
	            var results = JSON.parse(result);
	            var status = results.status;
	            
	            if(status.indexOf("success") >= 0) {
		            $('#results-wrapper').empty();
                    if(results.libraries.length <= 0) {
                        searchString = "No Results Found";
                    } else if(($('#search-tasks-by-project-name').val() === "")
                       &&(($('#project-cat').val() === "Select a Category")||($('#project-cat').val() === "---------"))) {
		                searchString = "All Completed Tasks";
                    } else if(($('#search-tasks-by-project-name').val() === "")
                              &&($('#project-cat').val() !== "Select a Category")&&($('#project-cat').val() !== "---------")) {
                        searchString = 'Completed Tasks Matching Selected Category "' + $('#project-cat').val() + '"';
                    } else if(($('#search-tasks-by-project-name').val() !== "")
                              &&($('#project-cat').val() !== "Select a Category")&&($('#project-cat').val() !== "---------")) {
                        searchString = 'Completed Tasks Matching Selected Category "' + $('#project-cat').val() + '" With Project Name Like "' + $('#search-tasks-by-project-name').val() + '"';
                    } else if(($('#search-tasks-by-project-name').val() !== "")
                              &&(($('#project-cat').val() === "Select a Category")||($('#project-cat').val() === "---------"))) {
                        searchString = 'Completed Tasks Matching Project Name Like "' + $('#search-tasks-by-project-name').val() + '"';
                    } else {
                    }
			        $('#results-wrapper').append('<h3 class="no-results">' + searchString + '</h3>');
			        display_library_results(results);
	            } else if(status.indexOf("failed") >= 0) {
		            // Show an error somewhere on the page
	            }
		    })
		    .fail(function() {
				// Send the user off to the connection error page
		    });
		    event.preventDefault();
        });
        //=========================================================
        // End Completed Tasks Search Code
        //=========================================================
        
        //=========================================================
        // Click Handling and Animation Code
        //=========================================================
        
        $(document).off().on("click",".result-item",function(e){
            if($(e.target).hasClass('btn')) {
                var target_library = $(e.target).parent().parent();
                var primary_key = $(this).attr("id");
                var button_id = $(e.target).attr("id");

                click_event(button_id, primary_key, target_library);
                return;
            }
            
            if($(e.target).hasClass('glyphicon')) {
                var target_library = $(e.target).parent().parent().parent();
                var primary_key = $(this).attr("id");
                var button_id = $(e.target).parent().attr("id");
                
                click_event(button_id, primary_key, target_library);
                return;
            }
            
            if(!$(this).hasClass('active')) {
                $(this).stop().addClass('active').animate(
                    {
                        'max-height': '400px',
                        'height': '400px'
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
        function display_library_results(results) {
	        //console.log(results);
            //console.log(results.libraries);
            //$item = array(); 
            //$item = results.libraries;
            if(typeof results.libraries.length <= 0) {
                // No Search Results
                $('#results-wrapper').empty();
                $('#results-wrapper').append('<h3 class="no-results">No Results Found</h3>');
            } else {
                // Display Results
                $.each(results.libraries, function(i, item) {

                    if(item.Name != null) {
                        var item_id = item.TaskID;

                        var result_box = '<div id="' + item.TaskID + '" class="result-item"><h3>' + item.Name + '<span class="pull-right col-md-6 col-lg-6">' + item.ProjectCode + '</span>' +
                            '<span class="pull-right">' + item.Category + '</span></h3>' + 
                            '<div id="content">' + 
                            '<h4>Task Due Date<span class="pull-right col-md-6 col-lg-6">'+
                            'Task Completion Before End Date</span></h4>' +
                            '<p>' + item.DueDate + '<span class="pull-right col-md-2 col-lg-2">' + 
                            '' + item.CBED + '</span></p> ' + 
                            '<h4>Task Revised Completion Date<span class="pull-right col-md-6 col-lg-6">' +
                            'Task Actual Completed Date</span></h4>' +
                            '<p>' + item.RCD + '<span class="pull-right col-md-2 col-lg-2">' + 
                            '' + item.ACD + '</span></p> ' + 
                            '<h4>Task Description<span class="pull-right col-md-6 col-lg-6">' +
                            'Task Status</span></h4>' +
                            '<p>' + item.Description + '<span class="pull-right col-md-2 col-lg-2">' + 
                            '' + item.Status +'</span></p>' +
                            '</div>' +
                            '</div>';
                        $('#results-wrapper').append(result_box);
                        /*if(item.Category !== null){
                            $('#' + item_id ).append('<span class="pull-right">' + item.Category + '</span>');
                        }*/
                        $('#' + item_id).fadeIn(300);
                    }
                });
            }
        }
        //=========================================================
        // End Results Display Code
        //=========================================================
    });
</script>
