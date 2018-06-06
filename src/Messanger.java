import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//Server client
public class Messanger{
    private static BufferedReader input;
    private static PrintWriter output;
    private boolean connected = false;

    @FXML private Button RegisterButton;

    public void clientOptions(int choice) {
        int userInput = choice;//scanner.nextInt();
            try(Socket socket = new Socket("localhost", 40450)){
                //Derry's server : "212.159.23.21",4443
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);
                connected = true;
                switch (userInput) {
                    case 1:

                        //output.println(1+","+cabinNo+","+email+","+userpw+","+cruise);
                        break;
                    case 2:
                        //output.println(2+","+cabinNo+","+userpw);
                        break;
                    case 3:
                        //output.println(3+","+cabinNo);
                        break;
                    case 4:
                        //output.println(4+","+cabinNo);
                        break;
                    case 5:
                         //output.println(5+","+cabinNo+","+excursionId+","+numberOfPeople);
                        break;
                    case 6:
                        //output.println(7+","+email);
                        break;

                        //output.println();
                }
            }
            catch(Exception e){
                System.out.println("Exception: "+e);
            }
        /*catch(SocketTimeoutException e){
            System.out.println("The socket has timed out");
        }
        catch(IOException e) {
            System.out.println("Messanger error: " + e.getMessage());
        }*/
    }


    public void registerUser(){
        System.out.println("Made it");
        String cabinNo = "57";
        String email = "George@live.com";
        String userpw = "Georgina";
        output.println(1+","+cabinNo+","+email+","+userpw);
    }

    public void logIn(){
        String cabinNo = "57";
        String userpw = "Georgina";
        output.println(2+","+cabinNo+","+userpw);
    }

    public void logOut(){
        String rndm = "random";
        output.println(3+","+"57");
    }

    public static void bookings(){

    }

    public void excursions(){
        System.out.println("yay");
    }
}

