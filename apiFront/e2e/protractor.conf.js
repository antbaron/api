// @ts-check
// Protractor configuration file, see link for more information
// https://github.com/angular/protractor/blob/master/lib/config.ts

/**
 * @type { import("protractor").Config }
 */

exports.config = {
  allScriptsTimeout: 11000,
  
  multiCapabilities: [{
    'browserName': 'chrome'
  }],
  directConnect: true,
  baseUrl: 'http://localhost:4200/',
  
  framework: 'custom',
  frameworkPath: require.resolve('protractor-cucumber-framework'),

  specs: ['./src/features/**/*.feature'],
  cucumberOpts: {
    compiler: "ts-node/register",
    require: ['./src/features/bootstrap/step_definitions/**/*.steps.ts'],
    strict: true,
    dryRun: false,
    failFast: true
  },
  onPrepare() {
    require('ts-node').register({
      project: require('path').join(__dirname, './tsconfig.json')
    });
    browser.ignoreSynchronization = true;
    browser.manage().window().maximize();
  }
};
