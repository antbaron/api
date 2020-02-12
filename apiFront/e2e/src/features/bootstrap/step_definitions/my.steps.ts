import {Before, Given, When, Then} from 'cucumber';
import {expect} from 'chai';
import {AppPage} from '../pages/app.po';


Before(() => {
});

Given('I am on the home page', async () => {
  await AppPage.navigateTo();
});

When('I do nothing', () => {
});

Then('I should see the title', async () => {
  expect(await AppPage.getTitleText()).to.equal('Connexion');
});
