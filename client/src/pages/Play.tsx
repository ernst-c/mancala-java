import { useState, useEffect } from "react";

export const Play = () => {
    const [currentPlayer, setCurrentPlayer] = useState(0);
    const [gameState, setGameState] = useState({
        players: [
            {
                name: "0",
                pits: [{ index: 0, nrOfStones: 4 }, { index: 1, nrOfStones: 4 }, { index: 2, nrOfStones: 4 }, { index: 3, nrOfStones: 4 }, { index: 4, nrOfStones: 4 }, { index: 5, nrOfStones: 4 }, { index: 6, nrOfStones: 0 }],
                hasTurn: false
            },
            {
                name: "1",
                pits: [{ index: 7, nrOfStones: 4 }, { index: 8, nrOfStones: 4 }, { index: 9, nrOfStones: 4 }, { index: 10, nrOfStones: 4 }, { index: 11, nrOfStones: 4 }, { index: 12, nrOfStones: 4 }, { index: 13, nrOfStones: 0 }],
                hasTurn: true
            }
        ],
        gameStatus: {
                endOfGame: false,
                winner: null,
            },
    });

    const handlePitClick = async (pitIndex) => {
        console.log("Clicked pit index: ", pitIndex);

        try {
            const response = await fetch('/mancala/api/play', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    indexToPlay: pitIndex,
                }),
            });

            if (response.ok) {
                const updatedGameState = await response.json();
                setGameState(updatedGameState);
                setCurrentPlayer(updatedGameState.players[0].hasTurn ? 0 : 1);
            }
        } catch (error) {
            console.error("Failed to send play request:", error);
        }
    };

    const handleRestartClick = async () => {
        console.log("Restarting match...");
        try {
            const response = await fetch('/mancala/api/start', {
                method: 'POST',
                headers: {
                    Accept: "application/json",
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    player1: gameState.players[0].name,
                    player2: gameState.players[1].name,
                }),
            });

            if (response.ok) {
                const updatedGameState = await response.json();
                setGameState(updatedGameState);
                setCurrentPlayer(updatedGameState.players[0].hasTurn ? 0 : 1);
            }
        } catch (error) {
            console.error("Failed to restart game:", error);
        }
    };

    const handleSaveClick = async () => {
        console.log("Saving game...");
        try {
            const response = await fetch('/mancala/api/save', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ gameState }),
            });

            if (response.ok) {
                console.log("Game saved successfully!");
            } else {
                console.error("Failed to save game.");
            }
        } catch (error) {
            console.error("Error saving game:", error);
        }
    };

    const handleLoadClick = async () => {
        console.log("Loading game...");
        try {
            const response = await fetch('/mancala/api/load', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (response.ok) {
                const loadedGameState = await response.json();
                setGameState(loadedGameState);
                setCurrentPlayer(loadedGameState.players[0].hasTurn ? 0 : 1);
                console.log("Game loaded successfully!");
            } else {
                console.error("Failed to load game.");
            }
        } catch (error) {
            console.error("Error loading game:", error);
        }
    };

    return (
        <div className="play-container flex flex-col items-center p-6 relative">
            <h1 className="text-2xl font-bold mb-4">Mancala Game</h1>

            {gameState.gameStatus.endOfGame ? (
                <div className="end-game-message text-xl font-bold text-red-500 mb-4 cursor-pointer" onClick={handleRestartClick}>
                    Game Over! Winner: {gameState.gameStatus.winner || "Draw"}. Click here to restart!
                </div>
            ) : (
                <div className="current-player mb-4">
                    {gameState.players[currentPlayer].name}'s Turn
                </div>
            )}

            <div className="mancala-board grid grid-cols-8 gap-4 w-full max-w-4xl p-4 bg-green-100 rounded-lg shadow-lg">
                <div className="mancala-store flex items-center justify-center bg-blue-300 rounded-lg h-full col-span-1">
                    {gameState.players[1].pits[6].nrOfStones}
                </div>

                {gameState.players[1].pits.slice(0, 6).reverse().map((pit) => (
                    <div key={`player2-pit-${pit.index}`} className="mancala-pit flex items-center justify-center bg-yellow-300 rounded-full h-16 w-16 cursor-pointer" onClick={() => handlePitClick(pit.index)}>
                        {pit.nrOfStones}
                    </div>
                ))}

                <div className="col-span-1"></div>

                {gameState.players[0].pits.slice(0, 6).map((pit) => (
                    <div key={`player1-pit-${pit.index}`} className="mancala-pit flex items-center justify-center bg-yellow-300 rounded-full h-16 w-16 cursor-pointer" onClick={() => handlePitClick(pit.index)}>
                        {pit.nrOfStones}
                    </div>
                ))}

                <div className="mancala-store flex items-center justify-center bg-blue-300 rounded-lg h-full col-span-1">
                    {gameState.players[0].pits[6].nrOfStones}
                </div>
            </div>

            {/* Save and Load Buttons */}
            <div className="absolute bottom-4 right-4 flex gap-4">
                <button className="bg-blue-500 text-white font-bold py-2 px-4 rounded-lg shadow-lg hover:bg-blue-600" onClick={handleSaveClick}>Save Game</button>
                <button className="bg-green-500 text-white font-bold py-2 px-4 rounded-lg shadow-lg hover:bg-green-600" onClick={handleLoadClick}>Load Game</button>
            </div>
        </div>
    );
};
