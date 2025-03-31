package dhilliprojects.abstractUtilities;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderPage extends AbstractComponents {

WebDriver driver;
	
	public OrderPage(WebDriver driver) {
		super(driver);
		this.driver =driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css=".totalRow button")
	WebElement checkout;
	
	@FindBy(css="tr td:nth-child(3)")
	private List<WebElement> productNames;
	
	
	
	
	public Boolean verifyOrderDisplay(String productName)
	{
		//filter() returns if the value if the condition is met. But we don't want to return anything and just want to
		//check if it matches. So, we are using anyMatch().
	
		Boolean match = productNames.stream().anyMatch(product-> product.getText().equalsIgnoreCase(productName));
		return match;
	}
	
	
}
