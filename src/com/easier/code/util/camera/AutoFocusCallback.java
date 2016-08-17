/*
 * Copyright (C) 2010 ZXing authors zhouxin 2012-11-21 修改
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.easier.code.util.camera;

import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 主要用于相机的的自动对焦操作的处理类
 */
final class AutoFocusCallback implements Camera.AutoFocusCallback {

    /**
     * 主要用于输出日志的tag
     */
    private static final String TAG = "AutoFocusCallback";

    /**
     * 对焦时间
     */
    private static final long AUTOFOCUS_INTERVAL_MS = 1500L;

    /**
     * 自动对焦的handler
     */
    private Handler autoFocusHandler;

    /**
     * 用于自动对焦的message
     */
    private int autoFocusMessage;

    /**
     * 用于设置消息的监听
     *
     * @param autoFocusHandler 自动对焦的handler
     * @param autoFocusMessage 自动对焦的message
     */
    void setHandler(Handler autoFocusHandler, int autoFocusMessage) {
        this.autoFocusHandler = autoFocusHandler;
        this.autoFocusMessage = autoFocusMessage;
    }

    /**
     * 执行自动对焦
     *
     * @param success 是否成功
     * @param camera  相机对象
     */
    public void onAutoFocus(boolean success, Camera camera) {
        if (autoFocusHandler != null) {
            Message message = autoFocusHandler.obtainMessage(autoFocusMessage, success);
            // Simulate continuous autofocus by sending a focus request every
            // AUTOFOCUS_INTERVAL_MS milliseconds.
            //Log.d(TAG, "Got auto-focus callback; requesting another");
            autoFocusHandler.sendMessageDelayed(message, AUTOFOCUS_INTERVAL_MS);
            autoFocusHandler = null;
        } else {
            Log.d(TAG, "Got auto-focus callback, but no handler for it");
        }
    }

}
