package com.template.design.IPC.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.template.design.Game;
import com.template.design.IGameManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.Nullable;

public class AIDLService extends Service {
    private CopyOnWriteArrayList<Game> mGameList=new CopyOnWriteArrayList<Game>();

    private Binder mBinder=new IGameManager.Stub() {
        @Override
        public List<Game> getGameList() throws RemoteException {
            return mGameList;
        }

        @Override
        public void addGame(Game game) throws RemoteException {
            mGameList.add(game);
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mGameList.add(new Game("九阴真经ol","最好玩的武侠网游"));
        mGameList.add(new Game("大航海时代ol","最好玩的航海网游"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
