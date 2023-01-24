import {Component, OnInit} from '@angular/core';
import {AppService} from "../app-service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {

  name: productData;
  quantity: number;
  type: productData;
  product : Product
  addOk: boolean

  selectedName : string = ""
  selectedType : string = ""
  constructor(private appService: AppService, private router: Router) {
    this.name = {
      value: ""
    }
    this.quantity = 0

    this.type = {
      value: ""
    }

    this.product = {
      name: "",
      quantity: 0,
      type:""
    }

    this.addOk = false
  }

  assignName(value: any) {
    this.name.value = value.value;
    this.product.name = value.value;
  }

  assignNameSelect(value: any) {
    this.name.value = this.selectedName
    this.product.name = value.value;
  }

  assignQuantity(value: any) {
    this.quantity = value.value;
    this.product.quantity = value.value;
  }

  assignType(value: any) {
    this.type.value = value.value;
    this.product.type = value.value;
  }

  assignTypeSelect(value: any) {
    this.type.value = this.selectedType;
    this.product.type = value.value;
  }
  ngOnInit(): void {
  }

  addProductToList() {
    if (localStorage.getItem("operation") == "create") {
      this.appService.addProductToList(this.product, localStorage.getItem("listName")).subscribe(
        () => {
          this.router.navigate(['/user-page'])
        },
        () => {
          this.addOk = true
        }
      );
    }else{
      this.appService.updateProduct(this.product, localStorage.getItem("productId")).subscribe(
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
interface productData{
  value: string;
}
export interface Product{
  name : string
  quantity : number
  type : string
}
