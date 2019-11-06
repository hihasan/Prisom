package com.hihasan.prisom.utils;

public interface TagAddCallback {
    /*
     * Called when add a tag
     * true: tag would be added
     * false: tag would not be added
     */
    boolean onTagAdd(String tagValue);
}
