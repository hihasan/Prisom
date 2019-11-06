package com.hihasan.prisom.utils;

public interface TagDeletedCallback {
    /**
     * Called when tag be deleted
     *
     * @param deletedTagValue
     */
    void onTagDelete(String deletedTagValue);
}