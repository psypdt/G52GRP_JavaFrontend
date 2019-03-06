package sample.functionality.parsing.parserReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ParserReader implements ParserReaderInterface
{
    public ParserReader(){}

    /***
     * This function will read the file containing the parsed site data.
     * NOTE: Not sure if this should return a String or something else, reason, returning a large string is inefficient
     * @param filePath Location of file that contains parsed data
     * @throws IOException Thrown due to BufferReader(), thrown if file not found
     */
    @Override
    public ArrayList<String> readParsedFile(String filePath) throws IOException
    {
        ArrayList <String> fileContents = new ArrayList<>();
        String fileLine; /*A single line in the file*/
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

        /*Reads the file into a string */
        while((fileLine = bufferedReader.readLine()) != null)
        {
            fileContents.add(fileLine);
        }
        bufferedReader.close();

        return fileContents;
    }
}
