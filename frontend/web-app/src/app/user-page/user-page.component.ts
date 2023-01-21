import { Component, OnInit } from '@angular/core';
import {ProductList} from "../dto/ProductList";
import {AppService} from "../app-service";

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit {

  lists : Array<ProductList> = []
  constructor(private appService: AppService) { }

  ngOnInit(): void {
    this.displayLists()
  }

  displayLists(){
    this.appService.displayLists(localStorage.getItem("email")).subscribe(
      (response) => {
        this.lists = response
        console.log(response)
      }
    )
  }
}
