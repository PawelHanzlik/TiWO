import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {UserPageComponent} from "./user-page/user-page.component";
import {RegisterComponent} from "./register/register.component";
import {AddProductComponent} from "./add-product/add-product.component";
import {AddProductListComponent} from "./add-product-list/add-product-list.component";
import {AuthGuard} from "./AuthGuard";
import {WarehouseComponent} from "./warehouse/warehouse.component";
import {UpdateProductComponent} from "./update-product/update-product.component";
import {UpdateProductListComponent} from "./update-product-list/update-product-list.component";

const routes: Routes =[
  {path: '', component: LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'user-page', component: UserPageComponent, canActivate : [AuthGuard]},
  {path: 'register', component: RegisterComponent},
  {path: 'add-product', component: AddProductComponent, canActivate : [AuthGuard]},
  {path: 'add-product-list', component: AddProductListComponent, canActivate : [AuthGuard]},
  {path: 'warehouse', component: WarehouseComponent},
  {path: 'update-product', component: UpdateProductComponent},
  {path: 'update-product-list', component: UpdateProductListComponent}
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
