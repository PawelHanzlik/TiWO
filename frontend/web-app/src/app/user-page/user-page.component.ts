import { Component, OnInit } from '@angular/core';
import {ProductList} from "../dto/ProductList";
import {AppService} from "../app-service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit {

  lists : Array<ProductList> = []
  deleteOk : boolean;
  constructor(private appService: AppService, private router: Router) {
    this.deleteOk = false
  }

  ngOnInit(): void {
    this.displayLists()
  }

  displayLists(){
    this.appService.displayLists(localStorage.getItem("email")).subscribe(
      (response) => {
        console.log(response)
        this.lists = response
      }
    )
  }

  logout(){
    localStorage.removeItem("token")
  }

  addListName(listName : string){
    localStorage.setItem("listName", listName)
    localStorage.setItem("operation", "create")
    this.router.navigate(['/add-product'])
  }

  updateProduct(productId : bigint){
    localStorage.setItem("productId", String(productId))
    localStorage.setItem("operation", "update")
    this.router.navigate(['/add-product'])
  }
  addListId(listId : bigint){
    localStorage.setItem("listId", String(listId))
    localStorage.setItem("operation", "update")
    this.router.navigate(['/add-product-list'])
  }
  addNewList(){
    localStorage.setItem("operation", "create")
    this.router.navigate(['/add-product-list'])
  }

  async deleteList(listId : bigint){
    await this.appService.deleteList(listId).toPromise()
    this.displayLists()
  }

  async deleteProduct(productId : bigint){
    await this.appService.deleteProduct(productId).toPromise()
    this.displayLists()
  }
}
