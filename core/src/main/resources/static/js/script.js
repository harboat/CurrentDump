const body = document.getElementsByTagName("body")[0]
const menuMusic = new Audio('../assets/audio/menuambience.mp3')
const gameInMusic = new Audio('../assets/audio/gameambience.mp3')
const shotMastSound = new Audio('../assets/audio/shot_mast.mp3')
const shotWaterSound = new Audio('../assets/audio/shot_water.mp3')

// REQUEST URL BASE
// TODO: MAKE IT ENV VARIABLE
const ip = 'http://localhost:8080'
const apiVersion = 'v1'

// BOARDS PROPERTIES
let enemyAnimation
let playerAnimation
let enemyLeft
let playerLeft
let height
let width
let playerTurn = "YOUR TURN"
let enemyTurn = "OPPONENT's TURN"

let gameid
// CELL INDEXING FROM [
let id = 1

menuMusic.loop = true;
menuMusic.play()

// CONNECT WITH BACKEND
connect()

function connect() {
    const socket = new SockJS('/ships-websocket')
    let stompClient = Stomp.over(socket)
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame)
        stompClient.subscribe('/user/events', async function (message) {
            let event = JSON.parse(message.body)
            let eventType = event['eventType']
            switch (eventType) {
                case "ENEMY_JOIN": {
                    menuMusic.pause()
                    gameInMusic.loop = true;
                    gameInMusic.play()
                    initializeBoard('enemy')
                    let turn = document.createElement('p');
                    turn.classList.add('turn')
                    turn.innerText = playerTurn
                    turn.setAttribute('id', 'turn')
                    body.appendChild(turn)
                    break
                }
                case "ENEMY_MISS": {
                    shotMastSound.load()
                    shotWaterSound.load()
                    shotWaterSound.play()
                    let cell = event['cell']
                    await shoot(cell, 'enemy')
                    activateCells()
                    swapBoards()
                    break
                }
                case "ENEMY_SHOT": {
                    shotMastSound.load()
                    shotWaterSound.load()
                    shotMastSound.play()
                    let cell = event['cell']
                    await shoot(cell, 'enemy')
                    break
                }
                case "ENEMY_WIN": {
                    shotMastSound.load()
                    shotWaterSound.load()
                    shotMastSound.play()
                    let cell = event['cell']
                    await shoot(cell, 'enemy')
                    alert('YOU LOST!')
                    window.location.reload()
                }
            }
        })
    });

}

function initializeBoard(type, fleet) {
    let container = document.createElement("div")
    container.classList.add('container')
    container.classList.add(type)
    container.setAttribute('id', type)

    var horizontalCoordinates = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
        'Q', 'R', 'S', 'T']

    for (let i = 0; i < width * height; i++) {
        let cell = document.createElement("div")
        cell.classList.add('cell')
        cell.setAttribute('id', id.toString() + ':' + type)
        if (type === 'enemy') {
            cell.setAttribute('onclick', 'shoot(this)')
            cell.style.cursor = 'crosshair'

            var coordinate = horizontalCoordinates[i % 10] + +(((i / width * 10 - i % 10) / 10) + 1)
            let displayCellCoordinate = document.createElement('div')
            displayCellCoordinate.classList.add('cellCoordinate')
            displayCellCoordinate.textContent = coordinate
            cell.appendChild(displayCellCoordinate)
        }
        id++
        container.appendChild(cell)
    }
    id = 1
    body.appendChild(container)
    createShips(fleet, type).then(r => {
        if (type === 'enemy') {
            document.getElementById('enemy').style.left = enemyLeft
            document.getElementById('enemy').classList.add(enemyAnimation)
        } else {
            document.getElementById('player').style.left = playerLeft
            document.getElementById('player').classList.add(playerAnimation)
        }
    })
}

async function createGame() {
    enemyAnimation = 'fadein'
    playerAnimation = 'fadeout'
    enemyLeft = '30vw'
    playerLeft = '-50vw'
    await resetBoardContainer()

    const game = {
        "board": {
            "width": 10,
            "height": 10
        }
    }

    const header = new Headers()
    header.append('Content-Type', 'application/json')
    header.append('Authorization', 'Basic dGVzdEBlbWFpbC5jb206dGVzdA==')

    const requestURL = ip + "/api/" + apiVersion + "/games"
    const request = new Request(requestURL, {
        method: 'POST',
        body: JSON.stringify(game),
        headers: header,
        mode: 'cors'
    })

    const response = await fetch(request)
    const createdGame = await response.json()
    const gameID = createdGame["id"].toString()
    const boardSize = createdGame["board"]

    width = boardSize["width"]
    height = boardSize["height"]

    const fleet = createdGame['fleet']

    gameid = gameID
    let displayId = document.createElement('p')
    displayId.classList.add('gameId')
    displayId.textContent = 'game-id: ' + gameid
    body.appendChild(displayId)
    console.log(gameID)
    initializeBoard('player', fleet)
}

async function joinGame(gameID) {
    menuMusic.pause()
    gameInMusic.loop = true;
    gameInMusic.play()
    enemyAnimation = 'fadeout'
    playerAnimation = 'fadein'
    enemyLeft = '-50vw'
    playerLeft = '30vw'
    await resetBoardContainer()
    const requestURL = ip + "/api/" + apiVersion + "/games/" + gameID
    const request = new Request(requestURL, {
        method: 'POST',
        mode: 'cors'
    })
    const response = await fetch(request)
    const joinedGame = await response.json()
    const joinedGameID = joinedGame["id"].toString()
    const boardSize = joinedGame["board"]

    gameid = joinedGameID

    width = boardSize["width"]
    height = boardSize["height"]

    const fleet = joinedGame['fleet']

    initializeBoard('player', fleet)
    initializeBoard('enemy')
    let turn = document.createElement('p');
    turn.setAttribute('id', 'turn')
    turn.classList.add('turn')
    turn.innerText = enemyTurn
    body.appendChild(turn)
}

function getGameIDFromPlayer() {
    const gameID = window.prompt("Enter game id: ")
    joinGame(gameID)
}

async function createShips(fleet, type) {
    if (type === 'player') {
        fleet.forEach(ship => ship["masts"].forEach(cellID => {
            document.getElementById(cellID + ":" + type).classList.add("ship")
        }))
    }
}

async function resetBoardContainer() {
    const boardContainer = document.getElementById("boardContainer")
    boardContainer.removeChild(document.getElementById("gameCreate"))
    boardContainer.removeChild(document.getElementById("gameJoin"))
    boardContainer.style.backgroundColor = "#3b4252"
    boardContainer.style.borderColor = "#3b4252"
}

function swapBoards() {

    if (document.getElementById('turn').innerText === enemyTurn) {
        document.getElementById('turn').innerText = playerTurn
    } else if (document.getElementById('turn').innerText === playerTurn) {
        document.getElementById('turn').innerText = enemyTurn
    }

    document.getElementById('enemy').style.left = playerLeft
    document.getElementById('player').style.left = enemyLeft

    let temp1 = playerLeft
    playerLeft = enemyLeft
    enemyLeft = temp1

    boardAnimation()
}

function boardAnimation() {
    document.getElementById('player').classList.add(enemyAnimation)
    document.getElementById('player').classList.remove(playerAnimation)
    document.getElementById('enemy').classList.add(playerAnimation)
    document.getElementById('enemy').classList.remove(enemyAnimation)

    let temp = playerAnimation
    playerAnimation = enemyAnimation
    enemyAnimation = temp
}

function activateCells() {
    let cells = document.getElementsByClassName('cell')
    for (let cellElement of cells) {
        if (cellElement.id.toString().includes('enemy')) {
            cellElement.classList.remove('deactivate')
        }
    }
}

function deactivateCells() {
    let cells = document.getElementsByClassName('cell')
    for (let cellElement of cells) {
        if (cellElement.id.toString().includes('enemy')) {
            cellElement.classList.add('deactivate')
        }
    }
}

async function shoot(x, type) {
    deactivateCells()
    if (type === 'enemy') {
        let playerCell = document.getElementById(x + ":player")
        let cellType
        if (playerCell.classList.contains('ship')) {
            cellType = 'shipHit'
        } else {
            cellType = 'cellHit'
        }
        await shootCell(x, cellType, type)
        return
    }
    let cellId = x.id.toString().split(":")[0]
    const requestURL = ip + "/api/" + apiVersion + "/shots?game-id=" + gameid + "&cell-id=" + cellId
    const request = new Request(requestURL, {
        method: 'POST',
        mode: 'cors'
    })
    const response = await fetch(request)
    console.log(response.status)
    if (response.status !== 200) {
        return
    }
    const shootMessage = await response.json()
    const shotResult = shootMessage['shotResult']
    console.log(shootMessage);
    switch (shotResult) {
        case 'MISS': {
            shotMastSound.load()
            shotWaterSound.load()
            shotWaterSound.play()
            await shootCell(cellId, 'cellHit')
            swapBoards()
            activateCells()
            break
        }
        case 'SHIP_HIT': {
            shotMastSound.load()
            shotWaterSound.load()
            shotMastSound.play()
            await shootCell(cellId, 'shipHit')
            activateCells()
            break
        }
        case 'SHIP_SUNK': {
            shotMastSound.load()
            shotWaterSound.load()
            shotMastSound.play()
            let cellsList = shootMessage['cells']

            await cellsList.slice(0, cellsList.indexOf(0)).forEach(c => {
                shootCell(c, 'cellSunk')
            })

            cellsList.slice(cellsList.indexOf(0) + 1, cellsList.length).forEach(c => {
                shootCell(c, 'cellHit')
            })

            activateCells()
            break
        }
        case 'FLEET_SUNK': {
            shotMastSound.load()
            shotWaterSound.load()
            shotMastSound.play()
            let cellsList = shootMessage['cells']
            cellsList.forEach(c => {
                shootCell(c, 'cellHit')
            })
            alert('YOU WON!')
            window.location.reload()
            break
        }
    }
}

async function shootCell(x, cellType, type) {
    if (type === 'enemy') {
        document.getElementById(x + ":player").classList.add(cellType)
        await delay(100)
        return
    }

    document.getElementById(x + ":enemy").classList.add(cellType)
    await delay(100)
}

const delay = millis => new Promise((resolve, reject) => {
    setTimeout(_ => resolve(), millis)
});
