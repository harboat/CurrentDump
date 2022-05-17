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
