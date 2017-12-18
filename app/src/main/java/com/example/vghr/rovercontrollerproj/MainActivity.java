package com.example.vghr.rovercontrollerproj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.ConnectCallback;
import com.koushikdutta.async.callback.DataCallback;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    Button roverOneForwardButton ;
    Button roverOneBackButton;
    Button roverTwoForwardButton ;
    Button roverTwoBackButton;
    Button roverOneLeftButton;
    Button roverOneRightButton;
    Button roverTwoLeftButton;
    Button roverTwoRightButton;
    RadioButton radioButtonOneCruise;
    RadioButton radioButtonOneManual;
    RadioButton radioButtonOneAutomatic;
    RadioButton radioButtonTwoCruise;
    RadioButton radioButtonTwoManual;
    RadioButton radioButtonTwoAutomatic;
    RelativeLayout loadingAnimationLayout;
    String responseMsgOne = "";
    String responseMsgTwo = "";
    boolean cruiseModeFlagOne = false;
    boolean cruiseModeFlagTwo = false;
    String currentState = "IDLE";
    String currentCruiseMode = "";


    AsyncSocket socketOne;
    AsyncSocket socketTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        roverOneForwardButton = (Button) findViewById(R.id.roverOneButtonForward);
        roverOneBackButton = (Button) findViewById(R.id.roverOneButtonBack);
        roverTwoForwardButton = (Button) findViewById(R.id.roverTwoButtonForward);
        roverTwoBackButton = (Button) findViewById(R.id.roverTwoButtonBack);
        roverOneLeftButton = (Button) findViewById(R.id.roverOneButtonLeft);
        roverOneRightButton = (Button) findViewById(R.id.roverOneButtonRight);
        roverTwoLeftButton = (Button) findViewById(R.id.roverTwoButtonLeft);
        roverTwoRightButton = (Button) findViewById(R.id.roverTwoButtonRight);
        radioButtonOneCruise = (RadioButton) findViewById(R.id.radioButtonOneCruiseMode);
        radioButtonOneManual = (RadioButton) findViewById(R.id.radioButtonOneManualMode);
        radioButtonOneAutomatic = (RadioButton) findViewById(R.id.radioButtonOneAutonomusMode);
        radioButtonTwoCruise = (RadioButton) findViewById(R.id.radioButtonTwoCruiseMode);
        radioButtonTwoManual = (RadioButton) findViewById(R.id.radioButtonTwoManualMode);
        radioButtonTwoAutomatic = (RadioButton) findViewById(R.id.radioButtonTwoAutonomusMode);
      //  loadingAnimationLayout = (RelativeLayout) findViewById(R.id.loadingPanel);

        roverOneForwardButton.setOnTouchListener(this);
        roverOneBackButton.setOnTouchListener(this);
        roverTwoForwardButton.setOnTouchListener(this);
        roverTwoBackButton.setOnTouchListener(this);
        roverOneLeftButton.setOnTouchListener(this);
        roverOneRightButton.setOnTouchListener(this);
        roverTwoLeftButton.setOnTouchListener(this);
        roverTwoRightButton.setOnTouchListener(this);
        /*Loading Animation Activated*/
      //  loadingAnimationLayout.setVisibility(View.VISIBLE);

        setup();

    }

   /* public void buttonClicked(View view) {
        if (view.getId() == R.id.roverOneButtonLeft){
            Log.d("ButtonClicked", "Rover One Left Button");
        }else if (view.getId() == R.id.roverOneButtonRight){
            Log.d("ButtonClicked", "Rover One Right Button");
        }
        else if (view.getId() == R.id.roverTwoButtonLeft){
            Log.d("ButtonClicked", "Rover Two Left Button");
        }
        else if (view.getId() == R.id.roverTwoButtonRight){
            Log.d("ButtonClicked", "Rover Two Right Button");
        }
    }*/
/*Buttons Event Handler Method*/
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v.getId() == R.id.roverOneButtonForward){
            if (event.getAction()==MotionEvent.ACTION_DOWN) {

                Log.d("ButtonClicked","Rover One Forward");
                sendMessage("send moveForward to capsule Top.rover.engineController:00\n","ONE");
                return true;
            }

            /*if (event.getAction()==MotionEvent.ACTION_MOVE){

                Log.e(TAG,"Move");
                return true;

            }*/
            if (event.getAction()==MotionEvent.ACTION_UP){

                Log.d("ButtonClicked","Rover One Stop Forward");
                sendMessage("send stop to capsule Top.rover.engineController:00\n","ONE");
                return true;
            }

        }else if(v.getId() == R.id.roverOneButtonBack){
            if (event.getAction()==MotionEvent.ACTION_DOWN) {

                Log.d("ButtonClicked","Rover One Back");
                sendMessage("send moveBackward to capsule Top.rover.engineController:00\n","ONE");
                return true;
            }


            if (event.getAction()==MotionEvent.ACTION_UP){

                Log.d("ButtonClicked","Rover One Stop Back");
                sendMessage("send stop to capsule Top.rover.engineController:00\n","ONE");
                return true;
            }

        }else if(v.getId() == R.id.roverOneButtonLeft){
            if (event.getAction()==MotionEvent.ACTION_DOWN) {

                Log.d("ButtonClicked","Rover One Left");
                sendMessage("send turnLeft to capsule Top.rover.engineController:00\n","ONE");
                return true;
            }


            if (event.getAction()==MotionEvent.ACTION_UP){

                Log.d("ButtonClicked","Rover One Stop Left");
                sendMessage("send stop to capsule Top.rover.engineController:00\n","ONE");
                return true;
            }

        }else if(v.getId() == R.id.roverOneButtonRight){
            if (event.getAction()==MotionEvent.ACTION_DOWN) {

                Log.d("ButtonClicked","Rover One Right");
                sendMessage("send turnRight to capsule Top.rover.engineController:00\n","ONE");
                return true;
            }


            if (event.getAction()==MotionEvent.ACTION_UP){

                Log.d("ButtonClicked","Rover One Stop Right");
                sendMessage("send stop to capsule Top.rover.engineController:00\n","ONE");
                return true;
            }

        }else if(v.getId() == R.id.roverTwoButtonForward){
            if (event.getAction()==MotionEvent.ACTION_DOWN) {

                Log.d("ButtonClicked","Rover Two Forward");
                sendMessage("send moveForward to capsule Top.rover.engineController:00\n","TWO");
                return true;
            }


            if (event.getAction()==MotionEvent.ACTION_UP){

                Log.d("ButtonClicked","Rover Two Stop Forward");
                sendMessage("send stop to capsule Top.rover.engineController:00\n","TWO");
                return true;
            }

        }else if(v.getId() == R.id.roverTwoButtonBack){
            if (event.getAction()==MotionEvent.ACTION_DOWN) {

                Log.d("ButtonClicked","Rover Two Back");
                sendMessage("send moveBackward to capsule Top.rover.engineController:00\n","TWO");
                return true;
            }


            if (event.getAction()==MotionEvent.ACTION_UP){

                Log.d("ButtonClicked","Rover Two Stop Back");
                sendMessage("send stop to capsule Top.rover.engineController:00\n","TWO");
                return true;
            }

        }else if(v.getId() == R.id.roverTwoButtonLeft){
            if (event.getAction()==MotionEvent.ACTION_DOWN) {

                Log.d("ButtonClicked","Rover Two Left");
                sendMessage("send turnLeft to capsule Top.rover.engineController:00\n","TWO");
                return true;
            }


            if (event.getAction()==MotionEvent.ACTION_UP){

                Log.d("ButtonClicked","Rover Two Stop Left");
                sendMessage("send stop to capsule Top.rover.engineController:00\n","TWO");
                return true;
            }

        }else if(v.getId() == R.id.roverTwoButtonRight){
            if (event.getAction()==MotionEvent.ACTION_DOWN) {

                Log.d("ButtonClicked","Rover Two Right");
                sendMessage("send turnRight to capsule Top.rover.engineController:00\n","TWO");
                return true;
            }

            if (event.getAction()==MotionEvent.ACTION_UP){

                Log.d("ButtonClicked","Rover Two Stop Right");
                sendMessage("send stop to capsule Top.rover.engineController:00\n","TWO");
                return true;
            }

        }
        return false;
    }
/*Radio Buttons Event Handler Method*/
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButtonOneManualMode:
                if (checked){
                    if(cruiseModeFlagOne == true){// Code to handle cruise mode
                        cruiseModeFlagOne = false;
                        radioButtonTwoCruise.setEnabled(true);
                    }
                    roverOneForwardButton.setEnabled(true);
                    roverOneBackButton.setEnabled(true);
                    roverOneLeftButton.setEnabled(true);
                    roverOneRightButton.setEnabled(true);
                   // Log.e("message:", "Calling sendMessage");
                    sendMessage("send goToManualMode to capsule Top.controlSoftware:00\n","ONE");

                }

                    break;
            case R.id.radioButtonOneAutonomusMode:
                if (checked){
                    if(cruiseModeFlagOne == true){// Code to handle cruise mode
                        cruiseModeFlagOne = false;
                        radioButtonTwoCruise.setEnabled(true);
                    }
                    roverOneForwardButton.setEnabled(false);
                    roverOneBackButton.setEnabled(false);
                    roverOneLeftButton.setEnabled(false);
                    roverOneRightButton.setEnabled(false);
                   // Log.e("message:", "Calling sendMessage");
                    sendMessage("send goToAutomaticMode to capsule Top.controlSoftware:00\n","ONE");
                }

                    break;
            case R.id.radioButtonOneCruiseMode:
                if (checked){
                    if(cruiseModeFlagOne == false){// Code to handle cruise mode
                        cruiseModeFlagOne = true;
                        radioButtonTwoCruise.setEnabled(false);
                        currentCruiseMode = "ONE";
                    }
                    roverOneForwardButton.setEnabled(false);
                    roverOneBackButton.setEnabled(false);
                    roverOneLeftButton.setEnabled(false);
                    roverOneRightButton.setEnabled(false);

                //    Log.e("message:", "Calling sendMessage");
                    sendMessage("send goToCruiseMode to capsule Top.controlSoftware:00\n","ONE");
                }

                    break;
            case R.id.radioButtonTwoAutonomusMode:
                if (checked){
                    if(cruiseModeFlagTwo == true){// Code to handle cruise mode
                        cruiseModeFlagTwo = false;
                        radioButtonOneCruise.setEnabled(true);
                    }
                    roverTwoForwardButton.setEnabled(false);
                    roverTwoBackButton.setEnabled(false);
                    roverTwoLeftButton.setEnabled(false);
                    roverTwoRightButton.setEnabled(false);
                    sendMessage("send goToAutomaticMode to capsule Top.controlSoftware:00\n","TWO");

                }

                    break;
            case R.id.radioButtonTwoManualMode:
                if (checked){
                    if(cruiseModeFlagTwo == true){// Code to handle cruise mode
                        cruiseModeFlagTwo = false;
                        radioButtonOneCruise.setEnabled(true);
                    }
                    roverTwoForwardButton.setEnabled(true);
                    roverTwoBackButton.setEnabled(true);
                    roverTwoLeftButton.setEnabled(true);
                    roverTwoRightButton.setEnabled(true);
                    sendMessage("send goToManualMode to capsule Top.controlSoftware:00\n","TWO");
                }

                    break;
            case R.id.radioButtonTwoCruiseMode:
                if (checked){
                    if(cruiseModeFlagTwo == false){// Code to handle cruise mode
                        cruiseModeFlagTwo = true;
                        radioButtonOneCruise.setEnabled(true);
                        currentCruiseMode = "TWO";
                    }
                    roverTwoForwardButton.setEnabled(false);
                    roverTwoBackButton.setEnabled(false);
                    roverTwoLeftButton.setEnabled(false);
                    roverTwoRightButton.setEnabled(false);
                    sendMessage("send goToCruiseMode to capsule Top.controlSoftware:00\n","TWO");

                }

                    break;
        }
    }
/*Method to Establish Socket Connection*/
   private void setup() {
       /*Rover ONE socket Connection*/
        AsyncServer.getDefault().connectSocket(new InetSocketAddress("10.217.168.28", 8080), new ConnectCallback() {//LocalHost-10.0.2.2 | RPI-10.217.168.28
            @Override
            public void onConnectCompleted(Exception ex, final AsyncSocket socket) {
              //  handleConnectCompleted(ex, socket);
                MainActivity.this.socketOne = socket;
                //Log.e("Socket Connected", "IN Setup");
                socket.setDataCallback(new DataCallback() {
                    @Override
                    public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                        responseMsgOne = new String(bb.getAllByteArray());
                        //Log.e("**msg received", new String(bb.getAllByteArray()));

                        if((cruiseModeFlagTwo == true) &&(currentCruiseMode.equals("TWO"))){
                            Log.e("ONE msg received", responseMsgOne);
                            receiveMessage(responseMsgOne,"TWO");//Read feedback from first Rover and Send the feedback to second ROver
                        }

                    }
                });

            }
        });
       /*Rover TWO socket Connection*/
     /*  AsyncServer.getDefault().connectSocket(new InetSocketAddress("10.217.20.165", 8080), new ConnectCallback() { //10.217.20.165
           @Override
           public void onConnectCompleted(Exception ex, final AsyncSocket socket) {
               //  handleConnectCompleted(ex, socket);
               MainActivity.this.socketTwo = socket;

               socket.setDataCallback(new DataCallback() {
                   @Override
                   public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                   responseMsgTwo = new String(bb.getAllByteArray());

                        if((cruiseModeFlagOne == true) &&(currentCruiseMode.equals("ONE"))){
                            Log.e("TWO msg received", responseMsgTwo);
                            receiveMessage(responseMsgTwo,"ONE");//Read feedback from second Rover and Send the feedback to first ROver
                        }

                   }
               });
           }
       });*/
       /*Loading Animation Deactivated*/
     //  loadingAnimationLayout.setVisibility(View.GONE);
    }
/*Method to send command to the Rover*/
    private void sendMessage(String msg, String socketType) {
     //   Log.e("message_Sent:", msg);
      //  Log.e("message_Sent:", "Inside");
      //  Log.e("message_Sent:ONE::", msg);
     //   socketOne.write(new ByteBufferList(msg.getBytes()));
        if(socketType.equals("ONE")){
            Log.e("message_Sent:ONE::", msg);
            socketOne.write(new ByteBufferList(msg.getBytes()));

        }else if(socketType.equals("TWO")){
            Log.e("message_Sent:TWO::", msg);
            socketTwo.write(new ByteBufferList(msg.getBytes()));

        }
    }

/*Method to Handle Cruise Mode by sending the command based on the feedback from Master Rover*/
     private void receiveMessage(String msg, String socketType){
        // Log.e("Received message", msg);
         String[] msgLine = msg.split("\n"); //Reading the feedback messages line by line
         for(String line: msgLine){
             String[] msgSplit = line.split("\\|");
             String state = msgSplit[1];
             String status = msgSplit[3];
             String[] capsuleString = msgSplit[6].split(":");
             String capsuleName = capsuleString[1];
             String command = "";
             //Log.e("Received message Line", line);
             //Log.e("CurrentState", currentState);
             //Log.e("CapsuleName", capsuleName);
             Log.e("CurrentState", currentState);
             if(socketType.equals("ONE")) {


                 if((!(state.equals(currentState))) && status.equals("15") && capsuleName.equals("EngineController")){
                     currentState = state;//to get the next state than the existing state
                     Log.e("Received message ONE", line);

                     command = buildMessage(state);
                     Log.e("Built message for ONE", command);
                     sendMessage(command,"ONE");
                 }

             }else if(socketType.equals("TWO")){
                 if((!(state.equals(currentState))) && status.equals("15") && capsuleName.equals("EngineController")){
                     currentState = state;//to get the next state than the existing state
                     Log.e("Received message TWO", line);
                     command = buildMessage(state);
                     Log.e("Built message for TWO", command);
                     sendMessage(command,"TWO");
                 }

             }
         }


     }
     /*Method to build command based on the feedback*/
     private String buildMessage(String state){
         String command = "";

             switch(state){
                 case "IDLE" : command = "send stop to capsule Top.rover.engineController:00\n";
                     break;
                 case "MOVING_FORWARD" : command = "send moveForward to capsule Top.rover.engineController:00\n";
                     break;
                 case "MOVING_BACKWARD" :command = "send moveBackward  to capsule Top.rover.engineController:00\n";
                     break;
                 case "ROTATE_LEFT" : command = "send turnLeft to capsule Top.rover.engineController:00\n";
                     break;
                 case "ROTATE_RIGHT" : command = "send turnRight to capsule Top.rover.engineController:00\n";
                     break;
             }


         return command;

     }
}
