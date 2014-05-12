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
        SimpleHashMap.Entry<String, WebPage> lru = null;
        //long lru;
        //String lruKey = null;
        while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			server = (int)(sCount % maxServers);
			if (map.get(line) == null){
				map.put(line, new WebPage(time, server));

				//if server does not have space then evicts page that matches
				//server with smallest access time
				if (counts[server] >= cacheSize){
					//Iterator for list of entries in map
					Iterator<SimpleHashMap.Entry<String,WebPage>> itr = 
							map.entries().iterator();
					//Entry in map that matches with server and has smallest
					//Access Time
					SimpleHashMap.Entry<String, WebPage> smallTime = null;
					//List of entries that match server
					List<SimpleHashMap.Entry<String, WebPage>> rightServer = 
							new LinkedList
							<SimpleHashMap.Entry<String, WebPage>>();
					
					//Runs through entries list and adds entries with right 
					//server to right Server list
					while(itr.hasNext()){
						SimpleHashMap.Entry<String, WebPage> temp = 
								itr.next();
						if(temp.getValue().getIP() == server){
							rightServer.add(temp);
						}
					}
					Iterator<SimpleHashMap.Entry<String, WebPage>> serverItr = 
							rightServer.iterator();
					smallTime = serverItr.next();
					//Searches list of right servers for entry with smallest
					//access time
					while(serverItr.hasNext()){
						SimpleHashMap.Entry<String, WebPage> temp = 
								serverItr.next();
						if(temp.getValue().getTime() <
								smallTime.getValue().getTime()){
							smallTime = temp;
						}
					}
					//remove from map
//					Scanner stop = new Scanner(System.in);
//					System.out.println("Page to be added: " + map.get(line));
//					System.out.println("Page to be evicted: " + map.get(smallTime));
//					stop.next();
					
					map.remove(smallTime);
					map.put(line, new WebPage (time,server));


					if (isVerbose){
						//Scanner scnr = new Scanner(System.in);
						System.out.println("Page " + smallTime + 
								" has been evicted.");
						//scnr.next();
					}
					evictions++;
				}//end eviction block
				counts[server]++;
				sCount++;

			} else {
				server = map.get(line).getIP();
				map.put(line, new WebPage(time, server));
				counts[server]++;
			}
			time++;
			//System.out.println(line + " " + time);
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
