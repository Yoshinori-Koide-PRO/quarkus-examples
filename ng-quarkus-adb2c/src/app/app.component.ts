import { Component } from '@angular/core';

import { OAuthService, NullValidationHandler } from 'angular-oauth2-oidc';
import { authConfig, DiscoveryDocumentConfig } from './auth.config';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'ng-quarkus-adb2c';
  constructor(private http: HttpClient, private oauthService: OAuthService) {
    this.configure();
    this.oauthService.tryLoginImplicitFlow();
  }

  message: string;

  public getMessage() {
    this.http.get("https://xxxxxxxxxxxxxxxxxxxxx.azurewebsites.net/api/hello", { responseType: 'text' })
      .subscribe(r => {
        this.message = r
        console.log("message: ", this.message);
      });
  }

  public login() {
    this.oauthService.initLoginFlow();
  }

  public logout() {
    this.oauthService.logOut();
  }

  public get claims() {
    let claims = this.oauthService.getIdentityClaims();
    return claims;

  }

  public get accToken() {
    return this.oauthService.getAccessToken();
  }

  public get idToken() {
    return this.oauthService.getIdToken();
  }

  public get scopes() {
    return this.oauthService.getGrantedScopes();
  }

  private configure() {
    this.oauthService.configure(authConfig);
    this.oauthService.tokenValidationHandler = new NullValidationHandler();
    this.oauthService.loadDiscoveryDocument(DiscoveryDocumentConfig.url);
  }
}
