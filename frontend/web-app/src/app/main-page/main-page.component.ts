import { Component, OnInit } from '@angular/core';
import {AppService} from "../app-service";

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {

  constructor(private appService: AppService) { }

  ngOnInit(): void {
  }

  public generateData(): void {
    this.appService.generateData();
  }

}
