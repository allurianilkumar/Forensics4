import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class emailAnalysis {
	public static void wouldSearch(List<String>  totalFileNames,Scanner s){
		System.out.println("Please Enter A Word:");
		String mySearchWord= s.next();
		for(int j=0;j<totalFileNames.size();j++){
			try {
				String fileData="";
				String file= totalFileNames.get(j);
				File fileName = new File(System.getProperty("user.dir")+"/Data/"+file);
				Scanner scanning = new Scanner(fileName);
				while(scanning.hasNext()){
					String rowline = scanning.nextLine();
		            if(!rowline.isEmpty()){
		            	fileData=fileData+rowline;
		            }
				}
				if(fileData.contains(mySearchWord)){
					System.out.println(mySearchWord+ " Search Word in this file "+file);
				}
				fileData="";
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}	
	}
	public static void findContacts(List<String> totalFileNames,Scanner s){
		System.out.println("Please Enter A Email :");
		String mySearchEmail= s.next();
		System.out.println("===============================================");
		for(int j=0;j<totalFileNames.size();j++){
			try {
				String fileData="";
				String file= totalFileNames.get(j);
				File fileName = new File(System.getProperty("user.dir")+"/Data/"+file);
				Scanner scanning = new Scanner(fileName);
				boolean matchFlag=false;
				while(scanning.hasNext()){
					String rowline = scanning.nextLine();
		            if(!rowline.isEmpty()){
		            	if(rowline.contains("To:") || rowline.contains("From:") ){
		            		String[] rowData = rowline.split("\\s+");
		            		for(int p=0;p<rowData.length;p++){
		            			if(rowData[p].equals(mySearchEmail) || rowData[p].equals("<"+mySearchEmail+">")){
		            				matchFlag=true;
		            			}
		            		}
	            			if(rowline.contains("To:") && rowline.substring(0,3).equals("To:") && matchFlag){
            					System.out.println(rowline+"   : File Name "+file);
            					matchFlag=false;
            				}
							if(rowline.contains("From:") && rowline.substring(0,5).equals("From:") && matchFlag){
								System.out.println(rowline+"   : File Name "+file);
								matchFlag=true;
							}
		            	}
		            }
				}
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	public static void trackEmailMessage(List<String> totalFileNames,Scanner s){
		try{
			System.out.println("Please Enter File : Ex:TEST_00000.eml To TEST_00999.eml");
			String mySearchingFile= s.next();
			boolean flag=false;
			for(int u=0;u<totalFileNames.size();u++){
				if(totalFileNames.get(u).equals(mySearchingFile)){
					flag=true;
				}
			}
			if(flag){
				try {
					String fileData="";
					String filename= mySearchingFile;
					System.out.println("===============================================");
					File fileName = new File(System.getProperty("user.dir")+"/Data/"+filename);
					Scanner scanning = new Scanner(fileName);
					boolean matchFlag=false;
					String myDate="";
					while(scanning.hasNext()){
						String rowline = scanning.nextLine();
						fileData = fileData+rowline;
						if(rowline.contains("Date:") && rowline.substring(0,5).equals("Date:")){
							myDate=rowline;
							System.out.println(rowline);
						}
						if(rowline.contains("Received:") && rowline.substring(0,9).equals("Received:")){
							matchFlag=true;
						}
						if(matchFlag){
							System.out.println(rowline);
						}
						if(rowline.contains("(") && rowline.contains(";")&& rowline.contains(")") && matchFlag ){
							System.out.println(rowline);
							matchFlag=false;
						}
					}
					}catch (FileNotFoundException e) {
						e.printStackTrace();
					}
			}else{
				System.out.println("Not vallid file, So Please enter the vallid file.");
			}
		}catch(Exception e){
				e.printStackTrace();
		}
	}
	public static void findIPAddress(Scanner s){
		try{
			System.out.println("Please Enter IP Address:");
			String myIpAddress= s.next();
			if(isValidIPAddress(myIpAddress)){
				String geoLocationInfo = "http://freegeoip.net/json/"+myIpAddress;
				String geoLocation = getHTMLResp(geoLocationInfo);
				String[] geoLocationList = geoLocation.split(",");
				System.out.println();
				System.out.println("The IP Address GEO Location Information as follow.");
				for(int k=0;k<geoLocationList.length;k++){
					System.out.println(geoLocationList[k]);
				}
			}else{
				System.out.println("U entered the invalid IP Address : "+myIpAddress);
				System.out.println("Please Enter the vallid IP Address");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String arg[])throws Exception{
		Scanner s =new Scanner(System.in);
		List<String> totalFileNames = new ArrayList<String>();
		File[] files = new File(System.getProperty("user.dir")+"/Data/").listFiles();
		for (File file : files) {
		    if (file.isFile()) {
		        totalFileNames.add(file.getName());
		    }
		}
		int ch=0;
		while(ch <= 4){
			System.out.println();
			System.out.println("Choose the option you would like to use:");
			System.out.println("1. Word Search");
			System.out.println("2. Find Contacts");
			System.out.println("3. Track an Email Message");
			System.out.println("4. Find IP location");
			System.out.println("5. Exit A Programme");
			System.out.println("Your Choice?");
			ch=s.nextInt();
			if(ch == 1){
				wouldSearch(totalFileNames,s);
			}
			if( ch == 2){
				findContacts(totalFileNames,s);	
			}
			if( ch == 3){
				trackEmailMessage(totalFileNames,s);
			}
			if( ch == 4){
				findIPAddress(s);
			}
			System.out.println();
		}

	}
	public static boolean isValidIPAddress(String ipAddr) {
        Pattern ptn = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
        Matcher mtch = ptn.matcher(ipAddr);
        return mtch.find();
    }
   public static String getHTMLResp(String myUrl) throws Exception {
      StringBuilder result = new StringBuilder();
      URL url = new URL(myUrl);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String line;
      while ((line = rd.readLine()) != null) {
         result.append(line);
      }
      rd.close();
      return result.toString();
   }
}

