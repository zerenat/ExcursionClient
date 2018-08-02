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
    public static boolean testBool = false;

    public static Messenger getInstance(){
        return instance;
    }

    public void clientOptions(String message) {
        String [] splitMessage = message.split(",");
            try(Socket socket = new Socket("localhost", 40450)){
                testBool = true;
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);
                Controller controller = new Controller();
                ClientData client = ClientData.getInstance();


                switch (splitMessage[0]) {
                    case "1": //Register
                        output.println(message);
                        System.out.println("Sending a message: "+message);
                        String response = input.readLine();
                        if(response.contains("successfully")){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText(response);
                            alert.showAndWait();
                            controller.toMainPage();
                        }else if(response.contains("Email and cabin already registered")){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText(response);
                            alert.showAndWait();
                        }else if(response.contains("Entered Cruise ID doesn't exist")){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText(response);
                            alert.showAndWait();
                        }
                        else{
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Failed to book the seats. Please try again");
                            alert.showAndWait();
                        }
                        break;
                    case "2": //Log in
                        output.println(message);
                        System.out.println("Sending a message: "+message);
                        response = input.readLine();
                        System.out.println(response);
                        if(response.contains("successfully")){
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
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText(response);
                            alert.showAndWait();
                        }
                        else{
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Invalid cabin number or password.");
                            alert.showAndWait();
                        }
                        break;
                    case "3": //Log out
                        output.println(message);
                        break;
                    case "4": //Get available excursions
                        output.println(message);
                        response = input.readLine();
                        ExcursionItem.getInstance().storeExcursionItems(response);
                        System.out.println(response);
                        break;
                    case "5": //Make a booking
                        output.println(message);
                        System.out.println("Sending a message: "+message);
                        response = input.readLine();
                        if(response.contains("successful")){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Booking completed successfully");
                            alert.showAndWait();
                            ViewExcursionsPageController.getInstance().updateValues();
                        }else{
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Failed to book the seats. Please try again");
                            alert.showAndWait();
                        }
                        break;
                    case "6": //Get bookings
                        output.println(message);
                        System.out.println("Sending a message: "+message);
                        response = input.readLine();
                        ExcursionItem.getInstance().storeExcursionItems(response);
                        break;
                    case "7": //Change Booking
                        output.println(message);
                        System.out.println("Sending a message: "+message);
                        response = input.readLine();
                        if(response.contains("successfully")){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Booking changed successfully");
                            alert.showAndWait();
                        }else{
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Failed to change the booking. Please try again");
                            alert.showAndWait();
                        }
                        break;
                    case "8": //Cancel Booking
                        output.println(message);
                        System.out.println("Sending a message: "+message);
                        response = input.readLine();
                        if(response.contains("successfully")){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Booking canceled successfully");
                            alert.showAndWait();
                        }else{
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Failed to cancel the booking. Please try again");
                            alert.showAndWait();
                        }

                }
            }catch (ConnectException e){
                testBool = false;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Unable to establish a connection to the server.");
                alert.showAndWait();
            }
            catch(Exception e){
                System.out.println("Exception: "+e);
                e.printStackTrace();
            }
    }
}



