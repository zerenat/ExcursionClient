import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//Server client
public class Messenger {
    private static BufferedReader input;
    private static PrintWriter output;
    private boolean connected = false;

    public static void clientOptions(String message) {
        String [] splitMessage = message.split(",");
            try(Socket socket = new Socket("localhost", 40450)){
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);
                Controller controller = new Controller();

                switch (splitMessage[0]) {
                    case "1": //Register
                        output.println(message);
                        System.out.println("Sending a message: "+message);
                        String response = input.readLine();
                        if(response.contains("successfully")){
                            controller.toMainPage();
                        }else{
                            System.out.println();
                        }
                        break;
                    case "2": //Log in
                        output.println(message);
                        System.out.println("Sending a message: "+message);
                        response = input.readLine();
                        System.out.println(response);
                        if(response.contains("successfully")){
                            Client.getInstance().setLoggedUser(splitMessage[1]);
                            System.out.println(splitMessage[1]);
                            controller.toLoggedInPage();
                            Client.getInstance().setCruiseId(response.split(",")[1]);
                        }
                        break;
                    case "3": //Log out
                        output.println(message+","+Client.getInstance().getLoggedUser());
                        break;
                    case "4": //Get available excursions
                        output.println(message+","+Client.getInstance().getCruiseId());
                        response = input.readLine();
                        System.out.println(response);
                        String [] splitResponse = response.split(",");
                        //ObservableList<String> temp = FXCollections.observableArrayList(splitResponse);
                        //System.out.println(temp.get(2));
//                        String [] temp = Client.getInstance().getExcursionInfo();
//                        for(int x=0; x<temp.length;x++){
//                            System.out.println(temp[x]);
//                        }
                        break;
                    case "5":
                         //output.println(5+","+cabinNo+","+excursionId+","+numberOfPeople);
                        break;
                    case "6":
                        //output.println(7+","+email);
                        break;

                        //output.println();
                }
            }catch(Exception e){
                System.out.println("Exception: "+e);
            }
    }
}



