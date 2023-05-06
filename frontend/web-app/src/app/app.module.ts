import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './header/header.component';
import {FooterComponent} from './footer/footer.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {AppService} from "./app-service";
import {LoginComponent} from './login/login.component';
import {UserPageComponent} from './user-page/user-page.component';
import {RegisterComponent} from './register/register.component';
import {AuthInterceptor} from "./AuthInterceptor";
import {AddProductComponent} from './add-product/add-product.component';
import {AddProductListComponent} from './add-product-list/add-product-list.component';
import { WarehouseComponent } from './warehouse/warehouse.component';
import { UpdateProductComponent } from './update-product/update-product.component';
import { UpdateProductListComponent } from './update-product-list/update-product-list.component';
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    LoginComponent,
    UserPageComponent,
    RegisterComponent,
    AddProductComponent,
    AddProductListComponent,
    WarehouseComponent,
    UpdateProductComponent,
    UpdateProductListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [AppService, {provide: HTTP_INTERCEPTORS , useClass : AuthInterceptor , multi : true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
