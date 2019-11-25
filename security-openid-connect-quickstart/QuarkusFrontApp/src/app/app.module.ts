import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { environment } from '../environments/environment';
import { ApiModule, BASE_PATH, Configuration } from './quarkus-api'
import { KeycloakService, KeycloakAngularModule } from 'keycloak-angular';
import { MainComponent } from './page/main/main.component';
import { HttpClientModule } from '@angular/common/http';

export function initKc(keycloak: KeycloakService): () => Promise<any> {
  return (): Promise<any> => keycloak.init({
    config: environment.keycloak
  });
}

@NgModule({
  declarations: [
    AppComponent,
    MainComponent
  ],
  imports: [
    ApiModule.forRoot(() => new Configuration({
      withCredentials: true
    })),
    KeycloakAngularModule,
    HttpClientModule,
    BrowserModule,
    AppRoutingModule
  ],
  providers: [
    {
      provide: BASE_PATH,
      useValue: environment.API_BASE_PATH
    },
    {
      provide: APP_INITIALIZER,
      useFactory: initKc,
      multi: true,
      deps: [KeycloakService]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
