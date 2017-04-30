import java.io.*;
import java.net.*;
public class Server {
	
	//����Ŀ¼webroot
	public static final String WEB_ROOT = System.getProperty("user.dir")+File.separator+"webroot";
	//ֹͣ����������
	private static final String SHUTDOWN_COMMAND="/SHUTDOWN";
	
	//�ȴ���������
	public void await(){
		ServerSocket serversocket = null;
		int port = 8080;
		try{
			 serversocket=new ServerSocket(port,1,InetAddress.getByName("127.0.0.1"));  
		}catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		while(true){
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			try{
				socket = serversocket.accept();
				input = socket.getInputStream();
				output = socket.getOutputStream();
				//�������Ĵ�����ȡuri
				Request request = new Request(input);
				request.parse();
				
				//����Ƿ�رշ���
				if(request.getUri().equals(SHUTDOWN_COMMAND)){
					break;
				}
				
				Response response = new Response(output);
				response.setRequest(request);
				response.sendStaticResource();
				
				socket.close();
				
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
		}
	}
	
	
}
