package com.cst438;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;

@SpringBootTest
public class EndToEndAddStudentTest {
	
	public static final String URL = "http://localhost:3000";
	public static final int TEST_STUDENT_ID = 9;
	public static final String TEST_STUDENT_EMAIL = "test@csumb.edu";
	public static final String TEST_STUDENT_NAME  = "test";
	
	public static final int SLEEP_DURATION = 1000;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Test
	public void addStudent() throws Exception {
		
		Student x = studentRepository.findByEmail(TEST_STUDENT_EMAIL);
		if (x != null) {
			studentRepository.delete(x);
		}
		
		WebDriver driver = new FirefoxDriver();
		
		try {
			WebElement web;
			
			driver.get(URL);
			Thread.sleep(SLEEP_DURATION);
			
			web = driver.findElement(By.id("addStudent"));
			web.click();
			Thread.sleep(SLEEP_DURATION);
			
			/* Enter student name */
			web = driver.findElement(By.name("name"));
			we.sendKeys(TEST_STUDENT_NAME);
			
			/* Enter student email */
			web = driver.findElement(By.name("email"));
			we.sendKeys(TEST_STUDENT_EMAIL);
			
			/* Find and click the submit button */
			web = driver.findElement(By.id("submit"));
			we.click();
			Thread.sleep(SLEEP_DURATION);
			
			String toastMessage = driver.findElement(By.cssSelector(".Toastify__toast-body div:nth-child(2)")).getText();
			
			System.out.println("Toast Message is: " + toastMessage);
			
			Assert.assertEquals(toastMessage, "Student was successfully added");
			
			Thread.sleep(1000);
			
			driver.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
			
		} finally {
			driver.quit();
		}
	}
}