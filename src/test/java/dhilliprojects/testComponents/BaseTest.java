package dhilliprojects.testComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import dhilliprojects.pageobjects.LandingPage;

public class BaseTest {

	public WebDriver driver;
	public LandingPage lp;
	
	public WebDriver initializeDriver() throws IOException
	{
		//Properties class
		Properties prop = new Properties();
		
		//'System.getProperty("user.dir")' contains the local system path till src/main/java/dhilliproject...... 
		//To avoid hardcoding. Local path cannot be given when we submit our project.

		
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\dhilliprojects\\resources\\globalData.properties");
		prop.load(fis);//as 'load' method in Properties class(which comes from java.util package) is requiring the argument as InputStream
		//(Check whta eclipse is recommending by when using load method), we Converted the globalData.properties file into INputStream
		
		String browserName = prop.getProperty("browser");
		
		if(browserName.equalsIgnoreCase("chrome"))
		{
			//Chrome
		driver = new ChromeDriver();
		
		}
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			//Firefox
		driver = new FirefoxDriver();
		
		}
		else if(browserName.equalsIgnoreCase("edge"))
		{
			//Edge
		driver = new EdgeDriver();
		
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}
	
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException
	{
		driver = initializeDriver();
		lp = new LandingPage(driver);
		lp.goTo();
		return lp;
	}
	
	//To read Test Data in Json file
	public List<HashMap<String, String>> getJsonDataToMap(String fileName) throws IOException
	{
		//Read Json file
		String jsonContent = FileUtils.readFileToString(new File(fileName), StandardCharsets.UTF_8);
		
		//Convert String data (which is in json) to HashMap and storing in List
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>(){});
		
		return data;
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown()
	{
		driver.quit();
	}
}
