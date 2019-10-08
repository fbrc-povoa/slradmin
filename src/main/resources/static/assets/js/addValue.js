var arr = [];

function addValue() {
	
	var field = document.getElementById("myField").value;
	var value = document.getElementById("myValue").value;
	
	var obj = {"field":"", "value":""};
	
	obj.field=field;
	obj.value=value;
	arr.push(obj);
	
	var text = "";
	for (i = 0; i < arr.length; i++) {
		  text += JSON.stringify(arr[i]) + '<br/>';
		}
	document.getElementById("previewObj").innerHTML = text;
}

function clearCollection() {
	arr = [];
	document.getElementById("previewObj").innerHTML = "";
}