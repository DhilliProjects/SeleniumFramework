package dhilliprojects.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import dhilliprojects.abstractUtilities.AbstractComponents;

public class ProductCatalogue extends AbstractComponents {


	WebDriver driver;
	
	public ProductCatalogue(WebDriver driver)
	{
		super(driver); //We must explicitly declare super() in child class to invoke the Parent's Constructor.
		
		//Initialization
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".col-sm-10")
	List<WebElement> products;
	
	@FindBy(css=".ng-animating")
	WebElement loading;
	
	By productsBy = By.cssSelector(".col-sm-10"); //We can't use @FindBy here - It is ExplicitWait Condition 
	// and we are not finding the Elementing. Just locating as a ''By' Locator'.
	
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	By message = By.cssSelector("#toast-container");
	
	public List<WebElement> getProductList()
	{
		waitForElementToAppear(productsBy);
		return products;
	}
	
	public WebElement getProductByName(String productName)
	{
		//Using stream() to iterate through each element (Like for loop. 
		//It'll iterate for every element/product and check if the condition matches.
		WebElement prod =	getProductList().stream().filter(product->
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		return prod;
	}
	
	//Even though there are multiple elements with the same cssSelector, as we are inside the product(a specific web element),
	//It'll search inside there only.
	//driver.findElement searches entire page, where as 'prod'(the name we've given) searches only the specific product.
	public void addProductToCart(String productName) throws InterruptedException
	{
		WebElement prod = getProductByName(productName);
		prod.findElement(addToCart).click(); //prod is already a WebElement. So we cannot use PageFactory within Webelemet.findelement
		//That's why we used ''By' Locator'
		waitForElementToAppear(message);
		waitForElementToDisappear(loading);
		Thread.sleep(3000);

	}
}

