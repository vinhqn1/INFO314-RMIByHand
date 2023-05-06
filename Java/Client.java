import java.io.*;
import java.net.*;

public class Client {

    /**
     * This method name and parameters must remain as-is
     */
    public static int add(int lhs, int rhs) {
        try {
            Socket socket = new Socket("localhost", PORT);
            // create an instance of the RemoteMethod
            RemoteMethod add = new RemoteMethod("add", new Object[] {lhs, rhs});
            // ObjectOutputStream to serialize add instance
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(add);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Object response = ois.readObject();
            return ((Integer)response).intValue();
        } catch(ConnectException e) {
            System.err.println("Could not connect to server");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    /**
     * This method name and parameters must remain as-is
     * @throws Exception
     */
    public static int divide(int num, int denom) {
        try {
            Socket socket = new Socket("localhost", PORT);
            // create an instance of the RemoteMethod
            RemoteMethod divide = new RemoteMethod("divide", new Object[] {num, denom});
            // ObjectOutputStream to serialize add instance
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(divide);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Object response = ois.readObject();
            
            if (response instanceof Exception) {
                throw (ArithmeticException) response;
            }
            return ((Integer)response).intValue();
        } catch(ConnectException e) {
            System.err.println("Could not connect to server");
        } catch(ArithmeticException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    /**
     * This method name and parameters must remain as-is
     */
    public static String echo(String message) {
        try {
            Socket socket = new Socket("localhost", PORT);
            // create an instance of the RemoteMethod
            RemoteMethod echo = new RemoteMethod("echo", new Object[] {message});
            // ObjectOutputStream to serialize add instance
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(echo);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Object response = ois.readObject();
            return response.toString();
        } catch(ConnectException e) {
            System.err.println("Could not connect to server");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // Do not modify any code below this line
    // --------------------------------------
    String server = "localhost";
    public static final int PORT = 10314;

    public static void main(String... args) {
        // All of the code below this line must be uncommented
        // to be successfully graded.
        System.out.print("Testing... ");

        if (add(2, 4) == 6)
            System.out.print(".");
        else
            System.out.print("X");

        try {
            divide(1, 0);
            System.out.print("X");
        }
        catch (ArithmeticException x) {
            System.out.print(".");
        }
        
        // if (echo("Hello").equals("You said Hello!"))
        // alternative line that works.
        if (echo("Hello") == "You said Hello!")
            System.out.print(".");
        else
            System.out.print("X");
        
        System.out.println(" Finished");
    }
}