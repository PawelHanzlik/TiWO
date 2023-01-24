import {ComponentFixture, TestBed} from '@angular/core/testing';

import {RegisterComponent, registerData} from './register.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {Router} from "@angular/router";
import {AppService} from "../app-service";
import {Observable, of, throwError} from "rxjs";
import {HttpClientModule} from '@angular/common/http';

class MockRegisterService{

  registerOk  = true
  user = {
    name: "",
    surname: "",
    email:"",
    lists: []
  }
  register(user : user, password : registerData) : Observable<any>{
    if(!this.registerOk) throwError({status: 404});
    return  of(this.registerOk);
  }
}
describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let routerMock = jasmine.createSpyObj<Router>("Router" , ["navigate"])
  let mockRegisterService : MockRegisterService
  let user : user = {
    name: "",
    surname: "",
    email:"",
    lists: []
  }
  let password : "password"
  beforeEach(async () => {
    mockRegisterService = new MockRegisterService()
    await TestBed.configureTestingModule({
      declarations: [ RegisterComponent],
      imports : [HttpClientTestingModule, HttpClientModule],
      providers : [{provide : Router , useValue : routerMock} , {provide: AppService, useValue: mockRegisterService}]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('when assign password then login has that value', () => {
    component.assignPassword({value : "test"})
    fixture.detectChanges()
    expect(component.password.value).toEqual("test")
  });

  it('when assign email then login has that value', () => {
    component.assignEmail({value : "test"})
    fixture.detectChanges()
    expect(component.email.value).toEqual("test")
  });

  it('when assign name then login has that value', () => {
    component.assignName({value : "test"})
    fixture.detectChanges()
    expect(component.name.value).toEqual("test")
  });

  it('when assign surname then login has that value', () => {
    component.assignSurname({value : "test"})
    fixture.detectChanges()
    expect(component.surname.value).toEqual("test")
  });

  it('when register successful go to register-result', () => {
    component.register()
    fixture.detectChanges()
    expect(routerMock.navigate).toHaveBeenCalledWith(["/register-result"])
  });

  it('when register not successful go to user-page', () => {
    mockRegisterService.registerOk = false
    spyOn(mockRegisterService, 'register').and.returnValue(throwError({status: 404}))
    component.register()
    fixture.detectChanges()
    expect(component.registerOk).toEqual(true)
  });
});
export  interface user{
  name: String;
  surname: String;
  email: String;
  lists: [];
}
