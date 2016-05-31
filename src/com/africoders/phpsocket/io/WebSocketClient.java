package com.africoders.phpsocket.io;

import android.util.Log;

import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Dr. Anthony Ogundipe on 1/10/2016.
 */
public class WebSocketClient {

    private String server_address; private boolean connected=false;private boolean shutdown=false;


    private List<WebSocketEvent> eventList;


    private String user=null;
    private WebSocket webSocket;

    public static WebSocketClient Instance;

    public WebSocketClient() {
        Instance=this;
        eventList=new ArrayList<WebSocketEvent>();

        final WebSocketClient ws=this;

        //add close fx
        this.on("close", new SocketEvent() {
            @Override
            public void run() {
            }
            public void run(String msg, String msg2) {
                ws.trigger("close",msg,"");
            }
        });
    }

    private String cmdwrap(String cmd,String data,Boolean broadcast) {
        JSONObject json=new JSONObject();
        String response="";
        try {
            json.put("cmd",cmd);
            json.put("data",data);
            json.put("broadcast", broadcast);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        response=json.toString();
        return response;
    };


    public void emit(String cmd,String data) {
        if (!connected) {return;}
        String message=this.cmdwrap(cmd,data,false);
        send(message);
    };

    public void broadcast(String cmd,String data) {
        if (!connected) {return;}
        String message=this.cmdwrap(cmd,data,true);
        send(message);
    };

    public void send(String message) {
        if (!connected) {return;}
        webSocket.send(message);
    }

    //add new event
    public void on(String ename,SocketEvent ehandle) {
        eventList.add(new WebSocketEvent(ename, ehandle));
    }

    private void trigger(String cmd,String params,String sender) {

        //display items one at a time
        Iterator<WebSocketEvent> it = eventList.iterator();
        while(it.hasNext())
        {
            WebSocketEvent item = it.next();
            if(item.ename.equals(cmd)) {
                item.ehandle.run(params,sender);
            }
        }

    }

    public void connect(String server_address) {

        this.server_address=server_address;
        this.init();
    }

    public void disconnect() {
        shutdown=true;
        connected=false;
        try {
            webSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        if(shutdown) {return;}
        connected=false;
        AsyncHttpClient.getDefaultInstance().websocket(server_address, "my-protocol", new AsyncHttpClient.WebSocketConnectCallback() {
            @Override
            public void onCompleted(Exception ex, WebSocket _webSocket) {
                webSocket=_webSocket;
                if (ex != null) {
                    ex.printStackTrace();
                    sleep(2000);
                    init();
                    return;
                }
                connected=true;
                doLog("Connected to chat network");

                webSocket.setClosedCallback(new CompletedCallback() {
                    @Override
                    public void onCompleted(Exception ex) {
                        connected = false;
                        doLog("Disconnected from chat network");
                        init(); //attempt connection again since server was closed
                    }
                });


                //webSocket.send("a string");

                webSocket.setStringCallback(new WebSocket.StringCallback() {
                    public void onStringAvailable(String s) {
                        JSONObject obj;
                        try {
                            obj = new JSONObject(s);

                            switch(obj.getString("cmd")) {
                                case "connect":
                                    user=obj.getString("data");
                                    break;
                                case "disconnect":
                                    user=null;
                                    break;
                                case "close":
                                    user=null;
                                    break;
                            }
                            trigger(obj.getString("cmd"), obj.getString("data"), obj.getString("sender"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //doLog(s);
                    }
                });

            }
        });

    }


    public void  sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void doLog(String log) {
        Log.i("webchat", log);
    }


}
