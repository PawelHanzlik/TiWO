import {ComponentFixture, fakeAsync, TestBed} from '@angular/core/testing';

import {UserPageComponent} from './user-page.component';
import {Router} from "@angular/router";
import {AppService} from "../app-service";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {HttpClientModule} from "@angular/common/http";
import {Observable, of} from "rxjs";

class MockUserPageService{

  displayLists(userEmail : string | null) : Observable<any>{
    return  of(Array.of());
  }

  deleteList(listId : bigint) : Observable<any>{
    return  of(Array.of());
  }

  deleteProduct(productId : bigint) : Observable<any>{
    return  of(Array.of());
  }

  cross(productId : bigint) : Observable<any>{
    return  of(Array.of());
  }
}
describe('UserPageComponent', () => {
  let component: UserPageComponent;
  let fixture: ComponentFixture<UserPageComponent>;
  let routerMock = jasmine.createSpyObj<Router>("Router" , ["navigate"])
  let appServiceMock : jasmine.SpyObj<AppService>
  let mockUserPageService : MockUserPageService
  beforeEach(async () => {
    mockUserPageService = new MockUserPageService()
    await TestBed.configureTestingModule({
      declarations: [ UserPageComponent ],
      imports : [HttpClientTestingModule, HttpClientModule],
      providers : [{provide : Router , useValue : routerMock} , {provide: AppService, useValue: mockUserPageService}]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('Should create', () => {
    expect(component).toBeTruthy()
  });

  it('When add list name method called go to add-product', () => {
    spyOn(component, 'addListName').and.callThrough()
    component.addListName("test")
    expect(component.addListName).toHaveBeenCalled()
    expect(routerMock.navigate).toHaveBeenCalledWith(["/add-product"])
  });

  it('When update method called go to add-product', () => {
    spyOn(component, 'updateProduct').and.callThrough()
    component.updateProduct(BigInt(1))
    expect(component.updateProduct).toHaveBeenCalled()
    expect(routerMock.navigate).toHaveBeenCalledWith(["/add-product"])
  });

  it('When add list id method called go to add-product', () => {
    spyOn(component, 'addListId').and.callThrough()
    component.addListId(BigInt(1))
    expect(component.addListId).toHaveBeenCalled()
    expect(routerMock.navigate).toHaveBeenCalledWith(["/add-product-list"])
  });

  it('When add new list  method called go to add-product', () => {
    spyOn(component, 'addNewList').and.callThrough()
    component.addNewList()
    expect(component.addNewList).toHaveBeenCalled()
    expect(routerMock.navigate).toHaveBeenCalledWith(["/add-product-list"])
  });

  it('when displayLists called with success lists = lists', () => {
    component.displayLists()
    fixture.detectChanges()
    expect(component.lists).toEqual(Array.of())
  });

  it('when logout delete token', () => {
    localStorage.setItem("token","token")
    component.logout()
    fixture.detectChanges()
    expect(localStorage.getItem("token")).toEqual('')
  });

  it('when deleteList called with success displayLists()', fakeAsync(() => {
    component.deleteList(BigInt(1))
    fixture.detectChanges()
    spyOn(component, 'displayLists').and.callThrough()
    component.ngOnInit()
    expect(component.displayLists).toHaveBeenCalled()
  }));

  it('when deleteList called with success displayLists()', fakeAsync(() => {
    component.cross(BigInt(1))
    fixture.detectChanges()
    spyOn(component, 'displayLists').and.callThrough()
    component.ngOnInit()
    expect(component.displayLists).toHaveBeenCalled()
  }));

  it('when displayLists called with success displayLists()', fakeAsync(() => {
    component.deleteProduct(BigInt(1))
    fixture.detectChanges()
    spyOn(component, 'displayLists').and.callThrough()
    component.ngOnInit()
    expect(component.displayLists).toHaveBeenCalled()
  }));
});
