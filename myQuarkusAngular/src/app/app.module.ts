import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { ApiModule, BASE_PATH} from "./quarkus-client"
import { HttpClientModule } from '@angular/common/http'

import { AppComponent } from './app.component';
import { environment } from '../environments/environment';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    ApiModule,
    HttpClientModule,
    BrowserModule
  ],
  providers: [{ provide: BASE_PATH, useValue: environment.API_BASE_PATH }],
  bootstrap: [AppComponent]
})
export class AppModule { }
