import {Component, OnInit} from '@angular/core';
import {ProductList} from "../dto/ProductList";
import {AppService} from "../app-service";
import {Router} from "@angular/router";
import {User} from "../dto/User";

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit {

  lists : Array<ProductList> = []
  deleteOk : boolean
  user : User
  money : bigint
  moneyToAdd: bigint = BigInt(0)
  constructor(private appService: AppService, private router: Router) {
    this.deleteOk = false
    this.user = {
      id : BigInt(0),
      name : "",
      surname : "",
      email : "",
      password : "",
      money : BigInt(0),
      productLists : new Set()
    }
    this.money = BigInt(0)
  }

  ngOnInit(): void {
    this.displayLists()
    this.displayUser()
  }

  displayLists(){
    this.appService.displayLists(localStorage.getItem("email")).subscribe(
      (response) => {
        this.lists = response
      }
    )
  }

  displayUser(){
    this.appService.displayUser(localStorage.getItem("email")).subscribe(
      (response) => {
        this.user = response
      }
    )
  }

  async cross(productId : bigint){
    await this.appService.cross(productId).toPromise()
    this.displayLists()
    this.displayUser()
  }
  assignMoney(value: any){
    if (Number(value.value) >= 0) {
      this.moneyToAdd = BigInt(value.value);
    }else{
      this.moneyToAdd = BigInt(0);
    }
  }

  logout(){
    localStorage.setItem("token", "")
    this.router.navigate(['/login'])
  }

  addListName(listName : string){
    localStorage.setItem("listName", listName)
    localStorage.setItem("operation", "create")
    this.router.navigate(['/add-product'])
  }

  updateProduct(productId : bigint){
    localStorage.setItem("productId", String(productId))
    localStorage.setItem("operation", "update")
    this.router.navigate(['/update-product'])
  }
  addListId(listId : bigint){
    localStorage.setItem("listId", String(listId))
    localStorage.setItem("operation", "update")
    this.router.navigate(['/update-product-list'])
  }
  addNewList(){
    localStorage.setItem("operation", "create")
    this.router.navigate(['/add-product-list'])
  }

  async deleteList(listId : bigint){
    await this.appService.deleteList(listId).toPromise()
    this.displayLists()
    this.displayUser()
  }

  async deleteProduct(productId : bigint){
    await this.appService.deleteProduct(productId).toPromise()
    this.displayLists()
    this.displayUser()
  }

  async addMoney(){
    await this.appService.addMoney(this.user.email, this.moneyToAdd).toPromise()
    this.displayLists()
    this.displayUser()
  }

  async buyProduct(productId : bigint,productName : string, productQuantity : number, productBought : boolean){
    if(!productBought){
      await this.appService.buyProduct(productId, productName, productQuantity, localStorage.getItem("email")).toPromise()
      this.displayLists()
      this.displayUser()
    }
  }

}
