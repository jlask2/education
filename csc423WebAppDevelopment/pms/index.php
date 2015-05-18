<html>
    <head>
        <title>Project Management System</title>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="stylesheet" href="/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/datetimepicker.min.css">
        <link href='http://fonts.googleapis.com/css?family=Roboto:400,100,300' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="/css/sticky.full.css" type="text/css" />
        <link rel="stylesheet" href="/css/styles.css">

        <!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->

    </head>
    <body>
        <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#menu-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">Project Management System</a>
                </div>

                <div class="collapse navbar-collapse" id="menu-collapse">
                    <ul class="nav navbar-nav">
                        <li id="dash" data-load="dash"><a href="#"><span class="glyphicon glyphicon-off"></span> Dashboard</a></li>
                        <li id="settings" data-load="settings"><a href="#"><span class="glyphicon glyphicon-cog"></span> Settings</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-3 col-md-2 col-lg-3 sidebar">
                    <div class="section-header active">
                        <h4><span class="glyphicon glyphicon-inbox"></span> Projects<span class="glyphicon glyphicon-chevron-down pull-right"></span></h4>
                    </div>
                    <ul class="nav nav-sidebar">
                        <li data-load="new-proj"><a href="#"><span class="glyphicon glyphicon-plus"></span> Create a New Project</a></li>
                        <li data-load="search-proj"><a href="#"><span class="glyphicon glyphicon-search"></span> Search for an Existing Project</a></li>
                        <!--<li data-load="del-proj"><a href="#"><span class="glyphicon glyphicon-trash"></span> Delete an Existing Project</a></li>-->
                    </ul>
                    <div class="section-header active">
                        <h4><span class="glyphicon glyphicon-tags"></span> Task Libraries<span class="glyphicon glyphicon-chevron-down pull-right"></span></h4>
                    </div>
                    <ul class="nav nav-sidebar">
                        <!--<li data-load="add-task-tatm"><a href="#"><span class="glyphicon glyphicon-share"></span> Add a Task Template to a Library</a></li>-->
                        <li data-load="new-task-lib"><a href="#"><span class="glyphicon glyphicon-plus"></span> Create a New Task Library</a></li>
                        <li data-load="search-task-lib"><a href="#"><span class="glyphicon glyphicon-search"></span> Search for an Existing Task Library</a></li>
                        <!--<li data-load="del-task-lib"><a href="#"><span class="glyphicon glyphicon-trash"></span> Delete an Existing Task Library</a></li>-->
                    </ul>
                    <div class="section-header active">
                        <h4><span class="glyphicon glyphicon-tag"></span> Task Templates<span class="glyphicon glyphicon-chevron-down pull-right"></span></h4>
                    </div>
                    <ul class="nav nav-sidebar">
                        <li data-load="new-tatm"><a href="#"><span class="glyphicon glyphicon-plus"></span> Create a New Task Template</a></li>
                        <li data-load="search-tatm"><a href="#"><span class="glyphicon glyphicon-search"></span> Search for an Existing Task Template</a></li>
                        <!--<li data-load="del-tatm"><a href="#"><span class="glyphicon glyphicon-trash"></span> Delete an Existing Task Template</a></li>-->
                    </ul>
                    <div class="section-header active">
                        <h4><span class="glyphicon glyphicon-list-alt"></span> Reports<span class="glyphicon glyphicon-chevron-down pull-right"></span></h4>
                    </div>
                    <ul class="nav nav-sidebar">
						<li data-load="task-due-dates-rep"><a href="#"><span class="glyphicon glyphicon-list-alt"></span> Task Due Dates</a></li>
						<li data-load="over-due-tasks-rep"><a href="#"><span class="glyphicon glyphicon-list-alt"></span> Over Due Tasks</a></li>
						<li data-load="late-tasks-rep"><a href="#"><span class="glyphicon glyphicon-list-alt"></span> Late Tasks</a></li>
						<li data-load="completed-tasks-rep"><a href="#"><span class="glyphicon glyphicon-list-alt"></span> Completed Tasks</a></li>
                    </ul>
                </div>
                <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 col-lg-9 col-lg-offset-3 main">
                    <div class="main-content-area">
                    </div>
                </div>
            </div>
        </div>

        <!-- Load Scripts -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="/js/bootstrap.min.js"></script>
        <script src="/js/sticky.full.js"></script>
        <script src="/js/Chart.min.js"></script>
        <script src="/js/main.js"></script>
        <script type="text/javascript">

            // Collapseable menu functionality
            $(document).ready(function() {

                $('.section-header').toggleClass('active')
                .find('.pull-right')
                .toggleClass('glyphicon-chevron-down glyphicon-chevron-right')                                    

                $('.section-header').click(function() {
                    $(this).toggleClass('active');
                    $(this).next().stop().slideToggle(250);
                    $(this).find('.pull-right')
                    .toggleClass('glyphicon-chevron-down glyphicon-chevron-right');
                });

                // Load the dashboard when the page is loaded
                init_dashboard();

                // Transition view for sidebar navigation
                $('.nav-sidebar li').click(function() {
                    $('.navbar-nav li').removeClass('active');
                    $('.nav-sidebar li').removeClass('active');
                    $(this).addClass('active');
                    transition_view($(this).data('load'));
                });

                // Transition view for Dashboard and Settings
                $('.navbar-nav li').click(function() {
                    $('.navbar-nav li').removeClass('active');
                    $(this).addClass('active');
                    $('.nav-sidebar li').removeClass('active');
                    transition_view($(this).data('load'));
                });
            });
        </script>
    </body>
</html>