<html>
<style>
.headers{text-align:center;}
.bikeIn{align:center; width:50%; margin-left:25%; margin-right:25%;}
.left{width:15%;}
.right{width:85%;}
.main-content{border:1px black; align:center; width:30%; text-align:left; margin-left:40%; margin-right:30%;}
.notes{vertical-align:top; vertical-align:top;}
.form-buttons{width:100%;}
.submit{align:right; text-align:right; margin-left:100px; margin-right:100px;}
.cancel{align:left; text-align:left; margin-left:100px; margin-right:100px;}
td{width:1%;}
</style>
<form id="" action="./processSearchBike.php" method="post">
<h1 class="headers"><b>RENTAL MANAGEMENT SYSTEM</b></h1>
<h2 class="headers">SEARCH BICYCLE</h2>
<h2 class="headers">If exact ID is known, enter it below:</h2>

<table class="bikeIn">
<tr>
<td></td>
<td>
<label>Bicycle ID: <input type="text" name="" value=""/></label>
</td>
<td></td>
</tr>
</table>

<h2 class="headers">- OTHERWISE -</h2>
<h2 class="headers">Enter data to search for Bicycle below:</h2>

<table class="main-content">
<tr>
<td class="left">
<label>Make</label>
</td>
<td class="right">
<label>: <input type="text" name="" value=""/></label>
</td>
</tr>

<tr>
<td class="left">
<label>Color</label>
</td>
<td class="right">
<label>: <input type="text" name="" value=""/></label>
</td>
</tr>

<tr>
<td class="left">
<label>Description</label>
</td>
<td class="right">
<label>: <input type="text" name="" value=""/></label>
</td>
</tr>

<tr>
<td class="left">
<label>Location</label>
</td>
<td class="right">
<label>: <input type="text" name="" value=""/></label>
</td>
</tr>

<tr>
<td class="left">
<label>Condition</label>
</td>
<td class="right">
<colspan>
<label>: <select name="" value="">
<option name="" value=""></option>
<option name="" value="">Good</option>
<option name="" value="">Damaged</option>
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
