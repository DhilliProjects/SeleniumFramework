package dhilliprojects.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import dhilliprojects.pageobjects.LandingPage;

public class StandAloneTest {

	public static void main(String[] args) {

		String productName = "ZARA COAT 3";
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();

		driver.get("https://rahulshettyacademy.com/client");
		
		LandingPage lp = new LandingPage(driver);

		driver.findElement(By.id("userEmail")).sendKeys("k.dhil@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Login@123");
		driver.findElement(By.id("login")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".col-sm-10"))); 
		List<WebElement> products = driver.findElements(By.cssSelector(".col-sm-10")); //Storing the list of web elements.
		
		//Using stream() to iterate through each element (Like for loop. 
		//It'll iterate for every element/product and check if the condition matches.
		WebElement prod = products.stream().filter(product-> 
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		
		//Even though there are multiple elements with the same cssSelector, as we are inside the product(a specific web element),
		//It'll search inside there only.
		//driver.findElement searches entire page, where as 'prod'(the name we've given) searches only the specific product.
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		//Using Explicit waits instead of Thread.sleep() to wait until loading and product adding to cart completes
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container"))); 
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating")))); //the loading page is invisible
		
		driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();
		
		List<WebElement> cartProducts = driver.findElements(By.xpath("//div[@class='cart']//h3"));
		
		//filter() returns if the value if the condition is met. But we don't want to return anything and just want to
		//check if it matches. So, we are using anyMatch().
		Boolean match = cartProducts.stream().anyMatch(cartProduct->
		cartProduct.getText().equalsIgnoreCase(productName));
		
		Assert.assertTrue(match);
		
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "India").build().perform();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		
		driver.findElement(By.xpath("//button[contains(@class, 'ta-item')][2]")).click();
		driver.findElement(By.cssSelector(".action__submit")).click();
	
		String confirmMessage =  driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		driver.quit();
	}

}
