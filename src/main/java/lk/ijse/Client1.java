package lk.ijse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client1 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 4000);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        new Thread(() -> {
            try {
                System.out.print("Enter command : ");
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println("Server: " + line);
                    System.out.print("Enter Command : ");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        String input;
        //get user input
        while ((input = userInput.readLine()) != null) {
            printWriter.println(input);
            if (input.trim().equalsIgnoreCase("BYE")){
                break;
            }
        }
        socket.close();
    }
}
