import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

public class Messenger {
    private static BufferedReader input;
    private static PrintWriter output;
    private static Messenger instance = new Messenger();
    public static boolean connected = false;

    public static Messenger getInstance(){
        return instance;
    }

    public void clientOptions(String message) {

        String [] splitMessage = message.split(",");
            try{
                Socket socket = new Socket("localhost", 40450);
                connected = true;
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);
                Controller controller = new Controller();
                ClientData client = ClientData.getInstance();

                Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                Alert alertError = new Alert(Alert.AlertType.ERROR);

                switch (splitMessage[0]) {
                    //Messages (message) are created by other methods in various different classes
                    //Messenger Class is a distributor for any incoming messages (message)
                    case "1": //Register
                        output.println(message);
                        System.out.println("Sending a message: "+message);
                        String response = input.readLine();
                        if(response.contains("successfully")){
                            alertInfo.setContentText(response);
                            alertInfo.showAndWait();
                            controller.toMainPage();
                        }else if(response.contains("Email and cabin already registered")){
                            alertError.setContentText(response);
                            alertError.showAndWait();
                        }else if(response.contains("Entered Cruise ID doesn't exist")){
                            alertError.setContentText(response);
                            alertError.showAndWait();
                        }
                        else{
                            alertError.setContentText("Failed to book the seats. Please try again");
                            alertError.showAndWait();
                        }

                        break;
                    case "2": //Log in
                        output.println(message);
                        System.out.println("Sending a message: "+message);
                        response = input.readLine();
                        System.out.println(response);
                        if(response.contains("successfully")){
                            connected = true;
                            client.setLoggedUser(splitMessage[1]);
                            System.out.println(splitMessage[1]);
                            if(client.getLoggedUser().equals("sle")){
                                client.setCruiseId(response.split(",")[1]);
                                AdministratorPageController.getInstance().toAdministratorPage();
                            }else {
                                client.setCruiseId(response.split(",")[1]);
                                LoginPageController.getInstance().toLoggedInPage();
                            }
                        }else if(response.contains("Login failed, user already logged in.")){
                            alertError.setContentText(response);
                            alertError.showAndWait();
                        }
                        else{
                            alertError.setContentText("Invalid cabin number or password.");
                            alertError.showAndWait();
                        }
                        //output.close();
                        System.out.println(socket.isClosed());
                        break;
                    case "3": //Log out
                        if(connected) {
                            output.println(message);
                        }
                        break;
                    case "4": //Get available excursions
                        output.println(message);
                        System.out.println(message);
                        response = input.readLine();
                        ExcursionItem.getInstance().storeExcursionItems(response);
                        System.out.println(response);
                        socket.close();
                        break;
                    case "5": //Make a booking
                        output.println(message);
                        System.out.println("Sending a message: "+message);
                        response = input.readLine();
                        if(response.contains("successful")){
                            alertInfo.setContentText("Booking completed successfully");
                            alertInfo.showAndWait();
                            ViewExcursionsPageController.getInstance().updateValues();
                        }else{
                            alertError.setContentText("Failed to book the seats. Please try again");
                            alertError.showAndWait();
                        }
                        break;
                    case "6": //Get bookings
                        output.println(message);
                        System.out.println("Sending a message: "+message);
                        response = input.readLine();
                        if(response.contains("No")){
                            alertError.setContentText("No customer found.");
                            alertError.showAndWait();
                        }else if(response.isEmpty()){
                            alertError.setContentText("No bookings found.");
                            alertError.showAndWait();
                        }else {
                            ExcursionItem.getInstance().storeExcursionItems(response);
                        }
                        break;
                    case "7": //Change Booking
                        output.println(message);
                        System.out.println("Sending a message: "+message);
                        response = input.readLine();
                        if(response.contains("successfully")){
                            alertInfo.setContentText("Booking changed successfully");
                            alertInfo.showAndWait();
                        }else{
                            alertError.setContentText("Failed to change the booking. Please try again");
                            alertError.showAndWait();
                        }
                        break;
                    case "8": //Cancel Booking
                        output.println(message);
                        System.out.println("Sending a message: "+message);
                        response = input.readLine();
                        if(response.contains("successfully")){
                            alertInfo.setContentText("Booking canceled successfully");
                            alertInfo.showAndWait();
                        }else{
                            alertError.setContentText("Failed to cancel the booking. Please try again");
                            alertError.showAndWait();
                        }
                        break;
                }
            }catch (ConnectException e) {
                connected = false;
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setContentText("Unable to establish a connection to the server.");
                alertError.showAndWait();
            }catch(Exception e){
                System.out.println("Exception: "+e);
                e.printStackTrace();

            }finally {
                output.flush();
                output.close();
            }
    }
}



