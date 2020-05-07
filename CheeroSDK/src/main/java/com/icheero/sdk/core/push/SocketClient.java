package com.icheero.sdk.core.push;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketClient
{
    public static void main(String[] args)
    {
        SocketClient client = new SocketClient();
        client.start();
    }

    private void start()
    {
        BufferedReader inputReader = null;
        BufferedReader socketReader = null;
        BufferedWriter socketWriter = null;
        Socket socket = null;
        try
        {
            socket = new Socket("127.0.0.1", 9898);
            socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            inputReader = new BufferedReader(new InputStreamReader(System.in));
            startServerReplyListener(socketReader);
            String inputContent;
            while (!(inputContent = inputReader.readLine()).equals("bye"))
            {
                socketWriter.write(inputContent + "\n");
                socketWriter.flush();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (inputReader != null) inputReader.close();
                if (socketReader != null) socketReader.close();
                if (socketWriter != null) socketWriter.close();
                if (socket != null) socket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void startServerReplyListener(final BufferedReader reader)
    {
        new Thread(() -> {
            String response;
            try
            {
                while ((response = reader.readLine()) != null)
                {
                    System.out.println(response);
                    if (response.equals("bye")) break;
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }).start();
    }
}
