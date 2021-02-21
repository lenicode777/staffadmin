function confirmationDialog(targetFormId, dialogBodyId, dialogTitle, widthSize, heightSize)
{
    $("#"+dialogBodyId).dialog
    (
        {
            option: "ui-dialog-titlebar",
            classes: 
            {
                "ui-dialog": "highlight"
            },
            //hide: { effect: "explode", duration: 1000 },
            modal: true,
            position: { my: "center top", at: "center top", of: "#"+targetFormId },
            show: { effect: "blind", duration: 800 },
            hide: { effect: "fade", duration: 800 },
            title: dialogTitle,
            width: widthSize,
            height: heightSize,
            buttons: 
            [
                {
                    text: "Ok",
                    
                    click: function() 
                    {
                        $("#"+targetFormId).submit();
                        $( this ).dialog( "close" );
                    }
                },

                {
                    text: "Cancel",
                    
                    click: function() 
                    {
                        $( this ).dialog( "close" );
                    }
                }
            ]
        }
    );
}