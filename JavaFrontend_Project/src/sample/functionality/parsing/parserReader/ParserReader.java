package sample.functionality.parsing.parserReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @implNote This class is not in use but has been implemented, this class allows Tags to be read from a file.
 */
public class ParserReader implements ParserReaderInterface
{
    /**
     * Constructor for {@link ParserReader}.
     * @implNote This constructor will only create the object, no function will be called by default.
     * @implSpec Should it be necessary to read some file by default, then this constructor may be changed.
     */
    public ParserReader(){}

    /***
     * @implSpec This function will read the file and do no parsing.
     * @implSpec Developer may wish to return something other than a {@link ArrayList<String>}, do this via a new method.
     * @param filePath Location of file that contains parsed data.
     * @throws IOException {@link BufferedReader#readLine()} can throw {@link IOException} if the file not found.
     *        {@link BufferedReader#close()} can throw {@link IOException} if closing the file fails.
     */
    @Override
    public ArrayList<String> readParsedFile(String filePath) throws IOException
    {
        String fileLine; // A single line in the file.
        ArrayList <String> fileContents = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

        // Reads the file into a string, tags can be extracted from here.
        while((fileLine = bufferedReader.readLine()) != null)
        {
            fileContents.add(fileLine);
        }
        bufferedReader.close();

        return fileContents;
    }
}
