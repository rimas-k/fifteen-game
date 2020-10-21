package com.someorg.fifteengame.domain;

import com.someorg.fifteengame.common.MoveResult;
import com.someorg.fifteengame.testutil.NonRandomTileSetsForTests;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;

public class GameTest {

    @Test
    public void checkInternalStructures_filledCorrectly() {
        List<Tile> nonRandomTiles = NonRandomTileSetsForTests.BOARD_SIZE_2_ONE_STEP_TO_COMPLETION;
        Game game = setupGameSpy(2, nonRandomTiles);

        assertThat(game.getTileLabelToTileMap().size(), is(4));
        assertThat(game.getTileLabelToTileMap().values(), containsInAnyOrder(nonRandomTiles.toArray()));

        assertThat(game.getTilePositionToTileMap().size(), is(4));
        assertThat(game.getTilePositionToTileMap().values(), containsInAnyOrder(nonRandomTiles.toArray()));
    }

    @Test
    public void checkSingleValidMove_performedCorrectlyAndGameComplete() {
        List<Tile> nonRandomTiles = NonRandomTileSetsForTests.BOARD_SIZE_2_ONE_STEP_TO_COMPLETION;
        Game game = setupGameSpy(2, nonRandomTiles);

        MoveResult moveResult = game.moveTileIntoBlankPosition("3");

        assertThat(moveResult, is(MoveResult.GAME_COMPLETE));
        assertTrue(game.isGameInCompleteState());
    }

    @Test
    public void checkSingleValidMove_performedCorrectlyAndGameNotComplete() {
        List<Tile> nonRandomTiles = NonRandomTileSetsForTests.BOARD_SIZE_2_ONE_STEP_TO_COMPLETION;
        Game game = setupGameSpy(2, nonRandomTiles);

        MoveResult moveResult = game.moveTileIntoBlankPosition("1");

        assertThat(moveResult, is(MoveResult.OK));
        assertFalse(game.isGameInCompleteState());
    }

    @Test
    public void checkSingleNonValidMove_detectedAsIllegalAndMoveNotPerformed() {
        List<Tile> nonRandomTiles = NonRandomTileSetsForTests.BOARD_SIZE_2_ONE_STEP_TO_COMPLETION;
        List<Tile> originalTiles = NonRandomTileSetsForTests.getDeepCopyOf(nonRandomTiles);

        Game game = setupGameSpy(2, nonRandomTiles);

        MoveResult moveResult = game.moveTileIntoBlankPosition("2");

        assertThat(moveResult, is(MoveResult.ILLEGAL_MOVE));
        assertFalse(game.isGameInCompleteState());
        assertThat(game.getTiles(), containsInAnyOrder(originalTiles.toArray()));
    }

    private Game setupGameSpy(int boardSize, List<Tile> tilesList) {
        Game game = new Game(boardSize, NonRandomTileSetsForTests.getDeepCopyOf(tilesList));

        return spy(game);
    }
}
