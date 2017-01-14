/**
 * 
 */
package server;

public interface SocketServerInterface {
	boolean openConnection(); 
    void handleSession();	
    void closeSession();	
}
