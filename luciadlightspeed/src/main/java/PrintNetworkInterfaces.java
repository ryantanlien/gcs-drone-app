import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Class used to print out all IP addresses taken into account by the license checking code
 */
public class PrintNetworkInterfaces {
    // DSTA Laptop error: com.luciad.util.TLcdLicenseError:
    //      Your LuciadLightspeed license is not valid for this host, sorry (IP = 10.255.253.156)
    public static void main(String[] args) throws SocketException {
        // Loop over all network interfaces.
        Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface network_interface = (NetworkInterface)interfaces.nextElement();
            System.out.println("Interface: " + network_interface.getName());
            // Loop over all IP addresses of this interface.
            Enumeration<InetAddress> addresses = network_interface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress inetAddress = addresses.nextElement();
                System.out.println("  " + inetAddress.getHostAddress() + " (Length: " + inetAddress.getAddress().length  + ")");
            }
        }
    }
}
