// IGameManager.aidl
package com.template.design;
import com.template.design.Game;

interface IGameManager {
    List<Game> getGameList();
    void addGame(in Game game);

    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
