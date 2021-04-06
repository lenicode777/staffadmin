function formatDropDownBoxToSelect2(idDropDown)
{
	$(document).ready(function() 
    {
		$('#'+idDropDown).select2();
    });
}

function formatToDataTable(idTable)
{
	$(document).ready(function() 
	{
		$('#'+idTable).DataTable();
	});	
}
