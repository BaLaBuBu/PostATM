package com.project.oo.postatm;

/**
 * Created by BaLaBuBu on 2017/6/15.
 */

public class HistoryManager extends RecordManager {
    private int maxRecordCount = 10;

    public int getMaxRecordCount() {
        return maxRecordCount;
    }

    public void setMaxRecordCount(int maxRecordCount) {
        this.maxRecordCount = maxRecordCount;
    }

    private static class SingletonHolder {
        private static final HistoryManager INSTANCE = new HistoryManager();
    }

    private HistoryManager() {

    }

    public static final HistoryManager getInstance() {
        return HistoryManager.SingletonHolder.INSTANCE;
    }

    @Override
    public void addRecord(PostATM atm) {
        getRecordATMs().add(0, atm);
        while (getRecordATMs().size() > maxRecordCount)
            getRecordATMs().remove(getRecordATMs().size() - 1);
    }
}
