import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterComponent } from './register.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {Router} from "@angular/router";
import {AppService} from "../app-service";
import {Observable} from "rxjs";
import {HttpClientModule} from '@angular/common/http';
fdescribe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let routerMock = jasmine.createSpyObj<Router>("Router" , ["navigate"])
  let appServiceMock : jasmine.SpyObj<AppService>
  let user : user = {
    name: "",
    surname: "",
    email:"",
    lists: []
  }
  let password : "password"
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegisterComponent],
      imports : [HttpClientTestingModule, HttpClientModule],
      providers : [{provide : Router , useValue : routerMock} , {provide: AppService, useValue: appServiceMock}]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('When register was successful go to register-result component', () => {
      appServiceMock = jasmine.createSpyObj(['register'])
      appServiceMock.register.and.returnValue(new Observable<true>())
      expect(appServiceMock.register(user,password)).toEqual(new Observable<true>())

      appServiceMock.register(user,password).subscribe(
        () => {
          expect(routerMock.navigate).toHaveBeenCalledWith(["router-result"])
        }
      )
  });


});
interface user{
  name: String;
  surname: String;
  email: String;
  lists: [];
}
