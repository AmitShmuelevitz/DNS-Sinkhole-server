package il.ac.idc.cs.sinkhole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Arrays;

public class UDPClient {
    private DatagramSocket clientSocket;
    private byte[] receiveData;

    /**
     * a UDP client. open a UDP socket on construction.
     * uses the it internal socket to send and receive UDP packets
     * @param inBuffSize
     * @throws SocketException
     */

    public UDPClient(int inBuffSize) throws SocketException {
        this.clientSocket = new DatagramSocket();
        this.receiveData = new byte[inBuffSize];
    }

    /**
     * send a packet and will blocks until receiving an answer
     * @param content
     * @param length
     * @param addr
     * @param port
     * @return
     * @throws IOException
     */
    public byte[] sendReceive(byte[] content, int length, String addr, int port) throws IOException {
        InetAddress IPAddress = InetAddress.getByName(addr);
        DatagramPacket sendPacket = new DatagramPacket(content, length, IPAddress, port);
        DatagramPacket receivePacket = new DatagramPacket(this.receiveData, this.receiveData.length);
        clientSocket.send(sendPacket);
        clientSocket.receive(receivePacket);
        return receivePacket.getData();

    }

    /**
     * close the client UDP port
     */
    public void close(){
        this.clientSocket.close();
    }
}
