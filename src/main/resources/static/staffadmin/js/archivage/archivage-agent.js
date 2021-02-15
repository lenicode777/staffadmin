//=======FAIRE LA VALIDATION DES PIECES JOINTES===========================================

function checkInputFile(checkedInputFileElmt, authorizeTypesTable, errorMsgId, typeErrorMsg, sizeErrorMsg)
{
    //alert(checkedInputFileElmt.value);
    var reader = new FileReader();
    if(checkedInputFileElmt.value == "" ||checkedInputFileElmt.value ==null)
    {
        //alert(checkedInputFileElmt.value);
        $("#"+errorMsgId).hide('slow');
        $("#"+errorMsgId).css('display', 'none');
        //switchOnOffBtnValider();
    }
    else
    {
        reader.addEventListener('load', function()
        {   
                var fileType = checkedInputFileElmt.files[0].type;
                var fileSize = checkedInputFileElmt.files[0].size;
                //alert(fileType);
                if(!authorizeTypesTable.includes(fileType))
                {
                    $("#"+errorMsgId).text(typeErrorMsg);
                    $("#"+errorMsgId).show('slow');
                }
                else
                {
                    if(fileSize<1000000)
                    {
                        $("#"+errorMsgId).hide('slow');
                        $("#"+errorMsgId).css('display', 'none');
                    }
                    else
                    {
                        $("#"+errorMsgId).text(sizeErrorMsg);
                        $("#"+errorMsgId).show('slow');
                    }
                }
                //switchOnOffBtnValider();
        });
        
        reader.readAsText(checkedInputFileElmt.files[0]);
        
    }
}
$( function()
{
    $( "#confirmation-modal" ).draggable();
} );                  

//=============================================IMPLEMENTATION DE LA VALIDATION======================================

var noteServiceDAAFInputFile = document.querySelector("#noteServiceDAAF");
var docTypesTable = ["application/pdf", "image/jpeg","image/png"];
var imageTypesTable = ["image/jpeg","image/png"];
var pdfTypesTable = ["application/pdf"];

/*document.querySelector("#noteServiceDAAF").addEventListener("change", function()
{
    checkInputFile(this, pdfTypesTable , "noteServiceDAAF-error-msg", " * Document PDF requis", " * Taille limite = 1Mo" );
});

document.querySelector("#noteServiceDGBF").addEventListener("change", function()
{
    checkInputFile(this, pdfTypesTable , "noteServiceDGBF-error-msg", " * Document PDF requis", " * Taille limite = 1Mo" );
});

document.querySelector("#certificatService1").addEventListener("change", function()
{
    checkInputFile(this, pdfTypesTable , "certificatService1-error-msg", " * Document PDF requis", " * Taille limite = 1Mo" );
});

document.querySelector("#arreteNomination").addEventListener("change", function()
{
    checkInputFile(this, pdfTypesTable , "arreteNomination-error-msg", " * Document PDF requis", " * Taille limite = 1Mo" );
});

document.querySelector("#decisionAttente").addEventListener("change", function()
{
    checkInputFile(this, pdfTypesTable , "decisionAttente-error-msg", " * Document PDF requis", " * Taille limite = 1Mo" );
});

document.querySelector("#cv").addEventListener("change", function()
{
    checkInputFile(this, docTypesTable , "cv-error-msg", " * Fichiers autorisés : PDF, JPEG, PNG", " * Taille limite = 1Mo" );
});

document.querySelector("#pieceIdentite").addEventListener("change", function()
{
    checkInputFile(this, pdfTypesTable , "pieceIdentite-error-msg", " * Fichiers autorisé : PDF, JPEG, PNG", " * Taille limite = 1Mo" );
});

document.querySelector("#photo").addEventListener("blur", function()
{
    checkInputFile(this, imageTypesTable , "photo-error-msg", " * Fichiers autorisé : JPEG, PNG", " * Taille limite = 1Mo" );
});*/

$(document).ready(function() 
{
    $('#typeFile').select2();
});


$("#typeFile").change(function (e) 
{ 
    if(e.target.value != "")
    {
        $("#btnUpload").removeAttr("disabled");
    }
    else
    {
        $("#btnUpload").addAttr("disabled");
    }
});