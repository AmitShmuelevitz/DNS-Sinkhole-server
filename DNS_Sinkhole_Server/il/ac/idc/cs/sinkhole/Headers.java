package il.ac.idc.cs.sinkhole;

public class Headers
{
    /**
     * a handler for the DNS query headers. can get and set all necessary headers for this application
     */
    private final int id;
    private byte[] content;
    private final int numQuestions;
    private final int numAnswers;
    private final int numAuthorities;
    private final int numAdditional;
    private final int headersLength = 12;


    public Headers(byte[] content)
    {
        id = ((content[0] << 8) | content[1]) & 0x0000ffff;
        numQuestions = (content[4] << 8) | content[5];
        numAnswers = (content[6] << 8) | content[7];
        numAuthorities = (content[8] << 8) | content[9];
        numAdditional = (content[10] << 8) | content[11];

        this.content = content;
    }
    public void setQR(int newQR)
    {
        if(newQR == 1)
        {
            content[2] = (byte) (content[2] | 0b10000000);
        }
        if(newQR == 0)
        {
            content[2] = (byte) (content[2] & 0b01111111);
        }
    }
    public void setAA(int newAA)
    {
        if(newAA == 1)
        {
            content[2] = (byte) (content[2] | 0b00000100);
        }
        if(newAA == 0)
        {
            content[2] = (byte) (content[2] & 0b11111011);
        }
    }
    public void setRA(int newRA)
    {
        if(newRA == 1) {
            content[3] = (byte) (content[3] | 0b10000000);
        }

        if(newRA == 0) {
            content[3] = (byte) (content[3] & 0b01111111);
        }
    }

    public void setRD(int newRA)
    {
        if(newRA == 1){
            content[2] = (byte) (content[2] | 0b00000001);
        }

        if(newRA == 0) {
            content[2] = (byte) (content[2] & 0b11111110);
        }
    }

    public void setRCODE(int newRCODE){
        if ( 0 > newRCODE || newRCODE > 5){
            throw new IllegalArgumentException("RCODE should be between 0 to 5");
        }
        content[3] = (byte) ((content[3] & 0xf0) | (byte) newRCODE);
    }

    public void setZToZero(){

        content[3] = (byte) (content[3] & 0b10001111);
    }

    public int getQR() {
        return (content[2] & 0b10000000) >> 7;
    }

    public int getAA()
    {
        return (content[2] & 0b00000100) >> 2;
    }

    public int getRA()
    {
        return (content[3] & 0b10000000) >> 7;
    }

    public int getRD() { return (content[2] & 0b00000001); }

    public int getRCODE() { return (content[3] & 0b00001111); }

    public int getId()
    {
        return id;
    }

    public int getNumQuestions()
    {
        return numQuestions;
    }

    public int getNumAnswers()
    {
        return numAnswers;
    }

    public  int getNumAuthorities()
    {
        return numAuthorities;
    }

    public  int getNumAdditional()
    {
        return numAdditional;
    }

    public int end(){
        return this.headersLength;
    }


}