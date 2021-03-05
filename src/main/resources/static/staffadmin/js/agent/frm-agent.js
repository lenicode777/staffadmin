//==================DEFINITION DE QUELQUES FONCTIONS UTILITAIRES POUR LA VALIDATION DU FORMULAIRE==================

//========================================Retourne la date dans le format dd/mm/yyyy====================================
function getDateDefaultFormat(date)
{
    return date.getDate() + "/" +(date.getMonth()+1)+"/"+(date.getYear()+1900);
}
//======================================================================================================================

//==========Active ou desactive le boutton de validation du formulaire selon la validité du formulaire==================
function switchOnOffBtnValider() //
{
    if (
            $("#email-error-msg").css("display") != "none" || $("#numPiece-error-msg").css("display") != "none" ||
            $("#tel-error-msg").css("display") != "none" || $("#matricule-error-msg").css("display") != "none" ||
            $("#dateNaissance-error-msg").css("display") != "none" || $("#datePriseService1-error-msg").css("display") != "none"
        ) 
    {
        $("#btn-valider").attr("disabled", "disabled");
    }
    else 
    {
        $("#btn-valider").removeAttr("disabled");
    }
}     
//======================================================================================================================

//===========================VERIFIE SI LA DATE EST COMPRISE DANS UNE FORCHETTE DONNEE++================================
function checkDate(inputDateId, dateMin, dateMax, errorMsg, errorMsgId)
{
    $("#"+inputDateId).blur(function ()
    {
        var checkedDate = new Date(this.value);
        if(checkedDate < dateMin || checkedDate > dateMax)
        {
            $("#"+errorMsgId).text(errorMsg);
            $("#"+errorMsgId).show("slow");                        
        }
        else
        {
            $("#"+errorMsgId).hide("slow");
            $("#"+errorMsgId).css("display","none");
        }
        switchOnOffBtnValider();
    });
}
//======================================================================================================================

//==================FAIT DES APPELS AJAX POUR VERIFIER LA NON EXISTENCE DES VALEURS UNIQUES============================
function unicityChecking(variant, checkedElmt, checkedValue, errorMsgElmt, errorMsg) 
{
    if(checkedValue != null && checkedValue != "")
    {
        const existingPromise = new Promise((resolve, reject) => 
        {
            var compleUrl="";
            
            if($("#idAgent").val()==undefined)
            {
                compleUrl = agentExistingRestUrl + variant + checkedValue+"?idAgent=0"
            }
            else
            {
                compleUrl = agentExistingRestUrl + variant + checkedValue+"?idAgent="+$("#idAgent").val();
            }
            //alert(compleUrl);
        	$.blockUI();
                $.ajax(
                {
                    type: "get",
                    url: compleUrl,
                    success: function(response) 
                    {
                        resolve(response);
                        $.unblockUI();
                    },
                    error: function(error) 
                    {
                        //alert(agentExistingRestUrl + variant + checkedValue);
                        //alert("An error occured! Please check your connexion");
                        reject(error);
                        $.unblockUI();
                    }
                });
            
        });
    
        txtEmail.blur(function(e) 
        {
            unicityChecking("email/", txtEmail, e.target.value, $("#email-error-msg"), "*adresse email déjà utilisée");
        });
        
        existingPromise.then((value) => 
        {
            if (value) 
            {
                errorMsgElmt.text(errorMsg);
                errorMsgElmt.show("slow");                  
                $("#btn-submit-modal").attr("disabled", "disabled");
            } 
            else 
            {
                errorMsgElmt.hide("slow");
                errorMsgElmt.css("display", "none");
                $("#btn-submit-modal").removeAttr("disabled");
            }
            switchOnOffBtnValider();
            $.unblockUI();
        }).catch((value) => 
        {
            $.unblockUI();
        });
    }
}   
//======================================================================================================================  

//=====================================FAIRE LA VALIDATION DES PIECES JOINTES===========================================


function checkInputFile(checkedInputFileElmt, authorizeTypesTable, errorMsgId, typeErrorMsg, sizeErrorMsg)
{
    //alert(checkedInputFileElmt.value);
    var reader = new FileReader();
    if(checkedInputFileElmt.value == "" ||checkedInputFileElmt.value ==null)
    {
        //alert(checkedInputFileElmt.value);
        $("#"+errorMsgId).hide('slow');
        $("#"+errorMsgId).css('display', 'none');
        switchOnOffBtnValider();
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
                switchOnOffBtnValider();
        });
        
        reader.readAsText(checkedInputFileElmt.files[0]);
        
    }
}
$( function()
{
    $( "#confirmation-modal" ).draggable();
} );                  



//=============================================IMPLEMENTATION DE LA VALIDATION======================================
var today = new Date();
var dateNaissMin = new Date(); 
var dateNaissMax = new Date(); 
var datePriseService1Min = new Date(); 
var datePriseService1Max = new Date(); 
var frmAgent = $("#frm-agent");
var idAgent = $("#idAgent").val();
var txtEmail = $("#email");
var txtNumPiece = $("#numPiece");
var txtTel = $("#tel");
var txtMatricule = $("#matricule");
var agentExistingRestUrl = "http://localhost:8081/staffadmin/exists/";
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

datePriseService1Min.setYear(today.getYear() - 50 + 1900);
datePriseService1Max = today;

dateNaissMin.setYear(today.getYear() -80 +1900);
dateNaissMax.setYear(today.getYear() -18 +1900);
checkDate("dateNaissance", dateNaissMin, dateNaissMax, " * "+getDateDefaultFormat(dateNaissMin)+" < date < "+getDateDefaultFormat(dateNaissMax), "dateNaissance-error-msg");
checkDate("datePriseService1", datePriseService1Min, datePriseService1Max, " * "+getDateDefaultFormat(datePriseService1Min)+" < date < "+getDateDefaultFormat(datePriseService1Max), "datePriseService1-error-msg");

$("#btn-valider").click(function(e) //Au click sur le bouton valider
{
    if (!frmAgent[0].checkValidity()) //Si le formulaire n'est pas valide (Champ requis non renseigné ou email invalide)
    {
        $("#btn-submit").trigger("click");//Je déclenche la soumission du formulaire pour que HTML5 le bolque et affiche le message correspondant 
    } 
    else 
    {
        const formCheckPromise = new Promise((resolve, reject)=>
        {
            resolve();
            $("#btn-modal-trigger").trigger("click");
        });

        formCheckPromise.then(()=>
        {
            txtEmail.trigger("blur");
            txtMatricule.trigger("blur");
            txtNumPiece.trigger("blur");
            txtTel.trigger("blur");
        });
        
    }
});

//Appel ajax pour vérification d'unicité
txtEmail.blur(function(e) 
{
    
    unicityChecking("email/", txtEmail, e.target.value, $("#email-error-msg"), "*adresse email déjà utilisée");
});

txtMatricule.blur(function(e) 
{
    unicityChecking("matricule/", txtMatricule, e.target.value, $("#matricule-error-msg"), "*matricule déjà utilisé");
});

txtNumPiece.blur(function(e) 
{
    unicityChecking("numPiece/", txtNumPiece, e.target.value, $("#numPiece-error-msg"), "*N° déjà utilisé");
});

txtTel.blur(function(e) 
{
    unicityChecking("tel/", txtTel, e.target.value, $("#tel-error-msg"), "*Tel déjà utilisé");
});

$("#btn-modal-trigger").click(function(e)
{
	//alert("HELLO");
	//alert($("#nom").val());
	$("#confirmNom").text($("#nom").val());
	$("#confirmPrenom").text($("#prenom").val());
	$("#confirmDateNaissance").text($("#dateNaissance").val());
	$("#confirmSexe").text($("#sexe option:selected").text());
	$("#confirmEmail").text($("#email").val());
	$("#confirmLieuNaissance").text($("#lieuNaissance").val());
	$("#confirmFixeBureau").text($("#fixeBureau").val());
	$("#confirmTel").text($("#tel").val());
	$("#confirmTypePiece").text($("#typePiece").val());
	$("#confirmNumPiece").text($("#numPiece").val());
	$("#confirmNomPere").text($("#nomPere").val());
	$("#confirmNomMere").text($("#nomMere").val());
	$("#confirmMatricule").text($("#matricule").val());
	$("#confirmFonction").text($("#fonction option:selected").text());
	$("#confirmEmploi").text($("#emploi option:selected").text());
	
	$("#confirmGrade").text($("#grade option:selected").text());
	$("#confirmDatePriseService1").text($("#datePriseService1").val());
	
});
