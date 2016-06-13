var alertFunction = function(){
    alert("sa vedem daca merge");
};

var validateNumberInput = function(elementID){

    var numberPattern = /^[1-9]$/;
    var expression = RegExp(numberPattern);
    var parent = "#parent\\:";
    var item = parent + elementID;
    var value = $(item).val();
    var message = value + " este invalid. Introduceti o cifra, dar nu 0.";


    if(!expression.test(value)) {
        PF('growlWV').renderMessage({"summary":"Eroare!",
            "detail": message,
            "severity":"warn"})
    }
};

