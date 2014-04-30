import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;


public class LoadBalancerMain
{

    public static void main(String[] args) throws IOException
    {
				Scanner scanner = null;
        //TODO: Check the number of arguments
        
        
        int maxServers = Integer.parseInt(args[0]);
        int cacheSize = Integer.parseInt(args[1]);
				//TODO: Make sure this file exists before trying to open it.
        scanner = new Scanner(new File(args[2]));
        boolean isVerbose = (args.length == 4) && (args[3].equals("-v"));
        
        String line;
        SimpleHashMap<K, V> map = new SimpleHashMap<K, V>(); //TODO: Set the types for the HashMap
        
        
        while (scanner.hasNextLine()) {
						line = scanner.nextLine();
            //TODO: Handle each page request
        }
        scanner.close();
        
        //TODO: Output the number of requests routed to each server
        //TODO: Output the total number of evictions
    }
    
    

}
