<h1 class="page-header">Search Existing Libraries</h1>

<form role="form" id="searchtasklib-form" name="searchtasklib-form">
    <input type="hidden" name="FormID" value="search-task-library">
    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <label for="search-task-library-name">Search For A Task Library</label>
                <div class="input-group">
                    <input type="text" name="search-task-library-name" id="search-task-library-name" class="form-control">
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
        $('#search-task-library-name').focus();
        
        //=========================================================
	    // Recent Task Library Search
	    //=========================================================
        call_searchTen("tasklibrary", "LibraryID").done(function(result) {
            var results = JSON.parse(result);
            var status = results.status;
            
            if(status.indexOf("success") >= 0) {
	            $('#results-wrapper').empty();
	            $('#results-wrapper').append('<h3 class="no-results">Recent Libraries</h3>');
	            display_library_results(results);
	        } else if(status.indexOf("failed") >= 0) {
		        // Show an error somewhere on the page
	        }
        })
        .fail(function() {
			// Send the user off to the connection error page
        });
        //=========================================================
	    // End Recent Task Library Search
	    //=========================================================
        
        //=========================================================
        // Task Library Search Code
        //=========================================================
        $("#searchtasklib-form").submit(function(event) {
            var theForm = $(this);
            
            process_form_new(theForm, event).done(function(result) {
	            var results = JSON.parse(result);
	            var status = results.status;
	            
	            if(status.indexOf("succes") >= 0) {
		            $('#results-wrapper').empty();
		            if($('#search-task-library-name').val() === "") {
		                searchString = "All Libraries";
		            } else {
		                searchString = 'Libraries Matching "' + $('#search-task-library-name').val() + '"';
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
        // End Task Library Search Code
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
            console.log(results);
            console.log(results.libraries);
	        if(typeof results.libraries === "undefined") {
                // No Search Results
                $('#results-wrapper').empty();
                $('#results-wrapper').append('<h3 class="no-results">No Results Found</h3>');
            } else {
                // Display Results
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
                                                                                '<span class="temp-items pull-right">' +                                                                            
                                                                                temp.CBED + ' Days</span> </div>');
                            });
                        } else {
                            $('#' + item_id + " #templates-wrapper").append('<h3 class="no-results">No Templates Found</h3>');
                        }
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
