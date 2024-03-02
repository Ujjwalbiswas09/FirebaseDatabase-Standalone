package idk.tools.firebasetest;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.UUID;

import firebase.ConnectionListener;
import firebase.ConnectionObserver;
import firebase.FirebaseReader;
import firebase.FirebaseWriter;
import firebase.ValueEventListener;

public class MainActivity extends Activity {

    FirebaseWriter firebaseWriter;
    FirebaseReader firebaseReader;
    ConnectionObserver observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        observer = new ConnectionObserver();
        firebaseReader = new FirebaseReader("https://mypro-19feb-default-rtdb.asia-southeast1.firebasedatabase.app/audio",
                new ValueEventListener() {
                    @Override
                    public void onChange(String path, Object obj) throws Exception {
                        Log.e(path, obj.toString());
                        firebaseWriter.quickPost(UUID.randomUUID().toString());
                    }
                });
        firebaseWriter = new FirebaseWriter("https://mypro-19feb-default-rtdb.asia-southeast1.firebasedatabase.app/audio");
        observer.setListener(new ConnectionListener() {
            @Override
            public void onConnected() {
                firebaseReader.connect();
                firebaseWriter.connect();
            }

            @Override
            public void onDisconnect() {
                firebaseWriter.disconnect();
                firebaseReader.disconnect();
            }

            @Override
            public void onUpdate(boolean state) {
                if (state) {
                    if (!firebaseReader.isConnected()) {
                        firebaseReader.connect();
                    }
                    if (!firebaseWriter.isConnected()) {
                        firebaseWriter.connect();
                    }
                }
            }
        });
        observer.startListening();
    }
}