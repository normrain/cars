import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent }  from './app.component';
import {RouterLink, RouterLinkActive } from "@angular/router";
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  imports: [BrowserModule, RouterLink, RouterLinkActive, HttpClientModule],
  declarations: [],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }
