package de.nicolas.bludbourne;

import com.badlogic.gdx.Game;
import de.nicolas.bludbourne.screens.MainGameScreen;

/** die Haupt GameKlasse des Spiels */
public class BludBourne extends Game {

    public static final MainGameScreen mainGameScreen = new MainGameScreen();

    @Override
    public void create() {
        setScreen(mainGameScreen);
    }

    @Override
    public void dispose() {
        mainGameScreen.dispose();
    }
}
