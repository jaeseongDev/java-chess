let store = {};

async function createChessBoard() {
    try {
        let roomName;
        do {
            roomName = prompt('입장할 방의 이름을 입력해주세요.');
            store.roomName = roomName;
        } while (!roomName)

        const res = await axios({
            method: 'get',
            url: `/grid/${roomName}`,
        });
        const data = res.data;
        if (data.code !== 200) {
            alert(data.message);
            return;
        }
        cleanChessBoard();
        createChessBoardAndPieces(data.data.gridDto, data.data.piecesResponseDto);
    } catch (e) {
        console.log(e);
    }
}

function addEvent() {
    const table = document.getElementById("chess-board");
    table.addEventListener("click", selectPiece);
}

createChessBoard();

function createChessBoardAndPieces(gridDto, piecesResponseDto) {
    store.gridDto = gridDto;
    piecesResponseDto.sort((a, b) => {
        if (a.position[1] > b.position[1]) {
            return -1;
        } else if (a.position[1] < b.position[1]) {
            return 1;
        } else if (a.position[1] == b.position[1]) {
            if (a.position[0] > b.position[0]) {
                return 1;
            } else {
                return -1;
            }
        }
    });
    let pieces = []
    for (let i = 0; i < 8; i++) {
        pieces.push(piecesResponseDto.slice(i * 8, (i + 1) * 8));
    }
    store.pieces = pieces;
    const table = document.getElementById("chess-board");
    for (let i = 0; i < 8; i++) {
        const newTr = document.createElement("tr");
        for (let j = 0; j < 8; j++) {
            const newTd = document.createElement("td");

            const row = String(8 - i); // 열(12345678)
            const asciiNum = 'h'.charCodeAt; // h의 아스키코드
            const column = String.fromCharCode(asciiNum + j);
            newTd.id = column + row;
            let pieceName = pieces[i][j].name;
            if (pieceName !== ".") {
                const piece = document.createElement("img");
                if (pieceName === pieceName.toUpperCase()) {
                    pieceName = "B" + pieceName.toUpperCase();
                } else {
                    pieceName = "W" + pieceName.toLowerCase();
                }
                piece.src = "images/" + pieceName + ".png";
                newTd.appendChild(piece);
            }
            if (i % 2 == j % 2) {
                newTd.className = "block1";
            } else {
                newTd.className = "block2";
            }
            newTr.appendChild(newTd);
        }
        table.appendChild(newTr);
    }
    addEvent();
}

function cleanChessBoard() {
    const chessBoard = document.getElementById("chess-board");
    chessBoard.innerHTML = '';
    store.gridDto = null;
}

function selectPiece(event) {
    if (!store.gridDto.isStarted) {
        alert("게임을 시작하시려면 Start 버튼을 눌러주세요.")
        return;
    }
    if (store.gridDto.isFinished) {
        alert("이미 게임이 끝났습니다. 게임을 다시 시작하시고 싶으면 Restart 버튼을 눌러주세요.")
        return;
    }
    const clickPiece = event.target.closest("td");
    const clickedPiece = getClickedPiece();

    // 클릭이 되있을 경우
    if (clickedPiece) {
        clickedPiece.classList.toggle("clicked");
        // 서로 다른 Piece를 클릭한 경우에만 move를 실행하기
        const sourcePosition = clickedPiece.id;
        const targetPosition = clickPiece.id;
        if (sourcePosition !== targetPosition) {
            move(sourcePosition, targetPosition);
        }

        // 아무것도 클릭이 안 되있는 상태 -> 클릭한 것 clicked로 바꾸기
    } else {
        clickPiece.classList.toggle("clicked");
    }
}

function getClickedPiece() {
    const tds = document.getElementsByTagName("td");
    for (let i = 0; i < tds.length; i++) {
        if (tds[i].classList.contains("clicked")) {
            return tds[i];
        }
    }
    return null;
}

async function move(sourcePosition, targetPosition) {
    try {
        const res = await axios({
            method: 'post',
            url: '/move',
            data: {
                piecesDto: store.pieces.flat(),
                sourcePosition,
                targetPosition,
                gridDto: store.gridDto
            }
        });

        const data = res.data;
        if (data.code === 401) {
            alert(data.message);
            return;
        }
        if (data.code !== 204) {
            alert(data.message);
            return;
        }
        const source = document.getElementById(sourcePosition);
        const target = document.getElementById(targetPosition);
        const piece = source.getElementsByTagName("img")[0];
        if (target.getElementsByTagName("img")[0]) {
            target.getElementsByTagName("img")[0].remove();
        }
        target.appendChild(piece);

        const sourcePiece = findPieceByPosition(store.pieces, sourcePosition);
        const sourcePieceIsBlack = sourcePiece.isBlack;
        const sourcePieceName = sourcePiece.name;
        const targetPiece = findPieceByPosition(store.pieces, targetPosition);

        if (targetPiece.name == "K" || targetPiece.name == "k") {
            await finish();
            if (store.gridDto.isBlackTurn) {
                alert("player2가 이겼습니다.")
            } else {
                alert("player1이 이겼습니다.")
            }
            return;
        }

        sourcePiece.name = ".";
        targetPiece.name = sourcePieceName;
        targetPiece.isBlack = sourcePieceIsBlack;
        store.gridDto.isBlackTurn = !store.gridDto.isBlackTurn;

        changeTurn();
    } catch (e) {
        console.log(e);
    }
}

async function start() {
    try {
        if (store.gridDto && store.gridDto.isStarted) {
            alert("이미 게임이 시작했습니다.")
            return;
        }
        const res = await axios({
            method: 'post',
            url: `/grid/${store.gridDto.gridId}/start`,
        });
        const data = res.data;
        if (data.code === 401) {
            alert(data.message);
            return;
        }
        if (data.code !== 204) {
            alert(data.message);
            return;
        }
        store.gridDto.isStarted = true;
        alert("게임을 시작합니다.");
        setFirstTurn();
    } catch (e) {
        console.log(e);
    }
}

function setFirstTurn() {
    const $players = document.getElementsByClassName("player");
    for (let i = 0; i < $players.length; i++) {
        $players[i].classList.remove("turn");
    }
    const $player1 = document.getElementById("player1");
    $player1.className += " turn"
}

function changeTurn() {
    const $players = document.getElementsByClassName("player");
    for (let i = 0; i < $players.length; i++) {
        $players[i].classList.toggle("turn");
    }
}

async function finish() {
    try {
        const res = await axios({
            method: 'post',
            url: `/grid/${store.gridDto.gridId}/finish`,
        });
        const data = res.data;
        if (data.code === 204) {
            store.gridDto.isFinished = true;
        }
    } catch (e) {
        console.log(e);
    }
}

async function restart() {
    try {
        const res = await axios({
            method: 'get',
            url: `/room/${store.gridDto.roomId}/restart`,
        });
        const data = res.data;
        if (data.code !== 200) {
            alert(data.message);
            return;
        }
        cleanChessBoard();
        createChessBoardAndPieces(data.data.gridDto, data.data.piecesResponseDto);
        start();
    } catch (e) {
        console.log(e);
    }
}

function findPieceByPosition(pieces, position) {
    rowIndex = "87654321".indexOf(position[1]);
    columnIndex = "abcdefgh".indexOf(position[0]);
    return pieces[rowIndex][columnIndex];
}