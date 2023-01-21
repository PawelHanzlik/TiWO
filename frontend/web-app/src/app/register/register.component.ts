import { Component, OnInit } from '@angular/core';
import {AppService} from "../app-service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  email: registerData;
  password: registerData;
  name: registerData;
  surname: registerData;
  registerOk: boolean
  user: user;
  constructor(private appService: AppService, private router: Router) {
    this.email = {
      value: ""
    }
    this.password = {
      value: ""
    }

    this.name = {
      value: ""
    }
    this.surname = {
      value: ""
    }
    this.user = {
      name: "",
      surname: "",
      email:"",
      lists: []
    }
    this.registerOk = false
  }

  assignPassword(value: any) {
    this.password.value = value.value;
  }

  assignEmail(value: any) {
    this.email.value = value.value;
    this.user.email = value.value;
  }

  assignName(value: any) {
    this.name.value = value.value;
    this.user.name = value.value;
  }

  assignSurname(value: any) {
    this.surname.value = value.value;
    this.user.surname = value.value;
  }
  ngOnInit(): void {
  }

  public register(): void {
    this.appService.register(this.user, this.password.value).subscribe(
      () => { this.router.navigate(['/register-result'])},
      () => {this.registerOk = true}
    );
  }
}
interface registerData{
  value: String;
}
interface user{
  name: String;
  surname: String;
  email: String;
  lists: [];
}
