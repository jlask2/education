<html>
<head>
<meta charset="utf-8">
<title>Enter A Book</title>
<script src="script.js"></script>
<style>
.2col  {
    float: right;
}
.verticalLine {
    border-left: thin solid black;
}
.centerAlign {
    text-align: center; font-size: 75%
}
</style>

</head>
<body>
<h1>EOP Library System<img src="EOP.png" style="float:right"></img></h1>
<h3>The College at Brockport</h3>
<br/>
<label class="centerAlign"><h4>Logged in as: Gary Owens(800008948). You 
have Administrator credentials</h4></label>
<form name="EOPAddBook" action="./processBook.php" method="post" 
onsubmit="return 
validateAll()">
<div style="background-color:blue">
<hr style="background-color:blue"/>
<h3 align="center" 
style="background-color:blue-grey; color:white">Add a Book</h3>
<hr style="background-color:blue"/>
</div>
<br/>
<label id="test"></label>
<table>
<tr>
<td><label>Barcode:    </label></td>
<td><input type="text" id="barcode" name="barcodename" value=""></input></td>
<td><label>Publisher:  </label></td>
<td><input type="text" id="publisher" name="publishername" 
value=""></input></td>
</tr>

<tr height="10px"></tr>

<tr>
<td><label>Discipline: </label></td>
<td><input type="text" id="discipline" name="disciplinename" 
value=""></input></td>
<td><label>Year of Publication:</label></td>
<td>
<select id="yearofpublication" name="yearofpublicationname">
<option id="" name="2014name" value="2014" selected>2014</option>
<option>2013</option>
<option>2012</option>
<option>2011</option>
<option>2010</option>
<option>2009</option>
<option>2008</option>
<option>2007</option>
<option>2006</option>
<option>2005</option>
<option>2004</option>
<option>2003</option>
<option>2002</option>
<option>2001</option>
<option>2000</option>
</select>
</td>
</tr>

<tr height="10px"></tr>

<tr>
<td><label>Title:      </label></td>
<td><input type="text" id="title" name="titlename" value="" 
size="50%"></input></td>
<td><label>ISBN:               </label></td>
<td><input type="text" id="isbn" name="isbnname" value=""></input></td>
</tr>

<tr height="10px"></tr>

<tr>
<td><label>Author1:    </label></td>
<td><input type="text" id="author1" name="author1name" value=""></input></td>
<td><label>Condition:          </label></td>
<td>
<select id="condition" name="conditionname" value="">
<option id="good" name="goodname" value="Good" selected>Good</option>
<option id="fair" name="fairname" value="Fair">Fair</option>
<option id="bad" name="badname" value="Bad">Bad</option>
</select>
</td>
</tr>

<tr height="10px"></tr>

<tr>
<td><label>Author2:    </label></td>
<td><input type="text" id="author2" name="author2name" value=""></input></td>
<td><label>Suggested Price:   $</label></td>
<td><input type="text" id="suggestedprice" name="suggestedpricename" 
value=""></input></td>
</tr>

<tr height="10px"></tr>

<tr>
<td><label>Author3:    </label></td>
<td><input type="text" id="author3" name="author3name" value=""></input></td>
</tr>

<tr height="10px"></tr>

<tr>
<td><label>Author4:    </label></td>
<td><input type="text" id="author4" name="author4name" value=""></input></td>
</tr>

<tr height="10px"></tr>

<tr>
<td><label>Notes:      </label></td>
<td><textarea rows="5" cols="75" width="50%" name="" value=""></textarea>
</td>
</tr>
</table>

<hr/>

<table>
<tr>
<td width="20%"></td>
<td width="10%">
<input align="center" type="submit" id="submit" name="submitname" 
value="Submit" onclick=validateAll()>
<!--<a align="center" id="submit" name="submitname" value="Submit" 
onclick="validateAll()">Submit</a>-->
</td>
<td width="19%"></td>
<td width="1%">
<div class="verticalLine">
<pre> </pre>
</div>
</td>
<td width="20%"></td>
<td width="10%">
<a align="center" id="cancel" name="cancelname" value="Cancel" 
onclick="resetFields()">Reset<a>
</td>
<td width="10%"></td>
</tr>
</table>
</form>

</body>
</html>

