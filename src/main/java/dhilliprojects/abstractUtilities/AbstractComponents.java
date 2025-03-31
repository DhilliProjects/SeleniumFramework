package dhilliprojects.abstractUtilities;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dhilliprojects.pageobjects.CartPage;

public class AbstractComponents {

	WebDriver driver;
	
	public AbstractComponents(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this); //for Common Header and elements, we are using PageFactory in Utilities as it is common.
	}
	
	
	@FindBy(css="[routerlink*='cart']")
	WebElement cartHeader;
	
	@FindBy(css="[routerlink*='myorders']")
	WebElement orderHeader;
	
	//Using Explicit waits instead of Thread.sleep() to wait until loading and product adding to cart completes
	public void waitForElementToAppear(By findBy) //using 'By Locator' 
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		//Note: WebElement is when it starts with driver.findElement.....
		//But, here it is not WebElement. It is a 'By Locator'
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy)); 
	}
	
	public void waitForWebElementToAppear(WebElement we) //using 'By Locator' 
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(we)); 
	}
	
	//Here we used driver.findElement... (in 'we' ) means it is a WebElement. So not using By Locator.
	public void waitForElementToDisappear(WebElement we) 
	{
		//Thread.sleep(1000);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOf(we));
	}
	
	public CartPage goToCartPage()
	{
		cartHeader.click();
		CartPage cp = new CartPage(driver);
		return cp;
	}
	public OrderPage goToOrderPage()
	{
		orderHeader.click();
		OrderPage op = new OrderPage(driver);
		return op;
	}
}
