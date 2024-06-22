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
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class emailAnalysis {
	public static void main(String[] arg) throws Exception {
		try{
		Scanner input =new Scanner(System.in);
		List<String> results = new ArrayList<String>();
		
		File[] files = new File(System.getProperty("user.dir")+"/Data/").listFiles();
		for (File file : files) {
		    if (file.isFile()) {
		        results.add(file.getName());
		    }
		}
		int chValue=0;
		while(chValue <= 5){
			System.out.println();
			System.out.println("+++++++++++++++++++++++++++++++++++++");
			System.out.println("1 ) Word Search");
			System.out.println("2 ) Find Contacts");
			System.out.println("3 ) Track an Email Message");
			System.out.println("4 ) Find IP location");
			System.out.println("5 ) Exit Programe");
			System.out.println();
			System.out.println("Your Choice ?");
			chValue=input.nextInt();
			switch(chValue){
			case 1:
				System.out.println("Please Enter An Word:");
				String search= input.next();
				for(int j=0;j<results.size();j++){
				try {
					String myFileDataInfo="";
					String file= results.get(j);
					File fileName = new File(System.getProperty("user.dir")+"/Data/"+file);
					Scanner sc = new Scanner(fileName);
					while(sc.hasNext()){
						String rowEmail = sc.nextLine();
			            if(!rowEmail.isEmpty()){
			            	myFileDataInfo=myFileDataInfo+rowEmail;
			            }
					}
					if(myFileDataInfo.contains(search)){
						System.out.println(search+ " --> Contains in file "+file);
					}
					myFileDataInfo="";
				}catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			break;
			case 2:
				System.out.println();
				System.out.println("Please Enter An Email :");
				String mySearchEmail= input.next();
				for(int i=0;i<results.size();i++){
				try {
					String myFileDataInfo="";
					String filename= results.get(i);
					File file = new File(System.getProperty("user.dir")+"/Data/"+filename);
					Scanner sc = new Scanner(file);
					boolean isMatching=false;
					while(sc.hasNext()){
						String rowEmail = sc.nextLine();
			            if(!rowEmail.isEmpty()){
			            	if(rowEmail.contains("To:") || rowEmail.contains("From:") ) {
			            		String[] rowEmailData = rowEmail.split("\\s+");
			            		for(int d=0;d<rowEmailData.length;d++){
			            			if(rowEmailData[d].equals(mySearchEmail) || rowEmailData[d].equals("<"+mySearchEmail+">")){
			            				isMatching=true;
			            			}
			            		}
		            			if(rowEmail.contains("To:") && rowEmail.substring(0,3).equals("To:") && isMatching){
		            				System.out.println(rowEmail+" in the file "+filename);
		            				isMatching=true;
	            				}
								if(rowEmail.length()>=5 && rowEmail.substring(0,5).equals("From:") && isMatching){
									System.out.println(rowEmail+" in the file "+filename);
									isMatching=false;
								}
								System.out.println("---------------------------------------");
			            	}
			            }
					}
				}catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			break;
			case 3:
				System.out.println("Your Selected Option is "+chValue);
				try{
					System.out.println("Please Enter a file :");
					System.out.println("For Example : TEST_00001.eml ( [ TEST_00001.eml ] To  [ TEST_00999.eml ] )");
					String searchFileName= input.next();
					boolean isSearchVal=false;
					for(int t=0;t<results.size();t++){
						if(results.get(t).equals(searchFileName)){
							isSearchVal=true;
						}
					}
					if(isSearchVal){
					try {
						String myFileDataInfo="";
						String fname= searchFileName;
						System.out.println("File name is "+fname);
						System.out.println("****************************************************");
						File fileName = new File(System.getProperty("user.dir")+"/Data/"+fname);
						Scanner sc = new Scanner(fileName);
						boolean match=false;
						String rawData="";
						while(sc.hasNext()){
							String rowEmail = sc.nextLine();
							myFileDataInfo = myFileDataInfo+rowEmail;
							if(rowEmail.length() >=5  && rowEmail.substring(0,5).equals("Date:")){
								rawData=rowEmail;
								System.out.println(rowEmail);
							}
							if(rowEmail.length() >=9 && rowEmail.substring(0,9).equals("Received:")){
								System.out.println(rowEmail);
								match=true;
							}
							if(match){
								System.out.println(rowEmail);
							}
							if(rowEmail.contains(")") && rowEmail.contains(";") && rowEmail.contains("(") && match ){
								System.out.println(rowEmail);
								match=false;
							}
						}
						System.out.println("**************************");
						}catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}else{
						System.out.println("Invalid File.Try Again :)");
					}
					}catch(Exception e){
						e.printStackTrace();
				}
			break;
			case 4:
				Scanner sc=new Scanner(System.in);
				System.out.println();
				try{
					System.out.println("Please Enter IP Address:");
					String myIP= sc.next();
					if(isValidIPTest(myIP)){
						String xmlData = "http://freegeoip.net/xml/"+myIP;
						String geoLocationInfo = getHTMLInformation(xmlData);
						java.io.FileWriter fw = new java.io.FileWriter("geoInfo.xml");
					    fw.write(geoLocationInfo);
					    fw.close();
						File file = new File(System.getProperty("user.dir")+"/geoInfo.xml");
						DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
						        .newInstance();
						DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
						Document document = documentBuilder.parse(file);
						String IP = document.getElementsByTagName("IP").item(0).getTextContent();
						String countryName = document.getElementsByTagName("CountryName").item(0).getTextContent();
						String city = document.getElementsByTagName("City").item(0).getTextContent();
						String latitude = document.getElementsByTagName("Latitude").item(0).getTextContent();
						String longitude = document.getElementsByTagName("Latitude").item(0).getTextContent();
						System.out.println();
						System.out.println("IP Address: "+IP);
						System.out.println("CountryName : "+countryName);
						System.out.println("City : "+city);
						System.out.println("Latitude : "+latitude);
						System.out.println("Longitude : "+longitude);
					}else{
						System.out.println("Invalid IP Address : "+myIP);
						System.out.println("Enter Again Vallid IP Address :");
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			case 5:
				System.out.println("Control went out from the programme");
				System.exit(0);
			default:
				System.out.println("Please Check Again");
			}
		}
		System.out.println("Exit Successfully.");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
    public static boolean isValidIPTest(String ip){
    	try{
	        Pattern pattern = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
	        Matcher matcher = pattern.matcher(ip);
	        return matcher.find();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return false;
    }
    public static String getHTMLInformation(String getURL) {
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
}
