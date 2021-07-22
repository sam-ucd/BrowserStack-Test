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
class TestClass1 implements Runnable {
	public void run() {
		Hashtable<String, String> capsHashtable = new Hashtable<String, String>();
		capsHashtable.put("browser", "chrome");
		capsHashtable.put("browser_version", "91.0");
		capsHashtable.put("os", "OS X");
		capsHashtable.put("os_version", "Big Sur");
    	capsHashtable.put("build", "browserstack-build-8");
		capsHashtable.put("name", "Thread 1");
		mainTestClass r1 = new mainTestClass();
		r1.executeTest(capsHashtable);
    }
}
class TestClass2 implements Runnable {
	public void run() {
		Hashtable<String, String> capsHashtable = new Hashtable<String, String>();
		capsHashtable.put("browser", "firefox");
		capsHashtable.put("browser_version", "90.0");
		capsHashtable.put("os", "OS X");
		capsHashtable.put("os_version", "Big Sur");
		capsHashtable.put("build", "browserstack-build-8");
		capsHashtable.put("name", "Thread 2");
		mainTestClass r2 = new mainTestClass();
    	r2.executeTest(capsHashtable);
  	}
}
class TestClass3 implements Runnable {
	public void run() {
		Hashtable<String, String> capsHashtable = new Hashtable<String, String>();
		capsHashtable.put("browser", "edge");
		capsHashtable.put("browser_version", "91.0");
		capsHashtable.put("os", "OS X");
		capsHashtable.put("os_version", "Big Sur");
		capsHashtable.put("build", "browserstack-build-8");
		capsHashtable.put("name", "Thread 3");
		mainTestClass r3 = new mainTestClass();
    	r3.executeTest(capsHashtable);
  	}
}
class TestClass4 implements Runnable {
	public void run() {
		Hashtable<String, String> capsHashtable = new Hashtable<String, String>();
		capsHashtable.put("browser", "safari");
		capsHashtable.put("browser_version", "14.1");
		capsHashtable.put("os", "OS X");
		capsHashtable.put("os_version", "Big Sur");
		capsHashtable.put("build", "browserstack-build-8");
		capsHashtable.put("name", "Thread 4");
		mainTestClass r4 = new mainTestClass();
	    r4.executeTest(capsHashtable);
	  	}
}
class TestClass5 implements Runnable {
	public void run() {
		Hashtable<String, String> capsHashtable = new Hashtable<String, String>();
		capsHashtable.put("device", "iPhone 12 Pro");
		capsHashtable.put("real_mobile", "true");
		capsHashtable.put("os_version", "14");
		capsHashtable.put("build", "browserstack-build-8");
		capsHashtable.put("name", "Thread 5");
		mainTestClass r5 = new mainTestClass();
	    r5.executeTest(capsHashtable);
	  	}
}
public class mainTestClass {
	public static final String USERNAME = "BROWSERSTACK_USERNAME";
	public static final String AUTOMATE_KEY = "BROWSERSTACK_ACCESS_KEY";
	public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
	public static void main(String[] args) throws Exception {
		Thread object1 = new Thread(new TestClass1());
		object1.start();
		Thread object2 = new Thread(new TestClass2());
		object2.start();
		Thread object3 = new Thread(new TestClass3());
		object3.start();
		Thread object4 = new Thread(new TestClass4());
		object4.start();
		Thread object5 = new Thread(new TestClass5());
		object5.start();
  	}

	public void executeTest(Hashtable<String, String> capsHashtable) {
		String key;
		String username = System.getenv("BROWSERSTACK_USERNAME");
		String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
		String buildName = System.getenv("BROWSERSTACK_BUILD_NAME");
		String browserstackLocal = System.getenv("BROWSERSTACK_LOCAL");
		String browserstackLocalIdentifier = System.getenv("BROWSERSTACK_LOCAL_IDENTIFIER");
		DesiredCapabilities caps = new DesiredCapabilities();
		// Iterate over the hashtable and set the capabilities
		caps.setCapability("browserstack.local", "true");
		caps.setCapability("browserstack.debug", "true");  // for enabling visual logs
		caps.setCapability("browserstack.console", "info");  // to enable console logs at the info level. You can also use other log levels here
		caps.setCapability("browserstack.networkLogs", "true");  // to enable network logs to be logged
		caps.setCapability("acceptSslCerts", "true");
		caps.setCapability("resolution", "1024x768");
		caps.setCapability("os", "Windows");
		caps.setCapability("os_version", "10");
		caps.setCapability("browser", "Chrome");
		caps.setCapability("browser_version", "latest");
		caps.setCapability("name", "BStack-[Java] Sample Test"); // test buildName
		caps.setCapability("build", buildName); // CI/CD job name using BROWSERSTACK_BUILD_NAME env variable
		caps.setCapability("browserstack.local", browserstackLocal);
		caps.setCapability("browserstack.localIdentifier", browserstackLocalIdentifier);
		
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