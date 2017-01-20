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

function wsURL(path) {
    var protocol = (location.protocol === 'https:') ? 'wss://' : 'ws://';
    var url = protocol + location.host;
    if(location.hostname === 'localhost') {
        url += '/' + location.pathname.split('/')[1]; // add context path
    }
    return url + path;
}


function connect() {
	//var path = wsURL('/chat-websocket');
	var socket = new SockJS('/chat-websocket');
	stompClient = Stomp.over(socket);
    stompClient.connect(document.getElementById("name").value,"", function (frame) {
        setConnected(true);
        stompClient.subscribe('/topic/messages', function (msg) {
            showMessage(JSON.parse(msg.body).sender, JSON.parse(msg.body).content);
        });
        stompClient.subscribe('/user/topic/priv', function (msg) {
            showPrivMessage(JSON.parse(msg.body).sender, JSON.parse(msg.body).content);
        });
        stompClient.subscribe('/topic/users', function (users) {
            showUsers(JSON.parse(users.body).users);
        });
        stompClient.subscribe('/app/login', function (msg) {});
    });
   
}

function disconnect() {
	stompClient.send("/app/disconnect", {}, "" );
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
}

function sendMessage() {
	if (document.getElementById("message").value.length==0){
		return;
	}
	stompClient.send("/topic/messages", {}, JSON.stringify({
		'sender' : $("#name").val(),
		'content' : $("#message").val()
	}));
	document.getElementById("message").value = "";
}

function showMessage(sender, message) {
	$("#messages").append("<span>" + sender + ": " + message + "</span><br/>");
	$("#messages").animate({ scrollTop: $('#messages').prop("scrollHeight")}, 1000);
}

function showPrivMessage(sender, message) {
	$("#messages").append("<span class='grey'>");
	$("#messages").append("<span>" + sender + ": " + message + "</span><br/>");
	$("#messages").animate({ scrollTop: $('#messages').prop("scrollHeight")}, 1000);
	$("#messages").append("</span>");
}

function showUsers(users) {
	for(var i = 0; i < users.length; i++) {
	    var user = users[i];
	    $("#users").append("<span><img src='/useravatar/" + user + "' style='width:25px; height: 25px;'/> &nbsp&nbsp" + user + "</span><br/>");
	};
}

$(function () {
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
    
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
});



	