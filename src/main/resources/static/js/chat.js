var stompClient = null;

function setConnected(connected) {
	$("#send").prop("disabled", !connected);
	$("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#messages").html("");
    $("#users").html("");
}

function connect() {
    var socket = new SockJS('/chat-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (msg) {
            showMessage(JSON.parse(msg.body).sender, JSON.parse(msg.body).content);
        });
        stompClient.subscribe('/topic/users', function (users) {
            showUsers(JSON.parse(users.body).users);
        });
        stompClient.send("/app/login", {}, JSON.stringify({'name': $("#name").val()}));
    });
   
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
	if (document.getElementById("message").value.length==0){
		return;
	}
	stompClient.send("/app/messages", {}, JSON.stringify({
		'sender' : $("#name").val(),
		'content' : $("#message").val()
	}));
	document.getElementById("message").value = "";
}

function showMessage(sender, message) {
	$("#messages").append("<span>" + sender + ": " + message + "</span><br/>");
	$("#messages").animate({ scrollTop: $('#messages').prop("scrollHeight")}, 1000);
}

function showUsers(users) {
	for(var i = 0; i < users.length; i++) {
	    var obj = users[i];
	    $("#messages").append("<span>" +obj.name + " <img src='" + obj.avatarPath + "' style='width:25px; height: 25px;'/></span><br/>");
	}
	
	
	$("#messages").append("<span>" + sender + ": " + message + "</span><br/>");
	$("#messages").animate({ scrollTop: $('#messages').prop("scrollHeight")}, 1000);
}


$(function () {
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
    
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
});



	