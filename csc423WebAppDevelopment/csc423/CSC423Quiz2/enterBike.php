<?php
  'mysqladmin flush-hosts';
?>
<html>
<style>
 .headers{text-align:center;}
 .main-div-left{align:right; width:25%; margin-left="250px"}
 .main-div-right{align:left; width:25%; margin-right"250px"}

 .main-content{border:1px black; align:center; 
width:100%;}<!--<text-align:left; margin-left:0px auto;         
margin-right:0px auto;}-->
 .notes{vertical-align:top; vertical-align:top;}
 .form-buttons{width:100%;}
 .submit{align:right; text-align:right; margin-left:100px; 
margin-right:100px;}
 .cancel{align:left; text-align:left; margin-left:100px; 
margin-right:100px;}
 <!-- td{width:1%;}body,td,th {
	font-size: 12px;
}
-->
</style>

<form id="enterBike" action="./processEnterBike.php" method="post" >
    <h1 class="headers"><b>RENTAL MANAGEMENT SYSTEM</b></h1>
    <h2 class="headers">ADD NEW BICYCLE</h2>

<div class="main-div">
<table class="main-content">
    <tr>    
        <td class="main-div-left">
            <label class=""><b>Bicycle ID: </b></label>
        </td>
        <td class="main-div-right">
            <input type="text" name="bike_id" value=""/>
        </td>
    </tr>
    <tr>
        <td>
            <label>Bicycle Make: </label>
        </td>
        <td>
            <input type="text" name="make" value=""/>
        </td>
    </tr>
    <tr>
        <td>
            <label>Bicycle Model Number: </label> 
        </td>
        <td>
            <input type="text" name="model_number" value=""/>
        </td>
    </tr>
    <tr>
        <td>
            <label>Bicycle Serial Number: </label>
        </td>
        <td>
            <input type="text" name="serial_number" value=""/>
        </td>
    </tr>
    <tr>
        <td>
            <label>Bicycle Color: </label>
        </td>
        <td>
            <input type="text" name="color" value=""/>
        </td>
    </tr>
    <tr>
        <td>
            <label>Bicycle Description: </label>
        </td>
        <td>
            <input type="text" name="description" value=""/>
        </td>
    </tr>
    <tr>
        <td>
            <label>Bicycle Current Location: </label>
        </td>
        <td>
            <select name="location" value="">
                <option value="------------" selected="selected" 
name="location">----------</option>
            </select>
        </td>
    </tr>
    <tr>
        <td>
            <label>Bicycle Current Condition: </label>
        </td>
        <td width="200">
            <select name="physical_condition" value="">
                <option name="physical_condition" 
value="Good">Good</option>
                <option name="physical_condition" 
value="Damaged">Damaged</option>
            </select>
        </td>
    </tr>
    <tr>
        <td valign="top">
            <label class="notes">Bicycle Notes: </label>
        </td>
        <td>
            <textarea col="80%" rows="5" name="notes" 
value=""></textarea>
        </td>
    </tr>
</table>
</div>    
<br/>
    <table class="form-buttons" name="form-buttons" value="">
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
