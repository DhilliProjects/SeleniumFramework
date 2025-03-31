package dhilliprojects.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import dhilliprojects.abstractUtilities.AbstractComponents;

public class CartPage extends AbstractComponents{

	WebDriver driver;
	
	public CartPage(WebDriver driver) {
		super(driver);
		this.driver =driver;
		PageFactory.initElements(driver, this);
	}

	
	@FindBy(css=".cartSection h3")
	private List<WebElement> productTitles;
	
	@FindBy(css=".totalRow button")
	WebElement checkout;
	
	
	public Boolean verifyProductDisplay(String productName)
	{
		//filter() returns if the value if the condition is met. But we don't want to return anything and just want to
		//check if it matches. So, we are using anyMatch().
	
		Boolean match = productTitles.stream().anyMatch(product-> product.getText().equalsIgnoreCase(productName));
		return match;
	}
	
	public CheckoutPage goToCheckOut()
	{
		checkout.click();
		return new CheckoutPage(driver);
	}
	
	
}
