<h1 class="page-header">Create a New Project</h1>

<form role="form" id="new-project-form" name="new-project-form">
    <input type="hidden" name="FormID" value="new-proj">
    <div class="row">
        <div class="col-md-6 col-lg-6">
            <div class="form-group">
				<label class="control-label" for="project-name">Project Name</label>
                <input type="text" name="project-name" id="project-name" class="form-control" placeholder="Project Name" required>
                <span class="help-block">Enter the name of the new project being created</span>
            </div>
        </div>
        <div class="col-md-6 col-lg-6">
            <div class="form-group">
				<label class="control-label" for="project-cat">Project Category</label>
                <select name="project-cat" id="project-cat" class="form-control" required>
                    <option>Select a Category</option>
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
                <span class="help-block">Select the category for the project</span>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6 col-lg-6">
            <div class="form-group">
				<label class="control-label" for="project-end">Project End Date</label>
                <div class='input-group date' id='datetimepicker'>
                    <input type='text' class="form-control" name="project-end" data-date-format="YYYY/MM/DD" placeholder="YYYY/MM/DD" required>
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button">
                            &nbsp;
                            <span class="glyphicon-calendar glyphicon"></span>
                            &nbsp;
                        </button>
                    </span>
                </div>
				<span class="help-block">Select the end date of the project</span>
            </div>
        </div>
        <div class="col-md-6 col-lg-6">
            <div class="form-group">
				<label class="control-label" for="project-lib">Task Library</label>
                <select name="project-lib" id="project-lib" class="form-control" required>
	                <option>Select a Task Library</option>
                    <option value="empty">Empty project</option>
                    <?php 
						require('../php/getLibrariesDropdown.php');
						build_dd(); 
					?>
                </select>
                <input type="hidden" id="selection">
                <span class="help-block">Select the project's task library</span>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="form-group">
                <label for="project-desc">Project Description</label>
                <textarea name="project-desc" id="project-desc" class="form-control" cols="3" rows="5" placeholder="Project Description"></textarea>
                <span class="help-block">Enter a description of the new project being created</span>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6 col-md-offset-6">
            <div class="pull-right">
                <button type="submit" name="submit" id="submit_btn" class="btn btn-primary">Create Project</button>
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
<script src="js/moment.js"></script>
<script src="js/datetimepicker.min.js"></script>
<script>
    $(function() {
		
		$('.form-control').blur(function() {
			validate_field($(this));
		});
		
        $("form").submit(function(event) {
			console.log(final_validation($('#new-project-form')));
			if(final_validation($('#new-project-form'))) {
				$('#selection').val($('#project-lib').val());
				var theForm = $(this);
				process_form_new(theForm, event).done(function(result) {
					var results = JSON.parse(result);
					if(results.status === "success_QUERY") {
						$("form").trigger("reset");
						$('.form-group').removeClass('has-error has-success');
						$("#notification-area").empty().append('<div class="alert alert-success">' + 
															   '<span class="pull-right glyphicon glyphicon-ok" aria-hidden="true"></span> <strong><u>' +
															   results.name + '</u></strong> submitted successfully.</div>');
					} else {
						$("#notification-area").empty().append('<div class="alert alert-danger">There was an issue with submitting ' + 
															   '<span class="pull-right glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> <strong><u>' + 
															   results.name + '</u></strong>.</div>');
					}
				})
				.fail(function() {
					// Something bad happened; redirected to disconnected page
				});
			}
			event.preventDefault();
        });
        
        $('#datetimepicker').datetimepicker({
            pickTime: false
        });
    });
</script>