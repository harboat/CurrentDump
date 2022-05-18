const body = document.getElementsByTagName("body")[0]
const menuMusic = new Audio('../assets/audio/menuambience.mp3')
const gameInMusic = new Audio('../assets/audio/gameambience.mp3')
const shotMastSound = new Audio('../assets/audio/shot_mast.mp3')
const shotWaterSound = new Audio('../assets/audio/shot_water.mp3')

// REQUEST URL BASE
// TODO: MAKE IT ENV VARIABLE
const ip = 'http://localhost:8080'
const apiVersion = 'v1'

let statisticsButton

let gameid
// CELL INDEXING FROM [
let id = 1

// CONNECT WITH BACKEND
// connect()
showRoom()


function showRoom() {
    menuMusic.pause()
    gameInMusic.loop = true;
    gameInMusic.play()
    showRoomContainer()
}

async function showRoomContainer() {
    let container = document.createElement("div")
    container.classList.add('container')

    for (let i = 1; i < 5; i++) {
        let roomContainer = document.createElement("div")
        roomContainer.classList.add("roomContainer")
        roomContainer.setAttribute('id', "roomContainer" + i)

        let roomText = document.createElement("p")
        roomText.setAttribute("id", "roomText" + i)
        roomText.classList.add("roomText")
        roomText.textContent = "room #" + i

        let roomButton = document.createElement("div")
        roomButton.classList.add("roomButton")
        roomButton.classList.add("room")
        roomButton.setAttribute('id', "roomButton" + i)
        roomButton.onclick = function () {
            enterRoom(i)
        }

        const buttonText = document.createElement("p")
        buttonText.setAttribute("id", "buttonText" + i)
        buttonText.classList.add("roomText")
        buttonText.textContent = "enter room"

        const player1Text = document.createElement("p")
        player1Text.setAttribute("id", "player1Text" + i)
        player1Text.style.top = "5"
        player1Text.classList.add("roomText")
        player1Text.textContent = "-"

        const player2Text = document.createElement("p")
        player2Text.setAttribute("id", "player2Text" + i)
        player2Text.classList.add("roomText")
        player2Text.textContent = "-"

        roomButton.appendChild(buttonText)
        roomContainer.appendChild(roomText)
        roomContainer.appendChild(player1Text)
        roomContainer.appendChild(player2Text)
        roomContainer.appendChild(roomButton)
        container.appendChild(roomContainer)
        body.appendChild(container)
        refreshRooms()
    }

    async function refreshRooms() {
        // when connected to backend refresh rooms occupation
    }

    async function enterRoom(index) {
        // when connected to backend refresh rooms screen for all users here
        const player1Text = document.getElementById("player1Text" + index)
        const player2Text = document.getElementById("player2Text" + index)

        const buttonText = document.getElementById("buttonText" + index)
        buttonText.textContent = "leave room"
        const roomButton = document.getElementById("roomButton" + index)
        leaveRoom(roomButton, buttonText, player1Text, index)

        if (player1Text.textContent === "-") {
            player1Text.textContent = "Player 1"
        } else if (player2Text.textContent === "-") {
            player1Text.textContent = "-"
        }

        if (player1Text.textContent !== "-" && player2Text.textContent !== "-") {
            // createGame() player 1
            // joinGame() player 2
        }
    }

    function leaveRoom(roomButton, buttonText, player1Text, index) {
        roomButton.onclick = function () {
            buttonText.textContent = "enter room"
            player1Text.textContent = "-"
            roomButton.onclick = function () {
                enterRoom(index)
            }
        }
    }
}