package il.ac.idc.cs.sinkhole;

import il.ac.idc.cs.sinkhole.DomainParser;

abstract class DNSSection {
    /**
     * an abstract class. defines a common interface for all DNS types of DNS sections records - question, authority,
     * answers and additional
     */
    protected int offset;
    protected byte[] content;
    protected DomainParser name;
    protected int end;

    abstract String getName();
    abstract int getEnd();
}
