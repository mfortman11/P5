
public class WebPage {
	
	private long time;
	private int ip; 

	public WebPage(long time, int ip){
		this.time = time;
		this.ip = ip;
	}
	
	public int getIP(){
		return ip;
	}
	public long getTime(){
		return time;
	}
}
