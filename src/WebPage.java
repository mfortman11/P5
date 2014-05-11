///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  LoadBalancerMain.java
// File:             WebPage.java
// Semester:         CS367 Spring 2014
//
// Author:           Michael Darling mddarling@wisc.edu
// CS Login:         mdarling
// Lecturer's Name:  Jim Skrentny
// Lab Section:      
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//                   CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//                   If allowed, learn what PAIR-PROGRAMMING IS, 
//                   choose a partner wisely, and complete this section.
//
// Pair Partner:     Mike Fortman
// CS Login:         mfortman
// Lecturer's Name:  Jim Skrentny
// Lab Section:      
//
//                   STUDENTS WHO GET HELP FROM ANYONE OTHER THAN THEIR PARTNER
// Credits:          
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * This class stores data related to web pages loaded onto servers.
 *
 * <p>Bugs: 
 *
 * @author Michael Darling
 */
public class WebPage {
	
	// The time at which this page was requested (relative to other pages).
	private long time;

	// The last digit of the IP address of the server on which this page 
	// is stored.
	private int ip; 

	/**
 	* Constructs a new WebPage object.
 	*
 	* @param time The time at which this page was constructed.
 	* @param ip The IP address of the server on which the page is stored.
 	* @return The webpage object.
 	*/
	public WebPage(long time, int ip){
		this.time = time;
		this.ip = ip;
	}
	
	/**
 	* Returns the IP of the server.
 	*
 	* @return the IP address of the page’s server.
 	*/
	public int getIP(){
		return ip;
	}

	/**
 	* Gets the time at which the page was made.
 	*
 	* @ return The creation time of the web page.
 	*/
	public long getTime(){
		return time;
	}
}
