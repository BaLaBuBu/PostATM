package com.project.oo.postatm;

/**
 * Created by BaLaBuBu on 2017/6/14.
 */

public class FavoriteManager extends RecordManager {
    private static class SingletonHolder {
        private static final FavoriteManager INSTANCE = new FavoriteManager();
    }

    private FavoriteManager() {

    }

    public static final FavoriteManager getInstance() {
        return FavoriteManager.SingletonHolder.INSTANCE;
    }
}
