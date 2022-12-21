package com.duproject.brailleglovechat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public final String TAG = "Error";
    public final int MESSAGE_READ = 1;
    public final int REQUEST_ENABLE_BT = 2;

    static Handler mHandler;

    private BluetoothAdapter mBluetoothAdapter = null;
    public static BluetoothSocket btSocket = null;

    public static ConnectedThread mConnectedThread;

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private ArrayList<String> pairedNames = new ArrayList<>();
    private ArrayList<String> pairedAddresses = new ArrayList<>();
    private ListView availableList;
    private ArrayList<String> availableNames = new ArrayList<>();
    private ArrayList<String> availableAddresses = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null)
            Toast.makeText(this, "Bluetooth not found in this device", Toast.LENGTH_SHORT).show();
        else if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            showPairedDevices();
            searchAvailableDevices(null);
        }
    }

    private void showPairedDevices() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        ListView pairedList = findViewById(R.id.paired_list);
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                pairedNames.add(device.getName());
                pairedAddresses.add(device.getAddress());
            }
        } else {
            TextView noneText = findViewById(R.id.none_text);
            noneText.setVisibility(View.VISIBLE);
            pairedList.setVisibility(View.GONE);
        }
        pairedList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pairedNames));
        pairedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                connect(pairedNames.get(i), pairedAddresses.get(i));
            }
        });
    }

    public void searchAvailableDevices(View view) {
        int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        mBluetoothAdapter.startDiscovery();
        // Register for broadcasts when a device is discovered.
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);

        availableList = findViewById(R.id.list_found);
        availableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                connect(availableNames.get(i), availableAddresses.get(i));
            }
        });
    }

    public class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private byte[] mmBuffer; // mmBuffer store for the stream

        private ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams; using temp objects because
            // member streams are final.
            try {
                tmpIn = socket.getInputStream();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when creating input stream", e);
            }
            try {
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when creating output stream", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            mmBuffer = new byte[1024];
            int numBytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs.
            while (true) {
                try {
                    if (mmInStream.available() > 0) {
                        // Read from the InputStream.
                        numBytes = mmInStream.read(mmBuffer);
                        // Send the obtained bytes to the UI activity.
                        mHandler.obtainMessage(MESSAGE_READ, numBytes, -1, mmBuffer)
                                .sendToTarget();
                    } else SystemClock.sleep(100);
                } catch (IOException e) {
                    Log.d(TAG, "Input stream was disconnected", e);
                    break;
                }
            }
        }

        // Call this from the main activity to send data to the remote device.
        public void write(String message) {
            try {
                byte[] bytes = message.getBytes();
                mmOutStream.write(bytes);
                // Share the sent message with the UI activity.
//                    mHandler.obtainMessage(MESSAGE_WRITE, -1, -1, mmBuffer)
//                            .sendToTarget();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when sending data", e);
            }
        }

        // Call this method from the main activity to shut down the connection.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the connect socket", e);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            showPairedDevices();
            searchAvailableDevices(null);
        }
        if (resultCode == RESULT_CANCELED)
            finish();
    }

    public void connect(String name, String address) {
        Toast.makeText(this, "Connecting with " + name + "...", Toast.LENGTH_SHORT).show();

        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        try {
            btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            finish();
        }
        mBluetoothAdapter.cancelDiscovery();
        try {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();
                Toast.makeText(this, "Failed to connect with " + name, Toast.LENGTH_SHORT).show();
            } catch (IOException e2) {
                finish();
            }
        }
        if (btSocket.isConnected()) {
            mConnectedThread = new ConnectedThread(btSocket);
            mConnectedThread.start();
            startActivity(new Intent(this, ChatActivity.class));
            Toast.makeText(this, "Connected with " + name, Toast.LENGTH_SHORT).show();
        }
    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                availableNames.add(device.getName());
                availableAddresses.add(device.getAddress());
                availableList.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, availableNames));
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (MainActivity.mConnectedThread != null) {
            MainActivity.mConnectedThread.cancel();
            try {
                btSocket.close();
            } catch (IOException e2) {
                finish();
            }
        }
        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(mReceiver);
    }
}