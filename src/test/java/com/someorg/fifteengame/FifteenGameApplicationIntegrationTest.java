package com.someorg.fifteengame;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.someorg.fifteengame.common.MoveResult;
import com.someorg.fifteengame.dto.CreateGameRequest;
import com.someorg.fifteengame.dto.MoveTileRequest;
import com.someorg.fifteengame.dto.domain.Game;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class FifteenGameApplicationIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

//    @MockBean
//    private GameFactory mockGameFactory;

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
    public void createGame_oneValidMove_returnsGameCompleted() throws Exception {
        setUpMocks();
        CreateGameRequest request = createCreateGameRequest(2, "userId1", "gameId3");
        Game game = performCreateGameRequest(request);

        MoveTileRequest moveTileRequest = new MoveTileRequest();
        moveTileRequest.setUserId("userId1");
        moveTileRequest.setGameId("gameId3");
        moveTileRequest.setTileLabel("3");

        MoveResult moveResult = performMoveTileRequest(moveTileRequest);

//        assertThat(moveResult, is(MoveResult.OK));
    }

    private MoveResult performMoveTileRequest(MoveTileRequest moveTileRequest) throws Exception {
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.post("/game/move")
                .content(objectMapper.writeValueAsString(moveTileRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult result = actions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        MoveResult response = objectMapper.readValue(contentAsString, MoveResult.class);

        return response;
    }

    private void setUpMocks() {
//        when(mockGameFactory.createRandomGame(2))
//                .thenReturn(new com.someorg.fifteengame.model.Game(2, NonRandomTileSetsForTests.BOARD_SIZE_2_ONE_STEP_TO_COMPLETION));

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
