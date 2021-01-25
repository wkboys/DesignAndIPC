package com.template.design.IPC.ContentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GameProvider extends ContentProvider {
    private static final String AUTHORITY="com.template.design.IPC.ContentProvider.GameProvider";
    public static final Uri GAME_CONTENT_URI=Uri.parse("content://"+AUTHORITY+"/game");
    public static final UriMatcher mUriMatcher=new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(AUTHORITY,"game",0);
    }

    private String table;
    private Context mContext;
    private SQLiteDatabase mDb;

    @Override
    public boolean onCreate() {
        table = DbOpenHelper.GAME_TABLE_NAME;
        mContext = getContext();
        initProvoder();
        return false;
    }

    private void initProvoder() {
        mDb = new DbOpenHelper(mContext).getWritableDatabase();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mDb.execSQL("delete from " +DbOpenHelper.GAME_TABLE_NAME);
                mDb.execSQL("insert into game values(1,'九阴真经ol','最好玩的网游武侠') " );
            }
        }).start();

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        String table= DbOpenHelper.GAME_TABLE_NAME;
        Cursor mCursor=mDb.query(table,strings,s,strings1,null,s1,null);
        return mCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        mDb.insert(table,null,contentValues);
        mContext.getContentResolver().notifyChange(uri,null);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
