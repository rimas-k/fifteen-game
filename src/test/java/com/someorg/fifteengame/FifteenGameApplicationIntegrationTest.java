package com.someorg.fifteengame;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.someorg.fifteengame.common.MoveResult;
import com.someorg.fifteengame.dto.CreateGameRequest;
import com.someorg.fifteengame.dto.MoveTileRequest;
import com.someorg.fifteengame.dto.MoveTileResponse;
import com.someorg.fifteengame.dto.domain.Game;
import com.someorg.fifteengame.dto.domain.Tile;
import com.someorg.fifteengame.factories.GameFactory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class FifteenGameApplicationIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createGame_validParams_returnsValidGame() throws Exception {
        CreateGameRequest request = createCreateGameRequest(3, "userId1", "gameId1");

        int validTilesCount = request.getBoardSize() * request.getBoardSize();

        Game game = performCreateGameRequest(request);

        assertThat(game.getBoardSize(), is(request.getBoardSize()));
        assertThat(game.getTiles().size(), is(validTilesCount));
    }

    @Test
    public void createGame_fetchGame_returnsSameGame() throws Exception {
        int boardSize = 3;
        String userId = "userId1";
        String gameId = "gameId2";

        CreateGameRequest request = createCreateGameRequest(boardSize, userId, gameId);
        Game createdGame = performCreateGameRequest(request);

        Game fetchedGame = performFetchGameRequest(userId, gameId);

        assertThat(createdGame, is(fetchedGame));
    }

    @Test
    public void createGame_oneValidMove_returnsMoveOK() throws Exception {
        CreateGameRequest request = createCreateGameRequest(2, "userId1", "gameId3");
        Game game = performCreateGameRequest(request);

        String validTileLabelForMove = findValidTileLabelForMove(game);

        MoveTileRequest moveTileRequest = new MoveTileRequest();
        moveTileRequest.setUserId("userId1");
        moveTileRequest.setGameId("gameId3");
        moveTileRequest.setTileLabel(validTileLabelForMove);

        MoveResult moveResult = performMoveTileRequest(moveTileRequest).getResult();

        assertThat(moveResult, anyOf(is(MoveResult.OK), is(MoveResult.GAME_COMPLETE)));
    }

    @Test
    public void createGame_oneInValidMove_returnsMoveIllegal() throws Exception {
        CreateGameRequest request = createCreateGameRequest(2, "userId1", "gameId4");
        Game game = performCreateGameRequest(request);

        String validTileLabelForMove = findInValidTileLabelForMove(game);

        MoveTileRequest moveTileRequest = new MoveTileRequest();
        moveTileRequest.setUserId("userId1");
        moveTileRequest.setGameId("gameId4");
        moveTileRequest.setTileLabel(validTileLabelForMove);

        MoveResult moveResult = performMoveTileRequest(moveTileRequest).getResult();

        assertThat(moveResult, is(MoveResult.ILLEGAL_MOVE));
    }

    private String findValidTileLabelForMove(Game game) {
        List<Tile> tiles = game.getTiles();

        Tile blankTile = findTileByLabel(tiles, GameFactory.BLANK_TILE_LABEL);

        int maxPosCoordinate = game.getBoardSize() - 1;

        //for simplicity, we're finding a neighbour horizontally, also handling the case if it is in a corner
        int newY = blankTile.getPositionY() == maxPosCoordinate ? blankTile.getPositionY() - 1 : blankTile.getPositionY() + 1;
        int newX = blankTile.getPositionX();

        Tile targetTile = getTileByCoordinates(tiles, newY, newX);

        return targetTile.getLabel();
    }

    private Tile getTileByCoordinates(List<Tile> tiles, int newY, int newX) {
        return tiles.stream()
                .filter(tile -> tile.getPositionX() == newX && tile.getPositionY() == newY)
                .findAny()
                .orElse(null);
    }

    private String findInValidTileLabelForMove(Game game) {
        List<Tile> tiles = game.getTiles();

        Tile blankTile = findTileByLabel(tiles, GameFactory.BLANK_TILE_LABEL);

        int maxPosCoordinate = game.getBoardSize() - 1;

        //for simplicity, we're finding a neighbour diagonally, also handling the case if it is in a corner
        int newY = blankTile.getPositionY() == maxPosCoordinate ? blankTile.getPositionY() - 1 : blankTile.getPositionY() + 1;
        int newX = blankTile.getPositionX() == maxPosCoordinate ? blankTile.getPositionX() - 1 : blankTile.getPositionX() + 1;

        Tile targetTile = getTileByCoordinates(tiles, newY, newX);

        return targetTile.getLabel();
    }

    private Tile findTileByLabel(List<Tile> tiles, String label) {
        return tiles.stream()
                .filter(tile -> label.equals(tile.getLabel()))
                .findAny()
                .orElse(null);
    }


    private MoveTileResponse performMoveTileRequest(MoveTileRequest moveTileRequest) throws Exception {
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.post("/game/move")
                .content(objectMapper.writeValueAsString(moveTileRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult result = actions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        MoveTileResponse response = objectMapper.readValue(contentAsString, MoveTileResponse.class);

        return response;
    }

    private CreateGameRequest createCreateGameRequest(int boardSize, String userId, String gameId) {
        CreateGameRequest request = new CreateGameRequest();

        request.setBoardSize(boardSize);
        request.setUserId(userId);
        request.setGameId(gameId);

        return request;
    }

    private Game performCreateGameRequest(CreateGameRequest createGameRequest) throws Exception {
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.post("/game")
                .content(objectMapper.writeValueAsString(createGameRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult result = actions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Game response = objectMapper.readValue(contentAsString, Game.class);

        return response;
    }

    private Game performFetchGameRequest(String userId, String gameId) throws Exception {
        ResultActions actions = mvc
                .perform(MockMvcRequestBuilders
                        .get("/game/{userId}/{gameId}", userId, gameId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult result = actions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Game response = objectMapper.readValue(contentAsString, Game.class);

        return response;
    }


}
