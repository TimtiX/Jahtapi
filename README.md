**Java HTTP API (Jahtapi)**  

A simple to use utility API written in Java. It provides simple server functionality and has the main purpose of acting as an HTTP server interface. This projects goal should be to make light Java HTTP servers as easy to make as possible. The classes might also be used to created HTTP requests. Note that this is in early development and is yet not fully functional, but can already server as a basic framework.

---

**Usage**  

To use this API simply use a .jar built (or build it yourself) and import that in your project. How to import the .jar file is depending on your IDE.

Creating a TCP Server is a easy as creating a class instance and passing it a port and a listener object. Example:
    
```java
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
```
In a similar way it is possible to create HTTP Servers:

```Java
import de.jahtapi.http.*;

public class HttpExample implements HttpServerListener {
    
    //The response to send to clients
    private static final String RESPONSE = ""
            + "<!DOCTYPE html>\n"
            + "<html>\n"
            + "    <head>\n"
            + "        <title>Test Response</title>\n"
            + "    </head>\n"
            + "    <body>\n"
            + "        <p>Hello, this is a test response!</p>\n"
            + "    </body>"
            + "</html>";
    
    public HttpExample() {
        new HttpServer(8800, this); // Create a server instance on port 8800
    }
    
    @Override
    public void onClientConnect(HttpClient client) {
        System.out.println("Connect"); // Log that a client connected
    }
    
    @Override
    public void onClientDisconnect(HttpClient client) {
        System.out.println("Disconnect"); // Log that a client disconnected
    }
    
    @Override
    public void onClientRequest(HttpClient client, HttpPacket packet) {
        if(packet instanceof HttpRequest) //Log a possible requested path
            System.out.println("Request: " + ((HttpRequest) packet).getPath());
        
        HttpResponse response = new HttpResponse();
        response.setStatus(HttpStatus.OK.toString());
        response.setContent(RESPONSE);
        client.send(response.toString()); //Respond to the incoming traffic
        client.close(); //Close the Client
    }
    
    public static void main(String[] args) {
        new HttpExample();
    }
}

```

WARNING: There is no timeout for clients yet, so without you explicitly closing them, they will stay open.