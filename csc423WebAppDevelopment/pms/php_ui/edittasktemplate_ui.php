<h1 class="page-header">Edit an Existing Task Template</h1>
<!--<p>
    This is the page which will display the "Edit an Existing Task Template" form.
</p>-->

<form role="form" name="update-task-template-form">
    <div>
         <div class="col-md-3">
            <div class="form-group">
                <label for="task-template-name">Select an existing Task Template</label>
                <input type="text" name="task-template-id" id="task-template-id" class="form-control" placeholder="Task Template ID">
                <span class="help-block">Enter the template's ID</span>
            </div>
        </div>
    </div>
    <hr/>
    <div class="row">
        <div class="col-md-3">
            <div class="form-group">
                <label for="task-template-name">Task Template Name</label>
                <input type="text" name="task-template-name" id="task-template-name" class="form-control" placeholder="Task Template Name">
                <span class="help-block">Enter the updated name of the chosen task template</span>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">
            <label for="task-template-end">Task Template End Date</label>
            <div class="input-group">
                <input type="text"  name="task-template-end" id="task-template-end" class="form-control" placeholder="End Date">
                <span class="help-block"></span>
            </div>
            <span class="help-block">Select the updated end date of the chosen task template</span>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <label for="task-template-desc">Task Template Description</label>
                <textarea name="task-template-desc" id="task-template-desc" class="form-control" cols="3" rows="5" placeholder="Task Template Description"></textarea>
                <span class="help-block">Enter an updated description of the chosen task template</span>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3 col-md-offset-3">
            <div class="pull-right">
                <button type="submit" name="submit" id="submit_btn" class="btn btn-primary">Update Task Template</button>
                <button type="reset" class="btn btn-default">Clear Fields</button>
            </div>
        </div>
    </div>
</form>

<script>
    $(function() {
        $("form").submit(function(event) {
            var theForm = $(this);
            process_form(theForm, event);
        });
    });
</script>