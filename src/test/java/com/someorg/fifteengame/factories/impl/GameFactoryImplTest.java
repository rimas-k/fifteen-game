package com.someorg.fifteengame.factories.impl;

import com.someorg.fifteengame.domain.Game;
import com.someorg.fifteengame.domain.Tile;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.spy;

public class GameFactoryImplTest {

    @Test
    public void createRandomGame_createsGameWithCorrectNumberOfDifferentTiles() {
        GameFactoryImpl gameFactory = createFactorySpy();
        int boardSize = 3;

        Game game = gameFactory.createRandomGame(boardSize);

        assertThat(game.getBoardSize(), is(boardSize));
        assertThat(game.getTiles().size(), is(boardSize * boardSize));
        assertFalse(game.isGameInCompleteState());

        Set<Tile> uniqueElementsSet = new HashSet<>(game.getTiles());

        assertThat(uniqueElementsSet.size(), is(game.getTiles().size()));

    }

    private GameFactoryImpl createFactorySpy() {
        return spy(new GameFactoryImpl());
    }

}
