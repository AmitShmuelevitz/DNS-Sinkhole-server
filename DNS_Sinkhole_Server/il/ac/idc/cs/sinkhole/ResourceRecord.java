package il.ac.idc.cs.sinkhole;

abstract class ResourceRecord extends DNSSection {
    /**
     * this class extend the common interface between authority, answers and additional records.
     */

    protected int rDataLength;
    abstract String getRData();

    public int getrDataLength() {
        return rDataLength;
    }
}
