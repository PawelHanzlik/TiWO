import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AddProductListComponent} from './add-product-list.component';
import {Router} from "@angular/router";
import {AppService} from "../app-service";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {HttpClientModule} from "@angular/common/http";
import {Observable, of, throwError} from "rxjs";

class MockAddProductListService{

  addOk  = true
  productList  : ProductList = {
    name: "",
    dueTo: new Date(),
    description:"",
    products : []
  }
  addProductListToUser(productList : ProductList, email : string | null) : Observable<any>{
    if(!this.addOk) throwError({status: 404});
    return  of(this.addOk);
  }

  updateProductList(productList : ProductList, listId : string | null) : Observable<any>{
    if(!this.addOk) throwError({status: 404});
    return  of(this.addOk);
  }
}
describe('AddProductListComponent', () => {
  let component: AddProductListComponent;
  let fixture: ComponentFixture<AddProductListComponent>;
  let routerMock = jasmine.createSpyObj<Router>("Router" , ["navigate"])
  let appServiceMock : jasmine.SpyObj<AppService>
  let mockAddProductListService : MockAddProductListService
  let productList : ProductList = {
    name: "",
    dueTo: new Date(),
    description:"",
    products : []
  }
  beforeEach(async () => {
    mockAddProductListService = new MockAddProductListService()
    await TestBed.configureTestingModule({
      declarations: [ AddProductListComponent ],
      imports : [HttpClientTestingModule, HttpClientModule],
      providers : [{provide : Router , useValue : routerMock} , {provide: AppService, useValue: mockAddProductListService}]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddProductListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('when assign name then login has that value', () => {
    component.assignName({value : "test"})
    fixture.detectChanges()
    expect(component.name.value).toEqual("test")
  });

  it('when assign dueTO then login has that value', () => {
    let data : Date = new Date()
    component.assignDueTo({value : data})
    fixture.detectChanges()
    expect(component.dueTo).toEqual(data)
  });

  it('when assign description then login has that value', () => {
    component.assignDescription({value : "test"})
    fixture.detectChanges()
    expect(component.description.value).toEqual("test")
  });

  it('when addProductListToUser method invoked with success go to user-page', () => {
    localStorage.setItem("operation","create")
    component.addProductListToUser()
    fixture.detectChanges()
    expect(routerMock.navigate).toHaveBeenCalledWith(["/user-page"])
  });

  it('when addProductListToUser method invoked with unsuccess go to user-page', () => {
    mockAddProductListService.addOk = false
    spyOn(mockAddProductListService, 'addProductListToUser').and.returnValue(throwError({status: 404}))
    localStorage.setItem("operation","create")
    component.addProductListToUser()
    fixture.detectChanges()
    expect(component.addOk).toEqual(true)
  });

  it('when updateProductList method invoked with success go to user-page', () => {
    localStorage.setItem("operation","update")
    component.addProductListToUser()
    fixture.detectChanges()
    expect(routerMock.navigate).toHaveBeenCalledWith(["/user-page"])
  });

  it('when updateProductList method invoked with unsuccess go to user-page', () => {
    mockAddProductListService.addOk = false
    spyOn(mockAddProductListService, 'updateProductList').and.returnValue(throwError({status: 404}))
    localStorage.setItem("operation","update")
    component.addProductListToUser()
    fixture.detectChanges()
    expect(component.addOk).toEqual(true)
  });
});
interface ProductList{
  name : string
  dueTo : Date
  description : string
  products : []
}
interface ProductList{
  name : string
  dueTo : Date
  description : string
  products : []
}
