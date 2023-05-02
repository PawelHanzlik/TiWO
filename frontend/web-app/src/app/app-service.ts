import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../environments/environment";
import {forkJoin, mapTo, Observable, tap} from "rxjs";
import {Token} from "./dto/Token";

@Injectable({
  providedIn: 'root'
})
export class AppService {

  private apiServerUrl = environment.apiBaseUrl;
  requestOptions: Object = {
    responseType: 'text'
  }
  response = ""
  constructor(private http: HttpClient) { }

  public loginToken(email: string, token : Token){
    localStorage.setItem("token", token.accessToken)
    localStorage.setItem("email", email)
  }
  public login(email: string, password: string): Observable<any>{
    return this.http.post<any>(`${this.apiServerUrl}/tiwo/user/login`,{
        "email" : email,
        "password" : password
    }).pipe(tap(token => this.loginToken(email, token)), mapTo(true))
  }

  public register(user: user, password: String ): Observable<any>{
    return this.http.post<Map<string, string>>(`${this.apiServerUrl}/tiwo/user/register?password=${password}`,{
      "name" : user.name,
      "surname" : user.surname,
      "email" : user.email,
      "productLists" : []
    })
  }

  public displayLists(userEmail : string | null) : Observable<any>{
    return this.http.get(`${this.apiServerUrl}/tiwo/user/getLists?email=${userEmail}`)
  }

  public displayUser(userEmail : string | null) : Observable<any>{
    return this.http.get(`${this.apiServerUrl}/tiwo/user/getUser?email=${userEmail}`)
  }

  public addProductToList(product : Product , listName : string | null) : Observable<any>{
    return this.http.post<Map<string, string>>(`${this.apiServerUrl}/tiwo/product/addProduct?listName=${listName}`,{
      "name" : product.name,
      "quantity" : product.quantity,
      "type" : product.type
    })
  }

  public updateProduct(product : Product , productId : string | null) : Observable<any>{
    return this.http.put<Map<string, string>>(`${this.apiServerUrl}/tiwo/product/updateProduct?productId=${productId}`,{
      "name" : product.name,
      "quantity" : product.quantity,
      "type" : product.type
    })
  }
  public addProductListToUser(productList : ProductList , email : string | null) : Observable<any>{
    return this.http.post<Map<string, string>>(`${this.apiServerUrl}/tiwo/productList/addProductList?userEmail=${email}`,{
      "name" : productList.name,
      "description" : productList.description,
      "dueTo" : productList.dueTo,
      "products" : []
    })
  }

  public updateProductList(productList : ProductList , listId : string | null) : Observable<any>{
    return this.http.put<Map<string, string>>(`${this.apiServerUrl}/tiwo/productList/updateProductList?listId=${listId}`,{
      "name" : productList.name,
      "description" : productList.description,
      "dueTo" : productList.dueTo,
      "products" : []
    })
  }

  public deleteList<T>(listId : bigint) : Observable<T>{
    return this.http.delete<T>(`${this.apiServerUrl}/tiwo/productList/deleteProductList?listId=${listId}`,{})
  }

  public deleteProduct<T>(productId : bigint) : Observable<T>{
    return this.http.delete<T>(`${this.apiServerUrl}/tiwo/product/deleteProduct?productId=${productId}`,{})
  }

  public cross<T>(productId : bigint) : Observable<T>{
    return this.http.put<T>(`${this.apiServerUrl}/tiwo/product/updateProductBought?productId=${productId}`,{})
  }

  public addMoney<T>(userEmail : string, money : bigint) : Observable<T>{
    return this.http.put<T>(`${this.apiServerUrl}/tiwo/user/addMoney?email=${userEmail}&money=${money}`,{})
  }

  public displayProducts(name : string | null) : Observable<any>{
    return this.http.get(`${this.apiServerUrl}/tiwo/warehouse/getProducts?name=${name}`)
  }

  public buyProduct<T>(productId : bigint, productName : string, productQuantity : number, userEmail : string | null) : Observable<any>{
    return forkJoin([this.http.put<T>(`${this.apiServerUrl}/tiwo/warehouse/buyProduct?productName=${productName}&productQuantity=${productQuantity}`,{}),
      this.http.put<T>(`${this.apiServerUrl}/tiwo/user/buyProduct?email=${userEmail}&productName=${productName}`,{}),
      this.http.put<T>(`${this.apiServerUrl}/tiwo/product/buyProduct?productId=${productId}`,{})])
  }
}
interface user{
  name: String;
  surname: String;
  email: String;
  lists: [];
}
interface Product{
  name : string
  quantity : number
  type : string
}
interface ProductList{
  name : string
  dueTo : Date
  description : string
  products : []
}
