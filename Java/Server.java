import java.net.*;

import javax.swing.tree.ExpandVetoException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Server {

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(10314);
            Socket socket = null;
            while ((socket = server.accept()) != null) {
                Socket threadSocket = socket;
                new Thread(() -> handleRequest(threadSocket)).start();
            }
            server.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void handleRequest(Socket socket) {
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            RemoteMethod remoteMethod = (RemoteMethod) ois.readObject();
            String methodName = remoteMethod.getMethodName();
            Object[] methodArgs = remoteMethod.getArguments();

            Server server = new Server();
            Method[] methods = server.getClass().getDeclaredMethods();
            Object result = null;
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    try {
                        result = method.invoke(server, methodArgs);
                    } catch (InvocationTargetException ex) {
                        result = ex.getCause();
                    } catch (IllegalAccessException ex) {
                        result = ex;
                    }
                }
            }
            System.out.println(result.toString());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(result);
            socket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // Do not modify any code below tihs line
    // --------------------------------------
    public static String echo(String message) { 
        return "You said " + message + "!";
    }
    public static int add(int lhs, int rhs) {
        return lhs + rhs;
    }
    public static int divide(int num, int denom) {
        if (denom == 0)
            throw new ArithmeticException();

        return num / denom;
    }
}