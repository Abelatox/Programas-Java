import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Internet{
	public static void main(String[] args){
		File interfaces = new File("/etc/network/interfaces");
		File resolv = new File("/etc/resolv.conf");
		BufferedWriter interfacesW = null;
		BufferedWriter resolvW = null;
		try {
			if(interfaces.exists() && resolv.exists())
			{
				interfacesW = new BufferedWriter(new FileWriter(interfaces));
				resolvW = new BufferedWriter(new FileWriter(resolv,true));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner sc = new Scanner(System.in);
		System.out.println("1- Insti\n2- Casa");
		int option = sc.nextInt();
		switch(option){
		case 1:
			try {
				interfacesW.write("auto enp7s0"+ System.getProperty("line.separator"));
				interfacesW.write("iface enp7s0 inet static"+ System.getProperty("line.separator"));
				interfacesW.write("address 192.168.2.215"+ System.getProperty("line.separator"));
				interfacesW.write("netmask 255.255.255.0"+ System.getProperty("line.separator"));
				interfacesW.write("gateway 192.168.0.1"+ System.getProperty("line.separator"));
				interfacesW.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case 2:
			try {
				interfacesW.write("auto enp7s0"+ System.getProperty("line.separator"));
				interfacesW.write("iface enp7s0 inet dhcp"+ System.getProperty("line.separator"));
				interfacesW.write("#address 192.168.2.215"+ System.getProperty("line.separator"));
				interfacesW.write("#netmask 255.255.255.0"+ System.getProperty("line.separator"));
				interfacesW.write("#gateway 192.168.0.1"+ System.getProperty("line.separator"));
				//interfacesW.write("dns-nameservers 8.8.8.8"+ System.getProperty("line.separator"));
				interfacesW.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
		try {
			Runtime.getRuntime().exec("/etc/init.d/networking restart");
			interfacesW.close();
			//Runtime.getRuntime().exec("echo nameserver 8.8.8.8 > "+resolv.getAbsolutePath());
			/*resolvW.write("nameserver 8.8.8.8");
			resolvW.flush();
			*/
			resolvW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}