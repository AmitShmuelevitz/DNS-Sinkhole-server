package il.ac.idc.cs.sinkhole;

public class AuthRecord extends ResourceRecord{
    /**
     * a parser for a single Auth record
     */
    private DomainParser rData;

    public AuthRecord(int offset, byte[] content){
        this.offset = offset;
        this.content = content;
        this.end = this.read();

    }

    private int read(){
        int end;
        int current_offset = offset;
        this.name = new DomainParser(current_offset, this.content);
        // reading name
        current_offset = this.name.getEnd();
        // skipping record type, class, ttl, data length
        current_offset += 8;
        this.rDataLength =  (this.content[current_offset] << 8 | this.content[current_offset + 1]) & 0x0000ffff;
        current_offset += 2;
        this.rData = new DomainParser(current_offset, this.content);

        return current_offset + this.rDataLength;
    }

    public String getRData(){
        return this.rData.getDomain();
    }

    public String getName(){
        return this.name.getDomain();
    }

    public int getEnd(){
        return this.end;
    }
}
