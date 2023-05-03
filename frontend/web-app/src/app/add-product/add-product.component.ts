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
  mapa : Map<string, string>
  selectedName : string = "chleb"
  selectedType : string = "sztuk"
  constructor(private appService: AppService, private router: Router) {
    this.name = {
      value: "chleb"
    }
    this.quantity = 1

    this.type = {
      value: "sztuk"
    }

    this.product = {
      name: "chleb",
      quantity: 1,
      type:"sztuk",
      url:""
    }

    this.addOk = false
    this.mapa = new Map([
      ["drukarka" , "sztuk"],
      ["wiertarka" , "sztuk"],
      ["chleb" , "sztuk"],
      ["maslo" , "sztuk"],
      ["szynka" , "kg"],
      ["ser" , "kg"],
      ["ciastka" , "sztuk"],
      ["cukier" , "kg"],
      ["mleko" , "sztuk"],
    ]);
  }

  assignNameSelect(value: any) {
    this.name.value = this.selectedName
    this.product.name = value.value;
    this.assignTypeSelect(this.mapa.get(value.value))
  }

  assignQuantity(value: any) {
    if (value.value >= 0) {
      this.quantity = value.value;
      this.product.quantity = value.value;
    }else {
      this.quantity = 1;
      this.product.quantity = 1;
    }
  }

  assignTypeSelect(value: any) {
    this.selectedType = value
    this.type.value = this.selectedType;
    this.product.type = value;
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
  url : string
}
