//TODO: REFACTOR
//TODO: GAME END, EXCEPTION, SERVER ERROR
//TODO: ERROR HANDLING

const body = document.getElementsByTagName("body")[0]

const ip = 'http://localhost'
const port = '8080'
const apiVersion = 'v1'

let enemyAnimation
let playerAnimation
let enemyLeft
let playerLeft
let height
let width
let yourTurn
let enemyTurn

let playerTurn
let gameId
let playerId
let enemyId
let ships

connect();

function forfeit() {
    const requestURL = "http://localhost:8080/api/v1/games/" + gameId + "/forfeit"
    const request = new Request(requestURL, {
        method: 'POST',
        mode: 'cors'
    })
    const response = fetch(request)
}

function nuke() {
    //TODO to be implmemented
}

function connect() {
    const socket = new SockJS('/websocket')
    let stompClient = Stomp.over(socket)
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame)
        stompClient.subscribe('/user/queue/notification', async function (message) {
            let event = JSON.parse(message.body)
            let eventType = event['eventType']
            let object = event['content']
            switch (eventType) {
                case "EXCEPTION": {
                    break
                }
                case "SERVER_ERROR": {
                    break
                }
                case "GAME_CREATED": {
                    gameId = object['gameId']
                    createGameIdElement()
                    requestBoard()
                    startGameButton()
                    break
                }
                case "GAME_JOINED": {
                    playerId = object['playerId']
                    enemyId = object['enemyId']
                    createGameIdElement()
                    requestPlacement()
                    break
                }
                case "BOARD_CREATED": {
                    width = object['width']
                    height = object['height']
                    break
                }
                case "FLEET_CREATED": {
                    ships = object['ships']
                    initializeBoard('player', ships)
                    initializeBoard('enemy', ships)
                    break
                }
                case "GAME_STARTED": {
                    playerTurn = object['playerTurn']
                    setUpBoardsBasedOnPlayerTurn()
                    createForfeitButton()
                    createNukeButton()
                    break
                }
                case "GAME_END": {
                    alert(object['winningPlayer'])
                    break
                }
                case "HIT": {
                    const shootingPlayerId = object['playerId']
                    const cells = object['cells']
                    await markCells(shootingPlayerId, cells)
                    break
                }
            }
        })
    });
}

function createForfeitButton() {
    let button = document.createElement('button')
    button.setAttribute('id', 'forfeitButton')
    button.setAttribute('onclick', 'forfeit()')
    button.innerText = "Forfeit"
    button.classList.add('forfeitButton')
    body.appendChild(button)
}

function createNukeButton() {
    let button = document.createElement('button')
    button.setAttribute('id', 'nukeButton')
    button.setAttribute('onclick', 'nuke()')
    button.innerText = "Nuke"
    button.classList.add('nukeButton')
    body.appendChild(button)
}

async function markCells(shootingPlayerId, cells) {
    if (shootingPlayerId === playerId) {
        await markCellsHelper(cells, 'enemy')
    } else {
        await markCellsHelper(cells, 'player')
    }
    if (cells.length === 1) {
        cells.forEach(cell => {
            if (!cell['wasShip']) {
                swapBoards()
            }
        })
    }
}

async function markCellsHelper(cells, type) {
    cells.forEach(cell => {
        const cellId = cell['cellId']
        const wasShip = cell['wasShip']
        const playerCell = document.getElementById(cellId.toString() + ":" + type)
        if (wasShip) {
            playerCell.classList.add('shipHit')
            activateCells()
        } else {
            playerCell.classList.add('cellHit')
        }
    })
}

function startGame() {
    const requestURL = "http://localhost:8080/api/v1/games/" + gameId + "/start"
    const request = new Request(requestURL, {
        method: 'POST',
        mode: 'cors'
    })
    const response = fetch(request).then(response => {
        if (response.status === 200) {
            document.getElementById("boardContainer")
                .removeChild(document.getElementById('startGameButton'))
        }
    })
}

function setUpBoardsBasedOnPlayerTurn() {
    if (playerTurn === playerId) {
        enemyAnimation = 'fadein'
        playerAnimation = 'fadeout'
        enemyLeft = '30vw'
        playerLeft = '-50vw'
        yourTurn = "YOUR TURN"
        enemyTurn = "OPPONENT's TURN"
    } else {
        enemyAnimation = 'fadeout'
        playerAnimation = 'fadein'
        enemyLeft = '-50vw'
        playerLeft = '30vw'
        yourTurn = "OPPONENT's TURN"
        enemyTurn = "YOUR TURN"
    }
    document.getElementById('enemy').style.left = enemyLeft
    document.getElementById('enemy').classList.add(enemyAnimation)
    document.getElementById('player').style.left = playerLeft
    document.getElementById('player').classList.add(playerAnimation)
}

//TODO: ERROR HANDLING
function requestPlacement() {
    const requestURL = "http://localhost:8080/api/v1/games/" + gameId + "/placements"
    const request = new Request(requestURL, {
        method: 'POST',
        mode: 'cors'
    })
    const response = fetch(request)
}

function startGameButton() {
    let button = document.createElement('button')
    button.setAttribute('onclick', "startGame()")
    button.setAttribute('id', 'startGameButton')
    button.classList.add('menuButton')
    button.classList.add('gameCreate')
    button.classList.add('text')
    button.innerText = 'Start Game'
    document.getElementById('boardContainer').appendChild(button)
}

//TODO: ERROR HANDLING | USER CAN SET BOARD SIZE
function requestBoard() {
    const requestURL = "http://localhost:8080/api/v1/games/" + gameId + "/boards"
    const board = {
        "width": 10,
        "height": 10
    }
    const header = new Headers()
    header.append('Content-Type', 'application/json')
    const request = new Request(requestURL, {
        method: 'POST',
        body: JSON.stringify(board),
        headers: header,
        mode: 'cors'
    })
    const response = fetch(request)
}

function initializeBoard(type, fleet) {
    let index = 1
    let container = document.createElement("div")
    container.classList.add('container')
    container.classList.add(type)
    container.setAttribute('id', type)

    let horizontalCoordinates = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
        'Q', 'R', 'S', 'T']

    for (let i = 0; i < width * height; i++) {
        let cell = document.createElement("div")
        cell.classList.add('cell')
        cell.setAttribute('id', index.toString() + ':' + type)

        if (type === 'enemy') {
            cell.setAttribute('onclick', 'shoot(this)')
            cell.style.cursor = 'crosshair'

            let coordinate = horizontalCoordinates[i % 10] + +(((i / width * 10 - i % 10) / 10) + 1)
            let displayCellCoordinate = document.createElement('div')
            displayCellCoordinate.classList.add('cellCoordinate')
            displayCellCoordinate.textContent = coordinate
            cell.appendChild(displayCellCoordinate)
        }
        container.appendChild(cell)
        index++
    }
    body.appendChild(container)

    createShips(fleet, type).then(() => {
        if (type === 'enemy') {
            document.getElementById('enemy').style.left = enemyLeft
            document.getElementById('enemy').classList.add(enemyAnimation)
        } else {
            document.getElementById('player').style.left = playerLeft
            document.getElementById('player').classList.add(playerAnimation)
        }
    })
}

async function createShips(fleet, type) {
    if (type === 'player') {
        fleet.forEach(ship => ship["masts"].forEach(cellID => {
            document.getElementById(cellID + ":" + type).classList.add("ship")
        }))
    }
}

//TODO: ERROR HANDLING

async function createGame() {
    await resetBoardContainer()
    const requestURL = "http://localhost:8080/api/v1/games"
    const request = new Request(requestURL, {
        method: 'POST',
        mode: 'cors',
    })
    const response = fetch(request)
    console.log(response);
}

async function joinGame(gameId) {
    await resetBoardContainer()
    const requestURL = "http://localhost:8080/api/v1/games/" + gameId + "/join"
    const request = new Request(requestURL, {
        method: 'POST',
        mode: 'cors',
    })
    const response = fetch(request)
    console.log(response);
}

async function resetBoardContainer() {
    const boardContainer = document.getElementById("boardContainer")
    boardContainer.removeChild(document.getElementById("gameCreate"))
    boardContainer.removeChild(document.getElementById("gameJoin"))
    boardContainer.style.backgroundColor = "#3b4252"
    boardContainer.style.borderColor = "#3b4252"
}

function swapBoards() {

    // if (document.getElementById('turn').innerText === enemyTurn) {
    //     document.getElementById('turn').innerText = yourTurn
    // } else if (document.getElementById('turn').innerText === yourTurn) {
    //     document.getElementById('turn').innerText = enemyTurn
    // }

    document.getElementById('enemy').style.left = playerLeft
    document.getElementById('player').style.left = enemyLeft

    let temp1 = playerLeft
    playerLeft = enemyLeft
    enemyLeft = temp1

    activateCells()
    boardAnimation()
}


function getGameIDFromPlayer() {
    const gameIdFromPlayer = window.prompt("Enter game id: ")
    if (gameIdFromPlayer.length > 0) {
        joinGame(gameIdFromPlayer).then(() => {
        })
    }
    gameId = gameIdFromPlayer
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

const delay = millis => new Promise((resolve, reject) => {
    setTimeout(_ => resolve(), millis)
});

function createGameIdElement() {
    if (document.getElementById('gameId') !== null) return
    let displayId = document.createElement('p')
    displayId.setAttribute('id', 'gameId')
    displayId.classList.add('gameId')
    displayId.textContent = 'game-id: ' + gameId
    body.appendChild(displayId)
}

function shoot(cell) {
    deactivateCells()
    const header = new Headers()
    header.append('Content-Type', 'application/json')
    const cellId = cell.id.toString().split(":")[0]
    const shoot = {
        "cellId": cellId
    }
    const requestURL = "http://localhost:8080/api/v1/games/" + gameId + "/shoot"
    const request = new Request(requestURL, {
        method: 'POST',
        body: JSON.stringify(shoot),
        headers: header,
        mode: 'cors'
    })
    const response = fetch(request)
}