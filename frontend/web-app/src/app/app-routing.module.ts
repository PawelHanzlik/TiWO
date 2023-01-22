import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainPageComponent} from "./main-page/main-page.component";
import {LoginComponent} from "./login/login.component";
import {UserPageComponent} from "./user-page/user-page.component";
import {RegisterComponent} from "./register/register.component";
import {RegisterResultComponent} from "./register-result/register-result.component";
import {AddProductComponent} from "./add-product/add-product.component";
import {AddProductListComponent} from "./add-product-list/add-product-list.component";

const routes: Routes =[
  {path: '', component: MainPageComponent},
  {path: 'login', component: LoginComponent},
  {path: 'user-page', component: UserPageComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'register-result', component: RegisterResultComponent},
  {path: 'add-product', component: AddProductComponent},
  {path: 'add-product-list', component: AddProductListComponent}
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
