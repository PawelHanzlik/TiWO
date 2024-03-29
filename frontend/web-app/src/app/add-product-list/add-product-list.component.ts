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
  }

  addProductListToUser(){
    this.appService.addProductListToUser(this.productList, localStorage.getItem("email")).subscribe(
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
