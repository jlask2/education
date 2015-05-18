/*

This class is used to handle click events for buttons. All button clicks that are not directly linked to a form (i.e. A button click for editing of deleting) should be routed to this script to be handled properly.

Forms handle their clicks from a "submit" button. This is not used for that. This is used for button clicks that are related to normal buttons.

*/

function click_event(button_id, primary_key, target) {
    
    console.log(button_id);
    switch(button_id) {
        case "delete-proj" :
            //var primaryKey = primary_key;//target.find("h5");
            var title = target.find('h3').html();
            var content = target.find('#content').html();
            console.log(primary_key);
            console.log(title);
            console.log(content);
            var modal = '<div class="modal" id="myModal">' +
                '<div class="modal-dialog">' +
                '<div class="modal-content">' +
                '<div class="modal-header">' +
                '<h4 class="modal-title">' +
                'Delete ' + title + ' ' + primary_key + '? </h4>' + 
                '</div><div class="container"></div>' +
                '<div class="modal-body">' +
                content + '</div>' +
                '<div class="modal-footer">' +
                '<span class="pull-left">' + 
                '<strong>Note:</strong> This deletion is PERMANENT' +
                '</span>' +
                '<form role="form" id="deleteproj-form" ' +
                'name="deleteproj-form">'+
                '<input type="hidden" name="FormID" value="del-proj">' +
                '<input type="hidden" name="PrimaryKey" ' +
                'value="' + primary_key + '">' +
                '<button type="button" data-dismiss="modal" ' +
                'class="btn btn-default">Cancel</button>' +
                '<button type="submit" ' + 
                'class="btn btn-primary">Delete</button>' +
                '</form>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>';

            $('#results-wrapper').append(modal);
            $('#myModal').modal({show:true});

            $('#deleteproj-form').submit(function(event) {
                var theForm = $(this);
                //process_form_new(theForm, event);
                
                process_form_new(theForm, event).done(function(result) {
                    var results = JSON.parse(result);
                    var status = results.status;
                    var action = results.action;
                    var move_view = results.move_view;

                    if(status.indexOf("success") >= 0) {
                        $('#' + results.target).fadeOut(300, function() {
                            $('#' + results.target).remove();
                        });
                    } else if(status.indexOf("failed") >=0) {
                    }
                })
                .fail(function() {
                });
                event.preventDefault();
                
                $('#myModal').modal('hide');
                //update_display(theForm);
            });

            $("#myModal").on('hidden.bs.modal', function () {
                $(this).remove();
            });
        break;    
        case "delete-lib" :
            var title = target.find('h3').html();
            var content = target.find('#content').html();
            var modal = '<div class="modal" id="myModal">' +
            '<div class="modal-dialog">' +
            '<div class="modal-content">' +
            '<div class="modal-header">' +
            '<h4 class="modal-title">' +
            'Delete ' + title + '? </h4>' + 
            '</div><div class="container"></div>' +
            '<div class="modal-body">' +
            content + '</div>' +
            '<div class="modal-footer">' +
            '<span class="pull-left">' + 
            '<strong>Note:</strong> This deletion is PERMANENT' +
            '</span>' +
            '<form role="form" id="deletetasklib-form" ' +
            'name="deletetasklib-form">'+
            '<input type="hidden" name="FormID" value="del-task-lib">' +
            '<input type="hidden" name="PrimaryKey" ' +
            'value="' + primary_key + '">' +
            '<button type="button" data-dismiss="modal" ' +
            'class="btn btn-default">Cancel</button>' +
            '<button type="submit" ' + 
            'class="btn btn-primary">Delete</button>' +
            '</form>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>';

            $('#results-wrapper').append(modal);
            $('#myModal').modal({show:true});
            
            $('#deletetasklib-form').submit(function(event) {
                var theForm = $(this);
                process_form(theForm, event);
                $('#myModal').modal('hide');
            });
            
            $("#myModal").on('hidden.bs.modal', function () {
                $(this).remove();
            });
        break;
        case "add-temp" :
            var primary_key = target.attr("id");
            var title = target.find('h3').html();
            target.addClass("active-library");
            get_all_templates().done(function(result) {
                var results = JSON.parse(result);
                if(results.length == 0) {
                    $('#pk-temp').append("<option>No templates found</option>");
                } else {
                    $.each(results, function(i, item) {
                        var select_option = '<option value=' + 
                            item.TemplateCode + '>' +
                            item.Name  + '</option>';
                        $('#pk-temp').append(select_option);
                    });
                }
            })
            .fail(function() {
            });
            var modal = '<div class="modal" id="myModal">' +
                '<div class="modal-dialog">' +
                '<div class="modal-content">' +
                '<div class="modal-header">' +
                '<h4 class="modal-title">' +
                'Add a Template to ' + title + '</h4>' + 
                '</div><div class="container"></div>' +
                '<div class="modal-body">' +
                'Select a Template<br>' +
                '<div class="form-group">' +
                '<select name="pk-temp" id="pk-temp" class="form-control">' +
                '</select>' +
                '</div>' +
                '</div>' +
                '<div class="modal-footer">' +
                '<form role="form" id="addtemptolib-form" ' +
                'name="addtemptolib-form">'+
                '<input type="hidden" name="FormID" value="add-temp-lib">' +
                '<input type="hidden" name="PrimaryKey" ' +
                'value="' + primary_key + '">' +
                '<input type="hidden" id="primary-key-temp" ' +
                'name="primary-key-temp">' +
                '<button type="button" data-dismiss="modal" ' +
                'class="btn btn-default cancel-button">Cancel</button>' +
                '<button type="submit" ' + 
                'class="btn btn-primary">Add</button>' +
                '</form>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>';

            $('#results-wrapper').append(modal);
            $('#myModal').modal({show:true});

            $('#addtemptolib-form').submit(function(event) {
                $('#primary-key-temp').val($('#pk-temp').val());
                var theForm = $(this);
                process_form(theForm, event);
                $('#myModal').modal('hide');
            });
            
            $('.cancel-button').click(function() {
                target.removeClass('active-library');
            });
            
            $("#myModal").on('hidden.bs.modal', function () {
                $(this).remove();
            });
        break;
        case "edit-lib":
            var primary_key = target.attr("id");
            var title = target.find('h3').html();
            var content = target.find('#content').html();
            
            var modal = '<div class="modal" id="myModal">' +
                '<div class="modal-dialog">' +
                '<div class="modal-content">' +
                '<div class="modal-header">' +
                '<h4 class="modal-title">' +
                'Editing ' + title + '</h4>' + 
                '</div><div class="container"></div>' +
                '<div class="modal-body">' +
                content + '</div>' +
                '<div class="modal-footer">' +
                '<span class="pull-left">' + 
                '<strong>Note:</strong> Deletions are PERMANENT' +
                '</span>' +
                '<form role="form" id="edittasklib-form" ' +
                'name="deletetasklib-form">'+
                '<input type="hidden" name="FormID" value="edit-task-lib">' +
                '<input type="hidden" name="PrimaryKey" ' +
                'value="' + primary_key + '">' +
                '<button type="button" data-dismiss="modal" ' +
                'class="btn btn-primary">Done</button>' +
                '</form>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>';

            $('#results-wrapper').append(modal);
            $('.modal-body .temp-items').append('<a class="btn del-template pull-right ' +                                                   'glyphicon ' + 
                                                'glyphicon-trash item-margin-fix"></a>');
            $('#myModal').modal({show:true});
            
            $('.del-template').click(function() {
                var pk = $(this).parent().parent().attr("id");
                var data = {PrimaryKey: pk, LibraryID: primary_key};
                $.ajax(
                    {
                        url : "/php/deleteLibTempAssoc.php",
                        type: "POST",
                        data: data
                    })
                .done(function(result) {
                    var results = JSON.parse(result);
                    var status = results.status;
                    var target = '.modal-body #templates-wrapper #' + pk + 
                                 ', #' + primary_key + ' #templates-wrapper #' +
                                 pk;
                    if(status.indexOf("success") >= 0) {
                        $(target).fadeOut(300, function() {
                            $(target).remove();
                            if($('#templates-wrapper').contents().length == 0) {
                                $('.modal-body #templates-wrapper, #' + primary_key + 
                                  ' #templates-wrapper').append('<h3 class="no-results">' +
                                                               'No Templates Found</h3>');
                            }
                        });
                    }
                })
                .fail(function() {
                    // Couldn't find the PHP file
                });
                event.preventDefault();
            });
            
            $("#myModal").on('hidden.bs.modal', function () {
                $(this).remove();
            });
        break;
        case "add-task" :
            var primary_key = target.attr("id");
            var end_date = $('#' + primary_key + ' #end-date').html();
            var title = target.find('h3').html();
            target.addClass("active-project");

            var modal = '<div class="modal" id="myModal">' +
                '<div class="modal-dialog">' +
                '<div class="modal-content">' +
                '<div class="modal-header">' +
                '<h4 class="modal-title">' +
                'Add a Task to ' + title + '</h4>' + 
                '</div><div class="container"></div>' +
                '<form role="form" id="addtask-form" ' +
                'name="addtask-form">'+
                '<div class="modal-body">' +
                '<div class="form-group">' +
                '<label for="project-name">Task Name</label>' +
                '<input type="text" name="task-name" ' +
                'id="project-name" class="form-control" placeholder="Task Name">' +
                '</div>' +
                '<div class="form-group">' +
                '<label for="task-desc">Description</label>' +
                '<textarea name="task-desc" id="task-desc" ' +
                'class="form-control" cols="3" rows="5" ' +
                'placeholder="Task Description"></textarea>' +
                '</div>' +
                '<div class="form-group">' +
                '<label for="task-CBED">Completion Before End Date (' + 
                end_date +
                ')</label>' +
                '<input type="text" name="task-CBED" ' +
                'id="task-CBED" class="form-control" placeholder="Days">' +
                '</div>' +
                '</div>' +
                '<div class="modal-footer">' +
                '<input type="hidden" name="FormID" value="add-task">' +
                '<input type="hidden" name="PrimaryKey" ' +
                'value="' + primary_key + '">' +
                '<button type="button" data-dismiss="modal" ' +
                'class="btn btn-default cancel-button">Cancel</button>' +
                '<button type="submit" ' +
                'class="btn btn-primary">Add Task</button>' +
                '</div>' +
                '</form>' +
                '</div>' +
                '</div>' +
                '</div>';
            
            $('#results-wrapper').append(modal);
            $('#myModal').modal({show:true});

            $('#addtask-form').submit(function(event) {
                var theForm = $(this);
                process_form(theForm, event);
                $('#myModal').modal('hide');
            });

            $('.cancel-button').click(function() {
                target.removeClass('active-project');
            });

            $("#myModal").on('hidden.bs.modal', function () {
                $(this).remove();
            });
        break;
        case "edit-proj" :
        	var title = target.find('h3').html();
        	
        	var modal = '<div class="modal" id="myModal">' +
				'<div class="modal-dialog">' +
				'<div class="modal-content">' +
				'<div class="modal-header">' +
				'<h4 class="modal-title">' +
				'Editing ' + title + '</h4>' + 
				'</div><div class="container"></div>' +
				'<form role="form" id="editproject-form" ' +
				'name="editproject-form">'+
				'<div class="modal-body">' +
				'<label for="template-name">Project Name</label>' +
				'<input type="text" name="project-name" id="project-name" class="form-control">' +
				'<br />' +
				'<label for="template-desc">Project Description</label>' +
				'<textarea name="project-desc" id="project-desc" class="form-control" cols="3" rows="5"></textarea>' +
				'</div>' +
				'<div class="modal-footer">' + 
				'<input type="hidden" name="FormID" value="edit-proj">' +
				'<input type="hidden" name="PrimaryKey" ' +
				'value="' + primary_key + '">' +
				'<button type="button" data-dismiss="modal" ' +
				'class="btn btn-default">Cancel</button>' +
				'<button type="submit" ' + 
				'class="btn btn-primary">Save</button>' +
				'</div>' +
				'</form>' +
				'</div>' +
				'</div>' +
				'</div>';
				
			$('#results-wrapper').append(modal);
			
			search_single("project", primary_key).done(function(result) {
				var results = JSON.parse(result);
				var status = results.status;
				if(status.indexOf("success") >= 0) {
					// Success so fill the input fields
					$('#project-name').val(results[0].Name);
					$('#project-desc').val(results[0].Description);
				} else {
					// Something failed so show an error
				}
			}).fail(function(result) {
				// Send the user to the connection error page
			});
			
			$('#myModal').modal({show:true});
			
			$('#editproject-form').submit(function(event) {
				var theForm = $(this);
				process_form_new(theForm, event).done(function(result) {
					var results = JSON.parse(result);
					var status = results.status;
					if(status.indexOf("success") >= 0) {
						// Success so show a notification
						$("#notification-area").empty().append('<div class="alert alert-success">' + 
															   '<span class="pull-right glyphicon glyphicon-ok" aria-hidden="true"></span> <strong><u>' +
															   results.name + '</u></strong> updated successfully.</div>');
						
						var updated_content = '<h4>Description</h4>' +
							'<p>' + results.desc + '</p> ' +
							'</div>';
						
						$('#' + results.target).find('h3').text(results.name);
						$('#' + results.target).find('#text-info').html(updated_content);
					} else {
						// Something failed so show an error
						$("#notification-area").empty().append('<div class="alert alert-danger">' + 
															   '<span class="pull-right glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> ' +
															   'There was an issue with updating <strong><u>' +
															   primary_key + 
															   '</u></strong>.</div>');
					}
					$('#myModal').modal('hide');
				})
				.fail(function() {
					// Send the user off to the connection error page
				});
				event.preventDefault();
			});

			$("#myModal").on('hidden.bs.modal', function () {
				$(this).remove();
			});

        break;
		case "edit-temp" :
			var primary_key = target.attr("id");
			var title = target.find('h3').html();

			var modal = '<div class="modal" id="myModal">' +
				'<div class="modal-dialog">' +
				'<div class="modal-content">' +
				'<div class="modal-header">' +
				'<h4 class="modal-title">' +
				'Editing ' + title + '</h4>' + 
				'</div><div class="container"></div>' +
				'<form role="form" id="edittasktemp-form" ' +
				'name="deletetasklib-form">'+
				'<div class="modal-body">' +
				'<label for="template-name">Task Template Name</label>' +
				'<input type="text" name="template-name" id="template-name" class="form-control">' +
				'<br />' +
				'<label for="template-desc">Template Description</label>' +
				'<textarea name="template-desc" id="template-desc" class="form-control" cols="3" rows="5"></textarea>' +
				'<br />' +
				'<label for="CBED">Template Completion Before End Date</label>' +
				'<input type="text" name="CBED" id="CBED" class="form-control">' +
				'</div>' +
				'<div class="modal-footer">' + 
				'<input type="hidden" name="FormID" value="edit-task-temp">' +
				'<input type="hidden" name="PrimaryKey" ' +
				'value="' + primary_key + '">' +
				'<button type="button" data-dismiss="modal" ' +
				'class="btn btn-default">Cancel</button>' +
				'<button type="submit" ' + 
				'class="btn btn-primary">Save</button>' +
				'</div>' +
				'</form>' +
				'</div>' +
				'</div>' +
				'</div>';

			$('#results-wrapper').append(modal);
			
			search_single("tasktemplate", primary_key).done(function(result) {
				var results = JSON.parse(result);
				var status = results.status;
				if(status.indexOf("success") >= 0) {
					// Success so fill the input fields
					$('#template-name').val(results[0].Name);
					$('#template-desc').val(results[0].Description);
					$('#CBED').val(results[0].CBED);
				} else {
					// Something failed so show an error
				}
			}).fail(function(result) {
				// Send the user to the connection error page
			});
			
			$('#myModal').modal({show:true});
			
			$('#edittasktemp-form').submit(function(event) {
				var theForm = $(this);
				process_form_new(theForm, event).done(function(result) {
					var results = JSON.parse(result);
					var status = results.status;
					if(status.indexOf("success") >= 0) {
						// Success so show a notification
						$("#notification-area").empty().append('<div class="alert alert-success">' + 
															   '<span class="pull-right glyphicon glyphicon-ok" aria-hidden="true"></span> <strong><u>' +
															   results.name + '</u></strong> updated successfully.</div>');
						
						var updated_content = '<h4>Description</h4>' +
							'<p>' + results.desc + '</p> ' +
							'<h4>Completion Before Due Date</h4>' +
							'<p>' + results.cbed + ' Days</p> ' +
							'</div>';
						
						$('#' + results.target).find('h3').text(results.name);
						$('#' + results.target).find('#content').html(updated_content);
					} else {
						// Something failed so show an error
						$("#notification-area").empty().append('<div class="alert alert-danger">' + 
															   '<span class="pull-right glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> ' +
															   'There was an issue with updating <strong><u>' +
															   'Template' + 
															   '</u></strong>.</div>');
					}
					$('#myModal').modal('hide');
				})
				.fail(function() {
					// Send the user off to the connection error page
				});
				event.preventDefault();
			});

			$("#myModal").on('hidden.bs.modal', function () {
				$(this).remove();
			});
		break;
		case "del-temp-perm" :
			var title = target.find('h3').html();
			var content = target.find('#content').html();
			var modal = '<div class="modal" id="myModal">' +
				'<div class="modal-dialog">' +
				'<div class="modal-content">' +
				'<div class="modal-header">' +
				'<h4 class="modal-title">' +
				'Delete ' + title + '? </h4>' + 
				'</div><div class="container"></div>' +
				'<div class="modal-body">' +
				content + '</div>' +
				'<div class="modal-footer">' +
				'<span class="pull-left">' + 
				'<strong>Note:</strong> This deletion is PERMANENT' +
				'</span>' +
				'<form role="form" id="deletetasktemp-form" ' +
				'name="deletetasklib-form">'+
				'<input type="hidden" name="FormID" value="del-task-temp">' +
				'<input type="hidden" name="PrimaryKey" ' +
				'value="' + primary_key + '">' +
				'<button type="button" data-dismiss="modal" ' +
				'class="btn btn-default">Cancel</button>' +
				'<button type="submit" ' + 
				'class="btn btn-primary">Delete</button>' +
				'</form>' +
				'</div>' +
				'</div>' +
				'</div>' +
				'</div>';

			$('#results-wrapper').append(modal);
			$('#myModal').modal({show:true});

			$('#deletetasktemp-form').submit(function(event) {
				var theForm = $(this);
				process_form_new(theForm, event).done(function(result) {
					var results = JSON.parse(result);
					var status = results.status;
					if(status.indexOf("success") >= 0) {
						// Success so remove the target
						$('#' + results.target).fadeOut(300, function() {
							$('#' + results.target).remove();
						});
					} else {
						// Something failed so show an error
					}
					$('#myModal').modal('hide');
				})
				.fail(function() {
					// Send the user off to the connection error page
				});
				event.preventDefault();
			});

			$("#myModal").on('hidden.bs.modal', function () {
				$(this).remove();
			});
		break;
		case "comp-task" :
			var task_id = target.attr("id");
			complete_task(task_id).done(function(result) {
				var results = JSON.parse(result);
				var status = results.status;
				if(status.indexOf("success") >= 0) {
					// Success so update the task
					$('#' + task_id + ' #comp-task').removeClass('task-status-ip').addClass('task-status-complete').html("Complete");
				} else {
					// Something failed so show an error
				}
			}).fail(function(result) {
				// Send the user off to the connection error page
			});
		break;
		case "del-task" :
			var task_id = target.attr("id");
			delete_task(task_id).done(function(result) {
				var results = JSON.parse(result);
				var status = results.status;
				if(status.indexOf("success") >= 0) {
					// Success so remove the task
					
					$('#' + task_id).fadeOut(300, function() {
						var task_wrapper = $('#' + task_id).parent();
						$('#' + task_id).remove();
						if(task_wrapper.contents().length == 0) {
                            task_wrapper.append('<h3 class="no-results">' +
                                                           'No Tasks Found</h3>');
                        }
        			});
				} else {
					// Something failed so show an error
				}
			}).fail(function(result) {
				// Send the user off to the connection error page
			});
		break;
		case "edit-task" :
			var task_id = target.attr("id");
			var title = target.find('h3').html();
			
			var modal = '<div class="modal" id="myModal">' +
				'<div class="modal-dialog">' +
				'<div class="modal-content">' +
				'<div class="modal-header">' +
				'<h4 class="modal-title">' +
				'Editing ' + title + '</h4>' + 
				'</div><div class="container"></div>' +
				'<form role="form" id="edittask-form" ' +
				'name="deletetasklib-form">'+
				'<div class="modal-body">' +
				'<label for="task-name">Task Name</label>' +
				'<input type="text" name="task-name" id="task-name" class="form-control">' +
				'<br />' +
				'<label for="task-desc">Task Description</label>' +
				'<textarea name="task-desc" id="task-desc" class="form-control" cols="3" rows="5"></textarea>' +
				'<br />' +
				'<label for="CBED">Task Completion Before End Date (In Days)</label>' +
				'<input type="text" name="CBED" id="CBED" class="form-control">' +
				'<br />' +
				'<label class="control-label" for="RCD">Revised Completion Date</label>' +
				'<div class="input-group date" id="datetimepicker">' +
				'<input type="text" class="form-control" id="RCD" name="RCD" ' +
				'data-date-format="YYYY/MM/DD" placeholder="YYYY/MM/DD">' +
				'<span class="input-group-btn">' +
				'<button class="btn btn-default" type="button">' +
				'&nbsp;' + '<span class="glyphicon-calendar glyphicon"></span>' +
				'&nbsp;' + '</button>' + '</span>' + '</div>' +
				'</div>' +
				'<div class="modal-footer">' + 
				'<input type="hidden" name="FormID" value="edit-task">' +
				'<input type="hidden" name="PrimaryKey" ' +
				'value="' + task_id + '">' +
				'<button type="button" data-dismiss="modal" ' +
				'class="btn btn-default">Cancel</button>' +
				'<button type="submit" ' + 
				'class="btn btn-primary">Save</button>' +
				'</div>' +
				'</form>' +
				'</div>' +
				'</div>' +
				'</div>';

			$('#results-wrapper').append(modal);

			search_single("task_direct", task_id).done(function(result) {
				var results = JSON.parse(result);
				console.log(results);
				var status = results.status;
				if(status.indexOf("success") >= 0) {
					// Success so fill the input fields
					$('#task-name').val(results[0].Name);
					$('#task-desc').val(results[0].Description);
					$('#CBED').val(results[0].CBED);
					if(results[0].RCD === "0000-00-00") {
						$('#RCD').val("");
					} else {
						$('#RCD').val(results[0].RCD);
					}
				} else {
					// Something failed so show an error
				}
			}).fail(function(result) {
				// Send the user to the connection error page
			});

			$('#myModal').modal({show:true});
			
			$('#edittask-form').submit(function(event) {
				var theForm = $(this);
				process_form_new(theForm, event).done(function(result) {
					var results = JSON.parse(result);
					var status = results.status;
					if(status.indexOf("success") >= 0) {
						// Success so show a notification
						$("#notification-area").empty().append('<div class="alert alert-success">' + 
															   '<span class="pull-right glyphicon glyphicon-ok" aria-hidden="true"></span> <strong><u>' +
															   results.name + '</u></strong> updated successfully.</div>');

						var updated_content = results.name + ' - ' +
							' <span class="due-date"> Due By ' + results.due + 
							' </span>';
						
						$('#' + results.target).find('#task-info').html(updated_content);
					} else {
						// Something failed so show an error
						$("#notification-area").empty().append('<div class="alert alert-danger">' + 
															   '<span class="pull-right glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> ' +
															   'There was an issue with updating <strong><u>' +
															   'Task' + 
															   '</u></strong>.</div>');
					}
					$('#myModal').modal('hide');
				})
				.fail(function() {
					// Send the user off to the connection error page
				});
				event.preventDefault();
			});

			$("#myModal").on('hidden.bs.modal', function () {
				$(this).remove();
			});
			
		break;
    }
}