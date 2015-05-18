function resetFields(){
    var inputs = document.getElementsByTagName("input");
    inputs.style.backgroundColor = "white";
    var str = '';
    var elem = document.getElementById('EOPAddBook').elements;
    for(var i = 0; i < elem.length; i++)
    {
	elem[i].style.backgroundColor = "white";
    }
}

function validateAll(){
    var reasonforerror = "";
    reasonforerror += checkBarcode();
    reasonforerror += checkDiscipline();
    reasonforerror += checkTitle();
    reasonforerror += checkAuthor1();
    reasonforerror += checkAuthor2();
    reasonforerror += checkAuthor3();
    reasonforerror += checkAuthor4();
    reasonforerror += checkPublisher();
    reasonforerror += checkIsbn();
    reasonforerror += checkSuggestedprice();
    if(reasonforerror != ""){
        alert(reasonforerror);
    }
}

function checkBarcode(){
    var error = "";
    var barcode = document.getElementById("barcode").value;
    //var reg = barcode.match("^[0-9]{6}$");
    //document.getElementById("test").innerHTML = reg; 
    if (!barcode.match("^[0-9]{6}$")) {
        document.getElementById("barcode").style.backgroundColor = "pink";
        return error = "Barcode not in correct format: \n";
    }
    document.getElementById("barcode").style.backgroundColor = "lightgreen";
    return error;
}

function checkDiscipline(){
    var error = "";
    var discipline = document.getElementById("discipline").value;
    if (!discipline.match("^[A-Za-z0-9\s\-]+$")) {
        document.getElementById("discipline").style.backgroundColor = "pink";
        return error = "Discipline not in correct format: \n";
    }
    document.getElementById("discipline").style.backgroundColor = "lightgreen";
    return error;
}

function checkTitle(){
    var error = "";
    var title = document.getElementById("title").value;
    if (!title.match("^[A-Za-z0-9\s\-]+$")) {
        document.getElementById("title").style.backgroundColor = "pink";
        return error = "Title not in correct format: \n";
    }
    document.getElementById("title").style.backgroundColor = "lightgreen";
    return error;
}

function checkAuthor1(){
    var error = "";
    var author1 = document.getElementById("author1").value;
    if (!author1.match("^[A-Za-z0-9\s\-]+$")) {
        document.getElementById("author1").style.backgroundColor = "pink";
        return error = "Author1 not in correct format: \n";
    }
    document.getElementById("author1").style.backgroundColor = "lightgreen";
    return error;
}

function checkAuthor2(){
    var error = "";
    var author2 = document.getElementById("author2").value;
    if (!author2.match("^[A-Za-z0-9\s\-]*$")) {
        document.getElementById("author2").style.backgroundColor = "pink";
        return error = "Author2 not in correct format: \n";
    }
    document.getElementById("author2").style.backgroundColor = "lightgreen";
    return error;
}

function checkAuthor3(){
    var error = "";
    var author3 = document.getElementById("author3").value;
    if (!author3.match("^[A-Za-z0-9\s\-]*$")) {
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
