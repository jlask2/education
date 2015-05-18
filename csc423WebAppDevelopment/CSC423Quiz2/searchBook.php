<html>
<style>
.headers{text-align:center;}
.bookIn{align:center; width:50%; margin-left:25%; margin-right:25%;}
.left{width:15%;}
.right{width:85%;}
.main-content{border:1px black; align:center; width:40%; text-align:left; 
margin-left:30%; margin-right:30%;}
.notes{vertical-align:top; vertical-align:top;}
.form-buttons{width:100%;}
.submit{align:right; text-align:right; margin-left:100px; 
margin-right:100px;}
.cancel{align:left; text-align:left; margin-left:100px; margin-right:100px;}
td{width:1%;}
.main-content1 {border:1px black; align:center; width:30%; text-align:left; 
margin-left:40%; margin-right:30%;}
.main-content11 {border:1px black; align:center; width:30%; text-align:left; 
margin-left:40%; margin-right:30%;}
</style>
<form id="" action="./processSearchBook.php" method="post">
<h1 class="headers"><b>Book Search</b></h1>

<table width="63%" class="bookIn">
<tr>
<td width="23%"></td>
<td width="55%">
<label>Enter a Barcode:  <input type="text" name="barcode" value=""/></label>
</td>
<td width="22%"></td>
</tr>
</table>

<h2 class="headers">-----OR-----</h2>
<h5 class="headers">Specify criteria below or leave the fields empty to 
return all books:</h5>

<table width="426" class="main-content">
<tr>
<td width="57" class="left">
<label>Title:</label>
</td>
<td width="142" class="right">
<label><input type="text" name="title" value=""/></label>
</td>

<td width="57" class="left">
<label>Author:</label>
</td>
<td width="142" class="right">
<label><input type="text" name="author" value=""/></label>
</td>
</tr>

<tr>
<td class="left">
<label>Publisher:</label>
</td>
<td class="right">
<label><input type="text" name="publisher" value=""/></label>
</td>

<td class="left">
<label>ISBN:</label>
</td>
<td class="right">
<label><input type="text" name="isbn" value=""/></label>
</td>
</tr>

<tr>
<td class="left">
<label>Condition:</label>
</td>
<td class="right">
<colspan>
<label><select name="" value="">
<option selected="selected" name="any" value="any">Any</option>
<option name="good" value="good">Good</option>
<option name="fair" value="fair">Fair</option>
<option name="damaged" value="damaged">Damaged</option>
</select></label>
</colspan>
</td>

<td class="left">
<label>Status</label>
</td>
<td class="right">
<colspan>
<label><select name="" value="">
<option selected="selected" name="any" value="any">Any</option>
<option name="active" value="active">Active</option>
<option name="lost" value="lost">Lost</option>
<option name="inactive" value="inactive">Inactive</option>
</select></label>
</colspan>
</td>
</tr>
</table>
<br/>
<table class="form-buttons" name="" value="">
<tr>
<td class="submit">
<input type="submit" name="" value="Submit" />
</td>
<td class="cancel">
<input type="reset" name="cancel" value="Cancel"/>
</td>
</tr>
</table>
</form>

</html>

