import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class emailAnalysis {
   public static String getHTMLInfo(String getURL) throws Exception {
	   try{
	      StringBuilder myoutout = new StringBuilder();
	      URL url = new URL(getURL);
	      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	      connection.setRequestMethod("GET");
	      BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	      String line;
	      while ((line = rd.readLine()) != null) {
	         myoutout.append(line);
	      }
	      rd.close();
	      return myoutout.toString();
	   }catch(Exception e){
			   e.printStackTrace();
	   }
	   return "";
   }
   	public static void wordSearch(List<String> results){
		System.out.println("Please Enter A Word:");
		Scanner input=new Scanner(System.in);
		String search= input.next();
		for(int j=0;j<results.size();j++){
			try {
				String myFileDataInfo="";
				String file= results.get(j);
				File fileName = new File(System.getProperty("user.dir")+"/Data/"+file);
				Scanner sc = new Scanner(fileName);
				while(sc.hasNext()){
					String row = sc.nextLine();
		            if(!row.isEmpty()){
		            	myFileDataInfo=myFileDataInfo+row;
		            }
				}
				if(myFileDataInfo.contains(search)){
					System.out.println(search+ " ## word contains in this file "+file);
				}
				myFileDataInfo="";
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
 	}
 	public static void findContacts(List<String> results){
 		System.out.println();
		System.out.println("Please Enter A Email :");
		Scanner input=new Scanner(System.in);
		String mySearchEmail= input.next();
		for(int i=0;i<results.size();i++){
			try {
				String myFileDataInfo="";
				String filename= results.get(i);
				File file = new File(System.getProperty("user.dir")+"/Data/"+filename);
				Scanner sc = new Scanner(file);
				boolean isMatching=false;
				while(sc.hasNext()){
					String row = sc.nextLine();
		            if(!row.isEmpty()){
		            	if(row.contains("To:") || row.contains("From:") ){
		            		String[] rowData = row.split("\\s+");
		            		for(int p=0;p<rowData.length;p++){
		            			if(rowData[p].equals(mySearchEmail) || rowData[p].equals("<"+mySearchEmail+">")){
		            				isMatching=true;
		            			}
		            		}
	            			if(row.contains("To:") && row.charAt(0) == 'T' && isMatching){
	            				System.out.println(row+" ## in the file "+filename);
	            				isMatching=false;
	        				}
							if(row.length()>=5 && row.substring(0,5).equals("From:") && isMatching){
								System.out.println(row+" ## in the file "+filename);
								isMatching=true;
							}
		            	}
		            }
				}
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
 	}
 	public static void trackAnEmailMessage(List<String> results){
 		try{
			System.out.println("Please Enter file :");
			System.out.println("###Ex: TEST_00001.eml ( TEST_00001.eml To TEST_00999.eml)");
			Scanner input=new Scanner(System.in);
			String searchFileName= input.next();
			boolean isSearch=false;
			for(int u=0;u<results.size();u++){
				if(results.get(u).equals(searchFileName)){
					isSearch=true;
				}
			}
			if(isSearch){
			try {
				String myFileDataInfo="";
				String fname= searchFileName;
				System.out.println("File name is "+fname);
				System.out.println("#################################################################");
				File fileName = new File(System.getProperty("user.dir")+"/Data/"+fname);
				Scanner sc = new Scanner(fileName);
				boolean match=false;
				String rawData="";
				while(sc.hasNext()){
					String row = sc.nextLine();
					myFileDataInfo = myFileDataInfo+row;
					if(row.length() >=5  && row.substring(0,5).equals("Date:")){
						rawData=row;
						System.out.println(row);
					}
					if(row.length() >=9 && row.substring(0,9).equals("Received:")){
						System.out.println(row);
						match=true;
					}
					if(match){
						System.out.println(row);
					}
					if(row.contains(")") && row.contains(";") && row.contains("(") && match ){
						System.out.println(row);
						match=false;
					}
				}
				}catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}else{
				System.out.println("File not vallid file.");
			}
			}catch(Exception e){
				e.printStackTrace();
			}
 	}
 	public static void findIPLocation(){
		Scanner input=new Scanner(System.in);
		System.out.println();
		try{
			System.out.println("Please Enter IP Address:");
			String myIpAddress= input.next();
			if(isValidIPInfo(myIpAddress)){
				String jsonData = "http://freegeoip.net/json/"+myIpAddress;
				jsonData = getHTMLInfo(jsonData);
				jsonData =jsonData.substring(1,jsonData.length()-1);
				String[] geoLocationList = jsonData.split(",");
				System.out.println();
				System.out.println("The IP Address GEO Location Information :");
				for(int k=0;k<geoLocationList.length;k++){
					System.out.println(geoLocationList[k]);
				}
			}else{
				System.out.println("Invalid IP Address : "+myIpAddress);
				System.out.println("Enter the vallid IP Address");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
 	}
	public static void main(String arg[])throws Exception{
		try{
		Scanner input =new Scanner(System.in);
		List<String> results = new ArrayList<String>();
		
		File[] files = new File(System.getProperty("user.dir")+"/Data/").listFiles();
		for (File file : files) {
		    if (file.isFile()) {
		        results.add(file.getName());
		    }
		}
		int chval=0;
		while(chval <= 5){
			System.out.println();
			System.out.println("Choose the option you would like to use:");
			System.out.println("#################################################################");
			System.out.println("1# Word Search");
			System.out.println("2# Find Contacts");
			System.out.println("3# Track an Email Message");
			System.out.println("4# Find IP location");
			System.out.println("5# Exit Programe");
			System.out.println("#################################################################");
			System.out.println("Your Choice ?");
			chval=input.nextInt();
			switch(chval){
			case 1:
				wordSearch(results);
				break;
			case 2:
				findContacts(results);
				break;
			case 3:
				trackAnEmailMessage(results);
				break;
			case 4:
				findIPLocation();
				break;
			case 5:
				System.out.println("Exit From Programe");
				System.exit(0);
			default:
				System.out.println("Worng Details.");
			}
		}
		System.out.println("Exit the programme");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
    public static boolean isValidIPInfo(String ip){
    	try{
	        Pattern pattern = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
	        Matcher matcher = pattern.matcher(ip);
	        return matcher.find();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return false;
    }
}
