/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
*/
package com.omniisg.cordova.fileopen;

import java.io.File;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;

public class FileOpen extends CordovaPlugin {
    public static final String TAG = "FileOpen";



    /**
     * Constructor.
     */
    public FileOpen() {
    }

    
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        final Context context= cordova.getActivity().getApplicationContext();
        final String path = args.get(0).toString();

        if("openFile".equals(action)){
            this.cordova.getActivity().runOnUiThread(new Runnable(){
                @Override
                public void run() {
                    FileOpen.openFile(path,context);        
                }
            });
        }       
        else{
            return false;
        }
        return true;
    }

    public static void openFile(String path,Context context){
        path=path.replace("file:///","/");
        String mimeType=getMimeType(path);
        Log.i("FileUtils", "open file:"+path);
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW); 
        intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);  
        File file = new File(path);
        intent.setDataAndType(Uri.fromFile(file), mimeType);
        
        context.startActivity(intent); 
    }

    public static String getMimeType(String filename) {
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getMimeTypeFromExtension(map.getFileExtensionFromUrl(filename));
    }

}
