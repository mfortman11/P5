import java.io.*;
import java.util.*;


public class LoadBalancerMain
{

    public static void main(String[] args) throws IOException
    {
		if(args.length != 3 && args.length != 4){
			System.out.println("Invalid number of command line arguements!");
			System.exit(0);
		}
    	
    	Scanner scanner = null;
        //TODO: Check the number of arguments
        
        
        int maxServers = Integer.parseInt(args[0]);
        int cacheSize = Integer.parseInt(args[1]);
				//TODO: Make sure this file exists before trying to open it.
        try{
        	scanner = new Scanner(new File(args[2]));
        }catch (FileNotFoundException e){
        	System.out.println("File not found");
        	System.exit(0);
        }
        boolean isVerbose = (args.length == 4) && (args[3].equals("-v"));
        
        String line;
        SimpleHashMap<String, WebPage> map = new SimpleHashMap<String, WebPage>();
        int evictions =0;
        long time = 0;
        int[] counts = new int[maxServers];
        
        while (scanner.hasNextLine() && scanner.hasNext()) {
			line = scanner.nextLine();				
			if(map.put(line, new WebPage(time, (int)(time % maxServers))) != null)
				evictions++;
			if(counts[(int)(time % maxServers)]++ > cacheSize){
				//search through hashtable for ones in that server and find last accessed
			}
			time++;			
        }
        scanner.close();
        
        //TODO: Output the number of requests routed to each server
        //TODO: Output the total number of evictions
    }
    
    

}
