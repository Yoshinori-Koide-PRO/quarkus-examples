// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
import { KeycloakConfig } from 'keycloak-angular';

// Add here your keycloak setup infos
let keycloakConfig: KeycloakConfig = {
  url: 'http://keycloak.192.168.1.65.nip.io/auth',
  realm: 'SampleApps',
  clientId: 'ng-quarkus',
  "credentials": {
    "secret": "80157fab-68d7-4708-8584-c4223e14f26c"
  }
};

export const environment = {
  production: false,
  API_BASE_PATH: 'http://quarkus-backend.192.168.1.65.nip.io',
  keycloak: keycloakConfig,
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
