package com.hihasan.prisom.tagy;

import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;

public class ZanyInputConnection extends InputConnectionWrapper {

    public ZanyInputConnection(InputConnection target, boolean mutable) {
        super(target, mutable);
    }

    @Override
    public boolean sendKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
            // Un-comment if you wish to cancel the backspace:
            // return false;
        }
        return super.sendKeyEvent(event);
    }

    @Override
    public boolean deleteSurroundingText(int beforeLength, int afterLength) {
        // magic: in latest Android, deleteSurroundingText(1, 0) will be
        // called for backspace
        if (beforeLength == 1 && afterLength == 0) {
            // backspace
            return sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
                    && sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
        }

        return super.deleteSurroundingText(beforeLength, afterLength);
    }
}