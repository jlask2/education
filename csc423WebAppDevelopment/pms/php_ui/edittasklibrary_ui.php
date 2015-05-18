<h1 class="page-header">Edit an Existing Task Library</h1>

<form role="form" name="update-task-library-form">
    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <label for="search-task-library-name">Search For A Task Library</label>
                <div class="input-group">
                    <input type="text" name="search-task-library-name" id="search-task-library-name" class="form-control">
                    <span class="input-group-btn">
                        <button class="btn btn-primary" type="button">
                            <span class="glyphicon glyphicon-search"></span>
                            Search
                        </button>
                    </span>
                </div>
            </div>
        </div>
    </div>
    <hr/>
    <div class="row">
        <div class="col-md-3">
            <div class="form-group">
                <label for="task-name">Task Library Name</label>
                <input type="text" name="task-name" id="task-name" class="form-control" placeholder="Task Name">
                <span class="help-block">Enter the name of the new task library being created</span>
            </div>
        </div>
    </div>   

    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <label for="tasknewlib-desc">Task Description</label>
                <textarea name="tasknewlib-desc" id="tasknewlib-desc" class="form-control" cols="3" rows="5" placeholder="Task Library Description"></textarea>
                <span class="help-block">Enter a description of the new task library</span>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3 col-md-offset-3">
            <div class="pull-right">
                <button type="submit" name="submit" id="submit_btn" class="btn btn-primary">
                    Edit Task Library
                </button>
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