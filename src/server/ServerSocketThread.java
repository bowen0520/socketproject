package server;

import request.Request;
import request.RequestImpl;
import response.Response;
import response.ResponseImpl;

import java.io.*;
import java.net.Socket;

public class ServerSocketThread implements Runnable {

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public ServerSocketThread(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = this.socket.getInputStream();
        this.outputStream = this.socket.getOutputStream();
    }

    @Override
    public void run() {
        try {
            System.out.println(this+"____________________________________________");
            Request req = new RequestImpl(inputStream);
            String resourse = req.getResourse();
            String method = req.getMethod();

            if("favicon.ico".equals(resourse)||"".equals(method)){
                return ;
            }

            ((RequestImpl) req).print();

            Response res = new ResponseImpl(req,outputStream);

            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
