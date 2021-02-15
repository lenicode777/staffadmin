$("#idTypeUniteAdmin").change(function (e) 
{ 
    $.ajax({
        type: "get",
        url: "http://localhost:8081/staffadmin/saf/frm-uniteAdmin/ajax/onTypeUniteAdminChange/"+this.value,
        success: function (response) 
        {
            //alert("HELLO AJAX");
            $("#idTutelleDirecte").children('option').remove();
            $('#idTutelleDirecte').append($("<option></option>"));
            $.each(response, function(key, value) 
            {
            	//alert(value["sigle"]);
                $('#idTutelleDirecte')
                    .append($("<option></option>")
                    .attr("value",value["idUniteAdmin"])
                    .text(value["sigle"]));
            });
        }
    });  
});

$(document).ready(function() 
{
    $('#idTutelleDirecte').select2();
});
