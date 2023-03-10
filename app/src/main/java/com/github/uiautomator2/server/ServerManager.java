package com.github.uiautomator2.server;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;

import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.Server;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

public class ServerManager {

    private final String TAG = "uia2-agent";

    private Server mServer;

    /**
     * Create server.
     */
    public ServerManager(Context context, InetAddress inetAddress, int port) {

        mServer = AndServer.serverBuilder(context)
                .inetAddress(inetAddress)
                .port(port)
                .timeout(10, TimeUnit.SECONDS)
                .listener(new Server.ServerListener() {
                    @Override
                    public void onStarted() {
                        // TODO The server started successfully.
                        ServerStatus.setServer(mServer);
                        Log.d(TAG, "The server started successfully");
                    }

                    @Override
                    public void onStopped() {
                        // TODO The server has stopped.
                        Log.d(TAG, "The server has stopped");
                    }

                    @Override
                    public void onException(Exception e) {
                        // TODO An exception occurred while the server was starting.
                        Log.d(TAG, "An exception occurred while the server was starting");
                    }
                })
                .build();
    }

    /**
     * Start server.
     */
    public void startServer() throws RemoteException {
        if (mServer.isRunning()) {
            // TODO The server is already up.
        } else {
            mServer.startup();
        }
    }

    /**
     * Stop server.
     */
    public void stopServer() {
        if (mServer.isRunning()) {
            mServer.shutdown();
        } else {
            Log.w("AndServer", "The server has not started yet.");
        }
    }
}
