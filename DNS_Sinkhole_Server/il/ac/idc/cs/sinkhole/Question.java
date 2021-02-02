package il.ac.idc.cs.sinkhole;

public class Question extends DNSSection {

    /**
     * a parser for questions records
     * @return
     */

    public int getEnd() {
        return end;
    }

    public Question(int offset, byte[] content){
        this.offset = offset;
        this.content = content;
        this.end = this.read();
    }

    private int read(){
        int current_offset = offset;
        this.name = new DomainParser(current_offset, this.content);
        current_offset = this.name.getEnd();
        // skipping QTYPE, QCLASS
        return current_offset + 4;
    }
    public String getName(){
        return this.name.getDomain();
    }

}
