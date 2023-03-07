package safe;

import administrator.Administrator;
import dao.domain.*;
import drivers.*;
import gui.enums.StateDisplayType;
import javafx.scene.paint.Color;
import safe.enums.State;

import java.util.ArrayList;
import java.util.List;

public class SafeControllerImpl implements SafeController {
    private static Administrator admin;
    private static LockController lockCont;
    private static DisplayControllerInt dispController;
    private static UsbDriver usb;
    private static KeypadController keypadCont;
    private State currState = State.LOCKED;
    private String userName;
    private String password;
    private UserProfile currUser;
    private String newPassword;
    private String requestedOperation;
    private Authorization authorized = Authorization.UNAUTHORIZED;

    public SafeControllerImpl(Administrator administrator, DisplayControllerInt displayController){
        admin = administrator;
        dispController = displayController;
    }
    public void setUsb(UsbDriver usbIn){
        usb = usbIn;
    }
    public void setLockCont(LockController lockController){
        lockCont = lockController;
    }
    public void setKeypadCont(KeypadController keypadController){
        keypadCont = keypadController;
    }
    @Override
    public boolean handleStateChangeRequest(State state) {
        if(currState==state){
            currState = State.LOCKED;
            //flash yellow LED
            dispController.signal(Color.YELLOW, StateDisplayType.FLASH);
            return false;
        } else if(currState==State.LOCKED){
            currState = state;
            //flash green LED
            dispController.signal(Color.GREEN, StateDisplayType.FLASH);
            return true;
        } else if (currState==State.UNLOCKED && state==State.LOCKED) {
            currState = state;
            keypadCont.setExpectedInputLength(4,4,false);
            return true;
        } else {
            //flash red LED
            dispController.signal(Color.RED, StateDisplayType.FLASH);
            return false;
        }

    }

    @Override
    public void handleInputRequest(String input) {
        boolean justInitialized = false;
        if (userName == null || password == null){
            if (userName==null){
                userName = input;
                keypadCont.setExpectedInputLength(8,12,true);
                dispController.displayPrompt("Input Password");
            } else {
                password = input;
                justInitialized = true;
                currUser = new UserProfile(userName,password,"USER");
                dispController.clear();
                keypadCont.setExpectedInputLength(4,4,false);
            }
        }
        if(justInitialized){
            switch (currState){
                case ADMIN:
                    authorized = admin.authorizeUser(currUser, Operation.USER_MANAGEMENT);
                    if(!(authorized==Authorization.AUTHORIZED)){
                        wipeUser();
                        //Flash red LED
                        dispController.signal(Color.RED, StateDisplayType.FLASH);
                    } else {
                        keypadCont.setExpectedInputLength(1,1,false);
                        dispController.displayPrompt("C: Create, D: Delete");
                    }
                    break;
                case CHANGEPWD:
                    authorized = admin.authorizeUser(currUser, Operation.CHANGE_PASSWORD);
                    if(!(authorized==Authorization.AUTHORIZED)){
                        wipeUser();
                        //Flash red LED
                        dispController.signal(Color.RED, StateDisplayType.FLASH);
                    } else {
                        keypadCont.setExpectedInputLength(8,12,true);
                        dispController.displayPrompt("Input New Password");
                    }
                    break;
                case EXPORT:
                    authorized = admin.authorizeUser(currUser, Operation.EXPORT_LOGS);
                    if(!(authorized==Authorization.AUTHORIZED)){
                        wipeUser();
                        //Flash red LED
                        dispController.signal(Color.RED, StateDisplayType.FLASH);
                        currState = State.LOCKED;
                    } else {
                        //flash green LED
                        dispController.signal(Color.GREEN, StateDisplayType.FLASH);
                        //export logs
                        usb.createFile("Safe Log Export");
                        List<LogRecord> logs = admin.getLogs();
                        List<String> stringLogs = new ArrayList<>();
                        for (LogRecord logrecord: logs) {
                            stringLogs.add(logrecord.toString());
                        }
                        usb.write(stringLogs);
                        wipeUser();
                        currState = State.LOCKED;
                    }
                    break;
                case LOCKED:
                    authorized = admin.authorizeUser(currUser, Operation.BASE_ACCESS);
                    if(!(authorized==Authorization.AUTHORIZED)){
                        wipeUser();
                        //Flash red LED
                        dispController.signal(Color.RED, StateDisplayType.FLASH);
                    } else {
                        lockCont.disengage();
                        wipeUser();
                        currState = State.UNLOCKED;
                        //flash green LED
                        dispController.signal(Color.GREEN, StateDisplayType.FLASH);
                        keypadCont.setExpectedInputLength(0,0,false);
                    }
            }
        } else {
            if ((authorized==Authorization.AUTHORIZED)) {
                if(currState == State.ADMIN){
                    if(requestedOperation == null){
                        requestedOperation = input;
                        if (requestedOperation.equals("C")){
                            keypadCont.setExpectedInputLength(4,4,false);
                            dispController.displayPrompt("New Username");
                        } else if (requestedOperation.equals("D")){
                            keypadCont.setExpectedInputLength(4,4,false);
                            dispController.displayPrompt("Username to Delete");
                        }else {
                            //flash red light
                            dispController.signal(Color.RED, StateDisplayType.FLASH);
                            requestedOperation = null;
                        }
                    } else {
                        if (requestedOperation.equals("C")){
                            admin.saveUser(input,"12345678", Authority.USER);
                            dispController.signal(Color.GREEN, StateDisplayType.FLASH);
                            wipeUser();
                            currState = State.LOCKED;
                        } else if (requestedOperation.equals("D")) {

                            admin.deleteUser(input);
                            dispController.signal(Color.GREEN, StateDisplayType.FLASH);
                            wipeUser();
                            currState = State.LOCKED;
                        }
                    }
                } else if(currState == State.CHANGEPWD){
                    if (newPassword == null){
                        newPassword = input;
                    } else if(newPassword.equals(input)) {
                        admin.saveUser(userName,newPassword,Authority.USER);
                        dispController.signal(Color.GREEN, StateDisplayType.FLASH);
                        wipeUser();
                        currState = State.LOCKED;
                    } else {
                        newPassword = null;
                        //flash red light
                        dispController.signal(Color.RED, StateDisplayType.FLASH);
                    }
                }
            }
        }

    }

    private void wipeUser(){
        dispController.clear();
        userName = null;
        password = null;
        newPassword = null;
        currUser = null;
        authorized = Authorization.UNAUTHORIZED;
        requestedOperation = null;
        keypadCont.setExpectedInputLength(4,4,false);

    }
}
