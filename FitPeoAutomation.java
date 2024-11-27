import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class FitPeoAutomation {
    public static void main(String[] args) throws InterruptedException {
        // Set up the WebDriver

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // Step 1: Navigate to FitPeo Homepage
            driver.get("https://fitpeo.com");
            driver.manage().window().maximize();

            // Step 2: Navigate to the Revenue Calculator Page
            WebElement revenueCalculatorLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Revenue Calculator")));
            revenueCalculatorLink.click();

            // Step 3: Scroll Down to the Slider Section
            WebElement sliderSection = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='MuiBox-root css-j7qwjs']/span/span[@class='MuiSlider-track css-10opxo5']")));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", sliderSection);
            
            Thread.sleep(1000);

           
            
            
            // Get the slider's width to calculate the move offset
            int sliderWidth = sliderSection.getSize().getWidth();

            // Define the target value and the current value
            int currentValue =200; 
            int targetValue = 163;  
            int maxValue = 2000; 

            // Calculate the offset percentage
            double offsetPercentage = (double) (targetValue - currentValue) / maxValue;

            // Calculate the pixel offset
            int pixelOffset = (int) (sliderWidth * offsetPercentage);

            // Move the slider using Actions
            Actions action = new Actions(driver);
            action.clickAndHold(sliderSection).moveByOffset(pixelOffset, 0).release().perform(); 

            System.out.println("Slider moved from " + currentValue + " to " + targetValue);
            
            Thread.sleep(1000);
            
            // Validate slider value
            WebElement sliderValue = driver.findElement(By.xpath("//input[@value='103']"));
            if (sliderValue.getAttribute("value").equals("103")) {
                System.out.println("Slider set to 103 successfully.");
            }
            
            Thread.sleep(1000);

            // Step 5: Update the Text Field to 560
            WebElement textField = driver.findElement(By.xpath("//input[@class='MuiInputBase-input MuiOutlinedInput-input MuiInputBase-inputSizeSmall css-1o6z5ng']")); 
            textField.clear();
            Thread.sleep(1000);
            textField.sendKeys("560");
            
            Thread.sleep(1000);

            
            // Step 6: Select CPT Codes
            String[] cptCodes = {"99091", "99453", "99454", "99474"};
            for (String code : cptCodes) {
                // Locate the parent element containing the CPT code text
            	  WebElement parentElement = driver.findElement(By.xpath("//p[contains(text(),'CPT-" + code + "')]/ancestor::div[contains(@class,'MuiBox-root')]"));
                
               
                
                // Find the checkbox within the parent element
                WebElement checkbox = parentElement.findElement(By.xpath(".//input[@type='checkbox']"));

                // Check if the checkboxe is not selected and click it
                if (!checkbox.isSelected()) {
                    checkbox.click();
                    Thread.sleep(1000);
                }
            }

            // Step 7: Validate Total Recurring Reimbursement
            WebElement totalReimbursement = driver.findElement(By.xpath("//*[contains(text(),'Total Recurring Reimbursement')]"));
            if (totalReimbursement.getText().contains("$58995")) {
                System.out.println("Total Recurring Reimbursement is correct.");
            } else {
                System.err.println("Total Recurring Reimbursement validation failed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
          //   Step 8: Close the browser
           driver.quit();
        }
    }
}
