package dhilliprojects.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import dhilliprojects.abstractUtilities.OrderPage;
import dhilliprojects.pageobjects.CartPage;
import dhilliprojects.pageobjects.CheckoutPage;
import dhilliprojects.pageobjects.ConfirmationPage;
import dhilliprojects.pageobjects.ProductCatalogue;
import dhilliprojects.testComponents.BaseTest;

public class SubmitOrderTest extends BaseTest {
 
	String productName ="ZARA COAT 3";

	@Test(dataProvider="getData", groups= {"PurchaseOrder"})
	public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException
	{

		
		ProductCatalogue pc = lp.loginApplication(input.get("email"), input.get("password"));
		
		List<WebElement> products = pc.getProductList();
		pc.addProductToCart(input.get("product"));
		
		CartPage cp = pc.goToCartPage(); //without creating an object for 'AbstractComponents', we are using ProductCatalogue object (i.e., pc) 
		//to call the method in AbstractComponents class  as it extends the AbstractComponents class.
		
		
		Boolean match = cp.verifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		
		CheckoutPage chp = cp.goToCheckOut();
		chp.selectCountry("India");
		ConfirmationPage cnp = chp.submitOrder();
		String confirmMesg = cnp.getConfirmMessage();
		
		Assert.assertTrue(confirmMesg.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	}
	
	@Test(dependsOnMethods= {"submitOrder"})
	public void orderHistoryTest()
	{
		ProductCatalogue pc = lp.loginApplication("k.dhil@gmail.com", "Login@123");
		OrderPage op = pc.goToOrderPage();
		Assert.assertTrue(op.verifyOrderDisplay(productName));
	}
	
	
	public String getScreenshot(String testCaseName) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File destination = new File(System.getProperty("user.dir")+"//reports"+testCaseName+".png");
		FileUtils.copyFile(source, destination);
		return System.getProperty("user.dir")+"//reports"+testCaseName+".png";
	}
	
	
	@DataProvider()
	public Object getData() throws IOException
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\dhilliprojects\\data\\Purchase.json");
		
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}

	
//	@DataProvider()
//	public Object getData()
//	{
//	return new Object[][] {{"k.dhil@gmail.com","Login@123","ZARA COAT 3" },{"kdr@gmail.com","Login@123","ADIDAS ORIGINAL"}};
//}
	
	
//	@DataProvider()
//	public Object getData()
//	{
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("email", "k.dhil@gmail.com");
//		map.put("password","Login@123");
//		map.put("product", "ZARA COAT 3");
//		
//		HashMap<String, String> map1 = new HashMap<String, String>();
//		map1.put("email","kdr@gmail.com");
//		map1.put("password","Login@123");
//		map1.put("product", "ADIDAS ORIGINAL");
//		
//		return new Object[][] {{map},{map1}};
//
//}
	
}
