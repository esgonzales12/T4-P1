package safe;

import administrator.Administrator;
import administrator.Authorizer;
import dao.domain.Operation;
import drivers.LockController;
import safe.enums.State;

public class SafeControllerImpl implements SafeController {
    private static Administrator admin;
    private static LockController lockCont;
    private static Authorizer auth;
    private State currState = State.LOCKED;
    private String userName;
    private String password;
    private String newPassword;
    private String requestedOperation;
    private boolean authorized = false;

    //need to add display stuff
    public void SafeController(Administrator administrator, LockController lockController, Authorizer authorizer){
        admin = administrator;
        lockCont = lockController;
        auth = authorizer;
    }
    @Override
    public boolean handleStateChangeRequest(State state) {
        if(currState==state){
            currState = State.LOCKED;
            //flash yellow LED
            return false;
        } else if(currState==State.LOCKED){
            currState = state;
            //flash green LED
            return true;
        } else {
            //flash red LED
            return false;
        }

    }

    @Override
    public void handleInputRequest(String input) {
        boolean justInitialized = false;
        if (userName == null || password == null){
            if (userName==null){
                userName = input;
            } else {
                password = input;
                justInitialized = true;
            }
        }
        if(justInitialized){
            switch (currState){
                case ADMIN:
                    authorized = auth.isAuthorized(userName,password, Operation.USER_MANAGEMENT);
                    if(!authorized){
                        wipeUser();
                        //Flash red LED
                    }
                    break;
                case CHANGEPWD:
                    authorized = auth.isAuthorized(userName,password, Operation.CHANGE_PASSWORD);
                    if(!authorized){
                        wipeUser();
                        //Flash red LED
                    }
                    break;
                case EXPORT:
                    authorized = auth.isAuthorized(userName,password, Operation.EXPORT_LOGS);
                    if (authorized){
                        //flash green LED
                        //export logs
                        wipeUser();
                        currState = State.LOCKED;
                    } else {
                        wipeUser();
                        //Flash red LED
                    }
                    break;
                case LOCKED:
                    authorized = auth.isAuthorized(userName,password, Operation.BASE_ACCESS);
                    if (authorized){
                        //flash green LED
                        lockCont.disengage();
                        wipeUser();
                        currState = State.UNLOCKED;
                        //communicate with keypad to not accept input
                    } else {
                        wipeUser();
                        //Flash red LED
                    }
                    break;
            }
        } else {
            if (authorized) {
                if(currState == State.ADMIN){
                    if(requestedOperation == null){
                        requestedOperation = input;
                    } else {
                        //do admin things accordingly using requestedoperation
                    }
                } else if(currState == State.CHANGEPWD){
                    if (newPassword == null){
                        newPassword = input;
                    } else if(newPassword.equals(input)) {
                        //need clarification of operation system
                        admin.saveUser(userName,newPassword,op);
                    } else {
                        newPassword = null;
                        //flash red light

                    }
                }
            }
        }

    }

    private void wipeUser(){
        userName = null;
        password = null;
        authorized = false;
    }
}
