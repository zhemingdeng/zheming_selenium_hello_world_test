package com.coveros.selenium_hello_world;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class SeleniumMain {

	public static void main(String[] args) throws MalformedURLException {
		String fileName="";
		String DNS="";
		String appVersion="";
		if (args[0]!=null && args[1]!=null){
			fileName=args[0];
			appVersion=args[1];
		}else{
			System.out.println("Missing file name! [filename] [hello_world_app_version]");
			System.exit(1);
		}
		try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);
            
            String line;
            

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                if (line.contains("Public DNS Name")){
                	DNS=line.substring(17);
                }
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
		DesiredCapabilities capability = DesiredCapabilities.firefox();
		WebDriver driver = new RemoteWebDriver(new URL("http://172.31.2.25:4444/wd/hub"), capability);
		
		// And now use this to visit the app
        driver.get("http://" +DNS+":8080/hello-world-"+ appVersion + "/");

        // Find the text input element by its name
        WebElement element = driver.findElement(By.tagName("h2"));
        String result = element.getText();
        if (result.contains("Hello World! The even number is:")){
        	System.out.println("It's working!");
        	System.out.println(result);
        	System.exit(0);
        }else{
        	System.out.println("It's not working!");
        	System.out.println(result);
        	System.exit(1);
        }
	
	}

}


