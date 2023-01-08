import { Component, OnInit } from '@angular/core';
import {AppService} from "../app-service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  userLogin: loginData;
  password: loginData;

  constructor(private appService: AppService) {
    this.userLogin = {
      value: ""
    }
    this.password = {
      value: ""
    }
  }

  assignPassword(value: any) {
    this.password.value = value.value;
  }

  assignLogin(value: any) {
    this.userLogin.value = value.value;
  }

  public login(): void {
    this.appService.login(this.userLogin.value, this.password.value)
  }
  ngOnInit(): void {
  }

}

interface loginData{
  value: String;
}
