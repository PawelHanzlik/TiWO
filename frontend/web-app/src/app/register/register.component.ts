import { Component, OnInit } from '@angular/core';
import {AppService} from "../app-service";

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

  user: user;
  constructor(private appService: AppService) {
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
    console.log(this.user)
    this.appService.register(this.user, this.password.value)
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
