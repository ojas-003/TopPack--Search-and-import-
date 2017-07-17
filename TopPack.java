import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TopPack {



	public static void search(String key) 
	{
		URL url;
	    InputStream is = null;
	    BufferedReader br;
	    String line;

	    try {
	    	BufferedWriter Repository_Id = new BufferedWriter(new FileWriter("D:/Repositories_Id.txt"));
	    	for(int l=0;l<5;l++) {
	        url = new URL("https://api.github.com/search/repositories?q=%22"+key+"&page="+l+"per_page=50");
	        is = url.openStream();
	        br = new BufferedReader(new InputStreamReader(is));
	        BufferedWriter out = new BufferedWriter(new FileWriter("D:/output.txt"));
            int Total_count =0;
	        while ((line = br.readLine()) != null) {
	            out.write(line);
	            StringTokenizer st = new StringTokenizer(line);
	            st.nextToken(":");
	            String s = st.nextToken(",").toString();
	            s = s.substring(1,s.length());
	            System.out.println(s);
	            Total_count = Integer.parseInt(s);
	            System.out.println("Total_Count= "+Total_count);
	        	StringTokenizer st1 = new StringTokenizer(line,":,\"");
	        	int counter =0;
	        	int c =0;
	        	//int a=1;
	        	while(st1.hasMoreTokens()) {
	        		String s1 =st1.nextToken().toString();
	        		
	        		if(s1.equals("id"))
	        		{
	        			if(counter%2==0) 
	        			{
	        				String Id="";
	        				Id = st1.nextToken(":,\"").toString();
	        		     	System.out.println("id :- "+Id);
	        		     	Repository_Id.write(Id);
	        		     	Repository_Id.newLine();
	        		    	c++;
	        			}
	        			counter++;	
	        		}
	        		
	        		if(s1.equals("name"))
	        		{
	        			
	        			System.out.println("Repository_Name:- "+st1.nextToken(":,\""));
	        			c++;
	        		}
	        		
	        		if(s1.equals("login"))
	        		{
	        			
	        			System.out.println("Owner :- "+st1.nextToken(":,\""));
	        			c++;
	        		}
	        		
	        		if(s1.equals("forks_count"))
	        		{
	        			System.out.println("Forks_Count :- "+st1.nextToken(":,\""));
	        			c++;
	        		}
	        		
	        		if(s1.equals("stargazers_count"))
	        		{
	        			System.out.println("Stargazers_count :- "+st1.nextToken(":,\""));
	        			c++;
	        		}
	        		if(c==5)
	        		{
	        			System.out.println("");
	        			System.out.println("");
	        			c=0;
	        		}
	        		
	        	}
	        }
	        out.close();
	             
	    
	    }
	    	Repository_Id.close();
	    }catch (MalformedURLException mue) {
	         mue.printStackTrace();
	    } catch (IOException ioe) {
	         ioe.printStackTrace();
	    } finally {
	        try {
	            if (is != null) is.close();
	        } catch (IOException ioe) {

	        }
	    }
	}

	

public static void Import(String repository_id)
		{
			URL url;
		    InputStream is = null;
		    BufferedReader br;
		    String line;
		    String user ="";
	        String repository_name="";
		    try {
		    	
		        url = new URL("https://api.github.com/repositories/"+repository_id);
		        is = url.openStream();  // throws an IOException
		        br = new BufferedReader(new InputStreamReader(is));
		        while ((line = br.readLine()) != null) {
		        	StringTokenizer st1 = new StringTokenizer(line,":,\"");
		        	while(st1.hasMoreTokens()) {
		        		String s1 =st1.nextToken().toString();
		        		
		        		
		        		if(s1.equals("name"))
		        		{
		        			repository_name = st1.nextToken(":,\"").toString();
		        			System.out.println("Repository_Name:- "+repository_name);
		        			
		        		}
		        		
		        		if(s1.equals("login"))
		        		{
		        			user = st1.nextToken(":,\"").toString();
		        			System.out.println("Owner :- "+user);
		        		}
		        	}
		        }
		        
		    br.close();
		    } catch (MalformedURLException mue) {
		         mue.printStackTrace();
		    } catch (IOException ioe) {
		         ioe.printStackTrace();
		    } finally {
		        try {
		            if (is != null) is.close();
		        } catch (IOException ioe) {
		        }
		    }
		    
		    String basic_url = "https://api.github.com/repos/"+user+"/"+repository_name+"/contents";
		    Inside_Importer(basic_url);
		    

		}
	    public static void Inside_Importer(String basic_url) { 
	    	    URL url,url1;
		        InputStream is = null, is1=null;
		        BufferedReader br=null ,br1 = null;
		        String line, line1;
		   
		        
	     try {
	    	    
		        url = new URL(basic_url);
		        is = url.openStream();
		        br = new BufferedReader(new InputStreamReader(is));
		        String contents="", download_url="";
		        BufferedWriter pack = new BufferedWriter(new FileWriter("D:/packages.txt"));
		        while ((line = br.readLine()) != null) {
		        	StringTokenizer st1 = new StringTokenizer(line,":,\"");
		        	//int tab_count=1;
		        	while(st1.hasMoreTokens()) {
		        		
		        		String s1 =st1.nextToken().toString();
		        		if(s1.equals("name"))
		        		{
		        			contents = st1.nextToken(":,\"").toString();
		        			if(contents.equals("package.json")) 
		        			{
		        				System.out.println("Eureka I have found it");
		        				while(!download_url.equals("download_url")) 
		        				{
		        					//System.out.println(download_url);
		        				    download_url = st1.nextToken(":,\"").toString();
		        				}
		        				download_url = st1.nextToken("\" ").toString();
		        				download_url = st1.nextToken("\" ").toString();
		        				
		        			    
		        			    try {
		        			    	
		        			        url1 = new URL(download_url);
		        			        is1 = url1.openStream();  // throws an IOException
		        			        br1 = new BufferedReader(new InputStreamReader(is1));
		        			        boolean flag=false;
		        			        String se="";
		        			        
		        			        while ((line1 = br1.readLine()) != null) 
		        			        {
		        			        	String s2,packages;
		        			        	StringTokenizer st2 = new StringTokenizer(line1,":,\" ");
		        			        	if(flag) 
		        			        	{
		        			        		se = st2.nextToken(":,\" ").toString();
		        			        		if(se.equals("}"))
		        			        		{
		        			        			flag=false;
		        			        		}
		        			        		else 
		        			        		{
		        			        		    System.out.print(se+", ");
		        			        		    pack.write(se);
		        			        		    pack.newLine();
		        			        		    
		        			        		    
		        			        		}
		        			        	}
		        			        	while(st2.hasMoreTokens()) {
		        			        		s2 =st2.nextToken(":,\" ").toString();
		        			        		if(s2.equals("dependencies") || s2.equals("devDependencies")) 
		        			        		{
		        			        			flag =true;
		        			        		}
		        			        			
		        			        	}
		        			        
		        			        }
		        			    }
		        			    catch (MalformedURLException mue) {
		        			         mue.printStackTrace();
		        			    } catch (IOException ioe) {
		        			         ioe.printStackTrace();
		        			    } finally {
		        			        try {
		        			            if (is1 != null) is1.close();
		        			        } catch (IOException ioe) {
		        			        }
		        			        br1.close();
		        			        is1.close();
		        			    }
		        			}
		        			
		        		}
		        	}
		        }
		        pack.close();
		        
		    
		    } catch (MalformedURLException mue) {
		         mue.printStackTrace();
		    } catch (IOException ioe) {
		         ioe.printStackTrace();
		    } finally {
		        try {
		        	br.close();
					if (is != null) is.close();
		        } catch (IOException ioe) {
		            // nothing to see here
		        }
		        
		    }
	    }
		public static void main(String[] args) {
			Scanner sc=new Scanner(System.in);  
			 String search_keyword = sc.next();
			 search(search_keyword);
			 Import(search_keyword);
			 sc.close();
		}





		
