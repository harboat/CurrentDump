function connect() {
    const socket = new SockJS('/websocket')
    let stompClient = Stomp.over(socket)
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame)
        stompClient.subscribe('/user/queue/notification', async function (message) {
            console.log(message)
            let event = JSON.parse(message.body)
            let eventType = event['eventType']
            let object = event['content']
            console.log(eventType)
            console.log(object)
        })
    });
}

connect();

function send() {
    fetch("http://localhost:8080/api/v1/games", {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: 'same-origin', // include, *same-origin, omit
        headers: {
            'Content-Type': 'application/json'
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: 'follow', // manual, *follow, error
        referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
    }).then(r => console.log(r));
}