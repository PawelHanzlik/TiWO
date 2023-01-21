import { Component, OnInit } from '@angular/core';
import {AppService} from "../app-service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  userLogin: loginData;
  password: loginData;
  loginOk: boolean
  loginResponse: string

  constructor(private appService: AppService, private router: Router) {

    this.userLogin = {
      value: ""
    }
    this.password = {
      value: ""
    }
    this.loginResponse = ""
    this.loginOk = false
  }

  assignPassword(value: any) {
    this.password.value = value.value;
  }

  assignLogin(value: any) {
    this.userLogin.value = value.value;
  }

  public login(): void {
    this.appService.login(this.userLogin.value, this.password.value).subscribe(
      () => { this.router.navigate(['/user-page'])},
      () => {this.loginOk = true}
    );
  }
  ngOnInit(): void {
  }

}

interface loginData{
  value: string;
}
