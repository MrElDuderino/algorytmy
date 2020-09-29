import java.io.*;

public class ModelWriter
{
    public static void writeModelFile(String tempalteFile, String newFile, String data)
    {
        FileInputStream instream;
        FileOutputStream outstream;

        try
        {
            File infile =new File(tempalteFile);
            File outfile =new File(newFile);

            instream = new FileInputStream(infile);
            outstream = new FileOutputStream(outfile);

            byte[] buffer = new byte[1024];

            int length;
            while ((length = instream.read(buffer)) > 0)
                outstream.write(buffer, 0, length);

            instream.close();
            outstream.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(newFile, true));
            writer.append('\n');
            writer.append(data);
            writer.close();

        }catch(IOException ioe){ ioe.printStackTrace(); }
    }
}
