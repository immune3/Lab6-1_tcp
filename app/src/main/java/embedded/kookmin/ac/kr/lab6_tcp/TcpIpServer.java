package embedded.kookmin.ac.kr.lab6_tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpIpServer {
    //    private HashMap<String, DataOutputStream> clients;
    private ServerSocket serverSocket;

    private int SERVERPORT = 54321;

    public static void main(String[] args) {
        new TcpIpServer().start();
    }

    public TcpIpServer() {

    }

    public void start() {
        try {
            Socket socket;

            // 리스너 소켓 생성
            serverSocket = new ServerSocket(SERVERPORT);
            System.out.println("서버가 시작되었습니다.");

            // 클라이언트와 연결되면
            while (true) {
                // 통신 소켓을 생성하고 스레드 생성(소켓은 1:1로만 연결된다)
                socket = serverSocket.accept();
                ServerReceiver receiver = new ServerReceiver(socket);
                receiver.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ServerReceiver extends Thread {
        Socket socket;
        DataInputStream input;
        DataOutputStream output;

        public ServerReceiver(Socket socket) {
            this.socket = socket;
            try {
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
            }
        }

        @Override
        public void run() {
            String address = socket.getLocalAddress().toString();
            try {

                System.out.println(address + " 접속");
                while (input != null) {
                    System.out.println(address + ":"+input.readUTF().toString());
                }
            } catch (IOException e) {
            } finally {
                System.out.println(socket.getLocalAddress()+" 연결 끊기");
            }
        }


    }
}