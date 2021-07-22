import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class Test1Class implements Runnable {
	public void run() {
		Hashtable<String, String> capsHashtable = new Hashtable<String, String>();
		capsHashtable.put("browser", "chrome");
		capsHashtable.put("browser_version", "91.0");
		capsHashtable.put("os", "OS X");
		capsHashtable.put("os_version", "Big Sur");
    	capsHashtable.put("build", "browserstack-build-3");
		capsHashtable.put("name", "Thread 1");
		mainTestClass1 r1 = new mainTestClass1();
		r1.executeTest(capsHashtable);
    }
}
public class mainTestClass1 {
	public static final String USERNAME = "sammitchell_UkSQNg";
	public static final String AUTOMATE_KEY = "qp3NRSjPpDsNoqZzxHHq";
	public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
	public static void main(String[] args) throws Exception {
		Thread object1 = new Thread(new TestClass1());
		object1.start();
  	}

	public void executeTest(Hashtable<String, String> capsHashtable) {
		String key;
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("browserstack.local", "true");
		caps.setCapability("os", "OS X");
		caps.setCapability("os_version", "Big Sur");
		caps.setCapability("browser", "Chrome");
		caps.setCapability("browser_version", "latest");
		caps.setCapability("browserstack.local", "false");
		caps.setCapability("browserstack.selenium_version", "3.14.0");
		caps.setCapability("project", "Sam's Test");
		caps.setCapability("build", "beta_0.0.1");
		caps.setCapability("name", "Must see home");
		// Iterate over the hashtable and set the capabilities
		Set<String> keys = capsHashtable.keySet();
		Iterator<String> itr = keys.iterator();
		while (itr.hasNext()) {
       		key = itr.next();
       		caps.setCapability(key, capsHashtable.get(key));
    	}
    	WebDriver driver;
		try {
			driver = new RemoteWebDriver(new URL(URL), caps);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			// Searching for 'BrowserStack' on bing.com
			driver.get("https://www.bing.com");
			WebElement element = driver.findElement(By.name("q"));
			element.sendKeys("BrowserStack");
			element.submit();
			// Setting the status of test as 'passed' or 'failed' based on the condition; if title of the web page contains 'BrowserStack'
			WebDriverWait wait = new WebDriverWait(driver, 5);
	    	try {
				wait.until(ExpectedConditions.titleContains("BrowserStack"));
				jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"Title matched!\"}}");
	    	}
	    	catch(Exception e) {
	    		jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \"Title not matched\"}}");
	    	}
	    	System.out.println(driver.getTitle());
	    	driver.quit();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}