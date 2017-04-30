import java.io.*;
public class Response {
	private static final int BUFFER_SIZE = 1024;
	Request request;
	OutputStream output;
	
	public Response(OutputStream output){
		this.output = output;
	}
	
	public void setRequest(Request request){
		this.request = request;
	}
	
	public void sendStaticResource() throws IOException{
		byte[] bytes=new byte[BUFFER_SIZE];  
	    FileInputStream fis=null;
	    try{
	    	File file = new File(Server.WEB_ROOT,request.getUri());
	    	if(file.exists()){
	    		fis = new FileInputStream(file);
	    		int ch = fis.read(bytes, 0, BUFFER_SIZE);
	    		while(ch != -1){
	    			output.write(bytes, 0, BUFFER_SIZE);
	    			ch = fis.read(bytes, 0, BUFFER_SIZE);
	    		}
	    	}else{
	    		//没找到服务器内对应的文件
	    		String errorMessage="HTTP/1.1 404 File Not Found\r\n"+
	    		"Content-Type:text/html\r\n"+  
	    	    "Content-Length:22\r\n"+  
	    	    "\r\n"+  
	    	    "<h1>File Not Find</h1>";  
	    	    output.write(errorMessage.getBytes());  
	    	}
	    }catch(Exception e){
	    	System.out.println(e.toString());
	    }finally{
	    	if(fis != null){
	    		fis.close();
	    	}
	    }
	}
}
