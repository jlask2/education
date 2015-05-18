function firstnameValidity() {
    var error = "";    
    var w = document.getElementById("firstname");
    document.getElementById("demo").innerHTML = w.value;
    document.getElementById("demo2").innerHTML = /^[A-Z][a-zA-Z\- ]*$/.test(w.value);
    var v = document.getElementById("demo2").innerHTML;
    if (v === "true") {
        w.style.backgroundColor = 'lightGreen';
       // return error;
    } else {
        error = "First name not in correct format: should have only letters and hyphens, the first letter should be" + 
"capitalized\n\n";
        w.style.backgroundColor = 'pink';
        return error;
    }
    window.onload = firstnameValidity;
    return error;
}

function miValidity() {
    var error = "";   
    var w = document.getElementById("mi"); 
    document.getElementById("demo").innerHTML = w.value;
    document.getElementById("demo2").innerHTML = /^[A-Z]$/.test(w.value);
    var v = document.getElementById("demo2").innerHTML;
    if (v === "true") {
        w.style.backgroundColor = 'lightGreen';
        //return error;
    } else if (w.value === "") {
        w.style.background = 'white';
        //return error;
    } else {
        error = "Middle initial not in correct format: should have only a single capital letter or left blank\n\n";
        w.style.backgroundColor = 'pink';
        return error;   
    }
    window.onload = miValidity;
    return error;
}   

function lastnameValidity() {
    var error = "";
    var w = document.getElementById("lastname"); 
    document.getElementById("demo").innerHTML = w.value;
    document.getElementById("demo2").innerHTML = /^[A-Z][a-zA-Z\'\- ]*$/.test(w.value);
    var v = document.getElementById("demo2").innerHTML;
    if (v === "true") {   
        w.style.backgroundColor = 'lightGreen';
        //return error;
    } else {
        error = "Last Name not in correct format: should have only letters, apostraphe's, spaces and hyphens, the" +
"first letter should be capitalized\n\n";
        w.style.backgroundColor = 'pink';
        return error;
    }
    window.onload = lastnameValidity;
    return error;
}


function addressValidity() {
    var error = "";    
    var w = document.getElementById("address"); 
    document.getElementById("demo").innerHTML = w.value;
    document.getElementById("demo2").innerHTML = /^[0-9]+[a-zA-z\.\- ]*$/.test(w.value);
    var v = document.getElementById("demo2").innerHTML;
    if (v === "true") {
        w.style.backgroundColor = 'lightgreen';
        //return error;
    } else {
        error = "Address not in correct format: should have only numbers, periods, hyphens and letters, start with" +
"the number followed by the name of the street\n\n";
        w.style.backgroundColor = 'pink';
        return error;
}
    window.onload = addressValidity;
    return error;
}

function cityValidity() {
    var error = "";    
    var w = document.getElementById("city"); 
    document.getElementById("demo").innerHTML = w.value;
    document.getElementById("demo2").innerHTML = /^[A-Z][a-zA-Z\.\- ]*$/.test(w.value);
    var v = document.getElementById("demo2").innerHTML;
    if (v === "true") {
        w.style.backgroundColor = 'lightGreen';
        //return error;
    } else {
        error = "City not in correct format: should have only letters, hyphens and spaces\n\n";
        w.style.backgroundColor = 'pink';
        return error;
    }
    window.onload = cityValidity;
    return error;
}

function zipValidity() {
    var error = "";    
    var w = document.getElementById("zip"); 
    document.getElementById("demo").innerHTML = w.value;
    document.getElementById("demo2").innerHTML = /^[0-9]{5}$/.test(w.value);
    var v = document.getElementById("demo2").innerHTML;
    if (v === "true") {
        w.style.backgroundColor = 'lightGreen';
        //return error;
    } else {
        error = "Zip Code not in correct format: should have exactly 5 digits only\n\n";
        w.style.backgroundColor = 'pink';
        return error;
    }
    window.onload = zipValidity;
    return error; 
}

function monthdobValidity() {
    var error = "";
    var w = document.getElementById("monthdob"); 
    document.getElementById("demo").innerHTML = w.value;
    document.getElementById("demo2").innerHTML = /^[0-1][0-9]$/.test(w.value);
    var v = document.getElementById("demo2").innerHTML;
    if(v === "true") {
        w.style.backgroundColor = 'lightGreen';
        //return error;
    } else {
        error = "DOB Month not in correct format: should have exactly 2 digits corresponding to a valid month," +
"between 1 and 12\n\n";
        w.style.backgroundColor = 'pink';
        return error;
    }
    window.onload = monthdobValidity;
    return error;
}

function daydobValidity() {
    var error = "";
    var m = document.getElementById("monthdob");
    var w = document.getElementById("daydob"); 
    var v = "";
    document.getElementById("demo").innerHTML = w.value;
    document.getElementById("demo2").innerHTML = /^[0-3][0-9]$/.test(w.value);
    v = document.getElementById("demo2").innerHTML;
    if (v === "true") {
        w.style.backgroundColor = 'lightGreen';
        //return error;
    } else {
        error = "DOB Day not in correct format: should have exactly 2 digits corresponding to a" + 
"valid day, between 1 and 31 for months 1, 3, 5, 7, 8, 10, 12, between 1 and 30 for months" +
"4, 6, 9, 11 and between 1 and 29 for month 2\n\n";
        w.style.backgroundColor = 'pink';
        return error;
    }
    window.onload = daydobValidity;
    return error;
}

function yeardobValidity() {
    var error = "";
    var w = document.getElementById("yeardob"); 
    document.getElementById("demo").innerHTML = w.value;
    document.getElementById("demo2").innerHTML = /^[1][9][1-9][0-9]$/.test(w.value);
    var v = document.getElementById("demo2").innerHTML;
    if(v === "true") {
        w.style.backgroundColor = 'lightGreen';
        //return error;
    } else {
        error = "DOB Year not in correct format: should have exactly 4 digits corresponding to a valid year," + 
"between 1912 and 1998\n\n";
        w.style.backgroundColor = 'pink';
        return error;
    }
    window.onload = yeardobValidity;
    return error;
}

function numofconvValidity() {
    var error = "";
    var w = document.getElementById("numofconv"); 
    document.getElementById("demo").innerHTML = w.value;
    document.getElementById("demo2").innerHTML = /^[0-100]$/.test(w.value);
    var v = document.getElementById("demo2").innerHTML;
    if(v === "true") {
        w.style.backgroundColor = 'lightGreen';
        //return error;
    } else {
        error = "Number of Convictions not in correct format: should have only numbers between 0-100\n\n";
        w.style.backgroundColor = 'pink';
        return error;
    }
    window.onload = numofconvValidity;
    return error;
}

function colorValidity() {
    var error = "";
    var w = document.getElementById("color"); 
    document.getElementById("demo").innerHTML = w.value;
    document.getElementById("demo2").innerHTML = /^[A-Z][A-Z\- ]*$/i.test(w.value);
    var v = document.getElementById("demo2").innerHTML;
    if(v === "true") {
        w.style.backgroundColor = 'lightGreen';
        //return error;
    } else {
        error = "Color not in correct format: should have only letters, hyphens and spaces\n\n";
        w.style.backgroundColor = 'pink';
        return error;
    }
    window.onload = colorValidity;
    return error;
}

function milesValidity() {
    var error = "";
    var w = document.getElementById("miles"); 
    document.getElementById("demo").innerHTML = w.value;
    document.getElementById("demo2").innerHTML = /[0-1]?[0-9]?[0-9]/.test(w.value);
    var v = document.getElementById("demo2").innerHTML;
    if(v === "true") {
        w.style.backgroundColor = 'lightGreen';
        //return error;
    } else {
        error = "Miles not in correct format: should have only numbers between 1-100\n";
        w.style.backgroundColor = 'pink';
        return error;
    }
    window.onload = milesValidity;
    return error;
}

function validateAutoFormOnSubmit() {
    var reasonForError = " ";
    reasonForError += firstnameValidity();
    reasonForError += lastnameValidity();
    reasonForError += miValidity();
    reasonForError += addressValidity();
    reasonForError += cityValidity();
    reasonForError += zipValidity();
    reasonForError += monthdobValidity();
    reasonForError += daydobValidity();
    reasonForError += yeardobValidity();
    reasonForError += numofconvValidity();
    reasonForError += colorValidity();
    reasonForError += milesValidity();
    alert("inside validate");
    if(reasonForError != " ") {
        alert(reasonForError);
        return false;
    }
    window.onload = validateAutoFormOnSubmit;
    return true;
}

function resetFields() {
    var f = document.getElementById("firstname");
    var mi = document.getElementById("mi");
    var l = document.getElementById("lastname");
    var a = document.getElementById("address");
    var c = document.getElementById("city");
    var z = document.getElementById("zip");
    var md = document.getElementById("monthdob");
    var dd = document.getElementById("daydob");
    var yd = document.getElementById("yeardob");
    var n = document.getElementById("numofconv");
    var co = document.getElementById("color");
    var m = document.getElementById("miles");
    
    f.style.backgroundColor = 'white';
    mi.style.backgroundColor = 'white';
    l.style.backgroundColor = 'white';
    a.style.backgroundColor = 'white';
    c.style.backgroundColor = 'white';
    z.style.backgroundColor = 'white';
    md.style.backgroundColor = 'white';
    dd.style.backgroundColor = 'white';
    yd.style.backgroundColor = 'white';
    n.style.backgroundColor = 'white';
    co.style.backgroundColor = 'white';
    m.style.backgroundColor = 'white';
    
    window.onload = resetFields;
}
