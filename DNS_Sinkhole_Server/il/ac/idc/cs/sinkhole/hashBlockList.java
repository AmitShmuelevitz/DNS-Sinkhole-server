package il.ac.idc.cs.sinkhole;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class hashBlockList {

    HashMap<Integer, String> hashMap;


    public hashBlockList(String path) throws IOException {
        hashMap = crateBlockListMap(path);
    }

    /**
     * given a message object, checks if in any of the the domain names in questions section
     * are in the block list
     * @param message
     * @return
     */
    public boolean isInBlockList(Message message)
    {

        for (Question question: message.questions)
        {
            if(hashMap.containsValue(question.getName()))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * loads a list of domain names from lines in a given file into a hash map
     * @param filePAth
     * @return
     * @throws IOException
     */
    private HashMap<Integer, String> crateBlockListMap(String filePAth) throws IOException
    {
        int index = 1;
        HashMap<Integer, String> BlocklistMap = new HashMap<>();

        BufferedReader reader = new BufferedReader(new FileReader(new File(filePAth)));
        String line;
        while((line = reader.readLine()) != null)
        {
            BlocklistMap.put(index, line);
            index++;
        }

        return BlocklistMap;
    }

}
