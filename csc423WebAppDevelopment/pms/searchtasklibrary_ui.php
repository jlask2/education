<h1 class="page-header">Search Task Libraries</h1>

<form role="form" id="searchtasklib-form" name="searchtasklib-form">
    <input type="hidden" name="FormID" value="search-task-library">
    <div class="row">
        <div class="col-sm-12 col-md-6">
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
    <div class="col-sm-9 col-md-6 col-lg-9">
        <div id="results-wrapper" class="results-wrapper"></div>
    </div>
</div>

<script type="text/javascript" src="/js/clickhandler.js"></script>
<script>
    $(function() {
        
        /**
            This is a workaround for allowing multiple modals to appear
            in Bootstrap 3.0 since stacking modals is not supported
            by the framework out-of-the-box.
            
            This is not my code, so give me some time to document it and
            understand it. Feel free to take a look at the source code
            located here: http://miles-by-motorcycle.com/static/bootstrap-modal/index.html
        **/
        

        $('.modal').on('hidden.bs.modal', function( event ) {
            $(this).removeClass( 'fv-modal-stack' );
            $('body').data( 'fv_open_modals', $('body').data( 'fv_open_modals' ) - 1 );
        });


        $( '.modal' ).on( 'shown.bs.modal', function ( event ) {

            // keep track of the number of open modals

            if ( typeof( $('body').data( 'fv_open_modals' ) ) == 'undefined' )
            {
                $('body').data( 'fv_open_modals', 0 );
            }


            // if the z-index of this modal has been set, ignore.

            if ( $(this).hasClass( 'fv-modal-stack' ) )
            {
                return;
            }

            $(this).addClass( 'fv-modal-stack' );

            $('body').data( 'fv_open_modals', $('body').data( 'fv_open_modals' ) + 1 );

            $(this).css('z-index', 1040 + (10 * $('body').data( 'fv_open_modals' )));

            $( '.modal-backdrop' ).not( '.fv-modal-stack' )
            .css( 'z-index', 1039 + (10 * $('body').data( 'fv_open_modals' )));


            $( '.modal-backdrop' ).not( 'fv-modal-stack' )
            .addClass( 'fv-modal-stack' ); 

        });

        
        $("#searchtasklib-form").submit(function(event) {
            var theForm = $(this);
            process_form(theForm, event);
        });
        
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
    });
</script>
