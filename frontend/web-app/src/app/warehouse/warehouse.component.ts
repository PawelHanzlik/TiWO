import { Component, OnInit } from '@angular/core';
import {AppService} from "../app-service";
import {Product} from "../dto/Product";

@Component({
  selector: 'app-warehouse',
  templateUrl: './warehouse.component.html',
  styleUrls: ['./warehouse.component.css']
})
export class WarehouseComponent implements OnInit {

  products : Array<Product> = []
  quantity: Map<string, number | undefined>
  quantityOk : boolean
  mapa : Map<string, string>
  index : number | undefined
  constructor(private appService: AppService) {
    this.index = undefined
    this.quantity = new Map([
      ["drukarka" , undefined],
      ["wiertarka" , undefined],
      ["chleb" , undefined],
      ["maslo" , undefined],
      ["szynka" , undefined],
      ["ser" , undefined],
      ["ciastka" , undefined],
      ["cukier" , undefined],
      ["mleko" , undefined],
    ]);
    console.log(this.quantity)
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

  ngOnInit(): void {
    this.displayProducts()
  }

  displayProducts(){
    this.appService.displayProducts("testWarehouse").subscribe(
      (response) => {
        this.products = response
      }
    )
  }

  assignQuantity(value: any, name : string, i : number){
    this.index = i
    this.quantityOk = false
    if (value.value >= 0) {
      if ((Number.isInteger(Number(value.value)) && this.mapa.get(name) === "sztuk") || this.mapa.get(name) != "sztuk") {
        this.quantity.set(name,value.value)
      } else {
        this.quantity.set(name,undefined)
        this.quantityOk = true
      }
    }else {
      this.quantity.set(name,undefined)
    }
  }

  async addProduct(name : string){
    await this.appService.addProductToWarehouse(name, this.quantity.get(name)).toPromise()
    this.displayProducts()
  }
}
