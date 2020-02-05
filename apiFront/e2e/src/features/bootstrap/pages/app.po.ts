import {browser, by, element, until, WebElementPromise} from 'protractor';

export class AppPage {
  static navigateTo() {
    return browser.get(browser.baseUrl) as Promise<any>;
  }

  static getTitleText() {
    return element(by.css('mat-card-title')).getText() as Promise<string>;
  }

 
}
