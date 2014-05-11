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
        long sCount = 0;
        int[] counts = new int[maxServers];
        int server;
        long lru;
        String lruKey = null;
        while (scanner.hasNextLine() && scanner.hasNext()) {
			line = scanner.nextLine();
			server = (int)(sCount % maxServers);
			if (map.get(line) == null) {
				
				if(counts[server] >= cacheSize){
					evictions++;
					lru = time;
					List<SimpleHashMap.Entry<String, WebPage>> entries = map.entries();
					for(SimpleHashMap.Entry<String, WebPage> e: entries){
						if(e.getValue().getIP() == server && e.getValue().getTime() < lru){
							lru = e.getValue().getTime();
							lruKey = e.getKey();
						}
					}
					map.remove(lruKey);
					System.out.println("Eviction made");
				}
				
				map.put(line, new WebPage(time, server));
				counts[server]++;
				sCount++;
			} else {
				server = map.get(line).getIP();
				map.put(line, new WebPage(time, server));
				counts[server]++;
			}
			time++;
			System.out.println(line + " " + time);
        }
        scanner.close();
        
        //TODO: Output the number of requests routed to each server
        for(int i = 0; i< counts.length;i++){
        	System.out.println("192.168.0." + i +": "+ counts[i]);
        }
        //TODO: Output the total number of evictions
        System.out.println("Evictions: " + evictions);
    }
    
    

}
