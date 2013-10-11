package com.xortech.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import com.example.com.xortech.multicast.R;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public static String BROADCAST_ADDR = "224.10.10.1";
	public static int BROADCAST_PORT = 17012;
	private static MulticastSocket mSocket;
	InetAddress broadcastAddr = null;
	
	String testString = "<?xml version='1.0' encoding='UTF-8' standalone='yes'?><event version='2.0' uid='GeoChat.bryan.All Chat Rooms.2' type='b-t-f' time='2013-04-03T22:33:55.407Z' start='2013-04-03T22:33:55.407Z' stale='2013-04-04T22:33:55.407Z' how='h-g-i-g-o'><point lat='0.0' lon='0.0' hae='0.0' ce='99999999' le='99999999' /><detail><__chat chatroom='All Chat Rooms'/><link uid='ANDROID-24:98:7C:1C:1C:31' type='a-f-G-U-C' relation='p-p'/><remarks source='BAO.F.ATAK1.Spoofed_Sender' time='2013-04-03T22:33:55.407Z'>{0}</remarks><__serverdestination destinations='udp:224.10.10.1:17012'/></detail></event>";
	
	EditText et;
	Button bt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		bt = (Button) findViewById(R.id.button1);
		et = (EditText) findViewById(R.id.editText1);
		
		WifiManager wifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
		
		if(wifi != null) {
		    WifiManager.MulticastLock lock = wifi.createMulticastLock("Log_Tag");
		    lock.acquire();
		    Toast.makeText(getApplicationContext(), "Lock Acquired", Toast.LENGTH_LONG).show();
		}
		
		ThreadPolicy tp = ThreadPolicy.LAX;
		StrictMode.setThreadPolicy(tp);
		
	    bt.setOnClickListener(new View.OnClickListener() {
	    	
	            public void onClick(View v) {
	            	
	        		try {
	        			broadcastAddr = InetAddress.getByName(BROADCAST_ADDR);
	        		} catch (UnknownHostException e1) {
	        			// TODO Auto-generated catch block
	        			e1.printStackTrace();
	        		}
	        		
	        	    try {
	        	    	
	        	    	broadcastAddr = InetAddress.getByName(BROADCAST_ADDR);
	        	    	System.out.println(broadcastAddr);
	        	    	
	        	    	mSocket = new MulticastSocket(BROADCAST_PORT);
	        	    	System.out.println(mSocket);
	        	    	
	        	    	mSocket.joinGroup(broadcastAddr);
	        	    	
	        	    	// String message = et.getText().toString();
	        	    	byte[] tMessage = new byte[65535];
	        	    	tMessage = testString.getBytes();
	        	    	System.out.println(tMessage);
	        	    	
	        	    	DatagramPacket dPacket = new DatagramPacket(tMessage, tMessage.length,broadcastAddr,BROADCAST_PORT);
	        	    	System.out.println(dPacket);
	        	    	try {
	        				mSocket.send(dPacket);
	        				Toast.makeText(getApplicationContext(), "Sent", Toast.LENGTH_LONG).show();
	        				
	        				// socket sent
	        			} catch (IOException e) {
	        				// TODO Auto-generated catch block
	        				e.printStackTrace();
	        			}

	        	    } catch (UnknownHostException e1) {
	        			// TODO Auto-generated catch block
	        			e1.printStackTrace();
	        		} catch (IOException e1) {
	        			// TODO Auto-generated catch block
	        			e1.printStackTrace();
	        		}

	        		try {
	        			mSocket.leaveGroup(broadcastAddr);
	        		} catch (IOException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	            }
	    });   
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
