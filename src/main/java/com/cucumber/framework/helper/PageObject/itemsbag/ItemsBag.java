/**
 * @author rahul.rathore
 *	
 *	21-Aug-2016
 */
package com.cucumber.framework.helper.PageObject.itemsbag;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.cucumber.framework.helper.PageObject.Userdetails;
import com.cucumber.framework.helper.PageObject.homepage.HomePage;
import com.cucumber.framework.settings.ObjectRepo;

/**
 * @author rahul.rathore
 *	
 *	21-Aug-2016
 *
 */
public class ItemsBag extends HomePage {
	
	private WebDriver driver;
	
	public ItemsBag(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	/** Region WebElement **/
	
	@FindBy(how=How.ID,using="update-basket")
	public WebElement updateBasket;
	
	@FindBy(how=How.NAME,using="update-and-continue")
	public WebElement reserveYourItem;
	
	/** Region Public **/
	
	public Userdetails reserveYourItem() {
		reserveYourItem.click();
		Userdetails details = new Userdetails(driver);
		waitForElement(details.reservationDestination, ObjectRepo.reader.getExplicitWait());
		log.info("");
		return details;
	}

}