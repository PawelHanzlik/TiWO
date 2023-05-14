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
  quantity: number | undefined;
  type: productData;
  product : Product
  addOk: boolean
  quantityOk : boolean
  mapa : Map<string, string>
  selectedName : string = "chleb"
  selectedType : string = "sztuk"
  constructor(private appService: AppService, private router: Router) {
    this.name = {
      value: "chleb"
    }
    this.quantity = undefined

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
    this.quantityOk = false
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
    this.quantityOk = false
    this.addOk = false
  }

  assignQuantity(value: any) {
    this.quantityOk = false
    this.addOk = false
    if (value.value >= 0) {
      if ((Number.isInteger(Number(value.value)) && this.type.value === "sztuk") || this.type.value != "sztuk") {
        this.quantity = value.value;
        this.product.quantity = value.value;
      } else {
        this.quantity = undefined;
        this.product.quantity = 1;
        this.quantityOk = true
      }
    }else {
      this.quantity = undefined;
      this.product.quantity = 1;
    }
  }

  assignTypeSelect(value: any) {
    this.quantityOk = false
    this.addOk = false
    this.selectedType = value
    this.type.value = this.selectedType;
    this.product.type = value;
  }
  ngOnInit(): void {
  }

  addProductToList() {
    this.quantityOk = false
    this.appService.addProductToList(this.product, localStorage.getItem("listName")).subscribe(
      () => {
        this.router.navigate(['/user-page'])
      },
      () => {
        this.addOk = true
      }
    );
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
