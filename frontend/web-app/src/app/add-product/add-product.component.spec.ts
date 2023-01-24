import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AddProductComponent} from './add-product.component';
import {Router} from "@angular/router";
import {AppService} from "../app-service";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {HttpClientModule} from "@angular/common/http";
import {Observable, of, throwError} from "rxjs";
import {FormsModule} from "@angular/forms";

class MockAddProductService{

  addOk  = true
  product : Product = {
    name: "",
    quantity: 0,
    type:""
  }
  addProductToList(product : Product, listName : string | null) : Observable<any>{
    if(!this.addOk) throwError({status: 404});
    return  of(this.addOk);
  }

  updateProduct(product : Product, productId : string | null) : Observable<any>{
    if(!this.addOk) throwError({status: 404});
    return  of(this.addOk);
  }
}
describe('AddProductComponent', () => {
  let component: AddProductComponent;
  let fixture: ComponentFixture<AddProductComponent>;
  let routerMock = jasmine.createSpyObj<Router>("Router" , ["navigate"])
  let mockAddProductService : MockAddProductService
  beforeEach(async () => {
    mockAddProductService = new MockAddProductService()
    await TestBed.configureTestingModule({
      declarations: [ AddProductComponent ],
      imports : [HttpClientTestingModule, HttpClientModule, FormsModule],
      providers : [{provide : Router , useValue : routerMock} , {provide: AppService, useValue: mockAddProductService}]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddProductComponent);
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

  it('when assign name select then login has that value', () => {
    component.selectedName = "test"
    component.assignNameSelect({value : "test"})
    fixture.detectChanges()
    expect(component.name.value).toEqual("test")
  });

  it('when assign quality then login has that value', () => {
    component.assignQuantity({value : 1})
    fixture.detectChanges()
    expect(component.quantity).toEqual(1)
  });

  it('when assign type then login has that value', () => {
    component.assignType({value : "test"})
    fixture.detectChanges()
    expect(component.type.value).toEqual("test")
  });

  it('when assign type select then login has that value', () => {
    component.selectedType = "test"
    component.assignTypeSelect({value : "test"})
    fixture.detectChanges()
    expect(component.type.value).toEqual("test")
  });

  it('when addProductToList method invoked with success go to user-page', () => {
    localStorage.setItem("operation","create")
    component.addProductToList()
    fixture.detectChanges()
    expect(routerMock.navigate).toHaveBeenCalledWith(["/user-page"])
  });

  it('when addProductToList method invoked with unsuccess go to user-page', () => {
    mockAddProductService.addOk = false
    spyOn(mockAddProductService, 'addProductToList').and.returnValue(throwError({status: 404}))
    localStorage.setItem("operation","create")
    component.addProductToList()
    fixture.detectChanges()
    expect(component.addOk).toEqual(true)
  });

  it('when updateProduct method invoked with success go to user-page', () => {
    localStorage.setItem("operation","update")
    component.addProductToList()
    fixture.detectChanges()
    expect(routerMock.navigate).toHaveBeenCalledWith(["/user-page"])
  });

  it('when update method invoked with unsuccess go to user-page', () => {
    mockAddProductService.addOk = false
    spyOn(mockAddProductService, 'updateProduct').and.returnValue(throwError({status: 404}))
    localStorage.setItem("operation","update")
    component.addProductToList()
    fixture.detectChanges()
    expect(component.addOk).toEqual(true)
  });

});
interface Product{
  name : string
  quantity : number
  type : string
}
