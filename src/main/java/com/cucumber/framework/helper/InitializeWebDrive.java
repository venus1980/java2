/**
 * rsr 
 *
 *Aug 5, 2016
 */
package com.cucumber.framework.helper;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.cucumber.framework.configreader.PropertyFileReader;
import com.cucumber.framework.configuration.browser.BrowserType;
import com.cucumber.framework.configuration.browser.ChromeBrowser;
import com.cucumber.framework.configuration.browser.FirefoxBrowser;
import com.cucumber.framework.configuration.browser.HtmlUnitBrowser;
import com.cucumber.framework.configuration.browser.IExploreBrowser;
import com.cucumber.framework.configuration.browser.PhantomJsBrowser;
import com.cucumber.framework.exception.NoSutiableDriverFoundException;
import com.cucumber.framework.helper.Logger.LoggerHelper;
import com.cucumber.framework.settings.ObjectRepo;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

/**
 * @author rsr
 *
 *         Aug 5, 2016
 */

public class InitializeWebDrive {

	private Logger oLog = LoggerHelper.getLogger(InitializeWebDrive.class);

	public InitializeWebDrive(PropertyFileReader reader) {
		ObjectRepo.reader = reader;
	}

	public WebDriver gridSetUp(String hubUrl, String browser)
			throws MalformedURLException {

		oLog.info(hubUrl + " : " + browser);

		switch (BrowserType.valueOf(browser)) {

		case Chrome:
			ChromeBrowser chrome = new ChromeBrowser();
			return chrome.getChromeDriver(hubUrl,
					chrome.getChromeCapabilities());

		case Firefox:
			FirefoxBrowser firefox = new FirefoxBrowser();
			return firefox.getFirefoxDriver(hubUrl,
					firefox.getFirefoxCapabilities());

		case HtmlUnitDriver:

		case Iexplorer:
			IExploreBrowser iExplore = new IExploreBrowser();
			return iExplore.getIExplorerDriver(hubUrl,
					iExplore.getIExplorerCapabilities());

		case PhantomJs:
			PhantomJsBrowser jsBrowser = new PhantomJsBrowser();
			return jsBrowser.getPhantomJsDriver(hubUrl,
					jsBrowser.getPhantomJsService(),
					jsBrowser.getPhantomJsCapability());

		default:
			throw new NoSutiableDriverFoundException(" Driver Not Found : "
					+ ObjectRepo.reader.getBrowser());
		}
	}

	public WebDriver standAloneStepUp(BrowserType bType) throws Exception {

		oLog.info(bType);

		switch (bType) {

		case Chrome:
			ChromeBrowser chrome = ChromeBrowser.class.newInstance();
			return chrome.getChromeDriver(chrome.getChromeCapabilities());

		case Firefox:
			FirefoxBrowser firefox = FirefoxBrowser.class.newInstance();
			return firefox.getFirefoxDriver(firefox.getFirefoxCapabilities());

		case HtmlUnitDriver:
			HtmlUnitBrowser htmlUnit = HtmlUnitBrowser.class.newInstance();
			return htmlUnit.getHtmlUnitDriver(htmlUnit
					.getHtmlUnitDriverCapabilities());

		case Iexplorer:
			IExploreBrowser iExplore = IExploreBrowser.class.newInstance();
			return iExplore.getIExplorerDriver(iExplore
					.getIExplorerCapabilities());

		case PhantomJs:
			PhantomJsBrowser jsBrowser = PhantomJsBrowser.class.newInstance();
			return jsBrowser.getPhantomJsDriver(
					jsBrowser.getPhantomJsService(),
					jsBrowser.getPhantomJsCapability());

		default:
			throw new NoSutiableDriverFoundException(" Driver Not Found : "
					+ ObjectRepo.reader.getBrowser());
		}

	}

	@Before("@firefox")
	public void beforeFirefox() throws Exception {
		setUpDriver(BrowserType.Firefox);
		oLog.info(BrowserType.Firefox);
	}

	@After("@firefox")
	public void afterFirefox(Scenario scenario) throws Exception {
		tearDownDriver();
		oLog.info("");
	}

	@Before("@chrome")
	public void beforeChrome() throws Exception {
		setUpDriver(BrowserType.Chrome);
		oLog.info(BrowserType.Chrome);
	}

	@After("@chrome")
	public void afterChrome(Scenario scenario) throws Exception {
		tearDownDriver();
		oLog.info("");
	}

	@Before("@phantomjs")
	public void beforePhantomjs() throws Exception {
		setUpDriver(BrowserType.PhantomJs);
		oLog.info(BrowserType.PhantomJs);
	}

	@After("@phantomjs")
	public void afterPhantomjs(Scenario scenario) throws Exception {
		tearDownDriver();
		oLog.info("");
	}

	public void setUpDriver(BrowserType bType) throws Exception {
		ObjectRepo.driver = standAloneStepUp(bType);
		oLog.debug("InitializeWebDrive : " + ObjectRepo.driver.hashCode());
		ObjectRepo.driver
				.manage()
				.timeouts()
				.pageLoadTimeout(ObjectRepo.reader.getPageLoadTimeOut(),
						TimeUnit.SECONDS);
		ObjectRepo.driver
				.manage()
				.timeouts()
				.implicitlyWait(ObjectRepo.reader.getImplicitWait(),
						TimeUnit.SECONDS);
		ObjectRepo.driver.manage().window().maximize();

	}

	public void tearDownDriver() throws Exception {
		oLog.info("Shutting Down the driver");
		try {
			if (ObjectRepo.driver != null) {
				ObjectRepo.driver.quit();
				ObjectRepo.reader = null;
				ObjectRepo.driver = null;
			}
		} catch (Exception e) {
			oLog.error(e);
			throw e;
		}

	}

}