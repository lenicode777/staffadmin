$(document).ready(function() 
{
	

	//================================================Verification de la validite des champs de saisie==============================
	const nomId = 'nom'; const nomElmt = $('#'+nomId); const nomErrorMsg0 = "Veuillez saisir le nom de l'employé";
	const nomErrorMsg1 = 'Veuillez saisir un nom correcte.'; const nomErrorMsgId = 'error-msg-nom';
    const nomRegExp0 = /^[a-z]+$/i; const nomRegExp1 = /^[a-z]+[\s\-']?[[a-z]+[\s\-']?]*[a-z]+$/gi;
    const nomRegExp = /^[a-z \-']+$/gi;

	const prenomId = 'prenom'; const prenomElmt = $('#'+prenomId); const prenomErrorMsg0 = "Veuillez saisir le prénom de l'employé";
	const prenomErrorMsg1 = 'Veuillez saisir un prénom correcte.'; const prenomErrorMsgId = 'error-msg-prenom'; const prenomRegExp = /^[a-z \-']+$/gi;

	const dateNaissId = 'dateNaiss';const dateNaissElmt = $('#'+dateNaissId); const dateNaissErrorMsg0 = "Veuillez saisir la date de naissance de l'employé.";
	//const dateNaissErrorMsg1 = 'la date de naissance doit être comprise entre ' + dateNaissMin + ' et ' + + dateNaissMax;
	const dateNaissErrorMsgId = 'error-msg-dateNaiss'; const dateNaissRegExp = /^[a-z]+$/i;

	const lieuNaissanceId = 'lieuNaissance'; const lieuNaissanceElmt = $('#'+lieuNaissanceId); const lieuNaissanceErrorMsg0 = "Veuillez saisir le lieu de naissance de l'employé";
	const lieuNaissanceErrorMsg1 = 'Veuillez saisir un lieu de naissance correcte.'; const lieuNaissanceErrorMsgId = 'error-msg-lieuNaissance'; const lieuNaissanceRegExp = /^[a-z]+$/i;

	const emailId = 'email'; const emailElmt = $('#'+emailId); const emailErrorMsg0 = 'Veuillez saisir une adresse mail'; const emailErrorMsg1 = 'Veuillez saisir une adresse mail correcte.';
	const emailErrorMsg2 = 'Cette adresse mail appartient à un autre employé.'; const emailErrorMsgId = 'error-msg-email'; 
	const emailAjaxUrl = '/staffadmin/employe/ajaxvalidation/email/'; const emailRegExp =/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

	const telId = 'tel'; const telElmt = $('#'+telId); const telErrorMsg0 = 'Veuillez saisir un numéro de téléphone'; const telErrorMsg1 = 'Veuillez saisir un numéro de téléphone correcte.';
	const telErrorMsg2 = 'Ce numéro de téléphone appartient à un autre employé.'; const telErrorMsgId = 'error-msg-tel'; 
	const telAjaxUrl = '/staffadmin/employe/ajaxvalidation/tel/'; const telRegExp =/[0-9]{2}\s[0-9]{2}\s[0-9]{2}\s[0-9]{2}/;

	const nomPereId = 'nomPere'; const nomPereElmt = $('#'+nomPereId); const nomPereErrorMsg0 = "Veuillez saisir le nom du père de l'employé";
	const nomPereErrorMsg1 = 'Veuillez saisir un nom correcte.'; const nomPereErrorMsgId = 'error-msg-nomPere'; const nomPereRegExp = /^[a-z \-']+$/gi;

	const nomMereId = 'nomMere'; const nomMereElmt = $('#'+nomMereId); const nomMereErrorMsg0 = "Veuillez saisir le nom de la mère de l'employé";
	const nomMereErrorMsg1 = 'Veuillez saisir un nom correcte.'; const nomMereErrorMsgId = 'error-msg-nomMere'; const nomMereRegExp = /^[a-z \-']+$/gi;

	const matriculeId = 'matricule'; const matriculeElmt = $('#'+matriculeId); const matriculeErrorMsg0 = 'Veuillez saisir un matricule'; const matriculeErrorMsg1 = 'Veuillez saisir un matricule correcte.';
	const matriculeErrorMsg2 = 'Ce matricule appartient à un autre employé.'; const matriculeErrorMsgId = 'error-msg-matricule';
	const matriculeAjaxUrl = '/staffadmin/employe/ajaxvalidation/matricule/'; const matriculeRegExp =/^[0-9a-z \-]+$/gi;

	const fonctionId = 'fonction'; const fonctionElmt = $('#'+fonctionId); const fonctionErrorMsg0 = "Veuillez saisir la fonction de l'employé"; const fonctionErrorMsg1 = 'Veuillez saisir une fonction correcte.';
	const fonctionErrorMsgId = 'error-msg-fonction';

	const gradeId = 'grade'; const gradeElmt = $('#'+gradeId); const gradeErrorMsg0 = "Veuillez saisir le grade de l'employé."; const gradeErrorMsg1 = 'Veuillez saisir un grade correcte.';
	const gradeErrorMsgId = 'error-msg-grade';

	nomElmt.blur(()=>{checkSimpleStringField(nomId, nomRegExp, nomErrorMsg0, nomErrorMsg1, nomErrorMsgId)});
	prenomElmt.blur(()=>{checkSimpleStringField(prenomId, prenomRegExp, prenomErrorMsg0, prenomErrorMsg1, prenomErrorMsgId)});
	emailElmt.blur(function (){ajaxCheckExisting(emailId, emailErrorMsg0, emailErrorMsg1, emailRegExp,'GET', emailAjaxUrl, emailErrorMsg2, emailErrorMsgId);});
    telElmt.blur(function (){ajaxCheckExisting(telId, telErrorMsg0, telErrorMsg1, telRegExp,'GET', telAjaxUrl, telErrorMsg2, telErrorMsgId);});
    matriculeElmt.blur(function (){ajaxCheckExisting(matriculeId, matriculeErrorMsg0, matriculeErrorMsg1, matriculeRegExp,'GET', matriculeAjaxUrl, matriculeErrorMsg2, matriculeErrorMsgId);});
	function checkSimpleStringField(fieldCheckedId, regExp, errorMsg0, errorMsg1, errorMsgId)
	{
		var valueChecked = $('#'+ fieldCheckedId).val();
		const errorMsgElmt = $('#'+errorMsgId);
		if(valueChecked == ''){errorMsgElmt.fadeIn('slow').text(errorMsg0);}
		else if(! isValideSimpleString(valueChecked, regExp)){errorMsgElmt.fadeIn('slow').text(errorMsg1);}
		else{errorMsgElmt.fadeOut('slow');}
	}

	function ajaxCheck(elmtCheckedId, errorMsg, errorMsgId, exists)
	{
		var elmtChecked = $('#' + elmtCheckedId);
		const errorMsgElmt = $('#'+ errorMsgId);
		//var valueChecked = elmtChecked.val();
		if( exists =='true' || exists == true)
		{
			errorMsgElmt.text('* '+ errorMsg).show('slow');
		}
		else{errorMsgElmt.hide('slow');  return true;}
	}

	function isValideSimpleString(nomString, regEx)
	{
		if(nomString.match(regEx)){return true;}
		return false;
	}

	
	function ajaxCheckExisting(elmtCheckedId, errorMsg0, errorMsg1, regExp,requestMethodType, urlTarget, errorMsg2, errorMsgId)
	{
		var elmtChecked = $('#'+elmtCheckedId);
		var valueChecked = $('#'+elmtCheckedId).val();
		checkSimpleStringField(elmtCheckedId, regExp, errorMsg0, errorMsg1, errorMsgId)		
		const existingPromise = new Promise(
								                (resolve, reject)=>
												{
												    if(isValideSimpleString(valueChecked, regExp)) 
													{
												        $.blockUI();
												        $.ajax
												        (
																{
																    type: requestMethodType,
																	url: urlTarget+valueChecked,
																	success: function(result){resolve(result);},
																	error: function(e)
																	                   {
																						  alert('URL : '+urlTarget+valueChecked); 
																						  reject();
																				       }
																}
												        );
																							
																							
												    }
												}
								            );
			existingPromise.then(
										(result)=>
										{
											
											ajaxCheck(elmtCheckedId, errorMsg2, errorMsgId, result);
											$.unblockUI();
										}
									).catch
									  (
										function()
										{
											alert('An error occured !');
										}
									  );								 
	}
//==================================================================================================================================================================

	$('#validate').click //Gestion du click sur le boutton poursuivre (On en profite pour verifier la validite de l'ensemble du formulaire)
					(
						function()
						{
							const verifyValidity = new Promise
															(
																(resolve, reject) =>
																{
																	$('#nom').trigger('blur'); 
																	$('#prenom').trigger('blur'); 
																	$('#email').trigger('blur');
																	if($('#error-msg-nom').css('display') == 'none' && $('#error-msg-prenom').css('display') == 'none' && $('#error-msg-email').css('display') == 'none' )	{resolve();}																
																	else{reject();}
																}
															);
						
							verifyValidity.then
											(function()
												{					
													//alert('RESOLVE');	
													$('#form-error-msg').fadeOut(1000, 'linear');							
													$('#next').show('fast');
													$('#next').trigger('click');
													$('#next').hide('fast');
													$('#validate').hide('slow');
													$('#previous').fadeIn('slow');
													$('#save').fadeIn('slow');
												}
											).catch
											(
												function()
												{
													$('#form-error-msg').text("Veuillez renseigner correctement tous les champs du formulaire");
													$('#form-error-msg').fadeIn(1000, 'linear');
													$('#goto-form-error-msg').trigger('click');
												}
											);
						}
					);

	$('#previous').click
				(
					function()
					{
						$('#save').fadeOut('slow');
						$('#previous').fadeOut('slow');
						$('#validate').fadeIn('slow');
						
					}
					

				);

	//========================================================Faire defiler le carrousel================================================
	$('.carousel').carousel('pause');
	carouselClick('next', 'next');
	carouselClick('previous', 'prev');
	onBlur();

	function carouselClick(ClicktargetId, action)
	{
		$('#'+ClicktargetId).click
							(
								function()
								{
									$('.carousel').carousel(action);
								}
							)
	}
	//==================================================================================================================================

	//=====================Je report les valeurs saisie par l'utilisateur dans la deuxieme partie du carrousel==========================
	function onBlur()
	{
		reportValueOnBlur('nom', 'cfrmNom');
		reportValueOnBlur('prenom', 'cfrmPrenom');
		reportValueOnBlur('dateNaiss', 'cfrmDateNaiss');
		reportValueOnBlur('lieuNaissance', 'cfrmLieuNaissance');
		reportValueOnBlur('tel', 'cfrmTel');
		reportValueOnBlur('email', 'cfrmEmail');
		reportValueOnBlur('nomPere', 'cfrmNomPere');
		reportValueOnBlur('nomMere', 'cfrmNomMere');
		reportValueOnBlur('matricule', 'cfrmMatricule');
		reportValueOnBlur('nomFonction', 'cfrmFonction');
		reportValueOnBlur('nomGrade', 'cfrmGrade');
	}
	function reportValueOnBlur(idSaisie, idAffiche)
	{
		$('#'+idSaisie).on('blur', function(){$('#'+idAffiche).text($('#'+idSaisie).val());});
	}
	//==================================================================================================================================
	/*
	 isValideDateNaissance(dateNaissance)
	{
		dateNaissance = $('dateNaiss').val();
		dateNaiss = Date.parse(dateNaissance);
		if((now()-dateNaissance) > 19 ){return true;}
		return false;
	} */

	$('#tel').mask("99 99 99 99", {placeholder:"## ## ## ##"});
	
	$( function() {
		$( "#dateNaiss" ).datepicker
						(
							{
								changeYear: true,
								changeMonth: true,
								dateFormat: "dd-mm-yy",
								maxDate: "-20y",
								minDate: "-100y",
								monthNamesShort: ["Jan", "Fév", "Mar", "Avr", "Mai", "Juin", "Juil", "Aout", "Sep", "Oct", "Nov", "Déc"],								
								dayNamesMin: ["Di", "Lu", "Ma", "Me", "Je", "Ve", "Sa"],
								constraintInput: true,
								duration: "slow"
							}
						);
					 } 
	 );
    
	$( function() {
		$( "#dateService1" ).datepicker
						(
							{
								changeYear: true,
								changeMonth: true,
								dateFormat: "dd-mm-yy",
								maxDate: "-0y",
								minDate: "-50y",
								monthNamesShort: ["Jan", "Fév", "Mar", "Avr", "Mai", "Juin", "Juil", "Aout", "Sep", "Oct", "Nov", "Déc"],								
								dayNamesMin: ["Di", "Lu", "Ma", "Me", "Je", "Ve", "Sa"],
								constraintInput: true,
								duration: "slow"
							}
						);
					 } 
	 );    
    
	$( function() {
		$( "#dateServiceDMP" ).datepicker
						(
							{
								changeYear: true,
								changeMonth: true,
								dateFormat: "dd-mm-yy",
								maxDate: "-0y",
								minDate: "-50y",
								monthNamesShort: ["Jan", "Fév", "Mar", "Avr", "Mai", "Juin", "Juil", "Aout", "Sep", "Oct", "Nov", "Déc"],								
								dayNamesMin: ["Di", "Lu", "Ma", "Me", "Je", "Ve", "Sa"],
								constraintInput: true,
								duration: "slow"
							}
						);
					 } 
	 );
    
    function onSdChange(sdId)
    {
        const sdChangePromise = new Promise((resolve, reject)=>
        {
        	$.blockUI();
            $.ajax
                (
                   {
                        type: "GET",
                        url: "/staffadmin/employe/ajaxSDChanging/?idSD="+sdId,
                        success: function(result){resolve(result);},
                        error: function(e)
                        {
                              alert('URL : '+"/staffadmin/employe/ajaxSDChanging?idSD="+sdId); 
                              reject();
                        }
                    }
                );
        });

        sdChangePromise.then((result)=>
        {
            $('#idService').children('option:not(:first)').remove();  

            $.each(result, function(key, value) 
            {
            $('#idService')
             .append($("<option></option>")
             .attr("value",value.id)
             .text(value.nom));
            $.unblockUI();
            });
        }).catch((err)=>{alert(err); $.unblockUI();});

    }

    $("#idSD").change(()=>
    {
        var selectedSDId = $("#idSD").children("option:selected").val();  
        onSdChange(selectedSDId);
    });       
         
    ///(Employe emp, String dateNaiss, String statusAgent, String idEmploi, String idFonction, String idGrade, 
							   //String idSD, String idService, String dateService1, String dateServiceDMP)
});
