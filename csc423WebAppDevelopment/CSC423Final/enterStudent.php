
<html>
<head>
<meta charset="utf-8">
<title>Add A Student Borrower</title>
<style>
table {align: center; margin-left:10%; margin-right:10%}
input {length:80%;}
.main-content {align:center; width:80%;}
.2col  {
    float: right;
}
.verticalLine {
    border-left: thin solid black;
}
.centerAlign {
    text-align: center; font-size: 75%
}
* {
margin: 0;
}
html, body {
height: 100%;
}
.wrapper {
min-height: 100%;
height: auto !important;
height: 100%;
margin: 0 auto -8em;
}
.footer, .push {
height: 6em;
}
</style>
<script type="text/javascript" src="./script.js"></script>
</head>
<body>

<div class="wrapper">

<h1>EOP Library System<img src="EOP.png" style="float:right"></img></h1>
<h3>The College at Brockport</h3>
<br/>
<label class="centerAlign"><h4>Logged in as: Gary Owens(800008948). You have Administrator credentials</h4></label>
    
<form id="EOPAddStudentBorrower" name="EOPAddStudentBorrower" action="./processEnterStudent.php" method="post" onsubmit="return validateAll()">
<!--<form id="EOPAddStudentBorrower" name="EOPAddStudentBorrower" action="./processEnterStudent.php" method="post" >-->
<div style="background-color:blue">
<hr style="background-color:blue"/>
<h3 align="center" style="background-color:blue-grey; color:white">ADD A STUDENT BORROWER</h3>
<hr style="background-color:blue"/>
</div>

<br/>

<table class="main-content">
<tr>
<td><label>Banner ID:    </label></td>
<td><input type="text" id="bannerid" name="bannerid" value=""></input></td>
<td><label for="phonenumber[]">Phone Number:  </label></td>
<td>
    <span>
    <input type="text" width="10%" size="3" length="3" maxlength="3" id="phonenumber[]" name="phonenumber[]" value=""></input>-
    <input type="text" width="10%" size="3" length="3" maxlength="3" id="phonenumber[]" name="phonenumber[]" value=""></input>-
    <input type="text" width="10%" size="4" length="4" maxlength="4" id="phonenumber[]" name="phonenumber[]" value=""></input>
    </span>
</td>
</tr>

<tr height="10px"></tr>

<tr>
<td><label>First Name: </label></td>
<td>
    <input type="text" id="firstname" name="firstname" value=""></input>
</td>
<td><label>Email: </label></td>
<td>
    <input type="text" id="email" name="email" value=""></input>
</td>
</tr>

<tr height="10px"></tr>

<tr>
<td><label>Last Name:      </label></td>
<td><input type="text" id="lastname" name="lastname" value=""></input></td>
</tr>
<tr height="10px"></tr>

<tr>
<td><label>Notes:      </label></td>
<td colspan="4"><textarea rows="5" cols="100" width="100%" id="notes" name="notes" value=""></textarea>
</td>
</tr>
</table>

</div>

<div class="push"></div>

<div class="footer">
<footer >
<hr/>
<table>
<tr>
<td width="20%"></td>
<td width="10%">
<!--<input align="center" type="submit" id="submit" name="submitname" value="Submit" onclick="this.disabled=true;this.form.submit()>-->
<a align="center" id="submit" name="submitname" value="Submit" onclick="validateAll()"><label>Submit</label></a>
</td>
<td width="19%"></td>
<td width="1%">
<div class="verticalLine">
<pre> </pre>
</div>
</td>
<td width="20%"></td>
<td width="10%">
<!--<input align="center" type="reset" id="reset" name="resetname" value="Reset">-->
<a align="center" id="cancel" name="cancelname" value="Cancel" onclick="resetFields()">Reset<a>
</td>
<td width="10%"></td>
</tr>
</table>
</footer>
</div>

</form>
</body>
</html>
