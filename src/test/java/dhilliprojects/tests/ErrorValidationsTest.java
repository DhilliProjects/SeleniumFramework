package dhilliprojects.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import dhilliprojects.pageobjects.CartPage;
import dhilliprojects.pageobjects.ProductCatalogue;
import dhilliprojects.testComponents.BaseTest;

//Validating Negative TestCases

public class ErrorValidationsTest extends BaseTest {

	@Test(groups= {"ErrorHandling"})
	public void loginErrorValidation() {
		String productName = "ZARA COAT 3";
		ProductCatalogue pc = lp.loginApplication("k.dhi@gmail.com", "Loin@123");
		Assert.assertEquals("Incorrect email or password.", lp.getErrorMsg());
	}
	
	
	@Test
	public void productErrorValidation() throws IOException, InterruptedException
	{

		String productName = "ZARA COAT 3";
		
		ProductCatalogue pc = lp.loginApplication("k.dhil@gmail.com", "Login@123");
		
		List<WebElement> products = pc.getProductList();
		pc.addProductToCart(productName);
		
		CartPage cp = pc.goToCartPage(); //without creating an object for 'AbstractComponents', we are using ProductCatalogue object (i.e., pc) 
		//to call the method in AbstractComponents class  as it extends the AbstractComponents class.
		
		
		Boolean match = cp.verifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);
		
	}

}
