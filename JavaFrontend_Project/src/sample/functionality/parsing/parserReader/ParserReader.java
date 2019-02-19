package sample.functionality.jSoupParsing.parserReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ParserReader implements ParserReaderInterface
{
    ParserReader(){}

    /***
     * This function will read the file containing the parsed site data.
     * NOTE: Not sure if this should return a String or something else, reason, returning a large string is inefficient
     * @param filePath Location of file that contains parsed data
     * @throws IOException Thrown due to BufferReader(), thrown if file not found
     */
    @Override
    public void readParsedFile(String filePath) throws IOException
    {
        String fileContent = "";
        String tempstr;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

        /*Reads the file into a string */
        while((tempstr = bufferedReader.readLine()) != null)
        {
            fileContent.concat(tempstr);
        }
        bufferedReader.close();
    }
}
