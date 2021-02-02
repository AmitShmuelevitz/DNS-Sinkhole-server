package il.ac.idc.cs.sinkhole;

import java.util.ArrayList;
import java.util.Arrays;

public class DomainParser {
    /**
     * parse a domain name from a given DNS query. due to compression, the entire content of the message is needed
     */

    private final int end;
    private int offset;
    private byte[] content;
    private ArrayList<String> labels;

    public int getEnd() {
        return end;
    }

    public DomainParser(int offset, byte[] content){
        this.offset = offset;
        this.content = content;
        this.labels = new ArrayList<>();
        this.end  = this.read();
    }

    public int getOffset() {
        return offset;
    }

    private int readLabel(int offset){
        int oct_count = this.content[offset];
        this.labels.add(new String(Arrays.copyOfRange(this.content, offset + 1, offset + oct_count + 1)));
        return offset + 1 + oct_count;
    }

    private int readPointer(int offset){
        byte mask = 0b00111111;
        // removing leading 11 of the pointer
        // then due to the bytes converted into int, from the first byte we will keep the the ones from bit 8-15,
        // and in the second byte, we will keep the ones from bit 0-7
        return ((this.content[offset] & mask) << 8 & 0x0000ff00) | (this.content[offset + 1] & 0x000000ff);
    }

    private int sectionType(int offset){
        // returns the type of the section: 0 for end, 1 for label, 2 for pointer
        int val;
        if(this.content[offset] == 0){
            val = 0;
        }
        else {
            byte mask = (byte) 0b11000000;
            if ((this.content[offset] & mask) == 0) {val = 1;}
            else { val = 2;}

        }
        return val;
    }


    private int read(){
        int section_type;
        int current_offset = this.offset;
        int last_pos = current_offset;
        boolean isOriginalSection = true;
        while((section_type = sectionType(current_offset)) != 0){
            if (section_type == 1){
                current_offset = this.readLabel(current_offset);
                if (isOriginalSection) {
                    last_pos = current_offset;
                }
            }
            else{
                if (isOriginalSection){
                    last_pos += 2;
                    isOriginalSection = false;

                }
                current_offset = this.readPointer(current_offset);

            }
        }
        if (isOriginalSection){
            last_pos ++;
        }
        return last_pos;
    }

    public String getDomain(){
        return String.join(".", this.labels);
    }


}
