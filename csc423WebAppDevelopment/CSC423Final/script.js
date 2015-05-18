function resetFields(){
    var inputs = document.getElementsByTagName("input");
    //inputs.style.backgroundColor = "white";
    var str = '';
    var elem = document.getElementById("EOPAddStudentBorrower").elements;
    for(var i = 0; i < elem.length; i++)
    {
	elem[i].style.backgroundColor = "white";
	elem[i].value = "";
    }
}

function validateAll(){
    var reasonforerror = "";
    reasonforerror += checkBannerID();
    reasonforerror += checkFirstName();
    reasonforerror += checkLastName();
    reasonforerror += checkContactPhone();
    reasonforerror += checkEmail();
    if(reasonforerror != ""){
        alert(reasonforerror);
	//.preventDefault();
    } else {
	document.getElementById("EOPAddStudentBorrower").submit();	
    }
}

function checkBannerID(){
    var error = "";
    var bannerid = document.getElementById("bannerid").value;
    //var reg = bannerid.match("^[0-9]{9}$");
    //document.getElementById("bannerid").innerHTML = reg; 
    if (!bannerid.match("^[0-9]{9}$")) {
        document.getElementById("bannerid").style.backgroundColor = "pink";
        return error = "BannerID not in correct format: \n";
    }
    document.getElementById("bannerid").style.backgroundColor = "lightgreen";
    return error;
}

function checkFirstName(){
    var error = "";
    var firstname = document.getElementById("firstname").value;
    if (!firstname.match("^[A-Za-z]+$")) {
        document.getElementById("firstname").style.backgroundColor = "pink";
        return error = "First name not in correct format: \n";
    }
    document.getElementById("firstname").style.backgroundColor = "lightgreen";
    return error;
}

function checkLastName(){
    var error = "";
    var lastname = document.getElementById("lastname").value;
    if (!lastname.match("^[A-Za-z]+$")) {
        document.getElementById("lastname").style.backgroundColor = "pink";
        return error = "Last name not in correct format: \n";
    }
    document.getElementById("lastname").style.backgroundColor = "lightgreen";
    return error;
}

function checkContactPhone(){
    var error = "";
    //var phone = document.getElementById("phonenumber").value;
    //if (!phone.match("^[0-9]{3}[0-9]{3}[0-9]{4}$")) {
      //  document.getElementById("phone").style.backgroundColor = "pink";
        //return error = "Phone number not in correct format: \n";
    //}
    //document.getElementById("phonenumber").style.backgroundColor = "lightgreen";
    return error;
}

function checkEmail(){
    var error = "";
    var email = document.getElementById("email").value;
    //if (!email.match("^[A-Za-z0-9]*\@[A-Za-z0-9]*\.'com'|'edu'|'net'|'gov'$")) {
    if (!email.match("^[A-Za-z0-9]*\@[A-Za-z0-9]*\.[a-z]{3}$")) {
        document.getElementById("email").style.backgroundColor = "pink";
        return error = "Email not in correct format: \n";
    }
    document.getElementById("email").style.backgroundColor = "lightgreen";
    return error;
}

function checkAuthor3(){
    var error = "";
    var author3 = document.getElementById("author3").value;
    if (!author3.match("^[A-Za-z0-9\s\-]$")) {
        document.getElementById("author3").style.backgroundColor = "pink";
        return error = "Author3 not in correct format: \n";
    }
    document.getElementById("author3").style.backgroundColor = "lightgreen";
    return error;
}

function checkAuthor4(){
    var error = "";
    var author4 = document.getElementById("author4").value;
    if (!author4.match("^[A-Za-z0-9\s\-]*$")) {
        document.getElementById("author4").style.backgroundColor = "pink";
        return error = "Author4 not in correct format: \n";
    }
    document.getElementById("author4").style.backgroundColor = "lightgreen";
    return error;
}

function checkPublisher(){
    var error = "";
    var publisher = document.getElementById("publisher").value;
    if (!publisher.match("^[A-Za-z0-9\s\-]+$")) {
        document.getElementById("publisher").style.backgroundColor = "pink";
        return error = "Publisher not in correct format: \n";
    }
    document.getElementById("publisher").style.backgroundColor = "lightgreen";
    return error;
}

function checkIsbn(){
    var error = "";
    var isbn = document.getElementById("isbn").value;
    if (!isbn.match("^[0-9x]+([\-]?[0-9x]*)*$")) {
        document.getElementById("isbn").style.backgroundColor = "pink";
        return error = "ISBN not in correct format: \n";
    }
    document.getElementById("isbn").style.backgroundColor = "lightgreen";
    return error;
}

function checkSuggestedprice(){
    var error = "";
    var suggestedprice = document.getElementById("suggestedprice").value;
    if (!suggestedprice.match("^[0-9]+([\.][0-9][0-9])?$")) {
        document.getElementById("suggestedprice").style.backgroundColor = "pink";
        return error = "Suggested Price not in correct format: \n";
    }
    document.getElementById("suggestedprice").style.backgroundColor = "lightgreen";
    return error;
}
