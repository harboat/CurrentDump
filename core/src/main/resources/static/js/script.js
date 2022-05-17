const body = document.getElementsByTagName("body")[0]
const menuMusic = new Audio('../assets/audio/menuambience.mp3')
const gameInMusic = new Audio('../assets/audio/gameambience.mp3')
const shotMastSound = new Audio('../assets/audio/shot_mast.mp3')
const shotWaterSound = new Audio('../assets/audio/shot_water.mp3')

// REQUEST URL BASE
// TODO: MAKE IT ENV VARIABLE
// const ip = 'https://polar-bastion-35217.herokuapp.com'
const ip = 'http://localhost:8080'
const apiVersion = 'v1'

let createButton
let joinButton
let statisticsButton
let closeButton = document.getElementById("closeStatistics")
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
        roomContainer.setAttribute('id', i)

        let roomText = document.createElement("p")
        roomText.setAttribute("id", i)
        roomText.classList.add("roomText")
        roomText.textContent = "room #" + i

        let enterButton = document.createElement("div")
        enterButton.classList.add("roomButton")
        enterButton.classList.add("room")
        enterButton.setAttribute('id', i)

        const buttonText = document.createElement("p")
        buttonText.setAttribute("id", i)
        buttonText.classList.add("roomText")
        buttonText.textContent = "enter room"

        enterButton.appendChild(buttonText)
        roomContainer.appendChild(roomText)
        roomContainer.appendChild(enterButton)
        container.appendChild(roomContainer)
        body.appendChild(container)
    }
}

