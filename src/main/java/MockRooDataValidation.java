import static org.testng.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.opencsv.CSVReader;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MockRooDataValidation {
	
	WebDriver driver;
	
	
	@BeforeClass //runs once for all tests
	public void setUp() {
		System.out.println("Setting up WebDriver in BeforeClass...");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().fullscreen();
		driver.get("https://mockaroo.com/");
	}
	
	@BeforeMethod
	public void loadPage() {
		
		
	}
	
	@Test(priority = 1)//step3
	public void getTitle()
	{
		
		String expected="Mockaroo - Random Data Generator and API Mocking Tool | JSON / CSV / SQL / Excel";
		
		Assert.assertTrue(driver.getTitle().contains(expected));	
	}
	
	@Test(priority = 2)//step4
	public void getDisplayed()
	{
		WebElement we=driver.findElement(By.xpath("//div[@class='tagline' and contains(text(),'realistic data generator')]"));
		
		Assert.assertTrue(we.isDisplayed());
		we=driver.findElement(By.xpath("//*[contains(text(),'mockaroo')and @class='brand']"));
		Assert.assertTrue(we.isDisplayed());
	}
	
	
	@Test(priority = 3)//step5
	public void RemoveFields()
	{
		List<WebElement> list=new ArrayList<WebElement>();
		
		list=driver.findElements(By.xpath("//a[@class='close remove-field remove_nested_fields']"));
		for(WebElement each: list) {
			
			each.click();
		}
		
		
		
	}
	@Test (priority = 4) //step6
	public void checkLabelsDisplayed() {
		
		WebElement we=driver.findElement(By.xpath("//div[@class=\"column column-header column-name\"and contains(text(),'Field Name')]"));
		Assert.assertTrue(we.isDisplayed());
		
		we=driver.findElement(By.xpath("//div[@class=\"column column-header column-type\"and contains(text(),'Type')]"));
		Assert.assertTrue(we.isDisplayed());
		
		we=driver.findElement(By.xpath("//div[@class=\"column column-header column-options\"and contains(text(),'Options')]"));
		Assert.assertTrue(we.isDisplayed());
		
		
	}
	
	@Test (priority = 5)//step7
	public void TestAddAnotherFieldEnabled() {
		
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='Add another field']")).isEnabled());
		
		
	}
	
	@Test (priority = 6) //step8
	
	public void TestDefaultNumberofRows() {
		
		String actual=driver.findElement(By.id("num_rows")).getAttribute("value");
		String expected="1000";
		Assert.assertEquals(actual, expected);
		
	}
	
	@Test (priority = 7)//step9
public void TestDefaultFormatSelection() {
		
		String actual=driver.findElement(By.id("schema_file_format")).getAttribute("value");
		String expected="csv";
		Assert.assertEquals(actual, expected);
		
	}
	
	@Test (priority = 8) //step10
	public void TestDefaultLineEnding() {
			
			Select select=new Select(driver.findElement(By.id("schema_line_ending")));
			String actual=select.getFirstSelectedOption().getText();
			String expected="Unix (LF)";
			Assert.assertEquals(actual, expected);
			
		}
	
	
	
	@Test (priority = 9) //step11
	public void TestHeaderChecked() {
			
			WebElement we=driver.findElement(By.id("schema_include_header"));
			Assert.assertTrue(we.isSelected());
			
			we=driver.findElement(By.id("schema_bom"));
			Assert.assertFalse(we.isSelected());
	}
	
	@Test (priority = 10)//step12-14
	public void EnterCityName() throws InterruptedException {
		
		driver.findElement(By.xpath("//a[text()='Add another field']")).click();;
		driver.findElement(By.xpath(
"(//div[@id='fields']//input[starts-with(@id, 'schema_columns_attributes_')][contains(@id,'name')])[last()]"))
				.sendKeys("City");	
	
		
		driver.findElement(By.xpath("(//input[@class='btn btn-default'])[last()]")).click();
		
		
		
		WebElement dialogBox = driver.findElement(By.id("type_dialog"));
		Assert.assertFalse(Boolean.getBoolean(dialogBox.getAttribute("aria-hidden")));
		
		driver.findElement(By.xpath("//input[@id='type_search_field']")).sendKeys("city"+Keys.ENTER);	
		
		driver.findElement(By.xpath("//div[@class='type-name']")).click();
		Thread.sleep(1000);
	}
	
	
	@Test (priority = 11)//step 15
	public void EnterCountryName() throws InterruptedException {
		
		driver.findElement(By.partialLinkText("Add another field")).click();
		Thread.sleep(1000);
		// this input has dynamic id changing on every page load
		driver.findElement(By.xpath(
				"(//div[@id='fields']//input[starts-with(@id, 'schema_columns_attributes_')][contains(@id,'name')])[last()]"))
				.sendKeys("Country");
		Thread.sleep(1000);
		// click on choose type button
		driver.findElement(By.xpath("(//div[@class='fields']//input[@class='btn btn-default'])[last()]")).click();
		Thread.sleep(1000);

		driver.findElement(By.id("type_search_field")).clear();
		Thread.sleep(1000);
		driver.findElement(By.id("type_search_field")).sendKeys("country"+Keys.ENTER);
		
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class='type-name']")).click();
		Thread.sleep(1000);
		
	}
	
	@Test (priority = 12) //step 16-23
	public void DownloadData() throws InterruptedException, IOException {
		
		driver.findElement(By.id("download")).click();
		Thread.sleep(4000);
		//String path;
		
		
		List<String> Cities=new ArrayList<>();
		List<String> Countries=new ArrayList<>();
		
		String pathToFile = "C:\\Users\\Sibel\\Downloads\\MOCK_DATA.csv";
		FileReader fr = new FileReader(pathToFile);
		
				
		  CSVReader reader = new CSVReader(fr);
	      String [] nextLine;
	      int lineNumber = 0;
	      while ((nextLine = reader.readNext()) != null) {
	        lineNumber++;
	        //System.out.println("Line # " + lineNumber);

	        // nextLine[] is an array of values from the line
	        
	        Cities.add(nextLine[0]);
	        Countries.add(nextLine[1]);
	      }
	    
		System.out.print(Cities);
		System.out.println();

		System.out.println(Countries);
		
		Collections.sort(Cities);
		  String shortest = Cities.get(0);
		  String longest=Cities.get(0);
		  
		  for (String each : Cities)
		  {
			  if (each.length() < shortest.length()) {
		            shortest = each;
		        }
			  if (each.length() > longest.length()) {
		            longest = each;
		        }
		  }
		System.out.println("shortest name is : "+shortest);
		System.out.println("longest name is : "+longest);	
		
		
	    Map<String, Integer> countrymap=new HashMap<String, Integer>();
		
		
		 for (String each : Countries) {
			
			 countrymap.put(each, Collections.frequency(Countries, each));
		}
		
		System.out.println(countrymap);
		
		assertEquals(lineNumber-1,1000);
		// Add all cities from file into Cities Array List, Countries Countries Array List
	
	
	Set<String> CitiesSet=new HashSet<>(Cities);
	
	
	List<String> distinct = new ArrayList<String>();

    Iterator<String> it = Cities.iterator();
    while (it.hasNext()) {
        String value = it.next().toString();
        if (!distinct.contains(value)) {
        	distinct.add(value);
        }
    }
	System.out.println(distinct);
	assertEquals(distinct.size(),CitiesSet.size());
	
Set<String> CountrySet=new HashSet<>(Countries);
	
	
	List<String> distinctC = new ArrayList<String>();

    Iterator<String> itC = Countries.iterator();
    while (itC.hasNext()) {
        String valueC = itC.next().toString();
        if (!distinctC.contains(valueC)) {
        	distinctC.add(valueC);
        }
    }
	System.out.println(distinctC);
	assertEquals(distinctC.size(),CountrySet.size());
	
	
	
	
	
	
	
	}
	
	
	
	
	
	@AfterClass //runs once for all tests
	public void cleanUp() {
		driver.close();
	}

}
