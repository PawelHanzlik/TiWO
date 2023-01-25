import {fakeAsync, TestBed} from '@angular/core/testing';

import {AppService} from './app-service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {environment} from "../environments/environment";
import {HttpClientModule} from "@angular/common/http";
import {Product} from "./add-product/add-product.component";

describe('AppServiceService', () => {
  let service: AppService;

  let httpMock: HttpTestingController;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports : [HttpClientModule, HttpClientTestingModule],
      providers : [AppService]
    });
    service = TestBed.inject(AppService);
    httpMock = TestBed.get(HttpTestingController)
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('when loginToken method called it should set data to local storage', () => {
    service.loginToken("test",{accessToken : "token"})
    expect(localStorage.getItem("token")).toEqual("token")
    expect(localStorage.getItem("email")).toEqual("test")
  });

  it('when login method called it should set data to local storage', () => {
    service.login("test","test").subscribe(response => {
      service.loginToken("test", {accessToken: "token"})
      expect(response).toBeTruthy();});
    const request = httpMock.expectOne({
      method: 'POST',
      url: environment.apiBaseUrl + '/tiwo/user/login'
    })

  });

  it('when register method called it should set data to local storage', () => {
     let user  : user =  {
      name: "",
      surname: "",
      email:"",
      lists: []
    }
    service.register(user,"test").subscribe(response => {
      expect(response).toBeTruthy();});
    const request = httpMock.expectOne({
      method: 'POST',
      url: environment.apiBaseUrl + '/tiwo/user/register?password=test'
    })

  });

  it('when displayLists method response was made',fakeAsync(() => {
    service.displayLists("test").subscribe(response => {
      expect(response).toBeTruthy();    });

    const request = httpMock.expectOne({
      method: 'GET',
      url: environment.apiBaseUrl + '/tiwo/user/getLists?email=test',
    })
  }));

  it('when addProduct method response was made',fakeAsync(() => {
    let product : Product = {
      name: "",
      quantity: 0,
      type:""
    }
    service.addProductToList(product,"test").subscribe(response => {
      expect(response).toBeTruthy();    });

    const request = httpMock.expectOne({
      method: 'POST',
      url: environment.apiBaseUrl + '/tiwo/product/addProduct?listName=test',
    })
  }));

  it('when updateProduct method response was made',fakeAsync(() => {
    let product : Product = {
      name: "",
      quantity: 0,
      type:""
    }
    service.updateProduct(product,"test").subscribe(response => {
      expect(response).toBeTruthy();    });

    const request = httpMock.expectOne({
      method: 'PUT',
      url: environment.apiBaseUrl + '/tiwo/product/updateProduct?productId=test',
    })
  }));

  it('when updateProductBought method response was made',fakeAsync(() => {
    service.cross(BigInt(1)).subscribe(response => {
      expect(response).toBeTruthy();    });

    const request = httpMock.expectOne({
      method: 'PUT',
      url: environment.apiBaseUrl + '/tiwo/product/updateProductBought?productId='+1,
    })
  }));

  it('when addList method response was made',fakeAsync(() => {
    let productList : ProductList= {
      name: "",
      dueTo: new Date(),
      description:"",
      products : []
    }
    service.addProductListToUser(productList,"test").subscribe(response => {
      expect(response).toBeTruthy();    });

    const request = httpMock.expectOne({
      method: 'POST',
      url: environment.apiBaseUrl + '/tiwo/productList/addProductList?userEmail=test',
    })
  }));

  it('when updateList method response was made',fakeAsync(() => {
    let productList : ProductList= {
      name: "",
      dueTo: new Date(),
      description:"",
      products : []
    }
    service.updateProductList(productList,"test").subscribe(response => {
      expect(response).toBeTruthy();    });

    const request = httpMock.expectOne({
      method: 'PUT',
      url: environment.apiBaseUrl + '/tiwo/productList/updateProductList?listId=test',
    })
  }));

  it('when deleteList method response was made',fakeAsync(() => {
    service.deleteList(BigInt(1)).subscribe(response => {
      expect(response).toBeTruthy();    });

    const request = httpMock.expectOne({
      method: 'DELETE',
      url: environment.apiBaseUrl + '/tiwo/productList/deleteProductList?listId='+1,
    })
  }));

  it('when deleteProduct method response was made',fakeAsync(() => {
    service.deleteProduct(BigInt(1)).subscribe(response => {
      expect(response).toBeTruthy();    });

    const request = httpMock.expectOne({
      method: 'DELETE',
      url: environment.apiBaseUrl + '/tiwo/product/deleteProduct?productId='+1,
    })
  }));
});
export  interface user{
  name: String;
  surname: String;
  email: String;
  lists: [];
}
interface ProductList{
  name : string
  dueTo : Date
  description : string
  products : []
}
