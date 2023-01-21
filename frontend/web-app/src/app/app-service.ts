import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../environments/environment";
import {mapTo, Observable, tap} from "rxjs";
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

  public register(user: user, password: String): Observable<any> {
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
}
interface user{
  name: String;
  surname: String;
  email: String;
  lists: [];
}
