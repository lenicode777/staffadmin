	    		$(document).ready(function() 
			    		{
		    			            // Setup - add a text input to each footer cell
		    			            $('#tbl-list-agents tfoot th').each( function () 
		    					    {
		    			                var title = $(this).text();
		    			                $(this).html( '<input type="text" placeholder="Search '+title+'" />' );
		    			            } );
		    			         
		    			            // DataTable
		    			            var table = $('#tbl-list-agents').DataTable(
		    			    	    {
		    			                initComplete: function () 
		    			                {
		    			                    // Apply the search
		    			                    this.api().columns().every( function () 
		    			    	            {
		    			                        var that = this;
		    			                        $( 'input', this.footer() ).on( 'keyup change clear', function () 
		    			    	                {
		    			                            if ( that.search() !== this.value ) 
		    				                        {
		    			                                that
		    			                                    .search( this.value )
		    			                                    .draw();
		    			                            }
		    			                        } );
		    			                    } );
		    			                }
		    			            });
		    	    	} );