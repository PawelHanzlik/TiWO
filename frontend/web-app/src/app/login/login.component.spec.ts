import {ComponentFixture, TestBed} from '@angular/core/testing';

import {LoginComponent, loginData} from './login.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {HttpClientModule} from "@angular/common/http";
import {Router} from "@angular/router";
import {AppService} from "../app-service";
import {Observable, of, throwError} from "rxjs";

class MockLoginService{

  loginOk  = true
  login(email : loginData, password : loginData) : Observable<any>{
    if(!this.loginOk) throwError({status: 404});
    return  of(this.loginOk);
  }
}
describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let routerMock = jasmine.createSpyObj<Router>("Router" , ["navigate"])
  let loginServiceMock : MockLoginService
  beforeEach(async () => {
    loginServiceMock = new MockLoginService()
    await TestBed.configureTestingModule({
      declarations: [ LoginComponent ],
      imports : [HttpClientTestingModule, HttpClientModule],
      providers : [{provide : Router , useValue : routerMock} , {provide: AppService, useValue: loginServiceMock}]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('when login successful go to user-page', () => {
    component.login()
    fixture.detectChanges()
    expect(routerMock.navigate).toHaveBeenCalledWith(["/user-page"])
  });

  it('when login not successful go to user-page', () => {
    loginServiceMock.loginOk = false
    spyOn(loginServiceMock, 'login').and.returnValue(throwError({status: 404}))
    component.login()
    fixture.detectChanges()
    expect(component.loginOk).toEqual(true)
  });

  it('when assign login then login has that value', () => {
    component.assignLogin({value : "test"})
    fixture.detectChanges()
    expect(component.userLogin.value).toEqual("test")
  });

  it('when assign password then login has that value', () => {
    component.assignPassword({value : "test"})
    fixture.detectChanges()
    expect(component.password.value).toEqual("test")
  });
});
