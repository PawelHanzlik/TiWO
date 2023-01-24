import {Component, OnInit} from '@angular/core';
import {AppService} from "../app-service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-product-list',
  templateUrl: './add-product-list.component.html',
  styleUrls: ['./add-product-list.component.css']
})
export class AddProductListComponent implements OnInit {

  name: productListData;
  dueTo: Date;
  description: productListData;
  productList : ProductList
  addOk: boolean
  today : string
  constructor(private appService: AppService, private router: Router) {
    this.today = new Date().getFullYear() + '-' + new Date().getMonth()+1 + '-' + new Date().getDate()
    this.name = {
      value: ""
    }
    this.dueTo = new Date()

    this.description = {
      value: ""
    }

    this.productList = {
      name: "",
      dueTo: new Date(),
      description:"",
      products : []
    }

    this.addOk = false
  }

  assignName(value: any) {
    this.name.value = value.value;
    this.productList.name = value.value;
  }

  assignDueTo(value: any) {
    this.dueTo = value.value;
    this.productList.dueTo = value.value;
  }

  assignDescription(value: any) {
    this.description.value = value.value;
    this.productList.description = value.value;
  }
  ngOnInit(): void {
  }

  addProductListToUser(){
    if (localStorage.getItem("operation") == "create") {
      this.appService.addProductListToUser(this.productList, localStorage.getItem("email")).subscribe(
        () => {
          this.router.navigate(['/user-page'])
        },
        () => {
          this.addOk = true
        }
      );
    }else{
      this.appService.updateProductList(this.productList, localStorage.getItem("listId")).subscribe(
        () => {
          this.router.navigate(['/user-page'])
        },
        () => {
          this.addOk = true
        }
      );
    }
  }
}
interface productListData{
  value: string;
}
interface ProductList{
  name : string
  dueTo : Date
  description : string
  products : []
}
