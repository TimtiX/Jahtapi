**Java HTTP API (Jahtapi)**  

A simple to use utility API written in Java. It provides simple Server functionality and (in the future) will have the main purpose of acting as an HTTP server interface. This projects goal should be to make light Java HTTP servers as easy to make as possible.

---

**Usage**  

To use this API simply use a .jar built (or build it yourself) and import that in your project. How to import the .jar file is different from IDE to IDE.

Creating a TCP Server is a easy as creating a class instance and passing it a port and a listener object. Example:

    import java.net.Socket;
    import de.jahtapi.network.*;
    
    public class TCPExample implements ServerListener {
    
        public TCPExample() {
            new Server(8800, this); //Create a Server instance on port 8800
        }
    
        @Override
        public void onClientConnect(Server server, Socket socket) {
            //Handle the connection here
        }
        
        public static void main(String[] args) {
           new TCPExample();
        }
    }