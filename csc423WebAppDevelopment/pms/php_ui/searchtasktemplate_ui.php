<h1 class="page-header">Search for an Existing Task Template</h1>

<form role="form" id="searchtasktemp-form" name="searchtasktemp-form">
    <input type="hidden" name="FormID" value="search-task-temp">
    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <label for="search-task-template-name">Search For A Task Template</label>
                <div class="input-group">
                    <input type="text" name="search-task-template-name" id="search-task-template-name" class="form-control">
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
        $('#search-task-template-name').focus();
        
		//=========================================================
	    // Recent Task Template Search
	    //=========================================================
        call_searchTen("tasktemplate", "TemplateCode").done(function(result) {
            var results = JSON.parse(result);
            var status = results.status;
            
            if(status.indexOf("success") >= 0) {
	            $('#results-wrapper').empty();
	            $('#results-wrapper').append('<h3 class="no-results">Recent Task Templates</h3>');
	            display_template_results(results);
	        } else if(status.indexOf("failed") >= 0) {
		        // Show an error somewhere on the page
	        }
        })
        .fail(function() {
			// Send the user off to the connection error page
        });
        //=========================================================
	    // End Recent Task Template Search
	    //=========================================================

		//=========================================================
        // Task Template Search Code
        //=========================================================
        $("#searchtasktemp-form").submit(function(event) {
            var theForm = $(this);
            
            process_form_new(theForm, event).done(function(result) {
	            var results = JSON.parse(result);
	            var status = results.status;
	            
	            if(status.indexOf("succes") >= 0) {
		            $('#results-wrapper').empty();
		            if($('#search-task-template-name').val() === "") {
		                searchString = "All Task Templates";
		            } else {
		                searchString = 'Task Templates Matching "' + $('#search-task-template-name').val() + '"';
		            }
			        $('#results-wrapper').append('<h3 class="no-results">' + searchString + '</h3>');
			        display_template_results(results);
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
        // End Task Library Search Code
        //=========================================================

		//=========================================================
        // Click Handling and Animation Code
        //=========================================================
        $(document).off().on("click",".result-item",function(e){
            if($(e.target).hasClass('btn')) {
                var target_template = $(e.target).parent().parent();
                var primary_key = $(this).attr("id");
                var button_id = $(e.target).attr("id");

                click_event(button_id, primary_key, target_template);
                return;
            }

            if($(e.target).hasClass('glyphicon')) {
                var target_template = $(e.target).parent().parent().parent();
                var primary_key = $(this).attr("id");
                var button_id = $(e.target).parent().attr("id");

                click_event(button_id, primary_key, target_template);
                return;
            }

            if(!$(this).hasClass('active')) {
                $(this).stop().addClass('active').animate(
                    {
                        'max-height': '200px',
                        'height': '200px'
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
        function display_template_results(results) {
	        if(results.templates.length === 0) {
                // No Search Results
                $('#results-wrapper').empty();
                $('#results-wrapper').append('<h3 class="no-results">No Results Found</h3>');
            } else {
                // Display Results
                $.each(results.templates, function(i, item) {

                    if(item.Name != null) {
                        var item_id = item.TemplateCode;

                        var result_box = '<div id="' + item_id + '" class="result-item"><h3>' + item.Name + '</h3>' +
                            '<div id="content">' +
                            '<h4>Description</h4>' +
                            '<p>' + item.Description + '</p> ' +
                            '<h4>Completion Before Due Date</h4>' +
                            '<p>' + item.CBED + ' Days</p> ' +
                            '</div>' +
                            '<div class="action-group">' + 
                            '<a id="edit-temp" class="btn btn-default">' + 
                            '<span class="glyphicon glyphicon-pencil"></span>' +
                            ' Edit</a>' +
                            '<a id="del-temp-perm" class="btn btn-default">' +
                            '<span class="glyphicon glyphicon-trash"></span>' +
                            ' Delete</a>' + '</div>' +
                            '</div>';
                        $('#results-wrapper').append(result_box);
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
