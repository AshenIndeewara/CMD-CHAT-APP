package lk.ijse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(4000);
        System.out.println("Server started...");
        int startTime = LocalTime.now().getSecond();

        while (true) {
            Socket socket = server.accept();
            System.out.println("New client join");
            new Thread(() -> {
                try {
                    InputStream inputStream = socket.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader in = new BufferedReader(inputStreamReader);
                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

                    String cmd;
                    while ((cmd = in.readLine()) != null) {
                        cmd = cmd.toUpperCase();
                        System.out.println("User input: "+cmd);

                        if (cmd.equals("TIME")) {
                            printWriter.println(LocalTime.now());
                        } else if (cmd.equals("DATE")) {
                            printWriter.println(LocalDate.now());
                        } else if (cmd.equals("UPTIME")) {
                            int uptime = (LocalTime.now().getSecond() - startTime);
                            printWriter.println("Uptime: " + uptime + " seconds");
                        } else if (cmd.equals("BYE")) {
                            printWriter.println("Goodbye");
                            break;
                        } else if (cmd.equals("HELP")) {
                            printWriter.println("*TIME -> returns current time\t*DATE -> Returns current date\t*UPTIME -> Returns Uptime\t*BYE Exit");
                        } else {
                            printWriter.println("Unknown command");
                        }
                    }
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}