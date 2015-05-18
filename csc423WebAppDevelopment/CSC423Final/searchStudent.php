<html>
<head>
<title>Search Student Results</title>
<style>
.headers{text-align:center;}
.centerAlign{text-align: center; font-size: 75%}
.studentBorrowerIn{align:center; width:50%; margin-left:25%; margin-right:25%;}
.left{width:15%;}
.right{width:35%;}
.main-content{border:1px black; align:center; width:60%; text-align:left; margin-left:15%; margin-right:25%;}
.notes{vertical-align:top; vertical-align:top;}
.form-buttons{width:100%;}
.submit{align:right; text-align:right; margin-left:100px; margin-right:100px;}
.cancel{align:left; text-align:left; margin-left:100px; margin-right:100px;}
td{width:1%;}
</style>
<!--<script type="text/javascript" src="./script.js"></script>-->
</head>

<form id="" action="./processSearchStudent.php" method="post" onsubmit="validateAll()">

<h1>EOP Library System<img src="EOP.png" style="float:right"></img></h1>
<h3>The College at Brockport</h3>
<br/>
<label class="centerAlign"><h4>Logged in as: Gary Owens(800008948). You have Administrator credentials</h4></label>

<div style="background-color:blue">
<hr style="background-color:blue"/>
<h3 align="center" style="background-color:blue-grey; color:white">SEARCH STUDENT BORROWER</h3>
<hr style="background-color:blue"/>
</div>
<br/>

<h2 class="headers">If exact ID is known, enter it below:</h2>

<table class="studentBorrowerIn">
<tr>
<td></td>
<td>
<label>Banner ID: <input type="text" id="bannerid" name="bannerid" value=""/></label>
</td>
<td></td>
</tr>
</table>

<h2 class="headers">- OTHERWISE -</h2>
<h2 class="headers">Enter data to search for Student Borrower below:</h2>

<table class="main-content">
<tr>
<td class="left">
<label>First Name</label>
</td>
<td class="right">
<label>: <input type="text" id="firstname" name="firstname" value=""/></label>
</td>
    
<td class="left">
<label>Last Name</label>
</td>
<td class="right">
<label>: <input type="text" id="lastname" name="lastname" value=""/></label>
</td>
</tr>

<tr>
<td class="left">
<label>Phone Number</label>
</td>
<td class="right">
<label for="phonenumber[]">: 
    <input type="text" size="3" length="3" maxlength="3" id="phonenumber[]" name="phonenumber[]" value=""/>-
    <input type="text" size="3" length="3" maxlength="3" id="phonenumber[]" name="phonenumber[]" value=""/>-
    <input type="text" size="4" length="4" maxlength="4" id="phonenumber[]" name="phonenumber[]" value=""/>
</label>
</td>
    
<td class="left">
<label>Email</label>
</td>
<td class="right">
<label>: <input type="text" id="email" name="email" value=""/></label>
</td>
</tr>
</table>

<br/>

<table class="form-buttons" name="" value="">
<tr>
<td class="submit">
<input type="submit" name="submit" value="Submit" />
</td>
<td class="cancel">
<input type="reset" name="cancel" value="Cancel"/>
</td>
</tr>
</table>

</form>

</html>

