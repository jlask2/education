<h1 class="page-header">Edit an Existing Project</h1>
<!--<p>
    This is the page which will display the "Edit an Existing Project" form.
</p>-->

<form role="form" name="update-project-form">
    <div>
         <div class="col-md-3">
            <div class="form-group">
                <label for="project-name">Select an existing Project</label>
                <input type="text" name="edit-project-name" id="edit-project-name" class="form-control" placeholder="Project Name">
                <span class="help-block">Choose an existing Project to edit or update</span>
            </div>
        </div>
    </div>
    <hr/>
    <div class="row">
        <div class="col-md-3">
            <div class="form-group">
                <label for="project-name">Project Name</label>
                <input type="text" name="project-name" id="project-name" class="form-control" placeholder="Project Name">
                <span class="help-block">Enter the updated name of the chosen project</span>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label for="project-cat">Project Category</label>
                <select name="project-cat" id="project-cat" class="form-control">
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
                <span class="help-block">Select the updated category for the chosen project</span>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">
            <label for="project-end">Project End Date</label>
            <div class="input-group">
                <input type="text"  name="project-end" id="project-end" class="form-control" placeholder="End Date">
                <span class="input-group-btn">
                    <button class="btn btn-default" type="button">
                        <span class="glyphicon glyphicon-calendar"></span>
                        Select
                    </button>
                </span>
            </div>
            <span class="help-block">Select the updated end date of the chosen project</span>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <label for="project-lib">Task Library</label>
                <select name="project-lib" id="project-lib" class="form-control">
                    <option>Select a Task Library</option>
                </select>
                <span class="help-block">Select the project's updated task library</span>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <label for="project-desc">Project Description</label>
                <textarea name="project-desc" id="project-desc" class="form-control" cols="3" rows="5" placeholder="Project Description"></textarea>
                <span class="help-block">Enter an updated description of the chosen project</span>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3 col-md-offset-3">
            <div class="pull-right">
                <button type="submit" name="submit" id="submit_btn" class="btn btn-primary">Update Project</button>
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