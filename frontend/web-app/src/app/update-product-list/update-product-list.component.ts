import { Component, OnInit } from '@angular/core';
import {AppService} from "../app-service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-update-product-list',
  templateUrl: './update-product-list.component.html',
  styleUrls: ['./update-product-list.component.css']
})
export class UpdateProductListComponent implements OnInit {
  name: productListData;
  dueTo: Date;
  description: productListData;
  productList : ProductList
  addOk: boolean
  today : string
  constructor(private appService: AppService, private router: Router) {
    this.today = this.getDate()
    this.name = {
      value: ""
    }
    this.dueTo = new Date()

    this.description = {
      value: ""
    }

    this.productList = {
      id : BigInt(0),
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
    this.getProductList()
  }

  updateProductList(){
    this.appService.updateProductList(this.productList, localStorage.getItem("listId")).subscribe(
      () => {
        this.router.navigate(['/user-page'])
      },
      () => {
        this.addOk = true
      }
    );
  }

  getDate(){
    let d = new Date(),
      month = '' + (d.getMonth() + 1),
      day = '' + d.getDate(),
      year = d.getFullYear();

    if (month.length < 2)
      month = '0' + month;
    if (day.length < 2)
      day = '0' + day;
    return year + '-' + month + '-' + day
  }

  getProductList(){
    console.log("dfsfdf")
    this.appService.getProductList(localStorage.getItem("listId")).subscribe(
      (response) => {
        console.log(response.dueTo)
        this.name.value = response.name;
        this.productList.name = response.name;
        // this.dueTo = response.dueTo;
        // this.productList.dueTo = response.dueTo;
        this.description.value = response.description;
        this.productList.description = response.description;
      }
    );
  }

}
interface productListData{
  value: string;
}
interface ProductList{
  id : bigint
  name : string
  dueTo : Date
  description : string
  products : []
}
