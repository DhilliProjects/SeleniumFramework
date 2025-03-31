package dhilliprojects.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import dhilliprojects.abstractUtilities.AbstractComponents;

public class LandingPage extends AbstractComponents {

	WebDriver driver;
	
	public LandingPage(WebDriver driver)
	{
		super(driver); //We must explicitly declare super() in child class to invoke the Parent's Constructor.
		
		//Initialization
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="userEmail")
	WebElement userEmail;
	
	@FindBy(id="userPassword")
	WebElement password;
	
	@FindBy(id="login")
	WebElement login;
		
	@FindBy(css="[class*='flyInOut']")
	WebElement errorMsg;
	
	public ProductCatalogue loginApplication(String email, String passwd)
	{
		userEmail.sendKeys(email);
		password.sendKeys(passwd);
		login.click();
		ProductCatalogue pc = new ProductCatalogue(driver);
		return pc;
	}
	
	public void goTo()
	{
		driver.get("https://rahulshettyacademy.com/client");
	}
	public String getErrorMsg()
	{
		waitForWebElementToAppear(errorMsg);
		return errorMsg.getText();
	}
}
