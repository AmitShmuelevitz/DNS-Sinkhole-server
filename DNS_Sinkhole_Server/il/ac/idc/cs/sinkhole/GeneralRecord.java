package il.ac.idc.cs.sinkhole;

public class GeneralRecord extends ResourceRecord {

    /**
     * a general record. due to the nature of this application, answers and additional records re parsed the same.
     * we only need to know where it starts and ends.
     * @param offset
     * @param content
     */

    // could be either answer or additional
    public GeneralRecord(int offset, byte[] content){
        this.offset = offset;
        this.content = content;
        this.end = this.read();

    }

    private int read(){
        int current_offset = offset;
        int rDataLength;
        this.name = new DomainParser(current_offset, this.content);

        // reading name
        current_offset = this.name.getEnd();
        // skipping record type, class, ttl
        current_offset += 8;
        this.rDataLength =  (this.content[current_offset] << 8 | this.content[current_offset + 1]) & 0x0000ffff;
        // skipping rData section
        return current_offset + 2 + this.rDataLength;
    }

    public String getRData(){
        // we are not using the rData in answer records
        return "rData";
    }

    public String getName(){
        return this.name.getDomain();
    }

    public int getEnd(){
        return this.end;
    }
}
