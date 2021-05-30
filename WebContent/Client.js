


window.addEventListener("load", () => {

    const pseudo = getCookie("login");
    const ChatTitle = getCookie("ChatTitle");

    const ws = new WebSocket("ws://localhost:8080/UT'Chat/chatserver/" + pseudo + "/" + ChatTitle);

    const txtHistory = document.getElementById("history");
    const txtMessage = document.getElementById("txtMessage");
    txtMessage.focus();   

    ws.addEventListener("open", () => {
        console.log("Connection established");
    });

    const updateListUser = (listMember) => {

        const listUser = document.getElementById('listUser');
        while (listUser.firstChild) {
            listUser.removeChild(listUser.lastChild);
        }

        listMember.forEach(element => {
            let noeud = document.createElement('li');
            noeud.appendChild(document.createTextNode(element));
            listUser.appendChild(noeud);
        });
    };

    ws.addEventListener("message", (evt) => {

        let message = evt.data;
        if (message.split(":")[0] == "INF") {
            let content = message.slice(message.search(":") + 1);
            content = content.substring(1, content.length - 1);
            updateListUser(content.split(","));
        }
        else if (message.split(":")[0] == "MSG") {
            let content = message.slice(message.search(":") + 1);
            console.log("Receive new message: " + content);
            txtHistory.value += content + "\n";
        }
    });

    ws.addEventListener("close", () => {
        console.log("Connection closed");
    });


    const btnSend = document.getElementById("btnSend");
    btnSend.addEventListener("click", () => {
        ws.send(txtMessage.value);
        txtMessage.value = "";
        txtMessage.focus();
    });

    const btnClose = document.getElementById("btnClose");
    btnClose.addEventListener("click", () => {
        ws.close();
        window.location.replace("http://localhost:8080/UT'Chat/RedirectToHub");
    });
    
	txtMessage.addEventListener("keyup", (event) => {
    	if (event.key === "Enter")
    		btnSend.click();
    });

});


const getCookie = (name) => {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}
