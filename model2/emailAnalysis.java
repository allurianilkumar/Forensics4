
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
   public static String myHTMLResponse(String getURL) throws Exception {
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
			System.out.println("Hey, Enter Option Number from 1 to 4)");
			System.out.println("==================================");
			System.out.println("1 ) Word Search");
			System.out.println("2 ) Find Contacts");
			System.out.println("3 ) Track an Email Message");
			System.out.println("4 ) Find IP location");
			System.out.println("==================================");
			System.out.println("Your Choice?");
			chval=input.nextInt();
			switch(chval){
			case 1:
				System.out.println("Please Enter A Word:");
				String mySearchWord= input.next();
				for(int j=0;j<results.size();j++){
				try {
					String emailFileData="";
					String file= results.get(j);
					File fileName = new File(System.getProperty("user.dir")+"/Data/"+file);
					Scanner scan = new Scanner(fileName);
					while(scan.hasNext()){
						String rowinfo = scan.nextLine();
			            if(!rowinfo.isEmpty()){
			            	emailFileData=emailFileData+rowinfo;
			            }
					}
					if(emailFileData.contains(mySearchWord)){
						System.out.println(mySearchWord+ " the word matching in this file "+file);
					}
					emailFileData="";
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
					boolean isMatched=false;
					String emailFileData="";
					String filename= results.get(i);
					File file = new File(System.getProperty("user.dir")+"/Data/"+filename);
					Scanner sc = new Scanner(file);
					while(sc.hasNext()){
						String rowinfo = sc.nextLine();
			            if(!rowinfo.isEmpty()){
			            	if(rowinfo.contains("To:") || rowinfo.contains("From:") ){
			            		String[] rowData = rowinfo.split("\\s+");
			            		for(int p=0;p<rowData.length;p++){
			            			if(rowData[p].equals(mySearchEmail) || rowData[p].equals("<"+mySearchEmail+">")){
			            				isMatched=true;
			            			}
			            		}
		            			if(rowinfo.contains("To:") && rowinfo.charAt(0) == 'T' && isMatched){
	            					System.out.println(rowinfo+" =====> in the file "+filename);
	            					isMatched=false;
	            				}
								if( rowinfo.contains("From:") && rowinfo.substring(0,5).equals("From:") && isMatched){
									System.out.println(rowinfo+" =====> in the file "+filename);
									isMatched=true;
								}
			            	}
			            }
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println();
			break;
			case 3:
				try{
					System.out.println("Please Enter file :");
					System.out.println("Ex: TEST_00001.eml ( TEST_00001.eml To TEST_00999.eml)");
					String searchFileName= input.next();
					boolean isSearched=false;
					for(int u=0;u<results.size();u++){
						if(results.get(u).equals(searchFileName)){
							isSearched=true;
						}
					}
					if(isSearched){
					try {
						boolean isMatching=false;
						String emailFileData="";
						System.out.println("File name is "+searchFileName);
						System.out.println();
						File fileName = new File(System.getProperty("user.dir")+"/Data/"+searchFileName);
						Scanner sc = new Scanner(fileName);
						String myDate="";
						while(sc.hasNext()){
							String rowinfo = sc.nextLine();
							emailFileData = emailFileData+rowinfo;
							if(rowinfo.contains("Date:") && rowinfo.substring(0,5).equals("Date:")){
								myDate=rowinfo;
								System.out.println(rowinfo);
							}
							if(rowinfo.contains("Received:") && rowinfo.substring(0,9).equals("Received:")){
								System.out.println(rowinfo);
								isMatching=true;
							}
							if(isMatching){
								System.out.println(rowinfo);
							}
							if(rowinfo.contains(";") && rowinfo.contains("(") && rowinfo.contains(")") && isMatching ){
								System.out.println(rowinfo);
								isMatching=false;
							}
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
					}else{
						System.out.println("File not vallid file!!!, So Please enter the vallid file.");
					}
					}catch(Exception e){
						e.printStackTrace();
					}
				break;
			case 4:
				Scanner sInput=new Scanner(System.in);
				System.out.println();
				try{
					System.out.println("Please Enter IP Address:");
					String myIpAddress= sInput.next();
					if(isValidIPInfo(myIpAddress)){
						String jsonData = "http://freegeoip.net/json/"+myIpAddress;
						jsonData = myHTMLResponse(jsonData);
						jsonData =jsonData.substring(1,jsonData.length()-1);
						String[] geoLocationList = jsonData.split(",");
						System.out.println();
						System.out.println("GEO Location :");
						for(int k=0;k<geoLocationList.length;k++){
							System.out.println(geoLocationList[k]);
						}
					}else{
						System.out.println("Invalid IP Address : "+myIpAddress);
						System.out.println("Vallid IP Address Please...");
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			case 5:
				System.out.println("Programe Exited");
				System.exit(0);
			default:
				System.out.println("Entered Worng values.");
			}
		}
		System.out.println("Exit the programme");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
    public static boolean isValidIPInfo(String ipAddress){
    	try{
	        Pattern patt = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
	        Matcher matcher = patt.matcher(ipAddress);
	        return matcher.find();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return false;
    }
}
