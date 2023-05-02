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
  constructor(private appService: AppService) { }

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

}
