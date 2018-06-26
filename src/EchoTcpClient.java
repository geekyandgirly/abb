import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoTcpClient {
    private static PrintWriter out;
    private static BufferedReader in;

    public static void main(String[] args) throws IOException {

        try{
            Socket socket = new Socket("kq6py", 4321);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: kq6py");
            System.exit(1);
        } catch  (IOException e) {
            System.out.println("No I/O");
            System.exit(1);
        }

        out.println("START blah");
        String res = in.readLine();
    }
}
