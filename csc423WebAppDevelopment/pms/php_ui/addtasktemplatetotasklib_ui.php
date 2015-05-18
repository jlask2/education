<h1 class="page-header">Add Task Template to an Existing Task Library</h1>

<form role="form" name="addtasktemptotasklib-form">
    <input type="hidden" name="FormID" value="search-task-library">
    <div class="row">
        <div class="col-md-6">
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
    <div class="col-md-6">
        <div id="results-wrapper" class="results-wrapper">
        </div>
    </div>

    <script type="text/javascript" src="/js/clickhandler.js"></script>
    <script>
        $(function() {
            $("form").submit(function(event) {
                $('#results-wrapper').empty();
                var theForm = $(this);
                process_form(theForm, event);
            });

            $(document).off().on("click",".result-item",function(e){
                if($(e.target).hasClass('btn') || $(e.target).hasClass('glyphicon')) {
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
        });
    </script>
