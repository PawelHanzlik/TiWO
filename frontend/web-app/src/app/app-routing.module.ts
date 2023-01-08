import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainPageComponent} from "./main-page/main-page.component";
import {LoginComponent} from "./login/login.component";
import {UserPageComponent} from "./user-page/user-page.component";
import {RegisterComponent} from "./register/register.component";
import {RegisterResultComponent} from "./register-result/register-result.component";

const routes: Routes =[
  {path: '', component: MainPageComponent},
  {path: 'login', component: LoginComponent},
  {path: 'user-page', component: UserPageComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'register-result', component: RegisterResultComponent}
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
