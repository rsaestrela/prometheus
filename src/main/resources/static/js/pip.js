function call(preference) {
     var xmlHttp = new XMLHttpRequest();
     xmlHttp.onreadystatechange = function() { }
     xmlHttp.open("POST", 'http://localhost:8080?preference=' + preference, true);
     xmlHttp.send(null);

}