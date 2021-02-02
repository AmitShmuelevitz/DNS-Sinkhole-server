package il.ac.idc.cs.sinkhole;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

public class UDPServer {

    private byte[] receiveData;
    private DatagramSocket serverSocket;
    private int inPort;
    private InetAddress IPAddress = null;
    private int outPort;

    /**
     * a UDP server. the server can send only after receiving a packet, and will send to the source of the
     * received packet
     * @param inPort
     * @param inBuffSize
     * @throws SocketException
     */
    public UDPServer(int inPort, int inBuffSize) throws SocketException {
        this.receiveData = new byte[inBuffSize];
        this.inPort = inPort;
        this.serverSocket = new DatagramSocket(this.inPort);
    }

    /**
     * listening of the initialize socket
     * @return
     * @throws IOException
     */
    public byte[] receive() throws IOException {

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        this.serverSocket.receive(receivePacket);

        this.outPort = receivePacket.getPort();
        this.IPAddress = receivePacket.getAddress();

        return Arrays.copyOf(this.receiveData, this.receiveData.length);
    }

    /**
     * send a packet to the source of the last received packet
     * @param sendData
     * @param length
     * @throws RuntimeException
     * @throws IOException
     */
    public void send(byte[] sendData, int length) throws RuntimeException, IOException {
        if (this.IPAddress == null){
            throw new RuntimeException("the server did not receive yet");
        }

        DatagramPacket sendPacket =
                new DatagramPacket(sendData, length, this.IPAddress, this.outPort);
        this.serverSocket.send(sendPacket);

    }

    /**
     * close the socket
     */
    public void close(){
        this.serverSocket.close();
    }

}
