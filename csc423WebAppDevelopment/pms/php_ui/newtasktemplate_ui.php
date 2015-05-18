<h1 class="page-header">Create a New Task Template</h1>

<form role="form" name="newtasktemp-form">
    <input type="hidden" name="FormID" value="add-task-temp">
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="form-group">
                <label for="template-name">Task Template Name</label>
                <input type="text" name="template-name" id="template-name" class="form-control" placeholder="Task Template Name">
                <span class="help-block">Enter the name of the new Task Template being created</span>
            </div>
        </div>
    </div>   
    
    <div class="row">
        <div class="col-md-12 col-md-12">
            <div class="form-group">
                <label for="template-desc">Template Description</label>
                <textarea name="template-desc" id="template-desc" class="form-control" cols="3" rows="5" placeholder="Task Template Description"></textarea>
                <span class="help-block">Enter a description of the new Task Template</span>
            </div>
        </div>
    </div>
    
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="form-group">
                <label for="CBED">Completion Before End Date (In Days)</label>
                <input type="text" name="CBED" id="CBED" class="form-control" placeholder="Completion Before End Date">
                <span class="help-block">Enter how many days the Task should be completed before the Project end date</span>
            </div>
        </div>
    </div>
    
    <div class="row">
		<div class="col-md-6 col-md-offset-6">
            <div class="pull-right">
                <button type="submit" name="submit" id="submit_btn" class="btn btn-primary">Create New Task Library</button>
                <button type="reset" class="btn btn-default">Clear Fields</button>
            </div>
        </div>
    </div>
    <br>
    <div class="row">
    	<div class="col-md-12 col-lg-12">
	    	<div id="notification-area">
	    	</div>
    	</div>
    </div>
</form>

<script>
    $(function() {
        $("form").submit(function(event) {
            var theForm = $(this);
            process_form_new(theForm, event).done(function(result) {
		        var results = JSON.parse(result);
		        if(results.status === "success_QUERY") {
			        $("form").trigger("reset");
			        $("#notification-area").empty().append('<div class="alert alert-success">' + 
			        									   '<span class="pull-right glyphicon glyphicon-ok" aria-hidden="true"></span> <strong><u>' +
			        									   results.name + '</u></strong> submitted successfully.</div>');
		        } else {
			     	$("#notification-area").empty().append('<div class="alert alert-danger">' + 
			     										   '<span class="pull-right glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> ' +
			     										   'There was an issue with submitting <strong><u>' +
				 										   	results.name + 
			     										   '</u></strong>.</div>');
		        }
		    })
		    .fail(function() {
		        // Something bad happened; redirected to disconnected page
		    });
		    event.preventDefault();
        });
    });
</script>
