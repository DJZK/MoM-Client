package com.Bryan.Client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) throws Exception {
        //Variable Declaration
        Scanner sc = new Scanner(System.in); //Scanner
        boolean ConnectionLoop = true;
        //   String ipHost;
        //  int port;
        // Getting the hostname from user

        while (ConnectionLoop) {
            println("Enter the hostname / ip address of the Server ");
            String ipHost = sc.next();

            println("Enter the port");
            int port = sc.nextInt();

            try (var socket = new Socket(ipHost, port)) {
                println("Now connected to Press Ctrl + D to quit");
                ConnectionLoop = false;
                var scanner = new Scanner(System.in);
                var in = new Scanner(socket.getInputStream());
                var out = new PrintWriter(socket.getOutputStream(), true);
                try {

                    Timer t = new Timer();
                    t.scheduleAtFixedRate(new TimerTask() {

                        @Override
                        public void run() {
                            if (in.hasNextLine()) {
                                println(in.nextLine());
                            } else {
                                t.cancel();
                                t.purge();
                                System.exit(0);
                            }
                        }
                    }, 500, 500);

                    while (scanner.hasNextLine()) {
                        out.println(scanner.nextLine());
                    }
                } catch (NoSuchElementException e) {
                    System.out.print("Error in hostname: ");

                }
            } catch (Exception e) {
                System.out.println("Error in hostname: " + ipHost);
            }
        }
    }

    public static void println(String message) {
        System.out.println(message);
    }
}
